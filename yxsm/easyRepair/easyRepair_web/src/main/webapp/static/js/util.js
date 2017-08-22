(function (win) {
    var wjy = {
        arr: [],
        navigation: function (item, attrs) {
            if (wjy.isArray(attrs)) {
                if (attrs.length > 1) {
                    for (var i = 0; i < attrs.length; i++) {
                        var o = item[attrs[i]];
                        wjy.arr = attrs.slice(i + 1);
                        //console.log("o: "+JSON.stringify(o)+"   |||   "+"i: "+i+"      |||     "+"attrs: "+attrs);
                        return wjy.navigation(o, wjy.arr);
                    }
                } else {
                    return item[attrs[0]];
                }
            } else {
                return item[attrs];
            }
        },
        isArray: function (o) {
            return Object.prototype.toString.call(o) == '[object Array]';
        }
    };
    win.util = wjy;
    return util;
})(window)
