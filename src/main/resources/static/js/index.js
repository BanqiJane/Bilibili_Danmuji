$(function () {
    "use strict";
    let time;
    let socket = null;
    let sliceh = 0;
    time = setInterval(heartBeat, 30000);
    function heartBeat() {
        "use strict";
        $.ajax({
            url: '../heartBeat',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    let popu = data.result;
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
    };
    method.getBlocks(1);
    $("#file").change(function () {
        method.importDfFile();
    });
    publicData.set = method.initSet(method.getSet());
    $('.thankgift_thank_status')
        .change(
            function () {
                let num = Number($(".thankgift_thank_status").children(
                    "option:selected").val());
                switch (num) {
                    case 1:
                        $(".thankgift_thank").val(
                            method.replaceThanko(method.getSet().thank_gift.thank));
                        $(".thankgift_thank").attr('placeholder',
                            "感谢%uName%%Type%的%GiftName% x%Num%~");
                        $(".thankgift_thank")
                            .attr(
                                'title',
                                '模式:单人单种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/><span class=\'red-font\'>%uName%</span>送礼人名称<br/><span class=\'red-font\'>%Type%</span>赠送类型<br/><span class=\'red-font\'>%GiftName%</span>礼物名称<br/><span class=\'red-font\'>%Num%</span>礼物数量');
                        break;
                    case 2:
                        $(".thankgift_thank").val(method.replaceThankt(method.getSet().thank_gift.thank));
                        $(".thankgift_thank").attr('placeholder',
                            "感謝%uName%贈送的%Gifts%~");
                        $(".thankgift_thank")
                            .attr('title',
                                '模式:单人多种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/> <span class=\'red-font\'>%uName%</span>送礼人名称<br/><span class=\'red-font\'>%Gifts%</span>礼物和数量的集合以逗号隔开');
                        break
                    case 3:
                        $(".thankgift_thank").val(method.replaceThankts(method.getSet().thank_gift.thank));
                        $(".thankgift_thank").attr('title', '模式:多人多种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/> <span class=\'red-font\'>%uNames%</span>送礼人名称集合<br/><span class=\'red-font\'>%Gifts%</span>礼物和数量的集合以逗号隔开');
                        $(".thankgift_thank").attr('placeholder',
                            "感謝%uNames%贈送的%Gifts%~");
                        break
                    default:
                        break;
                }
                let exampleTriggerEl2 = document.getElementById("thankgift_thank")
                let tooltip2 = bootstrap.Tooltip.getInstance(exampleTriggerEl2)
                tooltip2 = new bootstrap.Tooltip(exampleTriggerEl2)
                let exampleTriggerEl = document.getElementById('thankgift_thank_status')
                let tooltip = bootstrap.Tooltip.getInstance(exampleTriggerEl)
                tooltip.hide();
            });
    $('.thankgift_shield_status').change(
        function () {
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
    $(document).on('click', '.btn-connect-d', function () {
        let a = $(".connect-docket").val();
        if (a === "" || a === null) {
            $(".connect-docket").val("不能为空");
            return;
        }
        if (!$(this).attr("disabled")) {
            openSocket(socket, a, sliceh);
            $(this).attr("disabled", true);
        }
    });
    $(document).on('click', '#danmu_open', function () {
        let url_start = window.location.host;
        $(".connect-docket").val("ws://" + url_start + "/danmu/sub");
    });
    $('body').click(function (e) {
        let target = $(e.target);
        if (!target.is('.danmu-child-li')) {
            if ($('.danmu-tips').is(':visible')) {
                $('.danmu-tips').hide();
                $('.danmu-child').removeClass('danmu-child-z');
            }
        }
    });
});
$(document).on(
    'click',
    '.set-hold',
    function () {
        let c1 = false;
        let c2 = false;
        let c3 = false;
        let c4 = false;
        let c5 = false;
        let c6 = false;
        let c7 = false;
        let c8 = false;
        let c9 = false;
        let c10 = false;
        let c11 = false;
        let set = {
            "thank_gift": {
                "giftStrings": [],
                "thankGiftRuleSets": [],
                "codeStrings": [],
            },
            "advert": {},
            "follow": {},
            "reply": {"autoReplySets": []},
            "clock_in": {},
            "welcome": {},
            "auto_gift":{},
            "privacy":{},
        };
        set.is_auto = $(".is_autoStart").is(
            ':checked');
        set.win_auto_openSet = $(".win_auto_openSet").is(':checked');
        set.is_barrage_guard = $(".is_barrage_guard").is(
            ':checked');
        set.is_barrage_vip = $(".is_barrage_vip").is(
            ':checked');
        set.is_barrage_manager = $(".is_barrage_manager").is(':checked');
        set.is_barrage_medal = $(".is_barrage_medal").is(':checked');
        set.is_barrage_ul = $(".is_barrage_ul").is(':checked');
        set.is_barrage_anchor_shield = $(".is_barrage_anchor_shield").is(':checked');
        set.is_block = $(".is_block").is(':checked');
        set.is_cmd = $(".is_cmd").is(':checked');
        set.is_gift = $(".is_gift").is(':checked');
        set.is_welcome = $(".is_welcome").is(':checked');
        set.is_welcome_all = $(".is_welcome_all").is(':checked');
        set.is_follow = $(".is_follow").is(':checked');
        set.is_log = $(".is_log").is(':checked');
        set.is_online = $(".is_online").is(':checked');
        /* 管理登录 */
        set.is_manager_login = $(".is_manager_login").is(':checked');
        set.manager_maxSize = Number($(".manager_maxSize").val());
        //密码就不set给前端了
        set.manager_key = $(".manager_key").val();
        /* 管理结束*/
        set.is_sh = $(".is_sh").is(':checked');
        set.is_dosign = $(".is_dosign").is(':checked');
        set.sign_time = method.time_parse($(".sign_time").val());
        set.thank_gift.is_open = $(".thankgift_is_open").is(':checked');
        set.thank_gift.is_live_open = $(".thankgift_is_live_open").is(
            ':checked');
        set.thank_gift.is_tx_shield = $(".thankgift_is_tx_shield").is(
            ':checked');
        set.thank_gift.is_num = $(".thankgift_is_num").is(':checked');
        set.thank_gift.shield_status = Number($(".thankgift_shield_status")
            .find("option:selected").val()) - 1;
        set.thank_gift.giftStrings = method.giftStrings_handle(set.thank_gift.giftStrings, $(".thankgift_shield").val());
        if ($(".shieldgifts-tbody tr").length > 0) {
            let thankGiftRuleSet = {};
            $(".shieldgifts-tbody tr").each(function (i, v) {
                thankGiftRuleSet.is_open = $(".shieldgifts_open").eq(i).is(':checked');
                thankGiftRuleSet.gift_name = $(".shieldgifts_name").eq(i).val();
                thankGiftRuleSet.status = Number($(".shieldgifts_status").eq(i).find("option:selected").val()) - 1;
                thankGiftRuleSet.num = Number($(".shieldgifts_num").eq(i).val());
                set.thank_gift.thankGiftRuleSets.push(thankGiftRuleSet);
                thankGiftRuleSet = {};
            });
        }
        if ($(".replys-ul li").length > 0) {
            let autoReplySet = {};
            $(".replys-ul li").each(function (i, v) {
                autoReplySet.is_open = $(".reply_open").eq(i).is(':checked');
                autoReplySet.is_accurate = $(".reply_oc").eq(i).is(':checked');
                let keywords = [];
                let shields = [];
                autoReplySet.keywords = method.giftStrings_handle(keywords, $(".reply_keywords").eq(i).val());
                autoReplySet.shields = method.giftStrings_handle(shields, $(".reply_shields").eq(i).val());
                autoReplySet.reply = $(".reply_rs").eq(i).val();
                set.reply.autoReplySets.push(autoReplySet);
                autoReplySet = {};
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
        set.thank_gift.is_gift_code = $(".thankgift_is_gift_code")
            .is(':checked');
        set.thank_gift.codeStrings = method.codeStrings_handle(set.thank_gift.codeStrings, $(".thankgift_codeStrings").val());
        set.thank_gift.report = $(".thankgift_report").val();
        set.thank_gift.report_barrage = $(".thankgift_barrageReport").val();
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
        set.follow.delaytime = Number($(".thankfollow_delaytime").val());
        set.welcome.is_open = $(".welcome_is_open").is(':checked');
        set.welcome.is_live_open = $(".welcome_is_live_open").is(':checked');
        set.welcome.is_tx_shield = $(".welcome_tx_shield").is(':checked');
        set.welcome.num = Number($(".welcome_num").val());
        set.welcome.welcomes = $(".welcome_welcomes").val();
        set.welcome.delaytime = Number($(".thankwelcome_delaytime").val());
        set.reply.is_open = $(".replys_is_open").is(':checked');
        set.reply.is_live_open = $(".replys_is_live_open").is(':checked');
        set.reply.time = Number($(".replys_time").val());
        set.clock_in.is_open = $(".is_clockin").is(':checked');
        set.clock_in.time = method.time_parse($(".clockin_time").val());
        set.clock_in.barrage = $(".clockin_barrage").val();
        set.auto_gift.is_open = $(".is_autoGift_open").is(':checked');
        set.auto_gift.time = method.time_parse($(".autoGift_time").val());
        set.auto_gift.room_id = $(".autoGift_roomids").val();
        set.privacy.is_open = $(".is_privacy_open").is(':checked');
        set.privacy.small_heart_url = $(".privacy_heart_url").val();
        /*处理验证?*/
        if (set.clock_in.is_open) {
            set.clock_in.sign_day = (new Date()).getTime();
        }
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
        if ($(".welcome_is_open").is(':checked')) {
            if ($(".welcome_welcomes").val().trim() !== null
                && $(".welcome_welcomes").val().trim() !== "") {
            } else {
                c9 = true;
                method.delay_method(".notice-message", "感谢欢迎语不能为空");
            }
            if (Number($(".welcome_num").val()) > 0) {

            } else {
                c10 = true;
                method.delay_method(".notice-message", "感谢欢迎必须大于0");
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
        $(".shieldgifts-tbody").children("tr").each(function (i, v) {
            if ($(".shieldgifts_name").eq(i).val().trim() == "") {
                c7 = true;
                method.delay_method(".notice-message", "自定义规则不能为空");
            }
        })
        $(".replys-ul").children("li").each(function (i, v) {
            if ($(".reply_keywords").eq(i).val() === "" || $(".reply_rs").eq(i).val() === "") {
                c8 = true;
                method.delay_method(".notice-message", "自动回复姬的关键字和回复语句都不能为空！！！");
            } else {

            }
        });
        if ($(".is_autoGift_open").is(':checked')) {
            if($(".autoGift_roomids").val()==null||$(".autoGift_roomids").val().trim()===""){
                c11=true;
                method.delay_method(".notice-message", "礼物自动赠送姬房间号不能为空！！！");
            }
        }
        if ($(".card-body").find(".logined").length > 0) {
            if (!c1 && !c2 && !c3 && !c4 && !c5 && !c6 && !c7 && !c8 && !c9 && !c10&& !c11) {
                publicData.set = method.initSet(set);
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
$(document).on('click', '.is_guard_report_click', function () {
    if ($(".thankgift_is_guard_report").is(':checked')) {
        $(".thankgift_report").show();
        $(".thankgift_barrageReport").show();
        $(".thankgift_is_gift_code").attr("disabled", false);
        if ($(".thankgift_is_gift_code").is(':checked')) {
            $(".thankgift_codeStrings").show();
        }
    } else {
        $(".thankgift_is_gift_code").attr("disabled", true);
        $(".thankgift_is_gift_code").prop('checked', false);
        $(".thankgift_report").hide();
        $(".thankgift_barrageReport").hide();
        $(".thankgift_codeStrings").hide();
    }
});
$(document).on('click', '.is_guard_code_click', function () {
    if ($(".thankgift_is_gift_code").is(':checked') && $(".thankgift_is_guard_report").is(':checked')) {
        $(".thankgift_codeStrings").show();
    } else {
        $(".thankgift_codeStrings").hide();
    }
});
$(document).on('click', '.import-set', function () {
    $('#file').click();
});
$(document).on('click', '.export-set', function () {
    method.setExprot();
});
$(document).on('click', '.export-set-web', function () {
    method.setExprotWeb();
});
$(document).on('click', '.is_clockin', function () {
    if ($(".is_clockin").is(':checked')) {
        $(".clockin_barrage").show();
        $(".clockin_time").attr("disabled",false);
    } else {
        $(".clockin_barrage").hide();
        $(".clockin_time").attr("disabled",true);
    }
});
$(document).on('click', '.is_dosign', function () {
    if ($(".is_dosign").is(':checked')) {
        $(".sign_time").attr("disabled",false);
    } else {
        $(".sign_time").attr("disabled",true);
    }
});
$(document).on('click', '.is_online', function () {
    if ($(".is_online").is(':checked')) {
        $(".is_sh").attr("disabled", false);
    } else {
        $(".is_sh").attr("disabled", true);
        $(".is_sh").prop('checked', false);
    }

});
$(document).on('click', '#gift-shield-btn', function () {
    if (!$(".shieldgifts-mask").is(":visible")) {
        $(".shieldgifts-mask").show();
    }

});
$(document).on('click', '#replys-btn', function () {
    if (!$(".replys-mask").is(":visible")) {
        $(".replys-mask").show();
    }

});
$(document).on('click', '.btn-close', function () {
    if ($(".shieldgifts-mask").is(":visible")) {
        $(".shieldgifts-mask").hide();
    }
});
$(document).on('click', '.btn-close-block', function () {
    if ($(".block-mask").is(":visible")) {
        $(".block-mask").hide();
    }
});
$(document).on('click', '.btn-close-wel', function () {
    let is_kong = false;
    if ($(".wel-mask").is(":visible")) {
        $(".wel-mask").hide();
    }
});
$(document).on('click', '.btn-closer', function () {
    let is_hide = true;
    if ($(".replys-mask").is(":visible")) {
        $(".replys-ul").children("li").each(function (i, v) {
            if ($(".reply_keywords").eq(i).val() === "" || $(".reply_rs").eq(i).val() === "") {
                alert("关键字和回复语句都不能为空！！！");
                is_hide = false;
            }
            if (!is_hide) return false;
        });
        if (!is_hide) return;
        $(".replys-mask").hide();
    }
});
$(document).on('click', '.btn-block', function () {
    const uid = $(".block-input").attr("uid");
    const time = $(".block-input").val();
    if (time !== "" && time !== null && time.indexOf(".") < 0 && Number(time) > 0 && Number(time) <= 720) {
        if (Number(time) > 720 && Number(time) < 1) {
            alert("禁言时间错误")
        } else {
            const code = method.block(uid, time);
            if (code === 0) {
                alert("禁言成功");
                $(".block-mask").hide();
            } else {
                alert("禁言失败");
                console.log("禁言纠错码:" + code)
            }
        }
    } else {
        alert("请输入正确的时间")
    }
});
$(document)
    .on(
        'click',
        '.shieldgift_add',
        function () {
            $(".shieldgifts-tbody")
                .append(
                    `<tr>
									<td><input type='checkbox' class='shieldgifts_open' data-bs-toggle='tooltip' data-bs-placement='top' title='是否开启' data-original-title='是否开启'></td>
									<td><input class='small-input shieldgifts_name' placeholder='礼物名称' data-bs-toggle='tooltip' data-bs-placement='top' title='礼物名称' data-bs-html='true' data-original-title='礼物名称'></td>
									<td>
									<select class='custom-select-sm shieldgifts_status' data-bs-toggle='tooltip' data-bs-placement='top' title='选择类型<br/>1:屏蔽对应数量<br/>2:屏蔽一坨礼物对应电池' data-bs-html='true' data-original-title='选择类型'>
									<option value='1' selected='selected'>数量</option>
									<option value='2'>电池</option></select>
									</td>
									<td>
									<input type='number' min='0' class='small-input shieldgifts_num' placeholder='num' value='0' data-bs-toggle='tooltip' data-bs-placement='top' title='数量(电池)小于多少触发屏蔽' data-bs-html='true' data-original-title='大于多少(不得小于)'>
									</td>
									<td><button type='button' class='btn btn-danger btn-sm shieldgift_delete'>删除</button></td>
									</tr>`);
            let exampleTriggerEl2 = document.getElementById("shieldgift_add")
            let tooltip2 = bootstrap.Tooltip.getInstance(exampleTriggerEl2)
            tooltip2.hide()
            let tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            let tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                // this.addEventListener('hide.bs.tooltip', function () {
                //     new bootstrap.Tooltip(tooltipTriggerEl)
                // })
                return new bootstrap.Tooltip(tooltipTriggerEl)
            });
        });
$(document)
    .on(
        'click',
        '.replys_add',
        function () {
            $(".replys-ul")
                .append(
                    `<li><input type='checkbox' class='reply_open'
						data-bs-toggle='tooltip' data-bs-placement='top' title='是否开启'
						data-bs-html='true' data-original-title='是否开启'>
						<input type='checkbox' class='reply_oc'
						data-bs-toggle='tooltip' tabindex="0" data-bs-placement='top' title='是否精确匹配<br/>更多信息点进去编辑查看'
						data-bs-html='true' data-original-title='是否精确匹配'> 
						<span tabindex="0" placeholder='关键字'
						data-bs-toggle='tooltip' data-bs-placement='top' title='外部不能编辑:多个关键字,以中文逗号隔开<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='关键字'>
						<input class='small-input reply_keywords' tabindex="0" placeholder='关键字'
						data-bs-toggle='tooltip' data-bs-placement='top' title='外部不能编辑:多个关键字,以中文逗号隔开<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='关键字' readonly='readonly' disabled>
						</span>
						<span tabindex="0" placeholder='屏蔽词'
						data-bs-toggle='tooltip'  data-bs-placement='top' title='外部不能编辑:多个屏蔽词,以中文逗号隔开<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='关键字'>
						<input class='small-input reply_shields' tabindex="0" placeholder='屏蔽词'
						data-bs-toggle='tooltip'  data-bs-placement='top' title='外部不能编辑:多个屏蔽词,以中文逗号隔开<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='关键字' readonly='readonly' disabled>
						</span>
						<span placeholder='回复语句'
						data-bs-toggle='tooltip'  data-bs-placement='top' title='外部不能编辑:回复语句,提供%AT%参数,以打印:@提问问题人名称<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='回复语句'>
						<input class='big-input reply_rs'tabindex="0" placeholder='回复语句'
						data-bs-toggle='tooltip'  data-bs-placement='top' title='外部不能编辑:回复语句,提供%AT%参数,以打印:@提问问题人名称<br/>更多信息点编辑进去查看或编辑'
						data-bs-html='true' data-original-title='回复语句' readonly='readonly' disabled>
						</span>
						<span class='reply-btns'>
						<button type='button' class='btn btn-success btn-sm reply_edit'>编辑</button>
						<button type='button' class='btn btn-danger btn-sm reply_delete'>删除</button>
						</span>
					</li>`);
            let exampleTriggerEl2 = document.getElementById("replys_add")
            let tooltip2 = bootstrap.Tooltip.getInstance(exampleTriggerEl2)
            tooltip2.hide()
            let tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'))
            let tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
                // this.addEventListener('hide.bs.tooltip', function () {
                //     new bootstrap.Tooltip(tooltipTriggerEl)
                // })
                return new bootstrap.Tooltip(tooltipTriggerEl)
            });

        });
$(document).on('click', '.reply_delete', function () {
    $(this).parent().parent().remove();
});
$(document).on('click', '.shieldgift_delete', function () {
    $(this).parent().parent().remove();
});
$(document).on('click', '.del_block_click', function () {
    let id = $(this).attr("data-id");
    if (id != undefined) {
        method.delBlock(id);
    }
});
$(document).on('click', '.block-pre-page', function () {
    let page = $(".block-page").text();
    if (page > 1) {
        method.getBlocks(Number(page) - 1);
        $(".block-page").text(Number(page) - 1);
    } else {
        alert("没有上一页了");
    }
});
$(document).on('click', '.block-next-page', function () {
    let page = $(".block-page").text();
    if (method.getBlocks(Number(page) + 1)) {
        $(".block-page").text(Number(page) + 1);
    } else {
        alert("没有更多数据了");
    }
});
$(document).on('click', '.reply_edit', function () {
    let index = $(this).parent().parent().index();
    let is_open = $(this).parent().parent().children(".reply_open").is(':checked');
    let is_oc = $(this).parent().parent().children(".reply_oc").is(':checked');
    let keywords = $(this).parent().parent().children(".reply_keywords").val();
    let shields = $(this).parent().parent().children(".reply_shields").val();
    let rs = $(this).parent().parent().children(".reply_rs").val();
    $(".radd-mask").show();
    $(".radd-body").find(".reply_open_i").prop('checked', is_open);
    $(".radd-body").find(".reply_oc_i").prop('checked', is_oc);
    $(".radd-body").find(".reply_keywords_i").val(keywords);
    $(".radd-body").find(".reply_shields_i").val(shields);
    $(".radd-body").find(".reply_rs_i").val(rs);
    $(".radd-body").find(".reply_delete_i").attr("z-index", index);
});
$(document).on('click', '.reply_delete_i', function (e) {
    let index = $(this).attr("z-index");
    $(".replys-ul").children("li").eq(index).remove();
    e.stopPropagation();
    $(".radd-mask").hide();
});
$(document).on('click', '.btn-closeri', function () {
    if ($(".radd-mask").is(":visible")) {
        let index = $(this).parent().parent().find(".reply_delete_i").attr("z-index");
        let is_open = $(this).parent().parent().find(".reply_open_i").is(':checked');
        let is_oc = $(this).parent().parent().find(".reply_oc_i").is(':checked');
        let keywords = $(this).parent().parent().find(".reply_keywords_i").val();
        let shields = $(this).parent().parent().find(".reply_shields_i").val();
        let rs = $(this).parent().parent().find(".reply_rs_i").val();
        $(".replys-ul").children("li").eq(index).find(".reply_open").prop('checked', is_open);
        $(".replys-ul").children("li").eq(index).find(".reply_oc").prop('checked', is_oc);
        $(".replys-ul").children("li").eq(index).find(".reply_keywords").val(keywords);
        $(".replys-ul").children("li").eq(index).find(".reply_shields").val(shields);
        $(".replys-ul").children("li").eq(index).find(".reply_rs").val(rs);
        if (keywords === null || keywords === "" || rs === null || rs === "") {
            alert("关键字和回复语句都不能为空！！！");
            return;
        }
        $(".radd-mask").hide();
    }
});
$(document).on('click', '#checkupdate', function () {
    
    $(".tips-wrap").show();
    $(".tips-t").html("<span>少女祈祷中<img src='../img/loading-1.gif'></span>");
    $.when(method.checkUpdate()).done(function (data) {
        let num = Number(data.result.status);
        if (num === 0) {
            $("#edition_content").html(`有新版本(最新版本<span style="color:red;">:`+data.result.edition+`</span>)更新，请前往<a href="https://github.com/BanqiJane/Bilibili_Danmuji/releases/tag/`+data.result.edition+`">github</a>获取更新`);
        } else if (num === 1) {
            $("#edition_content").html("当前为最新版本，无需更新");
        } else {
            $("#edition_content").html("服务器无响应，获取更新失败");
        }
        let myToastEl = document.getElementById('updateEle')
        let myToast = bootstrap.Toast.getInstance(myToastEl)
        myToast.show()
        setTimeout(function () {
            $(".tips-wrap").hide();
        }, 1000)
    });
});
$(document).on('click', '.room-manager', function (e) {
    $(".wel-mask").show();
});
$(document).on('click', '.danmu-child', function (e) {
    $(this).children(".danmu-tips").css("left", e.pageX - $(this).offset().left);
    $(this).addClass("danmu-child-z");
    $(this).children(".danmu-tips").show();
    $(this).siblings().children(".danmu-tips").hide();
    $(this).siblings().removeClass("danmu-child-z");
});
$(document).on('click', '.danmu-tips-li', function (e) {
    e.stopPropagation();
    const text = $(this).text();
    const uname = $(this).parent().parent().parent().children().find(".danmu-name").text();
    const uid = $(this).parent().parent().attr("uid");
    if (text.trim() === "关闭") {
        $(this).parents().children(".danmu-tips").hide();
        $(".danmu-tips").hide();
        $(this).parents().children(".danmu-child").removeClass("danmu-child-z");
    } else if (text.trim() === "查看") {
        window.open("https://space.bilibili.com/" + uid, 'width=1000,height=800', '_blank');
    } else if (text.trim() === "禁言") {
        $(".block-input").attr("uid", uid);
        $(".block-input").val("");
        $(".block-input").attr("placeholder", "禁言(" + uname + "-" + uid + ")" + "-禁言时间为1-720小时");
        $(".block-mask").show();
    } else {

    }
});
const danmuku = {
    // 0弹幕 1礼物 2消息
    type: function (t) {
        if (t === 0) {
            return `<span class="danmu-type">弹幕</span>`;
        } else if (t === 1) {
            return `<span class="danmu-type">礼物</span>`;
        } else if (t === 2) {
            return `<span class="danmu-type">留言</span>`;
        } else {
            return `<span class="danmu-type">消息</span>`;
        }
    },
    time: function (d) {
        if(String(d.timestamp).length==10)d.timestamp=d.timestamp*1000;
        return `<span class="danmu-time">` + format(d.timestamp, false) + `</span>`;
    },
    only_time: function (d) {
        return `<span class="danmu-time">` + format(d, false) + `</span>`;
    },
    medal: function (d) {
        if (d.medal_name !== null && d.medal_name !== '') {
            return `<span class="danmu-medal">` + d.medal_name + addSpace(d.medal_level) + `</span>`;
        }
        return '';
    },
    guard: function (d) {
        if (d.uguard > 0) {
            return `<span class="danmu-guard">舰</span>`;
        } else {
            return '';
        }
    },
    vip: function (d) {
        if (d.vip === 1 || d.svip === 1) {
            return `<span class="danmu-vip">爷</span>`;
        } else {
            return '';
        }
    },
    manager: function (d) {
        if (d.manager > 0) {
            if (d.manager > 1) {
                return `<span class="danmu-manager">播</span>`;
            } else {
                return `<span class="danmu-manager">管</span>`;
            }
        } else {
            return '';
        }
    },
    ul: function (d) {
        if (d.ulevel != null) {
            return `<span class="danmu-ul">UL` + addSpace(d.ulevel) + `</span>`;
        }
        return '';
    },
    dname: function (d) {
        let clazz = "";
        if (d.uguard > 0) clazz = "name-guard";
        if (d.manager > 0) clazz = "name-manager";
        return `<a href="javascript:;"><span class="danmu-name` + (clazz === "" ? "" : (" " + clazz)) + `">` + d.uname + `:</span></a>`;
    },
    dmessage: function (d) {
        return `<span class="danmu-text">` + d.msg + `</span>`;
    },
    gname: function (d) {
        let clazz = "";
        if (d.uguard > 0) clazz = "name-guard";
        return `<a href="javascript:;"><span class="danmu-name` + (clazz === "" ? "" : (" " + clazz)) + `">` + d.uname + `</span></a>`;
    },
    gguard: function (d) {
        if (d.guard_level) {
            return `<span class="danmu-guard">舰</span>`;
        } else {
            return '';
        }
    },
    gmessage: function (d) {
        return `<span class="danmu-text">` + d.action + `了 ` + d.giftName + ` x ` + d.num + `</span>`;
    },
    stext: function (d) {
        return `<span class="danmu-text">留言了` + d.time + `秒说:` + d.message + `</span>`;
    },
    block_type: function (d) {
        if (d.operator === 1) {
            return "房管";
        } else {
            return "主播";
        }
    },
    tips: function (d) {
        return `<div class="danmu-tips" uid="` + d.uid + `"><ul class="danmu-tips-ul"><li class="danmu-tips-li">禁言</li><li class="danmu-tips-li">查看</li><li class="danmu-tips-li">关闭</li></ul></div>`;
    },
    danmu: function (type, d) {
        switch (type) {
            case "danmu":
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(0) + danmuku.time(d) + danmuku.medal(d) + danmuku.guard(d) + danmuku.vip(d) + danmuku.manager(d) + danmuku.ul(d) + danmuku.dname(d) + danmuku.dmessage(d) + danmuku.tips(d) + `</div>`;
            case "gift":
                d.timestamp = d.timestamp * 1000;
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(1) + danmuku.time(d) + danmuku.gguard(d) + danmuku.gname(d) + danmuku.gmessage(d) + danmuku.tips(d) + `</div>`;
            case "superchat":
                d.start_time = d.start_time * 1000;
                d.timestamp = d.start_time;
                d.uguard = d.user_info.guard_level;
                d.manager = d.user_info.manager;
                d.uname = d.user_info.uname;
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(2) + danmuku.time(d) + danmuku.dname(d) + danmuku.stext(d) + danmuku.tips(d) + `</div>`;
            case "welcomeVip":
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(4) + danmuku.only_time(getTimestamp()) + `<span class="danmu-text">欢迎</span><a href="javascript:;"><span class="danmu-name">` + d.uname + `</span></a><span class="danmu-text">老爷进入直播间</span>` + danmuku.tips(d) + `</div>`;
            case "welcomeGuard":
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(4) + danmuku.only_time(getTimestamp()) + `<span class="danmu-text">欢迎</span><a href="javascript:;"><span class="danmu-name">` + d.username + `</span></a><span class="danmu-text">舰长进入直播间</span>` + danmuku.tips(d) + `</div>`;
            case "block":
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(4) + danmuku.only_time(getTimestamp()) + `<a href="javascript:;"><span class="danmu-name">` + d.uname + `</span></a><span class="danmu-text">已被` + danmuku.block_type(d) + `禁言</span>` + danmuku.tips(d) + `</div>`;
            case "follow":
                return `<div class="danmu-child" uid="` + d.uid + `">` + danmuku.type(4) + danmuku.time(d) + `<a href="javascript:;"><span class="danmu-name">` + d.uname + `</span></a><span class="danmu-text">关注了直播间</span>` + danmuku.tips(d) + `</div>`;
            default:
                return "";
        }
    },
}
const publicData = {
    set: {},
}
const method = {
    getSet: function () {
        "use strict";
        let json = null;
        $.ajax({
            url: '../getSet',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    json = data.result;
                }
            }
        });
        return json;
    },
    sendSet: function (set) {
        "use strict";
        let flag = false;
        $.ajax({
            url: '../sendSet',
            data: {
                set: JSON.stringify(set)
            },
            async: false,
            cache: false,
            type: 'POST',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    flag = data.result
                }
            }
        });
        return flag;
    },
    initSet: function (set) {
        "use strict";
        if (set != null) {
            $(".is_autoStart").prop('checked',
                set.is_auto);
            $(".win_auto_openSet").prop('checked',set.win_auto_openSet);
            $(".is_barrage_guard").prop('checked',
                set.is_barrage_guard);
            $(".is_barrage_vip").prop('checked',
                set.is_barrage_vip);
            $(".is_barrage_manager").prop('checked', set.is_barrage_manager);
            $(".is_cmd").prop('checked', set.is_cmd);
            $(".is_barrage_medal").prop('checked', set.is_barrage_medal);
            $(".is_barrage_ul").prop('checked', set.is_barrage_ul);
            $(".is_barrage_anchor_shield").prop('checked', set.is_barrage_anchor_shield);
            $(".is_block").prop('checked', set.is_block);
            $(".is_gift").prop('checked', set.is_gift);
            $(".is_welcome").prop('checked', set.is_welcome);
            $(".is_welcome_all").prop('checked', set.is_welcome_all);
            $(".is_follow").prop('checked', set.is_follow);
            $(".is_log").prop('checked', set.is_log);
            /* 登录暗号                                      */
            $(".is_manager_login").prop('checked', set.is_manager_login);
            $(".manager_maxSize").val(set.manager_maxSize);
            /**/
            $(".is_online").prop('checked', set.is_online);
            $(".is_sh").prop('checked', set.is_sh);
            $(".is_dosign").prop('checked', set.is_dosign);
            $(".sign_time").val(set.sign_time);
            $(".thankgift_is_open").prop('checked', set.thank_gift.is_open);
            $(".thankgift_is_live_open").prop('checked',
                set.thank_gift.is_live_open);
            $(".thankgift_is_tx_shield").prop('checked',
                set.thank_gift.is_tx_shield);
            $(".thankgift_is_num").prop('checked',
                set.thank_gift.is_num);
            $(".thankgift_shield_status").find("option").eq(
                set.thank_gift.shield_status).prop('selected', true);
            $(".thankgift_shield").val(method.giftStrings_metod(set.thank_gift.giftStrings));
            $(".thankgift_codeStrings").val(method.codeStrings_metod(set.thank_gift.codeStrings));
            method.shieldgifts_each(set.thank_gift.thankGiftRuleSets);
            method.replys_each(set.reply.autoReplySets);
            // $(".thankgift_thankGiftRuleSets").val(
            // set.thank_gift.thankGiftRuleSets);// test
            $(".thankgift_thank_status").find("option").eq(
                set.thank_gift.thank_status).prop('selected', true);
            $(".thankgift_num").val(set.thank_gift.num);
            $(".thankgift_delaytime").val(set.thank_gift.delaytime);
            $(".thankgift_thank").val(set.thank_gift.thank);
            $(".thankgift_is_guard_report").prop('checked',
                set.thank_gift.is_guard_report);
            $(".thankgift_is_gift_code").prop('checked',
                set.thank_gift.is_gift_code);
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
            $(".welcome_is_open").prop('checked', set.welcome.is_open);
            $(".welcome_is_live_open").prop('checked', set.welcome.is_live_open);
            $(".welcome_tx_shield").prop('checked', set.welcome.is_tx_shield);
            $(".welcome_num").val(set.welcome.num);
            $(".welcome_welcomes").val(set.welcome.welcomes);
            $(".thankwelcome_delaytime").val(set.welcome.delaytime);
            $(".replys_is_open").prop('checked',
                set.reply.is_open);
            $(".replys_is_live_open").prop('checked',
                set.reply.is_live_open);
            $(".replys_time").val(set.reply.time);
            $(".is_clockin").prop('checked', set.clock_in.is_open);
            $(".clockin_time").val(set.clock_in.time);
            $(".clockin_barrage").val(set.clock_in.barrage);
            $(".is_autoGift_open").prop('checked',set.auto_gift.is_open);
            $(".autoGift_time").val(set.auto_gift.time);
            $(".autoGift_roomids").val(set.auto_gift.room_id);
            $(".is_privacy_open").prop('checked',set.privacy.is_open);
            $(".privacy_heart_url").val(set.privacy.small_heart_url);

            /* 处理？ */
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
                            'title',
                            '模式:单人单种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/> <span class=\'red-font\'>%uName%</span>送礼人名称<br/><span class=\'red-font\'>%Type%</span>赠送类型<br/><span class=\'red-font\'>%GiftName%</span>礼物名称<br/><span class=\'red-font\'>%Num%</span>礼物数量');
                    break;
                case 2:
                    $(".thankgift_thank").attr('placeholder',
                        "感謝%uName%贈送的%Gifts%~");
                    $(".thankgift_thank")
                        .attr('title',
                            '模式:单人多种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/> <span class=\'red-font\'>%uName%</span>送礼人名称<br/><span class=\'red-font\'>%Gifts%</span>礼物和数量的集合以逗号隔开');
                    break
                case 3:
                    $(".thankgift_thank").attr('placeholder',
                        "感謝%uNames%贈送的%Gifts%~");
                    $(".thankgift_thank")
                        .attr('title',
                            '模式:多人多种<br/>多条语句时候注意以回车为分割每条语句,多条语句会随机发送其中一条<br/>感谢语，可选参数<br/> <span class=\'red-font\'>%uNames%</span>送礼人名称集合<br/><span class=\'red-font\'>%Gifts%</span>礼物和数量的集合以逗号隔开');
                    break
                default:
                    break;
            }
            if ($(".thankgift_is_guard_report").is(':checked')) {
                $(".thankgift_report").show();
                $(".thankgift_barrageReport").show();
                if ($(".thankgift_is_gift_code").is(':checked')) {
                    $(".thankgift_codeStrings").show();
                }
            } else {
                $(".thankgift_report").hide();
                $(".thankgift_barrageReport").hide();
                $(".thankgift_codeStrings").hide();
            }
            if ($(".thankgift_is_guard_report").is(':checked')) {
                $(".thankgift_is_gift_code").attr("disabled", false);
            } else {
                $(".thankgift_is_gift_code").attr("disabled", true);
                $(".thankgift_is_gift_code").prop('checked', false);
            }
            if ($(".thankgift_is_gift_code").is(':checked') && $(".thankgift_is_guard_report").is(':checked')) {
                $(".thankgift_codeStrings").show();
            } else {
                $(".thankgift_codeStrings").hide();
            }
            if ($(".is_online").is(':checked')) {
                $(".is_sh").prop('checked', set.is_sh);
                $(".is_sh").attr("disabled", false);
            } else {
                $(".is_sh").attr("disabled", true);
                $(".is_sh").prop('checked', false);
            }
            if ($(".is_clockin").is(':checked')) {
                $(".clockin_barrage").show();
                $(".clockin_time").attr("disabled", false);
            } else {
                $(".clockin_barrage").hide();
                $(".clockin_time").attr("disabled", true);
            }
            if ($(".is_dosign").is(':checked')) {
                $(".sign_time").attr("disabled", false);
            }else{
                $(".sign_time").attr("disabled", true);
            }
            if (!$(".card-body").find(".logined").length > 0) {
                $(".is_online").attr("disabled", true);
                $(".is_dosign").attr("disabled", true);
                $(".sign_time").attr("disabled", true);
                $(".is_sh").attr("disabled", true);
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
                $(".thankgift_is_gift_code").attr("disabled", true);
                $(".thankgift_codeStrings").attr("disabled", true);
                $(".thankgift_is_guard_local").attr("disabled", true);
                $(".thankgift_report").attr("disabled", true);
                $("#gift-shield-btn").attr("disabled", true);
                $(".advert_is_open").attr("disabled", true);
                $(".advert_is_live_open").attr("disabled", true);
                $(".advert_status").attr("disabled", true);
                $(".advert_time").attr("disabled", true);
                $(".advert_adverts").attr("disabled", true);
                $(".follow_is_open").attr("disabled", true);
                $(".follow_is_live_open").attr("disabled", true);
                $(".follow_num").attr("disabled", true);
                $(".follow_follows").attr("disabled", true);
                $(".follow_tx_shield").attr("disabled", true);
                $(".thankfollow_delaytime").attr("disabled", true);
                $(".welcome_is_open").attr("disabled", true);
                $(".welcome_is_live_open").attr("disabled", true);
                $(".welcome_num").attr("disabled", true);
                $(".welcome_welcomes").attr("disabled", true);
                $(".welcome_tx_shield").attr("disabled", true);
                $(".thankwelcome_delaytime").attr("disabled", true);
                $(".shieldgift_delete").attr("disabled", true);
                $(".thankgift_barrageReport").attr("disabled", true);
                $(".thankgift_is_num").attr("disabled", true);
                $(".replys_is_open").attr("disabled", true);
                $(".replys_is_live_open").attr("disabled", true);
                $(".replys_time").attr("disabled", true);
                $("#replys-btn").attr("disabled", true);
                $(".is_clockin").attr("disabled", true);
                $(".clockin_time").attr("disabled", true);
                $(".clockin_barrage").attr("disabled", true);
            }
        }
        return set;
    },
    time_parse:function (t){
      if(t==null||t.trim()=="")return "00:30:00";
      let ts = t.split(":");
      if(ts.length==2){
          t = t + ":00";
      }
      return t;
    },
    wrap_replace: function (d) {
        "use strict";
        if (d.trim() !== null && d.trim() !== "") {
            let rc = d.replace(/\n/g, '').replace(/\r/g, '');
            // rc = rc.replace(/_#_@/g, '<br/>');
            // rc = rc.replace(/_@/g, '<br/>');
            // rc = rc.replace(/\s/g, '&nbsp;');
            return rc;
        } else {
            return d;
        }
    },
    delay_method: function (e, s) {
        "use strict";
        if (!$(e).is(":visible")) {
            $(e).show();
            $(e).html(s);
            setTimeout(function () {
                $(e).hide();
            }, 3000);
        }
    },
    giftStrings_metod: function (lists) {
        let s = "";
        if (lists != null) {
            s = lists.join("，");
        }
        return s;
    },
    codeStrings_metod: function (lists) {
        let s = "";
        if (lists != null) {
            s = lists.join("\n");
        }
        return s;
    },
    giftStrings_handle: function (lists, s) {
        if (s != "") {
            if (s.indexOf("，") >= 0) {
                let ss = s.split("，");
                for (let gs in ss) {
                    if (ss[gs].trim() != "") {
                        lists.push(ss[gs]);
                    }
                }
            } else {
                lists.push(s);
            }
        }
        return lists;
    },
    codeStrings_handle: function (lists, s) {
        if (s != "") {
            if (s.indexOf("\n") >= 0) {
                let ss = s.split("\n");
                for (let gs in ss) {
                    if (ss[gs].trim() != "") {
                        lists.push(ss[gs]);
                    }
                }
            } else {
                lists.push(s);
            }
        };
        return lists;
    },
    shieldgifts_each: function (lists) {
        if (lists != null) {
            $(".shieldgifts-tbody").children('tr').remove();
            for (let i in lists) {
                $(".shieldgifts-tbody")
                    .append(
                        "<tr><td><input type='checkbox' class='shieldgifts_open' data-bs-toggle='tooltip' data-bs-placement='top' title='是否开启' data-original-title='是否开启'></td><td><input class='small-input shieldgifts_name' value='"
                        + lists[i].gift_name
                        + "' placeholder='礼物名称' data-bs-toggle='tooltip' data-bs-placement='top' title='礼物名称' data-bs-html='true' data-original-title='礼物名称'></td><td><select class='custom-select-sm shieldgifts_status' data-bs-toggle='tooltip' data-bs-placement='top' title='选择类型' data-bs-html='true' data-original-title='选择类型'><option value='1' selected='selected'>数量</option><option value='2'>瓜子</option></select></td><td><input type='number' min='0' value='"
                        + lists[i].num
                        + "' class='small-input shieldgifts_num' placeholder='num' value='0' data-bs-toggle='tooltip' data-bs-placement='top' title='大于多少(不得小于' data-bs-html='true' data-original-title='大于多少(不得小于)'></td><td><button type='button' class='btn btn-danger btn-sm shieldgift_delete'>删除</button></td></tr>");
                $(".shieldgifts_open").eq(i).prop('checked', lists[i].is_open);
                $(".shieldgifts_status").eq(i).find("option").eq(
                    lists[i].status).prop('selected', true);
            }
        }
    },
    replys_each: function (lists) {
        if (lists != null) {
            $(".replys-ul").children("li").remove();
            for (let i in lists) {
                $(".replys-ul")
                    .append(
                        `<li><input type='checkbox' class='reply_open'
					data-bs-toggle='tooltip' data-bs-placement='top' title='是否开启'
					data-original-title='是否开启'> 
					<input type='checkbox' class='reply_oc'
						data-bs-toggle='tooltip' data-bs-placement='top' title='是否精确匹配'
						data-original-title='是否精确匹配'> 
					<input class='small-input reply_keywords' placeholder='关键字'
					data-bs-toggle='tooltip' data-bs-placement='top' title='不能编辑:多个关键字,以中文逗号隔开'
					data-bs-html='true' data-original-title='关键字' readonly='readonly' disabled>
					<input class='small-input reply_shields' placeholder='屏蔽词'
					data-bs-toggle='tooltip' data-bs-placement='top' title='不能编辑:多个屏蔽词,以中文逗号隔开'
					data-bs-html='true' data-original-title='关键字' readonly='readonly' disabled>
					<input class='big-input reply_rs' placeholder='回复语句'
					data-bs-toggle='tooltip' data-bs-placement='top' title='不能编辑:回复语句,提供%AT%参数,以打印:@提问问题人名称'
					data-bs-html='true' data-original-title='回复语句' readonly='readonly' disabled>
					<span class='reply-btns'>
					<button type='button' class='btn btn-success btn-sm reply_edit'>编辑</button>
					<button type='button' class='btn btn-danger btn-sm reply_delete'>删除</button>
					</span>
				</li>`);
// $("#replys-ul").append(
// "<li><input type='checkbox' class='reply_open' data-bs-toggle='tooltip'
// data-bs-placement='top' title='是否开启' data-original-title='是否开启'> "
// +"<input class='small-input reply_keywords' placeholder='关键字'
// data-bs-toggle='tooltip' data-bs-placement='top' title='不能编辑:多个关键字,以中文逗号隔开'
// data-original-title='关键字' readonly='readonly' value='"
// +method.giftStrings_metod(lists[i].keywords)+"' disabled/>"
// +"<input class='small-input reply_shields' placeholder='屏蔽词'
// data-bs-toggle='tooltip' data-bs-placement='top' title='不能编辑:多个屏蔽词,以中文逗号隔开'
// data-original-title='屏蔽词' readonly='readonly' value='"
// +method.giftStrings_metod(lists[i].shields)+"' disabled/>"
// +"<input class='big-input reply_rs' placeholder='回复语句' readonly='readonly'
// value='"
// +lists[i].reply+"' disabled/>"
// +"<span class='reply-btns'><button type='button' class='btn btn-success
// btn-sm reply_edit'>编辑</button><button type='button' class='btn btn-danger
// btn-sm reply_delete'>删除</button></span></li>");
                $(".reply_open").eq(i).prop('checked', lists[i].is_open);
                $(".reply_oc").eq(i).prop('checked', lists[i].is_accurate);
                $(".reply_keywords").eq(i).val(method.giftStrings_metod(lists[i].keywords));
                $(".reply_shields").eq(i).val(method.giftStrings_metod(lists[i].shields));
                $(".reply_rs").eq(i).val(lists[i].reply);
            }
        }
    },
    //隐私模式版本后移除
    // getIp: function () {
    //     let ip = null;
    //     $.ajax({
    //         url: '../getIp',
    //         async: false,
    //         cache: false,
    //         type: 'GET',
    //         dataType: 'json',
    //         success: function (data) {
    //             if (data.code == "200") {
    //                 ip = data.result
    //             }
    //         }
    //     });
    //     return ip;
    // },
    delBlock: function (id) {
        $.ajax({
            url: '../del_block?id=' + id,
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.result == 0) {
                    alert("撤销禁言成功");
                    method.getBlocks(1);
                } else {
                    alert("撤销禁言失败");
                }
            }
        });
    },
    getBlocks: function (page) {
        let ip = true;
        $.ajax({
            url: '../blocks?page=' + page,
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    if (data.result == null || data.result.length < 1) {
                        ip = false;
                    } else {
                        $('.list-row').remove();
                    }
                    if (ip) {
                        for (let i of data.result) {
                            $('.list-body').append(
                                ` <tr class="list-row">
                        <td width="35%" class="list-unit"><p class="user-name-col">` + i.uname + `</p></td>
                        <td width="44%" class="list-unit">` + i.block_end_time + `</td>
                        <td width="21%" class="list-unit pointer no-select"><span class="del_block_click" data-id="` + i.id + `">撤销</span>
                        </td>
                    </tr>`
                            );
                        }
                    }
                }
            }
        });
        return ip;
    },
    setExprot: function () {
        $.ajax({
            url: '../setExport',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.result == 0) {
                    alert("配置导出成功,位置位于弹幕姬目录set文件夹下");
                } else {
                    alert("配置导出失败,请联系开发人员");
                }
            }
        });
    },
    setExprotWeb:function (){
        window.open(window.location.origin+"/setExportWeb");
    },
