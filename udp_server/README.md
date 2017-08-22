  //A服务器
    @Value("${serviceProperties.inetAddressA.ip}")
    private String aip;
    @Value("${serviceProperties.inetAddressA.length}")
    private int alength;
    @Value("${serviceProperties.inetAddressA.port}")
    private int aport;
    //B服务器
    @Value("${serviceProperties.inetAddressB.ip}")
    private String bip;
    @Value("${serviceProperties.inetAddressB.length}")
    private int blength;
    @Value("${serviceProperties.inetAddressB.port}")
    private int bport;
    //C服务器
    @Value("${serviceProperties.inetAddressC.ip}")
    private String cip;
    @Value("${serviceProperties.inetAddressC.length}")
    private int clength;
    @Value("${serviceProperties.inetAddressC.port}")
    private int cport;
    //报文验证长度
    @Value("${serviceProperties.checklength}")
    private int checkLength;

    /**
         * 验证报文整体长度
         *
         * @param byteBuf 整体的报文
         * @return 是否符合
         */
        @Override
        public boolean checkLength(ByteBuf byteBuf) {
            int readableBytes = byteBuf.readableBytes() - checkLength;
            return readableBytes == alength || readableBytes == blength || readableBytes == clength;
        }


        /**
             * 检验IP地址和端口号
             *
             * @param datagramPacket netty包装过的套接字
             * @return 是否符合对应的ip 地址和端口号
             */
            @Override
            public boolean checkIpAndPort(DatagramPacket datagramPacket) {
                String ip = datagramPacket.sender().getAddress().toString();
                int port = datagramPacket.sender().getPort();
                return (aip.equals(ip) && aport == port) ||
                        (bip.equals(ip) && bport == port) || (cip.equals(ip) && cport == port);
            }


             /**
                 * 检验报文长度
                 * @param byteBuf 整条报文
                 * @return 是否属于规范的报文
                 */
                boolean checkLength(ByteBuf byteBuf);
                /**
                 * 验证IP地址和端口号
                 */
                boolean checkIpAndPort(DatagramPacket datagramPacket);
         for (; ; ) {
                    int a = byteBuf.readUnsignedShort();
                    System.out.println(~a);
                    System.out.println(a);
                    sum += ~a;
                    if (byteBuf.readableBytes() < 2) {
                        break;
                    }
                }
                System.out.println(sum);
                byteBuf.resetReaderIndex();




                Map<String, Object> keyVal = new ConcurrentHashMap<>();
                        Iterator<Map<String, Object>> it = deviceProperties.iterator();
                        while (it.hasNext()) {
                            Map<String, Object> deviceProperty = it.next();
                            int sort = (int) deviceProperty.get("sort");
                            if (sort <= 64) {//数字量
                                keyVal.put(deviceProperty.get("pk").toString(), allNumByte[sort]);
                            } else {//模拟量
                                keyVal.put(deviceProperty.get("pk").toString(), simulateFloat[sort - 65]);
                            }
                            if (!it.hasNext() || !it.next().containsValue(deviceProperty.get("dk"))) {
                                Map<String, Object> map = new ConcurrentHashMap();
                                map.putAll(keyVal);
                                String[] clientIds = deviceProperty.get("clientId").toString().split(",");
                                for (String s : clientIds) {
                                    if (data.containsKey(s)) {
                                        data.get(s).put(deviceProperty.get("dk").toString(), map);
                                    }
                                }
                                if (it.hasNext()) {
                                    keyVal.clear();
                                }
                            }
                        }


    //查询并缓存所有的UDP报文
        @Cacheable(value = "propertyAll")
        @Query(value = "select d from DeviceProperty as d where  d.readSort is not null " +
                "and d.udpServerIndex is not null order by d.udpServerIndex ,d.readSort")
        List<DeviceProperty> findAllAndEhcache();
       記錄發送信息代碼


       @Entity
       @Table(name = "message_record")
       //@DynamicUpdate(false)动态更新 默认true
       public class MessageRecord extends IdEntity {
           @Column(nullable = false,updatable = false)
           //原始报文
           private String message;

           //类型 0：接受 1:发送
           private Integer type;

           public String getMessage() {
               return message;
           }

           public void setMessage(String message) {
               this.message = message;
           }

           public Integer getType() {
               return type;
           }

           public void setType(Integer type) {
               this.type = type;
           }
       }



       package com.server.dao;

       import org.springframework.data.jpa.repository.JpaRepository;
       import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

       public interface MessageRecordDao extends JpaRepository<MessageRecord, Long>, JpaSpecificationExecutor<MessageRecord> {

       }



       package com.server.service;


       /**
        * Created by sean on 2017/4/17.
        */
       public interface MessageRecordService {
           void save(MessageRecord messageRecord) throws InterruptedException;
       }



    package com.server.service.impl;

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Service("messageRecordServiceImpl")
    @Transactional(readOnly = true)
    public class MessageRecordServiceImpl implements MessageRecordService {
        @Autowired
        private MessageRecordDao messageRecordDao;

        @Override
        @Transactional
        public void save(MessageRecord messageRecord) throws InterruptedException {
            messageRecordDao.save(messageRecord);
            long end = System.currentTimeMillis();
        }
    }
 //netty 編碼 解碼器
 package com.server.netty.buffer;

 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.socket.DatagramPacket;
 import io.netty.handler.codec.MessageToMessageDecoder;

 import java.util.List;

 /**
  * Created by admin on 2017/4/23.
  */
 public class UdpMessageToByteDecode extends MessageToMessageDecoder<DatagramPacket> {
     /*在out中添加了msg的bytebuf之后，注意调用msg的retain方法，防止msg中的bytebuf提前释放抛出异常
 这样写了之后，丢失了DatagramPacket中的sender，这样在后续的处理器中，无法直接向来源发送消息，所以这种方式基本只试用于客户端
     */
     @Override
     protected void decode(ChannelHandlerContext channelHandlerContext, DatagramPacket msg, List<Object> out) throws Exception {
         out.add (msg.content());
         //不释放消息msg.retain();
     }
 }





 package com.server.netty.buffer;

 import io.netty.buffer.ByteBuf;
 import io.netty.channel.ChannelHandlerContext;
 import io.netty.channel.socket.DatagramPacket;
 import io.netty.handler.codec.MessageToMessageEncoder;
 import io.netty.util.AttributeKey;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;

 import java.net.InetSocketAddress;
 import java.util.List;

 /**首先从原始编码器获得bytebuf，从channel的attribute中获取目标服务器地址，然后组装成最终发送的DatagramPacket。
  * 这里要注意的是：
  目标地址为了能在整个连接上下文中共享，需要保存在channel中，而不是context中
  发送的bytebuf仍然需要调用retain来防止提前释放抛出异常Created by admin on 2017/4/23.
  */
 public class UdpByteToMessageEncode extends MessageToMessageEncoder<ByteBuf> {
     private static Log log = LogFactory.getLog(UdpByteToMessageEncode.class);
     public static final AttributeKey<InetSocketAddress> TARGET_ADDRESS = AttributeKey.valueOf("TARGET_ADDRESS");

     @Override
     protected void encode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
         InetSocketAddress ip = ctx.channel().attr(TARGET_ADDRESS).get();
         log.error("no server ip："+ip);
         if (ip == null) {
             log.error("no server ip");
             return;
         }
         DatagramPacket packet = new DatagramPacket(msg,ip);
         //不释放消息 msg.retain();
         out.add(packet);
     }
 }
 //接口包裝類
