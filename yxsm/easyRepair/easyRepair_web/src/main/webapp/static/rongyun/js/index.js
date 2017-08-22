var demo = angular.module("demo", ["RongWebIMWidget"]);

demo.controller("main", ["$scope", "WebIMWidget", "$http", function($scope, WebIMWidget, $http) {

    $scope.targetType = 1; //1：私聊 更多会话类型查看http://www.rongcloud.cn/docs/api/js/global.html#ConversationType
    
    //对方用户的id
    $scope.targetId = 'aa';
    
    //融云初始化
    WebIMWidget.init({
        appkey: "bmdehs6pdw0ss",
        token: "T0EAr02cSZ816H/fm/POi8vVz5jKjrGTX+4hbq436lxI+TXY9FPwG5cKBWb4Qi4YAoL8QbzPDDo=",
        displayConversationList: true,
        displayMinButton:true,//是否显示最小化图标
        style:{
            left:3,
            bottom:3,
            width:430
        },
        onSuccess: function(id) {
            $scope.user = id;
            document.title = '用户：' + id;
            console.log('连接成功：' + id);
        },
        onError: function(error) {
            console.log('连接失败：' + error);
        }
    });


//侧栏显示对方的名称
    WebIMWidget.setUserInfoProvider(function(targetId, obj) {
//  	name: "用户：" + targetId;
        obj.onSuccess({
            name: "用户：" + targetId
        });
    });
    
//侧栏显示群组的名称
    WebIMWidget.setGroupInfoProvider(function(targetId, obj){
        obj.onSuccess({
            name:'群组：' + targetId
        });
    })

//右边聊天内容
    $scope.setconversation = function() {
        if (!!$scope.targetId) {
            WebIMWidget.setConversation(Number($scope.targetType), $scope.targetId, "用户：" + $scope.targetId);
            WebIMWidget.show();
        }
    }

    $scope.show = function() {
        WebIMWidget.show();
    };

    $scope.hidden = function() {
        WebIMWidget.hidden();
    };

//  WebIMWidget.show();//显示聊天窗口


//     示例：获取 userinfo.json 中数据，根据 targetId 获取对应用户信息
       WebIMWidget.setUserInfoProvider(function(targetId,obj){
           $http({
             url:"userinfo.json"
           }).success(function(rep){
             var user;
             rep.userlist.forEach(function(item){
               if(item.id==targetId){
                 user=item;
               }
             })
             if(user){
               obj.onSuccess({id:user.id,name:user.name,portraitUri:user.portraitUri});
             }else{
               obj.onSuccess({id:targetId,name:"用户："+targetId});
             }
           })
       });

    // 示例：获取 online.json 中数据，根据传入用户 id 数组获取对应在线状态
       WebIMWidget.setOnlineStatusProvider(function(arr, obj) {
           $http({
               url: "online.json"
           }).success(function(rep) {
               obj.onSuccess(rep.data);
           })
       });
       
}
	

]);



