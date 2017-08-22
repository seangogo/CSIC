package com.server;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;

/**
 * Created by sean on 2017/6/30.
 */
public class TestDll1Service {


    public interface TestDll1 extends Library {
        TestDll1 INSTANCE = (TestDll1) Native.loadLibrary("D:\\udp_server\\src\\test\\java\\com\\server\\can_c\\ControlCAN", TestDll1.class);
        int VCI_StartCAN(int DevType, int DevIndex, int CANIndex);
        //复位CAN
        int  VCI_ResetCAN(int DevType, int DevIndex, int CANIndex);
        //打开设备
        int VCI_OpenDevice(int DevType, int DevIndex, int CANIndex);
        //关闭设备
        int VCI_CloseDevice(int DevType, int DevIndex, int CANIndex);
        //读取某一路CAN的状态
        int  VCI_ReadCanStatus(int DevType, int  DevIndex, int CANIndex,
                               StructObj.PVCI_CAN_STATUS.ByReference status);

        //  此函数用以获取设备的相应参数
        int VCI_GetReference(int DevType, int DevIndex, int CANIndex,
                             int RefType, Pointer pData);
        //初始化设备
        int VCI_InitCAN(int DevType, int DevIndex, int CANIndex, StructObj.VCI_INIT_CONFIG.ByReference vci_init_config);
        //获取数据
        int VCI_Receive(int DevType, int DevIndex, int CANIndex,StructObj._VCI_CAN_OBJ.ByReference vci_can_obj,long len,int wait);
        //读取错误码
        int VCI_ReadErrInfo(int DevType, int DevIndex, int CANIndex,StructObj.VCI_ERR_INFO.ByReference vci_err);
        //获取CAN通道的接受接受缓冲区，接受到但尚未被读取的帧的数量
        NativeLong VCI_GetReceiveNum(int DevType, int DevIndex, int CANIndex);
        //清空缓存区
        int VCI_ClearBuffer(int DevType, int DevIndex, int CANIndex);



    }
    public TestDll1Service() {
        // TODO Auto-generated constructor stub
    }
    public static void main(String[] args) {
        // TODO Auto-generated method stub
       // IntByReference v1=new IntByReference(16);
       // IntByReference v2=new IntByReference(0);
      //  IntByReference v3=new IntByReference(1);
        //打开设备
        if (TestDll1.INSTANCE.VCI_OpenDevice(16,0,0)==0){
            System.out.println("设备开启失败");
            return;
        }
        StructObj.VCI_INIT_CONFIG.ByReference config=new StructObj.VCI_INIT_CONFIG.ByReference();

      // StructObj.PVCI_CAN_STATUS.ByReference status=new StructObj.PVCI_CAN_STATUS.ByReference();
       // System.out.println("状态："+TestDll1.INSTANCE.VCI_ReadCanStatus(16,0,0,status));
  //      System.out.println(statusResult);
        config.AccCode = new NativeLong (0);
        config.AccMask = new NativeLong (0xFFFFFFFF);
      //  config.Reserved =new NativeLong (3435973836l);
        config.Filter = 0x01; //Buffer.from([0x0])//滤波方式。 =1 表示单滤波， =0 表示双滤波
        config.Mode = 0x00; //Buffer.from([0x0])
        config.Timing0 =0x03; //Buffer.from([0x01])//0x01 250赫兹 0x03 125 赫兹
        config.Timing1 = 0x1c; //Buffer.from([0x1c])
        if (TestDll1.INSTANCE.VCI_InitCAN(16,0,0, config) != 1) {
            System.out.println("初始化CAN失败!");
            TestDll1.INSTANCE.VCI_CloseDevice(16, 0,0);
            return;
        }
        if (TestDll1.INSTANCE.VCI_StartCAN(16, 0, 0) == 1) {
            System.out.println("启动can成功!");
        } else {
            System.out.println("启动can失败!");
            return;
        }
       // int resetResult=TestDll1.INSTANCE.VCI_ResetCAN(16,0,0);
      //  int clearBuffer=TestDll1.INSTANCE.VCI_ClearBuffer(16,0,0);
      //  System.out.println(clearBuffer);
    //    System.out.println("复位："+resetResult);

       // System.out.println(TestDll1.INSTANCE.VCI_GetReceiveNum(16,0,0));
        StructObj._VCI_CAN_OBJ.ByReference vci_can_obj=new StructObj._VCI_CAN_OBJ.ByReference();
        StructObj.VCI_ERR_INFO.ByReference vci_err_info=new StructObj.VCI_ERR_INFO.ByReference();
        int len = 0;
        int od = 0;
        while (true) {
            if (TestDll1.INSTANCE.VCI_GetReceiveNum(16,0,0).longValue()<=0){
                continue;
            }
            System.out.println("当前缓存数量："+TestDll1.INSTANCE.VCI_GetReceiveNum(16,0,0));
            len = TestDll1.INSTANCE.VCI_Receive(16, 0, 0, vci_can_obj, 1, 2000);
            if (len <= 0) {
                System.out.println(len);
                //注意：如果没有读到数据则必须调用此函数来读取出当前的错误码，
                //千万不能省略这一步（即使你可能不想知道错误码是什么）
                System.out.println(TestDll1.INSTANCE.VCI_ReadErrInfo(16, 0, 0, vci_err_info));
                //continue;
            }
            System.out.println("len:"+len);
            System.out.println((vci_can_obj.ID));
            System.out.println(ByteBufUtil.prettyHexDump(Unpooled.copyInt(vci_can_obj.ID)));
            System.out.println(ByteBufUtil.prettyHexDump(Unpooled.copiedBuffer(vci_can_obj.Data)));
          //  int resetResult=TestDll1.INSTANCE.VCI_ResetCAN(16,0,0);
        //    System.out.println("复位："+resetResult);
            // dest[frameinfo.data]();
        }
        //此函数用以启动 CAN 卡的某一个 CAN 通道。有多个 CAN 通道时，需要多次调用此函数用以启动 CAN 卡的某一个 CAN 通道。有多个 CAN 通道时，需要多次调用
        //System.out.println("开启某一路CAN："+TestDll1.INSTANCE.VCI_StartCAN(16,1,0));
        //复位CAN
        //System.out.println("复位CAN："+TestDll1.INSTANCE.VCI_ResetCAN(16,1,0));


        //


        //关闭设备
        //int closeDevice=TestDll1.INSTANCE.VCI_CloseDevice(16,0);
       //System.out.println("closeDevice:"+closeDevice);
        //初始化设备

    //

       //TestDll1.INSTANCE.VCI_GetReference(16,0,0,Pointer.NULL);
            // System.out.println(v5);
      /*  int v4=TestDll1.INSTANCE.VCI_StartCAN(16,0,0);
        System.out.println("startCAN"+v4);
        StructObj._VCI_CAN_OBJ.ByReference vci_can_obj=new StructObj._VCI_CAN_OBJ.ByReference();
        StructObj.VCI_ERR_INFO.ByReference vci_err_info=new StructObj.VCI_ERR_INFO.ByReference();
        for (;;){
            long len=TestDll1.INSTANCE.VCI_Receive(16,0,0,vci_can_obj,1,2000);
            System.out.println("receive:"+len);
            if (len<=0){
                int errCode=TestDll1.INSTANCE.VCI_ReadErrInfo(16,0,1,vci_err_info);
                System.out.println("errCode:"+errCode);
                System.out.println(vci_err_info.Passive_ErrData.length);
                System.out.println(vci_err_info.ArLost_ErrData);
            }
            System.out.println("ID:"+vci_can_obj.ID);
            //ByteBufUtil.prettyHexDump(Unpooled.copyInt(vci_can_obj.ID));
            System.out.println("DATa:"+vci_can_obj.Data.toString());
        }*/

        /*if (canm.VCI_OpenDevice(m_devtype, index, 0) != STATUS_OK) {
            process.send("打开设备失败!");
            return;
        }*/
    }



}
