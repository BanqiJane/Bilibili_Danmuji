$(function() {
	"use strict";

	$(document).on('click', '#connect', function() {
		if (!$('#connect').hasClass('disabled')) {
			$('#connect').addClass('disabled');
			var roomid = $('#room_id').val();
			if (roomid !== null && roomid !== "") {
				$('.notice-message').html("<span>连接中<img src='../img/loading-1.gif'></span>");
				$.when(method.connectRoom(roomid)).done(function(data) {
					if (data.code === "200") {
						if (data.result) {
							$('#connect').addClass('disabled');
							$('.notice-message').html("连接成功");
							$('#disconnect').removeClass('disabled');
							alert("成功连接");
							window.location.replace("/");
						} else {
							$('.notice-message').html("连接失败");
							$('#disconnect').addClass('disabled');
							$('#connect').removeClass('disabled');
							alert("连接失败");
						}
					}
					
				});
			} else {
				$('.notice-message').html("还没输入房间号");
				setTimeout(function() {
					$('.notice-message').html("");
				}, 1000);
			}
		}
	});

	$(document).on('click', '#disconnect', function() {
		if (!$(this).hasClass('disabled')) {
			if (method.disconnectRoom()) {
				$('#disconnect').addClass('disabled');
				$('#connect').removeClass('disabled');
				alert("断开成功");
			} else {
				$('#connect').addClass('disabled');
				$('#disconnect').removeClass('disabled');
			}
		}
	});

	if ($('.card-header').children('h2').children('span').html() === "弹幕姬连接") {
		if (method.connectCheck()) {
			$('#connect').addClass('disabled');
			$('#disconnect').removeClass('disabled');
		} else {
			$('#disconnect').addClass('disabled');
			$('#connect').removeClass('disabled');
		}

	}

});

const method = {
	connectRoom : function(roomid) {
		"use strict";
		var deferred = $.Deferred();
		$.ajax({
			url : './connectRoom',
			data : {
				roomid : roomid,
			},
			async : true,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				deferred.resolve(data);
			}
		});
		return deferred.promise();
	},
	disconnectRoom : function() {
		"use strict";
		var flag = false;
		$.ajax({
			url : './disconnectRoom',
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (data.code === "200") {
					flag = data.result;
				}
			}
		});
		return flag;
	},
	connectCheck : function() {
		"use strict";
		var flag = false;
		$.ajax({
			url : './connectCheck',
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (data.code === "200") {
					flag = data.result;
				}
			}
		});
		return flag
	},
};