//导入附件
    importDfFile: function () {
        let formData = new FormData();
        // 获取上传文件的数据
        formData.append('file', $("#file")[0].files[0]);
        $.ajax({
            url: "../setImport",
            type: 'post',
            async: false,
            processData: false,// 将数据转换成对象，不对数据做处理，故 processData: false
            contentType: false,    // 不设置数据类型
            data: formData,
            success: function (data) {
                if (data.result == 0) {
                    alert("配置导入成功");
                } else if (data.result == 2) {
                    alert("配置导入失败文件名称应为.json结尾");
                } else {
                    alert("配置导入失败,请联系开发人员");
                }
            },
            error: function (data) {
            }
        })
        $("#file").val("");
    },

    replaceThanko: function (s) {
        s = s.replace(/uNames/g, "uName");
        s = s.replace(/\%Gifts\%/g, "%GiftName% x%Num%");
        return s;
    },
    replaceThankt: function (s) {
        s = s.replace(/uNames/g, "uName");
        s = s.replace(/\%GiftName\% x\%Num\%/g, "%Gifts%");
        return s;
    },
    replaceThankts: function (s) {
        if (s.indexOf("uNames") === -1) {
            s = s.replace(/uName/g, "uNames");
        }
        s = s.replace(/\%GiftName\% x\%Num\%/g, "%Gifts%");
        return s;
    },
    checkUpdate: function () {
        "use strict";
        let deferred = $.Deferred();
        $.ajax({
            url: '../checkupdate',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                deferred.resolve(data);
            }
        });
        return deferred.promise();
    },
    checkWebInit: function () {
        let content = {init_edition:false,init_announce:false};
        $.ajax({
            url: '../checkWebInit',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    content.init_edition = data.result.init_edition;
                    content.init_announce = data.result.init_announce;
                }
            }
        });
        return content;
    },
    getAnnounce: function () {
        let content = "";
        $.ajax({
            url: '../checkNewAnnounce',
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    content = data.result;
                }
            }
        });
        return content;
    },
    block: function (uid, time) {
        let code = null;
        $.ajax({
            url: '../block',
            data: {
                uid: uid,
                time, time
            },
            async: false,
            cache: false,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                if (data.code == "200") {
                    code = data.result
                }
            }
        });
        return code;
    },
};