package com.server.domain;

/**
 * http请求返回的最外层对象
 * Created by sean
 * 2017-01-21 13:34
 */
public class Result<T> {

    /** 错误码. */
    private Integer code;

    /** 提示信息. */
    private String msg;

    /** 具体的内容. */
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}





 package com.server.utils.restUtils;

 import com.server.domain.Result;


 public class ResultUtil {

     public static Result success(Object object) {
         Result result = new Result();
         result.setCode(0);
         result.setMsg("成功");
         result.setData(object);
         return result;
     }

     public static Result success() {
         return success(null);
     }

     public static Result error(Integer code, String msg) {
         Result result = new Result();
         result.setCode(code);
         result.setMsg(msg);
         return result;
     }
 }
//工具類
package com.server.utils.byteUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by sean on 2017/4/20.
 */
public class ByteUtil {

    /**
     * 将byte转换为一个长度为8的byte数组，数组每个值代表bit
     */
    public static byte[] getBooleanArray(byte bs[]) {
        byte[] array = new byte[64];
        for (int j=7;j>=0;j--){
            byte b=bs[j];
            for (int i = 7; i >= 0; i--) {
                array[j*8+i] = (byte)(b & 1);
                b = (byte) (b >> 1);
            }
        }
        return array;
    }


    /**
     * 得到本机IP
     */
    private static String getIpAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        return address.getHostAddress();
    }
    /**
     * byte转16进制
     */
    public static String byteToHex(byte b) {
        int i = b & 0xFF;
        return Integer.toHexString(i);
    }
    /**
     * byte[]转int
     */
    public static int bytesToInt(byte[] bytes) {
        return (int) ((((bytes[3] & 0xff) << 24)
                | ((bytes[2] & 0xff) << 16)
                | ((bytes[1] & 0xff) << 8) | ((bytes[0] & 0xff) << 0)));
    }

    /**
     * int 转byte[]
     * @param res
     * @return
     */
    public static byte[] int2byte(int res) {
        byte[] targets = new byte[2];

        targets[0] = (byte) (res & 0xff);// 最低位
        targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
       // targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
      //  targets[3] = (byte) (res >>> 24);// 最高位,无符号右移。
        return targets;
    }
    /**
     * byte[]转short
     */
    public static short bytesToShort(byte[] bytes) {
        return (short) (((bytes[1] << 8) | bytes[0] & 0xff));
    }
    /**
     * 把byte转为字符串的bit
     */
    public static String byteToBit(byte b) {
        return ""
                + (byte) ((b >> 7) & 0x1) + (byte) ((b >> 6) & 0x1)
                + (byte) ((b >> 5) & 0x1) + (byte) ((b >> 4) & 0x1)
                + (byte) ((b >> 3) & 0x1) + (byte) ((b >> 2) & 0x1)
                + (byte) ((b >> 1) & 0x1) + (byte) ((b >> 0) & 0x1);
    }
    /**
     * 二进制字符串转byte
     */
    public static byte decodeBinaryString(String byteStr) {
        int re, len;
        if (null == byteStr) {
            return 0;
        }
        len = byteStr.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {// 8 bit处理
            if (byteStr.charAt(0) == '0') {// 正数
                re = Integer.parseInt(byteStr, 2);
            } else {// 负数
                re = Integer.parseInt(byteStr, 2) - 256;
            }
        } else {// 4 bit处理
            re = Integer.parseInt(byteStr, 2);
        }
        return (byte) re;
    }
    /*  可以在代码中使用二进制字面量常量来验证一个规范文档, 比如一个针对假设的8位微处理器的模拟器:
     public State decodeInstruction(int instruction, State state) {
       if ((instruction & 0b11100000) == 0b00000000) {
          final int register = instruction & 0b00001111;
          switch (instruction & 0b11110000) {
            case 0b00000000: return state.nop();
            case 0b00010000: return state.copyAccumTo(register);
            case 0b00100000: return state.addToAccum(register);
            case 0b00110000: return state.subFromAccum(register);
            case 0b01000000: return state.multiplyAccumBy(register);
            case 0b01010000: return state.divideAccumBy(register);
            case 0b01100000: return state.setAccumFrom(register);
            case 0b01110000: return state.returnFromCall();
            default: throw new IllegalArgumentException();
          }
       } else {
          final int address = instruction & 0b00011111;
          switch (instruction & 0b11100000) {
            case 0b00100000: return state.jumpTo(address);
            case 0b01000000: return state.jumpIfAccumZeroTo(address);
            case 0b01000000: return state.jumpIfAccumNonzeroTo(address);
            case 0b01100000: return state.setAccumFromMemory(address);
            case 0b10100000: return state.writeAccumToMemory(address);
            case 0b11000000: return state.callTo(address);
            default: throw new IllegalArgumentException();
          }
       }
     }
         可以使用二进制字面量增强位图的可读性:
     public static final short[] HAPPY_FACE = {
       (short)0b0000011111100000,
       (short)0b0000100000010000,
       (short)0b0001000000001000,
       (short)0b0010000000000100,
       (short)0b0100000000000010,
       (short)0b1000011001100001,
       (short)0b1000011001100001,
       (short)0b1000000000000001,
       (short)0b1000000000000001,
       (short)0b1001000000001001,
       (short)0b1000100000010001,
       (short)0b0100011111100010,
       (short)0b0010000000000100,
       (short)0b0001000000001000,
       (short)0b0000100000010000,
       (short)0b0000011111100000
     }*/

}

