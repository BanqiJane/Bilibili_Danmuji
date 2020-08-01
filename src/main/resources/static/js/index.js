$(function() {
	"use strict";
	$(".notice-message").hide();
	$(".success-message").hide();
	$(".shieldgifts-mask").hide();
	var time;
	var socket;
	time = setInterval(heartBeat, 30000);
	openSocket(socket,"127.0.0.1");
	function heartBeat() {
		"use strict";
		$.ajax({
			url : '../heartBeat',
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (data.code == "200") {
					var popu = data.result;
					if ($(".popu").length > 0) {
						if (popu != null) {
							$(".popu").html(popu);
						} else {
							clearInterval(time);
						}
					} else {
						clearInterval(time);
					}
				}
			}
		});
	}
	method.initSet(method.getSet());
	$('.thankgift_thank_status')
			.change(
					function() {
						var num = Number($(".thankgift_thank_status").children(
								"option:selected").val());
						switch (num) {
						case 1:
							$(".thankgift_thank").val(
									method.replaceThanko(method.getSet().thank_gift.thank));
							$(".thankgift_thank").attr('placeholder',
									"感谢%uName%%Type%的%GiftName% x%Num%~");
							$(".thankgift_thank")
									.attr(
											'data-original-title',
											'感谢语，单人单种，可选参数<br/> %uName%送礼人名称<br/>%Type%赠送类型<br/>%GiftName%礼物名称<br/>%Num%礼物数量');
							break;
						case 2:
							$(".thankgift_thank").val(method.replaceThankt(method.getSet().thank_gift.thank));
							$(".thankgift_thank").attr('placeholder',
									"感謝%uName%贈送的%Gifts%~");
							$(".thankgift_thank")
									.attr('data-original-title',
											'感谢语，单人多种，可选参数<br/> %uName%送礼人名称<br/>%Gifts%礼物和数量的集合以逗号隔开');
							break
						case 3:
							$(".thankgift_thank").val(method.replaceThankts(method.getSet().thank_gift.thank));
							$(".thankgift_thank").attr('data-original-title','感谢语，多人多种，可选参数<br/> %uNames%送礼人名称集合<br/>%Gifts%礼物和数量的集合以逗号隔开');
							$(".thankgift_thank").attr('placeholder',
									"感謝%uNames%贈送的%Gifts%~");					
							break
						default:
							break;
						}
					});
	$('.thankgift_shield_status').change(
			function() {
				if (Number($(".thankgift_shield_status").children(
						"option:selected").val()) !== 1) {
					$(".thankgift_shield").hide();
				} else {
					$(".thankgift_shield").show();
				}
				if (Number($(".thankgift_shield_status").children(
						"option:selected").val()) !== 4) {
					$("#gift-shield-btn").hide();
				} else {
					$("#gift-shield-btn").show();
				}
			});
});
$(document).on(
		'click',
		'.set-hold',
		function() {
			var c1 = false;
			var c2 = false;
			var c3 = false;
			var c4 = false;
			var c5 = false;
			var c6 = false;
			var c7 = false;
			var set = {
				"thank_gift" : {
					"giftStrings" :[],
					"thankGiftRuleSets" : []
				},
				"advert" : {},
				"follow" : {}
			};
			set.is_barrage_guardAndvip = $(".is_barrage_guardAndvip").is(
					':checked');
			set.is_barrage_manager = $(".is_barrage_manager").is(':checked');
			set.is_barrage_medal = $(".is_barrage_medal").is(':checked');
			set.is_barrage_ul = $(".is_barrage_ul").is(':checked');
			set.is_block = $(".is_block").is(':checked');
			set.is_gift = $(".is_gift").is(':checked');
			set.is_welcome = $(".is_welcome").is(':checked');
			set.is_follow = $(".is_follow").is(':checked');
			set.is_log = $(".is_log").is(':checked');
			set.is_online = $(".is_online").is(':checked');
			set.thank_gift.is_open = $(".thankgift_is_open").is(':checked');
			set.thank_gift.is_live_open = $(".thankgift_is_live_open").is(
					':checked');
			set.thank_gift.is_tx_shield = $(".thankgift_is_tx_shield").is(
					':checked');
			set.thank_gift.shield_status = Number($(".thankgift_shield_status")
					.find("option:selected").val()) - 1;
			set.thank_gift.giftStrings =method.giftStrings_handle(set.thank_gift.giftStrings,$(".thankgift_shield").val());
			if ($(".shieldgifts-tbody tr").length > 0) {
				var thankGiftRuleSet={};
				$(".shieldgifts-tbody tr").each(function(i, v) {
					thankGiftRuleSet.is_open =$(".shieldgifts_open").eq(i).is(':checked');
					thankGiftRuleSet.gift_name =$(".shieldgifts_name").eq(i).val();
					thankGiftRuleSet.status =Number($(".shieldgifts_status").eq(i).find("option:selected").val()) - 1;
					thankGiftRuleSet.num =Number($(".shieldgifts_num").eq(i).val());
					set.thank_gift.thankGiftRuleSets.push(thankGiftRuleSet);
					thankGiftRuleSet={};
				});
			}
			set.thank_gift.thank_status = Number($(".thankgift_thank_status")
					.find("option:selected").val()) - 1;
			set.thank_gift.num = Number($(".thankgift_num").val());
			set.thank_gift.delaytime = Number($(".thankgift_delaytime").val());
			set.thank_gift.thank = $(".thankgift_thank").val();
			set.thank_gift.is_guard_report = $(".thankgift_is_guard_report")
					.is(':checked');
			set.thank_gift.is_guard_local = $(".thankgift_is_guard_local")
			.is(':checked');
			set.thank_gift.report = $(".thankgift_report").val();
			set.thank_gift.report_barrage=$(".thankgift_barrageReport").val();
			set.advert.is_open = $(".advert_is_open").is(':checked');
			set.advert.is_live_open = $(".advert_is_live_open").is(':checked');
			set.advert.status = Number($(".advert_status").find(
					"option:selected").val()) - 1;
			set.advert.time = Number($(".advert_time").val());
			set.advert.adverts = $(".advert_adverts").val();
			set.follow.is_open = $(".follow_is_open").is(':checked');
			set.follow.is_live_open = $(".follow_is_live_open").is(':checked');
			set.follow.is_tx_shield = $(".follow_tx_shield").is(':checked');
			set.follow.num = Number($(".follow_num").val());
			set.follow.follows = $(".follow_follows").val();
			set.follow.delaytime= Number($(".thankfollow_delaytime").val());
			if ($(".follow_is_open").is(':checked')) {
				if ($(".follow_follows").val().trim() !== null
						&& $(".follow_follows").val().trim() !== "") {
				} else {
					c1 = true;
					method.delay_method(".notice-message", "感谢关注语不能为空");
				}
				if (Number($(".follow_num").val()) > 0) {

				} else {
					c5 = true;
					method.delay_method(".notice-message", "感谢关注必须大于0");
				}
			}
			if ($(".thankgift_is_open").is(':checked')) {
				if ($(".thankgift_thank").val().trim() !== null
						&& $(".thankgift_thank").val().trim() !== "") {

				} else {
					c2 = true;
					method.delay_method(".notice-message", "感谢礼物语不能为空");
				}
				if ($(".thankgift_is_guard_report").is(':checked')) {
					if ($(".thankgift_report").val().trim() !== null
							&& $(".thankgift_report").val().trim() !== "") {
						if ($(".thankgift_report").val().length >= 500) {
							c6 = true;
							method.delay_method(".notice-message",
									"上舰回复语不能超过500字");
						}
					} else {
						c3 = true;
						method.delay_method(".notice-message", "上舰回复语不能为空");
					}
				}
			}
			if ($(".advert_is_open").is(':checked')) {
				if ($(".advert_adverts").val().trim() !== null
						&& $(".advert_adverts").val().trim() !== "") {
				} else {
					c4 = true;
					method.delay_method(".notice-message", "广告语不能为空");
				}

			}
			$(".shieldgifts-tbody").children("tr").each(function(i,v){
				if($(".shieldgifts_name").eq(i).val().trim()==""){
					c7=true;
					method.delay_method(".notice-message", "自定义规则不能为空");
				}
			})
			if ($(".card-body").find(".logined").length > 0) {
				if (!c1 && !c2 && !c3 && !c4 && !c5 && !c6&&!c7) {
					console.log(set);
					method.initSet(set);
					if (method.sendSet(set)) {
						method.delay_method(".success-message", "保存配置成功");
						alert("修改配置成功");
					} else {
						method.delay_method(".notice-message", "修改配置失败");
						alert("修改配置失败");
					}
				} else {
					method.delay_method(".notice-message", "修改配置失败");
					alert("修改配置失败")
				}
			} else {
				console.log(set);
				method.initSet(set);
				if (method.sendSet(set)) {
					method.delay_method(".success-message", "保存配置成功");
					alert("修改配置成功");
				} else {
					method.delay_method(".notice-message", "修改配置失败");
					alert("修改配置失败");
				}
			}
		});
