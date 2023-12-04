$(function () {
    "use strict";
    connect_method.connectCheckDom();

    $(document).on('click', '#connect', function () {
        var roomid = $('#room_id').val();
        if (roomid !== null && roomid !== "") {
            $('#connect').attr('class', 'btn btn-info');
            $('#connect').text('连接中');
            $.when(connect_method.connectRoom(roomid)).done(function (data) {
                if (data.code === "200") {
                    if (data.result) {
                        showMessage("连接房间成功!","success",2)
                        // 延迟1秒后执行页面刷新
                        setTimeout(function(){
                            window.location.reload();
                        }, 1000);
                    } else {
                        showMessage("连接房间失败!","danger",3)
                    }
                }
                connect_method.connectCheckDom();
            });
        } else {
            showMessage("连接房间-还没输入房间号!","danger",3)
        }
    });

    $(document).on('click', '#disconnect', function () {
        if(connect_method.disconnectRoom()){
            showMessage("断开房间成功!","success",2)
        }else{
            showMessage("断开房间失败,请刷新重试!","danger",3)
        }
        connect_method.connectCheckDom()
    });

});

const connect_method = {
    connectRoom: function (roomid) {
        "use strict";
        var deferred = $.Deferred();
        $.ajax({
            url: './connectRoom',
            data: {
                roomid: roomid,
            },
            async: true,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                deferred.resolve(data);
            }
        });
        return deferred.promise();
    },
    disconnectRoom: function () {
        "use strict";
        var flag = false;
        $.ajax({
            url: './disconnectRoom',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code === "200") {
                    flag = data.result;
                }
            }
        });
        return flag;
    },
    connectCheck: function () {
        "use strict";
        var flag = false;
        $.ajax({
            url: './connectCheck',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code === "200") {
                    flag = data.result;
                }
            }
        });
        return flag
    },
    connectCheckDom: function (){
        // check connect
        if (connect_method.connectCheck()) {
            $('#connect').attr('id', 'disconnect');
            $('#disconnect').attr('class', 'btn btn-danger');
            $('#disconnect').text('断连');
        } else {
            $('#disconnect').attr('id', 'connect');
            $('#connect').attr('class', 'btn btn-primary');
            $('#connect').text('连接');
        }

    },
};
