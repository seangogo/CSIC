package com.server.netty.can;

import com.server.config.UdpDateContext;
import com.sun.jna.NativeLong;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static com.server.config.UdpDateContext.canHashMap;

/**
 * Created by Administrator on 2017/7/7.
 */
public class CanDevice implements Runnable {
    private Log logger = LogFactory.getLog(this.getClass());
    private int devType;
    private int devIndex;
    private int canIndex;
    private byte fiter;
    private byte Timing0;
    private byte Timing1;

    public CanDevice() {
    }

    public CanDevice(int devType, int devIndex, int canIndex, byte fiter, byte timing0, byte timing1) {
        this.devType = devType;
        this.devIndex = devIndex;
        this.canIndex = canIndex;
        this.fiter = fiter;
        Timing0 = timing0;
        Timing1 = timing1;
    }

    @Override
    public void run() {
        //打开第一个can设备
        if (VBFunction.INSTANCE.VCI_OpenDevice(this.devType, this.devIndex, this.canIndex) ==
                UdpDateContext.CanStatus.ERROR.getStatus()) {
            logger.error("device can0 start error");
            return;
        }
        //初始化CAN  结构体
        StructObj.VCI_INIT_CONFIG.ByReference config = new StructObj.VCI_INIT_CONFIG.ByReference();
        config.AccCode = new NativeLong(0);
        config.AccMask = new NativeLong(0xFFFFFFFF);
        config.Filter = this.fiter; //Buffer.from([0x0])//滤波方式。 =1 表示单滤波， =0 表示双滤波
        config.Mode = 0x00; //Buffer.from([0x0])
        config.Timing0 = this.Timing0;//0x03; //Buffer.from([0x01])//0x01 250赫兹 0x03 125 赫兹
        config.Timing1 = this.Timing1;///0x1c; //Buffer.from([0x1c])
        //  config.Reserved =new NativeLong (3435973836l);//备用
        if (VBFunction.INSTANCE.VCI_InitCAN(16, 0, 0, config) == UdpDateContext.CanStatus.ERROR.getStatus()) {
            logger.error("device can0 init error !");
            if (VBFunction.INSTANCE.VCI_CloseDevice(16, 0, 0) == UdpDateContext.CanStatus.OK.getStatus()) {
                logger.info("device can0 close");
            }
            return;
        }
        if (VBFunction.INSTANCE.VCI_StartCAN(this.devType, this.devIndex, this.canIndex) ==
                UdpDateContext.CanStatus.OK.getStatus()) {
            logger.info("启动can成功!");
        } else {
            logger.error("启动can失败!");
            return;
        }
        StructObj._VCI_CAN_OBJ.ByReference vci_can_obj = new StructObj._VCI_CAN_OBJ.ByReference();
        StructObj.VCI_ERR_INFO.ByReference vci_err_info = new StructObj.VCI_ERR_INFO.ByReference();
        int len;
        while (true) {
            //  System.out.println("缓存数：" + VBFunction.INSTANCE.VCI_GetReceiveNum(this.devType, this.devIndex, this.canIndex).longValue());
            if (VBFunction.INSTANCE.VCI_GetReceiveNum(this.devType, this.devIndex, this.canIndex).longValue() <= 0) {
                continue;
            }
            len = VBFunction.INSTANCE.VCI_Receive(this.devType, this.devIndex, this.canIndex, vci_can_obj, 1, 2000);
            if (len <= 0) {
                System.out.println(len);
                //注意：如果没有读到数据则必须调用此函数来读取出当前的错误码，
                //千万不能省略这一步（即使你可能不想知道错误码是什么）
                System.out.println(VBFunction.INSTANCE.VCI_ReadErrInfo(this.devType, this.devIndex, this.canIndex, vci_err_info));
                continue;
            }
            if (canHashMap.containsKey(vci_can_obj.ID) && canHashMap.containsValue(vci_can_obj.Data)) {
                return;
            }
            canHashMap.put(vci_can_obj.ID, vci_can_obj.Data);
            // ByteBuf byteBuf = Unpooled.copyInt(1);//加入头
            //for (Map.Entry<Integer, byte[]> entry : canHashMap.entrySet()) {
            //    byteBuf.writeBytes(entry.getValdue());
            //}
            //System.out.println(ByteBufUtil.prettyHexDump(byteBuf));*/
            //int resetResult=TestDll1.INSTANCE.VCI_ResetCAN(16,0,0);
            //System.out.println("复位："+resetResult);
        }
    }

}
