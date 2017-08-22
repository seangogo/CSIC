package com.server.service.impl;

import com.server.config.UdpDateContext;
import com.server.service.DevicePropertyService;
import com.server.service.OriginalMessageService;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

import static com.server.netty.udp.ClientHandler.byteBufHashMap;

/**
 * 处理报文逻辑
 */
@SuppressWarnings({"SpringAutowiredFieldsWarningInspection", "SpringJavaAutowiringInspection"})
@Service(value = "originalMessageService")
@Transactional(readOnly = true)
public class OriginalMessageServiceImpl implements OriginalMessageService {
    private Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private DevicePropertyService devicePropertyService;

    /**
     * 验证PC
     *
     * @param datagramPacket 报文客户端
     * @return 对应PC机
     */
    @Override
    public UdpDateContext.UdpPcEnum checkPc(DatagramPacket datagramPacket) {
        for (UdpDateContext.UdpPcEnum udpPcEnum : UdpDateContext.UdpPcEnum.values()) {
            if (udpPcEnum.getIp().equals(datagramPacket.sender().getAddress().toString()) &&
                    udpPcEnum.getPort().equals(datagramPacket.sender().getPort())) {
                logger.info("PC:" + udpPcEnum);
                return udpPcEnum;
            }
        }
        return null;
    }

    /**
     * 将接收到的报文内部校验
     *
     * @param udpPcEnum pc
     * @param byteBuf   报文
     * @return
     */
    @Override
    public boolean validation(UdpDateContext.UdpPcEnum udpPcEnum, ByteBuf byteBuf) {
        //接收的报文写入日志
        logger.info("receiveUdp:" + ByteBufUtil.prettyHexDump(byteBuf));
        if (byteBuf.getShort(0) != 0x55aa ||                              //a.起始符
                byteBuf.getShort(byteBuf.readableBytes() - 2) != 0xff00 ||//b.结束符
                byteBuf.getShort(2) != 0x0840 ||                           //c.报文属性
                byteBuf.getShort(8) != udpPcEnum.getLength() ||
                udpPcEnum.getLength() != byteBuf.readableBytes()) {         //d.报文总长度
            return false;
        }
        byte[] bytes = new byte[4];
        byteBuf.getBytes(4, bytes);                                        //TODO e.报文类型(需求确认后加入校验)
        getSendDate(byteBuf);                                              //f.报文发送时间
        return validationDeCode(byteBuf);                                  //g.校验和
    }

    /**
     * 封装数据并推送到指定页面
     *
     * @param udpPcEnum 计算机套接字信息
     * @param byteBuf   整条报文
     */
    @Override
    public void dataSend(UdpDateContext.UdpPcEnum udpPcEnum, ByteBuf byteBuf) {
        byteBuf.readBytes(14);
        ByteBuf contentBuf = byteBuf.readBytes(byteBuf.readableBytes() - 4);
        //不是第一次获取该地址的报文并且报文主体内容相同
        if (byteBufHashMap.containsKey(udpPcEnum.getIp()) && byteBufHashMap.containsValue(contentBuf)) {
            logger.info("udp is same,return");
            return;
        }
        devicePropertyService.sendContent(contentBuf, udpPcEnum);//备份byteBuf的读下标并发送报文
    }

    /**
     * 解密效验和
     *
     * @param byteBuf 整体报文
     * @return 效验和是否正确
     */
    @Override
    public boolean validationDeCode(ByteBuf byteBuf) {
        short sum = 0;
        while (byteBuf.readableBytes() > 0) {
            sum += byteBuf.readShort();

        }
        return (sum & 0xffff) == 0xffff;
    }

    /**
     * 加密效验和
     *
     * @param byteBuf 置零效验和的数据
     */
    @Override
    public void validationEnCode(ByteBuf byteBuf) {
        //ByteBuf byteBufHead=byteBuf.readBytes(byteBuf.readableBytes()-4);
        byteBuf.markWriterIndex();//备份写下标
        byteBuf.markReaderIndex();//备份读下标
        byteBuf.writeShort(0x0000);//校验和替换为0000
        byteBuf.writeShort(0xff00);//结束标识符
        short sum = 0;
        while (byteBuf.readableBytes() > 0) {
            sum += byteBuf.readShort();
        }
        byteBuf.resetWriterIndex();//重置写
        byteBuf.resetReaderIndex();//重置读
        byteBuf.writeShort(~sum & '\uffff');//算出和反码
        byteBuf.writeShort(0xff00);//结束标识符
    }


    /**
     * 封装当前时间到4字节的bytes[]
     *
     * @return 时间字节
     */
    @Override
    public byte[] getDateBytes() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int[] date = {calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND),
        };
        logger.info("sendTime:" + new Date());
        byte[] bytes = {(byte) (((byte) date[0] << 2) + ((byte) date[1] >> 6)),
                (byte) ((byte) ((byte) date[1] << 2) + ((byte) date[2] >>> 6)),
                (byte) ((date[2] << 2) + (date[3] >>> 8)),
                (byte) (date[3] - (date[3] >> 8 << 8))};
        return bytes;
    }

    /**
     * 获取发送报文时间
     *
     * @param byteBuf 解析的报文
     */
    @Override
    public void getSendDate(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() > 4) {
            logger.info((byteBuf.getUnsignedByte(10) >>> 2) + " " +//时
                    (byteBuf.getUnsignedByte(10) >>> 6 << 6) + (byteBuf.getUnsignedByte(11) >>> 2) + " " +//分
                    (((byteBuf.getUnsignedByte(11) - (byteBuf.getUnsignedByte(11) >> 2 << 2)) << 6) + (byteBuf.getUnsignedByte(12) >> 2)) + "：" +//秒
                    (((byteBuf.getUnsignedByte(12) - (byteBuf.getUnsignedByte(12) >> 2 << 2)) << 8) + byteBuf.getUnsignedByte(13))//毫米
            );
        }
        logger.info("time is error");
    }

    /**
     * 封装报文头部信息
     *
     * @param sendLength 要发送报文的整体长度
     * @return 封装好头部的buf
     */
    @Override
    public ByteBuf packagingHead(short sendLength) {
        ByteBuf byteBuf = Unpooled.copyShort(0x55aa);
        byteBuf.writeShort(0x0840);//报文属性
        byteBuf.writeByte(0x01000001);//1.信息范围（01 系统内信息）2.系统归属地(000010 一体化指挥信息系统)(index:5)
        byteBuf.writeByte(0x00000010);//3.发送方标识字段（index:6）
        byteBuf.writeByte(0x0010000);//4.信息类类别字段(控制类别)00100自定义00(index:7)
        byteBuf.writeByte(0x00000001);//5.自定义 000 6.标识某系统发送的报文(index:8)
        byteBuf.writeShort(sendLength);//长度（index:10）
        return byteBuf.writeBytes(getDateBytes());//时戳
    }

}