$(document).on('click', '.is_guard_report_click', function() {
	if ($(".thankgift_is_guard_report").is(':checked')) {
		$(".thankgift_report").show();
		$(".thankgift_barrageReport").show();
	} else {
		$(".thankgift_report").hide();
		$(".thankgift_barrageReport").hide();
	}

});
$(document).on('click', '#gift-shield-btn', function() {
	if (!$(".shieldgifts-mask").is(":visible")) {
		$(".shieldgifts-mask").show();
	}

});
$(document).on('click', '.btn-close', function() {
	if ($(".shieldgifts-mask").is(":visible")) {
		$(".shieldgifts-mask").hide();
	}
});
$(document)
		.on(
				'click',
				'.shieldgift_add',
				function() {
					$(".shieldgifts-tbody")
							.append(
									"<tr><td><input type='checkbox' class='shieldgifts_open' data-toggle='tooltip' data-placement='top' title='是否开启' data-original-title='是否开启'></td><td><input class='small-input shieldgifts_name' placeholder='礼物名称' data-toggle='tooltip' data-placement='top' title='礼物名称' data-html='true' data-original-title='礼物名称'></td><td><select class='custom-select-sm shieldgifts_status' data-toggle='tooltip' data-placement='top' title='选择类型' data-html='true' data-original-title='选择类型'><option value='1' selected='selected'>数量</option><option value='2'>瓜子</option></select></td><td><input type='number' min='0' class='small-input shieldgifts_num' placeholder='num' value='0' data-toggle='tooltip' data-placement='top' title='大于多少(不得小于)' data-html='true' data-original-title='大于多少(不得小于)'></td><td><button type='button' class='btn btn-danger btn-sm shieldgift_delete'>删除</button></td></tr>");
				});