//高低位工具類

package com.server.utils.byteUtils;

/**
 * Created by sean on 2017/4/19.
 */
public class HLUtil {
    public static void shortToByte_LH(short shortVal, byte[] b, int offset) {
        b[0 + offset] = (byte) (shortVal & 0xff);
        b[1 + offset] = (byte) (shortVal >> 8 & 0xff);
    }

    public static short byteToShort_HL(byte[] b, int offset) {
        short result;
        result = (short) ((((b[offset + 1]) << 8) & 0xff00 | b[offset] & 0x00ff));
        return result;
    }

    public static void intToByte_LH(int intVal, byte[] b, int offset) {
        b[0 + offset] = (byte) (intVal & 0xff);
        b[1 + offset] = (byte) (intVal >> 8 & 0xff);
        b[2 + offset] = (byte) (intVal >> 16 & 0xff);
        b[3 + offset] = (byte) (intVal >> 24 & 0xff);
    }

    public static int byteToInt_HL(byte[] b, int offset) {
        int result;
        result = (((b[3 + offset] & 0x00ff) << 24) & 0xff000000)
                | (((b[2 + offset] & 0x00ff) << 16) & 0x00ff0000)
                | (((b[1 + offset] & 0x00ff) << 8) & 0x0000ff00)
                | ((b[0 + offset] & 0x00ff));
        return result;
    }
    //上下文對象
       private static ApplicationContext applicationContext;

