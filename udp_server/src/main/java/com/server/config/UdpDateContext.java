package com.server.config;

import java.util.Map;
import java.util.TreeMap;

public class UdpDateContext {
    public static final Map<Integer, byte[]> canHashMap = new TreeMap<>();
    /**
     * 所有的客户PC机器
     */
    public enum UdpPcEnum {
        TH_01("TH", "/198.1.130.148", 2000, 90, (short) 62, true),//天禾（氟利昂机组）
        HW_01("HW", "/127.0.0.1", 2001, 178, (short) 62, true),//海王  //100
        HW_02("HW", "/198.1.130.102", 2000, 206, (short) 62, true),//海王
        HW_03("HW", "/198.1.130.104", 2000, 306, (short) 62, true),//海王
        ZL_01("ZL", "/198.1.130.122", 2000, 54, (short) 0, false),//自流循环
        //五室
       // WS_01("WS","198.1.130.30",5000,54,(short)0,false),
        WS_01("WS","127.0.0.1",5040,54,(short)0,false),
        //北京航天
        HT_01("HT", "/127.0.0.1", 2001, 34, (short) 0, false),//1#氧气 udp_index=6 198.1.130.130
        HT_02("HT", "/198.1.130.132", 2000, 34, (short) 0, false),//1#氢气 udp_index=7
        HT_03("HT", "/198.1.130.134", 2000, 34, (short) 0, false),//1#二氧化碳 udp_index=8
        HT_04("HT", "/198.1.130.136", 2000, 90, (short) 0, false),//采样分析仪 udp_index=9
        HT_05("HT", "/198.1.130.138", 2000, 34, (short) 0, false),//2#氧气 udp_index=10
        HT_06("HT", "/198.1.130.140", 2000, 34, (short) 0, false),//2#氢气 udp_index=11
        HT_07("HT", "/198.1.130.142", 2000, 34, (short) 0, false),//2#二氧化碳 udp_index=12
        HT_08("HT", "/198.1.130.144", 2000, 34, (short) 0, false),//3#氧气 udp_index=13
        HT_09("HT", "/198.1.130.146", 2000, 34, (short) 0, false),//3#二氧化碳 udp_index=14
        HT_10("HT", "/198.1.130.148", 2000, 34, (short) 0, false),//氟利昂浓度 udp_index=15
        HT_11("HT", "/198.1.130.150", 2000, 34, (short) 0, false);//一氧化碳 udp_index=16
       // LL_01("LL", "/198.1.130.42", 5170, 54, (short) 0, false);//流量计量仪
        //所属厂家
        private String developers;
        //ip地址
        private String ip;
        //端口
        private Integer port;
        //接收报文整体长度
        private Integer length;
        //发送报文整体长度
        private Short sendLength;
        //是否返回指令报文
        private Boolean isReturn;

        UdpPcEnum() {
        }

        UdpPcEnum(String developers, String ip, Integer port, Integer length, Short sendLength, Boolean isReturn) {
            this.developers = developers;
            this.ip = ip;
            this.port = port;
            this.length = length;
            this.sendLength = sendLength;
            this.isReturn = isReturn;
        }

        public Boolean getReturn() {
            return isReturn;
        }

        public void setReturn(Boolean aReturn) {
            isReturn = aReturn;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        public Short getSendLength() {
            return sendLength;
        }

        public void setSendLength(Short sendLength) {
            this.sendLength = sendLength;
        }

        public String getDevelopers() {
            return developers;
        }

        public void setDevelopers(String developers) {
            this.developers = developers;
        }
    }

    /**
     * 所有的命名空间
     */
    public enum NameSpaceEnum {
        MAIN("/main"),
        COOLING("/cooling"),
        DIVE("/dive"),
        FUEL("/fuel"),
        ENVIRONMENT("/environment"),
        TRIMMING("/trimming"),
        FIRE("/fire"),
        CIRCUIT("/circuit"),
        FLOW("/flow"),
        FIRELIST("/firelist");

        private String namespace;

        NameSpaceEnum(String namespace) {
            this.namespace = namespace;
        }

        public String getNamespace() {
            return this.namespace;
        }

        public void setNamespace(String namespace) {
            this.namespace = namespace;
        }

        public String toString() {
            return super.toString().toLowerCase();
        }
    }
    public enum CanStatus{
        OK(1),
        ERROR(0);
        private int status;

        CanStatus(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