$(document).on('click', '.shieldgift_delete', function() {
	$(this).parent().parent().remove();
});
const method = {
	getSet : function() {
		"use strict";
		var json = null;
		$.ajax({
			url : '../getSet',
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (data.code == "200") {
					json = data.result;
				}
			}
		});
		return json;
	},
	sendSet : function(set) {
		"use strict";
		var flag = false;
		$.ajax({
			url : '../sendSet',
			data : {
				set : JSON.stringify(set)
			},
			async : false,
			cache : false,
			type : 'POST',
			dataType : 'json',
			success : function(data) {
				if (data.code == "200") {
					flag = data.result
				}
			}
		});
		return flag;
	},
	initSet : function(set) {
		"use strict";
		if (set != null) {
			$(".is_barrage_guardAndvip").prop('checked',
					set.is_barrage_guardAndvip);
			$(".is_barrage_manager").prop('checked', set.is_barrage_manager);
			$(".is_barrage_medal").prop('checked', set.is_barrage_medal);
			$(".is_barrage_ul").prop('checked', set.is_barrage_ul);
			$(".is_block").prop('checked', set.is_block);
			$(".is_gift").prop('checked', set.is_gift);
			$(".is_welcome").prop('checked', set.is_welcome);
			$(".is_follow").prop('checked', set.is_follow);
			$(".is_log").prop('checked', set.is_log);
			$(".is_online").prop('checked', set.is_online);
			$(".thankgift_is_open").prop('checked', set.thank_gift.is_open);
			$(".thankgift_is_live_open").prop('checked',
					set.thank_gift.is_live_open);
			$(".thankgift_is_tx_shield").prop('checked',
					set.thank_gift.is_tx_shield);
			$(".thankgift_shield_status").find("option").eq(
					set.thank_gift.shield_status).prop('selected', true);
			$(".thankgift_shield").val(method.giftStrings_metod(set.thank_gift.giftStrings));
			method.shieldgifts_each(set.thank_gift.thankGiftRuleSets);
			// $(".thankgift_thankGiftRuleSets").val(
			// set.thank_gift.thankGiftRuleSets);// test
			$(".thankgift_thank_status").find("option").eq(
					set.thank_gift.thank_status).prop('selected', true);
			$(".thankgift_num").val(set.thank_gift.num);
			$(".thankgift_delaytime").val(set.thank_gift.delaytime);
			$(".thankgift_thank").val(set.thank_gift.thank);
			$(".thankgift_is_guard_report").prop('checked',
					set.thank_gift.is_guard_report);
			$(".thankgift_is_guard_local").prop('checked',
					set.thank_gift.is_guard_local);
			$(".thankgift_report").val(set.thank_gift.report);
			$(".thankgift_barrageReport").val(set.thank_gift.report_barrage);
			$(".advert_is_open").prop('checked', set.advert.is_open);
			$(".advert_is_live_open").prop('checked', set.advert.is_live_open);
			$(".advert_status").find("option").eq(set.advert.status).prop(
					'selected', true)
			$(".advert_time").val(set.advert.time);
			$(".advert_adverts").val(set.advert.adverts);
			$(".follow_is_open").prop('checked', set.follow.is_open);
			$(".follow_is_live_open").prop('checked', set.follow.is_live_open);
			$(".follow_tx_shield").prop('checked', set.follow.is_tx_shield);
			$(".follow_num").val(set.follow.num);
			$(".follow_follows").val(set.follow.follows);
			$(".thankfollow_delaytime").val(set.follow.delaytime);
			if (Number($(".thankgift_shield_status")
					.children("option:selected").val()) !== 1) {
				$(".thankgift_shield").hide();
			} else {
				$(".thankgift_shield").show();
			}
			if (Number($(".thankgift_shield_status")
					.children("option:selected").val()) !== 4) {
				$("#gift-shield-btn").hide();
			} else {
				$("#gift-shield-btn").show();
			}
			switch (Number($(".thankgift_thank_status").children(
					"option:selected").val())) {
			case 1:
				$(".thankgift_thank").attr('placeholder',
						"感谢%uName%%Type%的%GiftName% x%Num%~");
				$(".thankgift_thank")
						.attr(
								'data-original-title',
								'感谢语，单人单种，可选参数<br/> %uName%送礼人名称<br/>%Type%赠送类型<br/>%GiftName%礼物名称<br/>%Num%礼物数量');
				break;
			case 2:
				$(".thankgift_thank").attr('placeholder',
						"感謝%uName%贈送的%Gifts%~");
				$(".thankgift_thank")
						.attr('data-original-title',
								'感谢语，单人多种，可选参数<br/> %uName%送礼人名称<br/>%Gifts%礼物和数量的集合以逗号隔开');
				break
			case 3:
				$(".thankgift_thank").attr('placeholder',
						"感謝%uNames%贈送的%Gifts%~");
				$(".thankgift_thank")
				.attr('data-original-title',
						'感谢语，多人多种，可选参数<br/> %uNames%送礼人名称集合<br/>%Gifts%礼物和数量的集合以逗号隔开');
				break
			default:
				break;
			}
			if ($(".thankgift_is_guard_report").is(':checked')) {
				$(".thankgift_report").show();
				$(".thankgift_barrageReport").show();
			} else {
				$(".thankgift_report").hide();
				$(".thankgift_barrageReport").hide();
			}
			if (!$(".card-body").find(".logined").length > 0) {
				$(".is_online").attr("disabled", true);
				$(".thankgift_is_open").attr("disabled", true);
				$(".thankgift_is_live_open").attr("disabled", true);
				$(".thankgift_is_tx_shield").attr("disabled", true);
				$(".thankgift_shield_status").attr("disabled", true);
				$(".thankgift_shield").attr("disabled", true);
				$(".thankgift_thankGiftRuleSets").attr("disabled", true);// test
				$(".thankgift_thank_status").attr("disabled", true);
				$(".thankgift_num").attr("disabled", true);
				$(".thankgift_delaytime").attr("disabled", true);
				$(".thankgift_thank").attr("disabled", true);
				$(".thankgift_is_guard_report").attr("disabled", true);
				$(".thankgift_is_guard_local").attr("disabled", true);
				$(".thankgift_report").attr("disabled", true);
				$(".advert_is_open").attr("disabled", true);
				$(".advert_is_live_open").attr("disabled", true);
				$(".advert_status").attr("disabled", true);
				$(".advert_time").attr("disabled", true);
				$(".advert_adverts").attr("disabled", true);
				$(".follow_is_open").attr("disabled", true);
				$(".follow_is_live_open").attr("disabled", true);
				$(".follow_num").attr("disabled", true);
				$(".follow_follows").attr("disabled", true);
				$(".follow_tx_shield").attr("disabled",true);
				$(".thankfollow_delaytime").attr("disabled",true);
				$(".shieldgift_delete").attr("disabled", true);
				$(".thankgift_barrageReport").attr("disabled",true);
			}
		}
	},
	wrap_replace : function(d) {
		"use strict";
		if (d.trim() !== null && d.trim() !== "") {
			var rc = d.replace(/\n/g, '').replace(/\r/g, '');
			// rc = rc.replace(/_#_@/g, '<br/>');
			// rc = rc.replace(/_@/g, '<br/>');
			// rc = rc.replace(/\s/g, '&nbsp;');
			return rc;
		} else {
			return d;
		}
	},
	delay_method : function(e, s) {
		"use strict";
		if (!$(e).is(":visible")) {
			$(e).show();
			$(e).html(s);
			setTimeout(function() {
				$(e).hide();
			}, 3000);
		}
	},
	giftStrings_metod: function(lists){
		var s = "";
		if(lists!=null){
			s=lists.join("，");
		}
		return s;
	},
	giftStrings_handle : function(lists,s){
		if(s!=""){
			if(s.indexOf("，")>=0){
			var ss = s.split("，");
			for(let gs in ss){
				if(ss[gs].trim()!=""){
					lists.push(ss[gs]);	
				}
			}
			}else{
				lists.push(s);
			}
		}
		return lists;
	},
	shieldgifts_each : function(lists) {
		if (lists != null) {
			$(".shieldgifts-tbody").children('tr').remove();
			for (let i in lists) {
				$(".shieldgifts-tbody")
						.append(
								"<tr><td><input type='checkbox' class='shieldgifts_open' data-toggle='tooltip' data-placement='top' title='是否开启' data-original-title='是否开启'></td><td><input class='small-input shieldgifts_name' value='"
										+ lists[i].gift_name
										+ "' placeholder='礼物名称' data-toggle='tooltip' data-placement='top' title='礼物名称' data-html='true' data-original-title='礼物名称'></td><td><select class='custom-select-sm shieldgifts_status' data-toggle='tooltip' data-placement='top' title='选择类型' data-html='true' data-original-title='选择类型'><option value='1' selected='selected'>数量</option><option value='2'>瓜子</option></select></td><td><input type='number' min='0' value='"
										+ lists[i].num
										+ "' class='small-input shieldgifts_num' placeholder='num' value='0' data-toggle='tooltip' data-placement='top' title='大于多少(不得小于' data-html='true' data-original-title='大于多少(不得小于)'></td><td><button type='button' class='btn btn-danger btn-sm shieldgift_delete'>删除</button></td></tr>");
				$(".shieldgifts_open").eq(i).prop('checked', lists[i].is_open);
				$(".shieldgifts_status").eq(i).find("option").eq(
						lists[i].status).prop('selected', true);
			}
		}
	},
	getIp : function (){
		var ip =null;
		$.ajax({
			url : '../getIp',
			async : false,
			cache : false,
			type : 'GET',
			dataType : 'json',
			success : function(data) {
				if (data.code == "200") {
					ip = data.result
				}
			}
		});
		return ip;
	},
	replaceThanko:function(s){
		s=s.replace(/uNames/g,"uName");
		s=s.replace(/%Gifts%/g,"%Num%个%GiftName%");
		return s;
	},
	replaceThankt:function(s){
		s=s.replace(/uNames/g,"uName");
		s=s.replace(/%Num%个%GiftName%/g,"%Gifts%");
		return s;
	},
	replaceThankts:function(s){
		s=s.replace(/uName/g,"uNames");
		s=s.replace(/%Num%个%GiftName%/g,"%Gifts%");
		return s;
	},
};
function openSocket(socket,ip) {
	if (typeof (WebSocket) == "undefined") {
		alert("您的浏览器不支持WebSocket，显示弹幕功能异常，请升级你的浏览器版本，推荐谷歌");
	} else {
		console.log("弹幕服务器正在连接");
		var socketUrl = "ws://%IP%:23333/danmu/sub";
		if(ip!=null){
			socketUrl = socketUrl.replace("%IP%",ip);
		}
		if (socket != null) {
			socket.close();
			socket = null;
		}
		try {
		socket = new WebSocket(socketUrl);
		} catch(err) {
			console.log(err);
		}
		// 打开事件
		socket.onopen = function() {
			$("#danmu").append("<div class='danmu-child'>连接成功<div/>");
			console.log("连接已打开");
		};
		// 获得消息事件
		socket.onmessage = function(msg) {
			// 发现消息进入 开始处理前端触发逻辑
			if ($("#danmu").children().length > 99) {
				$("#danmu").children().first().remove();
				$("#danmu").children("div:last-child").after(
						"<div class='danmu-child'>" + msg.data + "<div/>");
			} else {
				$("#danmu").append(
						"<div class='danmu-child'>" + msg.data + "<div/>");
			}
			if ($('#danmu')[0].scrollHeight - $("#danmu").scrollTop() <= 544) {
				$('#danmu').scrollTop($('#danmu')[0].scrollHeight);
			}

		};
		// 关闭事件
		socket.onclose = function() {
			$("#danmu").append("<div class='danmu-child'>连接已关闭<div/>");
			console.log("连接已关闭");
		};
		// 发生了错误事件
		socket.onerror = function() {
			$("#danmu").append("<div class='danmu-child'>连接到弹幕服务器发生了错误<div/>");
			console.log("连接到弹幕服务器发生了错误");
			openSocket(socket,method.getIp());
		}
	}
}
function sendMessage() {
	if (typeof (WebSocket) == "undefined") {
		console.log("您的浏览器不支持WebSocket");
	} else {
		console.log("您的浏览器支持WebSocket");
		socket.send('{"toUserId":"' + $("#toUserId").val()
				+ '","contentText":"' + $("#contentText").val() + '"}');
	}
}