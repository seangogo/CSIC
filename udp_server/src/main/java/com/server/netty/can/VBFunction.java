package com.server.netty.can;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;

/**
 * Created by Administrator on 2017/7/7.
 */
public interface VBFunction extends Library {
    VBFunction INSTANCE = (VBFunction) Native.loadLibrary("D:\\udp_server\\src\\test\\java\\com\\server\\can_c\\ControlCAN", VBFunction.class);
    //打开设备
    int VCI_OpenDevice(int v1, int v2, int v3);
    //开启设备
    int VCI_StartCAN(int v1,
                     int v2, int v3);
    //复位CAN
    int  VCI_ResetCAN(int DevType, int DevIndex, int CANIndex);
    //关闭设备
    int VCI_CloseDevice(int DevType, int DevIndex, int CANIndex);
    //读取某一路CAN的状态
    int  VCI_ReadCanStatus(int DevType, int  DevIndex, int CANIndex,
                           StructObj.PVCI_CAN_STATUS.ByReference status);

    //获取CAN通道的接受接受缓冲区，接受到但尚未被读取的帧的数量
    NativeLong VCI_GetReceiveNum(int DevType, int DevIndex, int CANIndex);

    //  此函数用以获取设备的相应参数
    int VCI_GetReference(int DevType, int DevIndex, int CANIndex,
                         int RefType, Pointer pData);
    //初始化设备
    int VCI_InitCAN(int DevType, int DevIndex, int CANIndex, StructObj.VCI_INIT_CONFIG.ByReference vci_init_config);
    //获取数据
    int VCI_Receive(int DevType, int DevIndex, int CANIndex,StructObj._VCI_CAN_OBJ.ByReference vci_can_obj,long len,int wait);
    //读取错误码
    int VCI_ReadErrInfo(int DevType, int DevIndex, int CANIndex,StructObj.VCI_ERR_INFO.ByReference vci_err);
}