        public static void setApplicationContext(ApplicationContext context) {
            applicationContext = context;
        }

        public static ApplicationContext getContext() {
            return applicationContext;
        }
    // MVC
    package com.server.config;

    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.Ordered;
    import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
    import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

    /**
     * Created by sean on 2017/4/18.
     */
    @Configuration
    public class MvcConfigurer extends WebMvcConfigurerAdapter {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/error").setViewName("error.html");
            registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        }

        @Override
        public void configurePathMatch(PathMatchConfigurer configurer) {
            super.configurePathMatch(configurer);
            configurer.setUseSuffixPatternMatch(false);
        }
    }
}
//切面
package com.server.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class HttpAspect {

    private final static Logger logger = LoggerFactory.getLogger(HttpAspect.class);


    @Pointcut("execution(public * com.imooc.controller.*.*(..))")
    public void log() {
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //url
        logger.info("url={}", request.getRequestURL());

        //method
        logger.info("method={}", request.getMethod());

        //ip
        logger.info("ip={}", request.getRemoteAddr());

        //类方法
        logger.info("class_method={}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //参数
        logger.info("args={}", joinPoint.getArgs());
    }

    @After("log()")
    public void doAfter() {
        logger.info("222222222222");
    }

    @AfterReturning(returning = "object", pointcut = "log()")
    public void doAfterReturning(Object object) {
        logger.info("response={}", object.toString());
    }
        Float f1=5.5f;
        System.out.println(Integer.toHexString(Float.floatToIntBits(f1)))


    OriginalMessageService originalMessageService = (OriginalMessageService) SpringContextUtils.getBean("originalMessageService");
    originalMessageService.packagingDateAndSend(messageInfo);






            /**
             * 发送报文
             *
             * @param contentBuf 报文主体
             */
            void sendContent(ByteBuf contentBuf, UdpDateContext.UdpPcEnum udpPcEnum);



             /**
                 * 封装要发送指令的头部
                 *
                 * @param messageInfo 前台数据
                 */
                @Override
                public void packagingDate(MessageInfo messageInfo) {
                    //获得要发送的目的地服务器和报文长度
                    short sendLength = 0;
                    List<UdpDateContext.UdpPcEnum> udpPcEnumList = new ArrayList<>();
                    for (UdpDateContext.UdpPcEnum udpPcEnum : UdpDateContext.UdpPcEnum.values()) {
                        if (udpPcEnum.getDevelopers().equals(messageInfo.getDevelopers())) {
                            udpPcEnumList.add(udpPcEnum);
                            sendLength = (short) (udpPcEnum.getSendLength() - 22);
                        }
                    }
                    ByteBuf byteBuf = Unpooled.copyShort(0x55aa);//2
                    byteBuf = packagingHead(byteBuf, sendLength);
                    //因为都是偶数位，填充字段为空
                    byteBuf = packagingInstruct(byteBuf, messageInfo.getMsgContent(), sendLength);
                    byteBuf.writeFloat(messageInfo.getReal());
                    //模拟量
                    validationEnCode(byteBuf);
                    logger.info(ByteBufUtil.prettyHexDump(byteBuf));
                    Collection<ChannelUser> channelUsers = byteBufHashMap.values();
                    for (ChannelUser channelUser : channelUsers) {
                        if (udpPcEnumList.contains(channelUser.getDeveloper())) {
                            logger.info("ip: " + channelUser.getDatagramPacket().sender().getAddress().toString());
                            logger.info("send to" + channelUser.getDeveloper());
                            try {
                                DatagramSocket sendSocket = new DatagramSocket();
                                sendSocket.send(new java.net.DatagramPacket(byteBuf.array(), byteBuf.readableBytes(), channelUser.getDatagramPacket().sender()));
                                sendSocket.close();
                            } catch (SocketException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
          /**
             * 封装指令
             */
            ByteBuf packagingInstruct(ByteBuf byteBuf, int index, int sendLength);



             /**
                 * 封装发送报文的主体
                 *
                 * @param byteBuf    封装好头部的报文
                 * @param index      发送指令的下标
                 * @param sendLength 报文主体的字节长度
                 * @return 封装好头部主体的报文
                 */
                @Override
                public ByteBuf packagingInstruct(ByteBuf byteBuf, int index, int sendLength) {
                    byte[] bytes = new byte[sendLength];
                    for (int i = 0; i < sendLength; i++) {
                        if (index / 8 == i) {
                            int a = index % 8;
                            bytes[i] = (byte) (0x00000001 << (8 - a));
                        } else {
                            bytes[i] = 0x00000000;
                        }
                    }
                    return byteBuf.writeBytes(bytes);
                }


                /**
                     * 封装发送指令报文的数据
                     *
                     * @param messageInfo 前台发送数据对象
                     */
                    void packagingDate(MessageInfo messageInfo);






           public static void main(String[] args) {
                   ByteBuf byteBuf=Unpooled.copyShort(0xbcaf);
                   byteBuf.writeShort(0x406d);
                   byteBuf.writeShort(0x5f78);
                /*   for (int i=0;i<177;i++){
                       byteBuf.writeShort(0);
                   }*/
                   short sum = 0;
                   while (byteBuf.readableBytes() > 0) {
                       sum += byteBuf.readShort();
                   }
                   byteBuf.resetWriterIndex();//重置写
                   byteBuf.resetReaderIndex();//重置读
                   byteBuf.writeShort(~sum & '\uffff');//算出和反码
                   System.out.println(~sum & '\uffff');
                   int f1=~sum & '\uffff';
                   System.out.println(Integer.toHexString(f1));
                    System.out.println(Integer.toHexString(f1));
               }
}




  //不是第一次获取该地址的报文并且报文主体内容相同
            if ((byteBufHashMap.containsKey(udpPcEnum.getIp()) && !byteBufHashMap.containsValue(contentBuf))
                    || !byteBufHashMap.containsKey(udpPcEnum.getIp())) {

            }
            // devicePropertyService.sendUdp(ctx,datagramPacket,udpPcEnum);




            /**
                 * 检验报文来源有效性
                 *
                 * @param datagramPacket 报文
                 * @return 对应的PC
                 @Override public UdpDateContext.UdpPcEnum checkUdpPC(DatagramPacket datagramPacket) {
                 for (UdpDateContext.UdpPcEnum udpPcEnum : UdpDateContext.UdpPcEnum.values()) {
                 if (udpPcEnum.getIp().equals(datagramPacket.sender().getAddress().toString()) &&
                 udpPcEnum.getPort().equals(datagramPacket.sender().getPort()) &&
                 udpPcEnum.getLength().equals(datagramPacket.content().readableBytes())) {
                 return udpPcEnum;
                 }
                 }
                 return null;
                 }*/


                  try {
                             logger.info(originalMessageService.validationAndSend(byteBuf, ctx, datagramPacket)
                                     ? "udpContent through" : "udpContent not pass");
                         } catch (JsonProcessingException e) {
                             e.printStackTrace();
                         }




                         /**
                              * 效验并发送报文
                              *
                              * @param byteBuf        整体报文
                              * @param ctx            管道
                              * @param datagramPacket 套接字
                              * @return 是否通过验证并发送
                              */
                             @Override
                             @Transactional
                             public boolean validationAndSend(ByteBuf byteBuf, ChannelHandlerContext ctx, DatagramPacket datagramPacket) {
                                 byteBuf.markReaderIndex();//拆分报文，备份读下标
                                 //效验报文格式
                                 if (validationUdp(byteBuf.resetReaderIndex())) {
                                     byteBuf.resetReaderIndex();
                                     byteBuf.readBytes(14);
                                     ByteBuf contentBuf = byteBuf.readBytes(byteBuf.readableBytes() - 4);
                                     logger.info(!byteBufHashMap.containsKey(datagramPacket.sender().getAddress().toString())
                                             ? "First" : "More than once");
                                     logger.info((byteBufHashMap.containsKey(datagramPacket.sender().getAddress().toString())
                                             && !byteBufHashMap.get(datagramPacket.sender().getAddress().toString()).equals(contentBuf)) ? "udp is different" : "udp is same");
                                     //获得报文对应厂商信息
                                     //   UdpDateContext.UdpPcEnum udpPcEnum = findUdpPcEnum(datagramPacket, contentBuf.resetReaderIndex());
                                     if (udpPcEnum == null) {
                                         return false;
                                     }

                                     devicePropertyService.sendContent(contentBuf.markReaderIndex(), udpPcEnum);//备份byteBuf的读下标并发送报文
                                     /*//每接受一台服务器udp,发送对应客户端指令返回对应厂商的所有最新指令
                                     OriginalMessage originalMessage = new OriginalMessage();
                                     originalMessage.setResourceIp(datagramPacket.sender().getAddress().toString());//IP
                                     originalMessage.setOriginalCode(ByteBufUtil.getBytes(datagramPacket.content()));//整条报文内容
                                     originalMessage.setCreateTime(new Date());//接收时间
                                     originalMessageDao.save(originalMessage);//保存报文*/
                                     return true;
                                 }
                                 return false;
                             }



                              /**
                                  * 效验报文数据完整信息和协议信息
                                  *
                                  * @param byteBuf 整条报文
                                  * @return 是否通过验证
                                  */
                                 @Override
                                 public boolean validationUdp(ByteBuf byteBuf) {
                                     if (byteBuf.getShort(0) != 0x55aa ||                              //a.起始符
                                             byteBuf.getShort(byteBuf.readableBytes() - 2) != 0xff00 ||//b.结束符
                                             byteBuf.getShort(2) != 0x0840) {                          //c.报文属性
                                         return false;
                                     }
                                     byte[] bytes = new byte[4];
                                     byteBuf.getBytes(4, bytes);
                                     getSendDate(byteBuf);//e.
                                     return validationDeCode(byteBuf); //a.效验和測試已通過，方便測試暫時關閉
                                 }
                                     /**
                                      * 效验报文
                                      *
                                      * @param headBuf 整条报文
                                      * @return 效验udp报文头部和尾部内容
                                      */
                                     boolean validationUdp(ByteBuf headBuf);




    /**
     * 效验并发送报文
     *
     * @param byteBuf        原始整条报文
     * @param ctx            管道
     * @param datagramPacket 计算机套接字信息
     * @return 是否通过验证并发送
     */
    boolean validationAndSend(ByteBuf byteBuf, ChannelHandlerContext ctx, DatagramPacket datagramPacket) throws JsonProcessingException;




package com.server.netty.udp.pojo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.socket.DatagramPacket;

/**
 * udp客户端缓存对象
 */
public class ChannelUser {
    //对应的计算机信息
    private DatagramPacket datagramPacket;
    //报文主体
    private ByteBuf byteBuf;

    public ChannelUser() {
    }

    public ChannelUser(DatagramPacket datagramPacket, ByteBuf byteBuf) {
        this.datagramPacket = datagramPacket;
        this.byteBuf = byteBuf;
    }


    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }

    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public void setByteBuf(ByteBuf byteBuf) {
        this.byteBuf = byteBuf;
    }
}
