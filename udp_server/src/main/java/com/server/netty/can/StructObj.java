package com.server.netty.can;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

/**
 * 定义结构体
 */
public class StructObj {
    /*//2.定义CAN信息帧的数据类型。
var VCI_CAN_OBJ = Struct({
  'ID': 'uint',
  'TimeStamp': 'uint',
  'TimeFlag': 'byte',
  'SendType': 'byte',
  'RemoteFlag': 'byte',
  'ExternFlag': 'byte',
  'DataLen': 'byte',
  'Data': refArray('byte', 8),
  'Reserved': refArray('byte'
  “【, 3)
});
//3.定义CAN控制器状态的数据类型。
var VCI_CAN_STATUS = Struct({
  'ErrInterrupt': 'uchar',
  'regMode': 'uchar',
  'regStatus': 'uchar',
  'regALCapture': 'uchar',
  'regECCapture': 'uchar',
  'regEWLimit': 'uchar',
  'regRECounter': 'uchar',
  'regTECounter': 'uchar',
  'Reserved': 'uint32'
});
//1.ZLGCAN系列接口卡信息的数据类型。
var VCI_BOARD_INFO = Struct({
  'hw_Version': 'ushort',
  'fw_Version': 'ushort',
  'dr_Version': 'ushort',
  'in_Version': 'ushort',
  'irq_Num': 'ushort',
  'can_Num': 'byte',
  'str_Serial_Num': refArray('byte', 20),
  'str_hw_Type': refArray('byte', 40),
  'Reserved': refArray('ushort', 4)
});

//5.定义初始化CAN的数据类型
var VCI_INIT_CONFIG = Struct({
  3
  'AccMask': 'uint32',
  'Reserved': 'uint32',
  'Filter': 'byte',
  'Timing0': 'byte',
  'Timing1': 'byte',
  'Mode': 'byte',
});
//4.定义错误信息的数据类型。
var VCI_ERR_INFO = Struct({
  'ErrCode': 'uint',
  'Passive_ErrData': refArray('byte', 3),
  'ArLost_ErrData': 'byte'
});*/
    public static class VCI_INIT_CONFIG extends Structure {

        public NativeLong AccCode;
        public NativeLong  AccMask;
        public NativeLong  Reserved;

        public byte  Filter;
        public byte  Timing0;
        public byte  Timing1;
        public byte  Mode;
        public static class ByReference extends VCI_INIT_CONFIG implements Structure.ByReference{
        }

        public static class ByValue extends VCI_INIT_CONFIG implements Structure.ByValue{
        }
        @Override
        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"AccCode", "AccMask", "Reserved", "Filter","Timing0","Timing1","Mode"});
        }
    }

    public static class VCI_ERR_INFO extends Structure {

        public NativeLong ErrCode;
        public byte[] Passive_ErrData=new byte[3];
        public byte  ArLost_ErrData;

        public static class ByReference extends VCI_ERR_INFO implements Structure.ByReference{
        }

        public static class ByValue extends VCI_ERR_INFO implements Structure.ByValue{
        }
        @Override
        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"ErrCode", "Passive_ErrData", "ArLost_ErrData"});
        }
    }

    public static class  _VCI_CAN_OBJ extends Structure {

        public int ID;
        public int TimeStamp;
        public byte TimeFlag;
        public byte SendType;
        public byte RemoteFlag;
        public byte ExternFlag;
        public byte DataLen;
        public byte[] Data=new byte[8];
        public byte[] Reserved=new byte[3];

        public static class ByReference extends _VCI_CAN_OBJ implements Structure.ByReference{
        }

        public static class ByValue extends _VCI_CAN_OBJ implements Structure.ByValue{
        }
        @Override
        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"ID", "TimeStamp", "TimeFlag","SendType","RemoteFlag","ExternFlag","DataLen","Data","Reserved"});
        }
    }

    public static class  PVCI_CAN_STATUS extends Structure {
        short ErrInterrupt;
        short regMode;
        short regStatus;
        short regALCapture;
        short regECCapture;
        short regEWLimit;
        short regRECounter;
        short regTECounter;
        int Reserved;

        public static class ByReference extends PVCI_CAN_STATUS implements Structure.ByReference{
        }

        public static class ByValue extends PVCI_CAN_STATUS implements Structure.ByValue{
        }
        @Override
        protected List getFieldOrder() {
            return Arrays.asList(new String[]{"ErrInterrupt", "regMode", "regStatus","regALCapture","regECCapture","regEWLimit","regRECounter","regTECounter","Reserved"});
         //   return Arrays.asList(new String[]{"ErrInterrupt", "Reserved", "regALCapture","regECCapture","regEWLimit","regMode","regRECounter","regStatus","regTECounter"});
        }
    }



}
