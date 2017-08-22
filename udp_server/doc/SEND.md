d1.发送报文前台发送对象属性结构
   private String nameSpace;
   a.页面对应客户端命名空间
   目前存在的：
           MAIN("/main"),
           COOLING("/cooling"),
           DIVE("/dive"),
           FUEL("/fuel"),
           ENVIRONMENT("/environment"),
           TRIMMING("/trimming"),
           FIRE("/fire"),
           CIRCUIT("/circuit"),
           FLOW("/flow")
   注：命名空间必须带以"/"
   例：nameSpace: '/main'

   b.指令要发往的厂商
   private String developers;
   目前存在的：
          "TH" //天禾（溴化锂机组）
          "HW" //海王
          "ZL" //自流循环
   注：双字母都必须大写
   例：developers: 'HW'

   c.指令类型
   private String msgType;
   目前存在的：
              "0" //界面指令
              "1" //CAN指令
   注：目前暂无业务需求挂钩，暂留
   例：msgType: '0'

   d.消息内容
   private Integer msgContent;
   目前存在的：
              "HW"共71个指令: 0<msgContent<72
              "TH" 共2个指令：0<msgContent<3
   注：具体指令与数字编号对应关系参照各厂商指令表
   例：msgContent: 5
2.前端发送指令调用js代码
   例：
   //名称空间为"/main"的主页面，往海王服务器发送编号为2的界面指令
   var jsonObject = {nameSpace: '/main',developers: 'HW',
                        msgType: '0',msgContent: 2};
    socket.emit('eventData', jsonObject);