function openSocket(socket, ip, sliceh) {
    if (typeof (WebSocket) == "undefined") {
        alert("您的浏览器不支持WebSocket，显示弹幕功能异常，请升级你的浏览器版本，推荐谷歌，网页显示弹幕失败 但不影响其他功能使用");
    } else {
        console.log("弹幕服务器正在连接");
        let socketUrl = ip;
        if (socket != null) {
            socket.close();
            socket = null;
        }
        try {
            socket = new WebSocket(socketUrl);
        } catch (err) {
            console.log(err);
        }
        // 打开事件
        socket.onopen = function () {
            $("#danmu").append("<div class='danmu-child'>连接成功<div/>");
            console.log("连接已打开");
        };
        // 获得消息事件
        socket.onmessage = function (msg) {
            // 发现消息进入 开始处理前端触发逻辑
            let data = JSON.parse(msg.data);
            if (data.cmd === "cmdp") {
                if ($("#danmu").children().length > 99) {
                    $("#danmu").children().first().remove();
                    $("#danmu").children("div:last-child").after(
                        "<div class='danmu-child'>" + data.result + "</div>");
                } else {
                    $("#danmu").append("<div class='danmu-child'>" + data.result + "</div>");
                }
                let lastNodeH = $(".danmu-child").eq(-1).height();
                let lasttNodeH = $(".danmu-child").eq(-2).height();
                if (Math.abs(lastNodeH - lasttNodeH) > sliceh) {
                    sliceh = Math.abs(lastNodeH - lasttNodeH);
                }
                if ($('#danmu')[0].scrollHeight - $("#danmu").scrollTop() <= (804 + sliceh)) {
                    let h = $("div[class='danmu-child']:last").height();
                    let top = $("div[class='danmu-child']:last").offset().top + h + 6;
                    $("#danmu").scrollTop($("#danmu").scrollTop() + top);
// $('#danmu').scrollTop($('#danmu')[0].scrollHeight);
                }
            } else {
                if ($("#danmu").children().length > 99) {
                    $("#danmu").children().first().remove();
                    $("#danmu").children("div:last-child").after(
                        danmuku.danmu(data.cmd, data.result));
                } else {
                    $("#danmu").append(danmuku.danmu(data.cmd, data.result));
                }
                let lastNodeH = $(".danmu-child").eq(-1).height();
                let lasttNodeH = $(".danmu-child").eq(-2).height();
                if (Math.abs(lastNodeH - lasttNodeH) > sliceh) {
                    sliceh = Math.abs(lastNodeH - lasttNodeH);
                }
                if ($('#danmu')[0].scrollHeight - $("#danmu").scrollTop() <= (804 + sliceh)) {
                    let h = $("div[class='danmu-child']:last").height();
                    let top = $("div[class='danmu-child']:last").offset().top + h + 6;
                    $("#danmu").scrollTop($("#danmu").scrollTop() + top);
                    // $('#danmu').scrollTop($('#danmu')[0].scrollHeight);
                }
            }
        };
        // 关闭事件
        socket.onclose = function () {
            $("#danmu").append("<div class='danmu-child'>连接已关闭，网页显示弹幕失败 但不影响其他功能使用<div/>");
            console.log("连接已关闭，网页显示弹幕失败 但不影响其他功能使用");
        };
        // 发生了错误事件
        socket.onerror = function () {
            $("#danmu").append("<div class='danmu-child'>连接到弹幕服务器发生了错误,请刷新网页并确认地址正确无误后再次连接尝试<div/>");
            console.log("连接到弹幕服务器发生了错误，网页显示弹幕失败 但不影响其他功能使用");
        }
    }
}

function sendMessage() {
    if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket，网页显示弹幕失败 但不影响其他功能使用");
    } else {
        console.log("您的浏览器支持WebSocket");
        socket.send('{"toUserId":"' + $("#toUserId").val()
            + '","contentText":"' + $("#contentText").val() + '"}');
    }
}

function add0(m) {
    return m < 10 ? '0' + m : m
}

function addSpace(m) {
    return m < 10 ? ' ' + m : m
}

function format(timestamp, flag) {
    let time = new Date(parseInt(timestamp));
    let y = time.getFullYear();
    let m = time.getMonth() + 1;
    let d = time.getDate();
    let h = time.getHours();
    let mm = time.getMinutes();
    let s = time.getSeconds();
    if (flag) {
        return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
    } else {
        return add0(h) + ':' + add0(mm) + ':' + add0(s);
    }
}

function getTimestamp() {
    return (new Date()).getTime();
}