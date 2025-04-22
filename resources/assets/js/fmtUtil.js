const $vld_nm				= ("((^[가-힣]{2,18}$)|(^[a-zA-Z ]{2,}$))");
const $vld_email			= ("(^[0-9a-zA-Z._-]{4,20}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+$)|(^[0-9a-zA-Z._-]{4,20}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+[.][a-zA-Z]+$)");
const $vld_id				= ("^[0-9a-z]{4,20}$");
const $vld_paspt			= ("^[mMsSrRoOdD][0-9]{7}$");
const $vld_ip				= ("^[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}$");
const $vld_ssn				= ("(^[0-9]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28)|(29)|(30)|(31))]{2}[-][1-4]{1}[0-9]{6}$)|(^[0-9]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28)|(29)|(30)|(31))]{2}[1-4]{1}[0-9]{6}$)");
const $vld_cph				= ("(^[((010)|(017)|(019))]{3}[-][0-9]{4}[-][0-9]{4}$)|(^[((010)|(017)|(019))]{3}[0-9]{8}$)");
const $vld_card				= ("(^[0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}$)|(^[0-9]{16}$)");
const $vld_mmyy				= ("(^[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[0-9]{2}$)|(^[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[/][0-9]{2}$)");
const $vld_drvg				= ("(^[((11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28))]{2}[-][0-9]{2}[-][0-9]{6}[-][0-9]{2}$)|(^[((11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28))]{2}[0-9]{2}[0-9]{6}[0-9]{2}$)");
const $vld_car				= ("^[0-9]{2}[가-힣][0-9]{4}$");
const $vld_bizr				= ("(^[1-9]{1}[0-9]{2}[0-9]{2}[0-9]{5}$)|(^[1-9]{1}[0-9]{2}[-][0-9]{2}[-][0-9]{5}$)");
const $vld_acct				= ("(^[0-9-]{7,19}[0-9]$)|(^[0-9]{8,20}$)");
const $vld_pst				= ("(^[0-9]{5}$)|(^[0-9]{3}[-][0-9]{3}$)");
const $vld_int				= ("(^[0]$)|(^[1-9]{1}[0-9]+$)");
const $vld_addr				= ("^[0-9a-zA-Z가-힣#*)(_+=,.' -]+$");
const $vld_addr_dtl			= ("^[0-9a-zA-Z가-힣#*)(_+=,.' -]+$");
const $reg_nm				= (new RegExp($vld_nm));
const $reg_email			= (new RegExp($vld_email));
const $reg_id				= (new RegExp($vld_id));
const $reg_paspt			= (new RegExp($vld_paspt));
const $reg_ip				= (new RegExp($vld_ip));
const $reg_ssn				= (new RegExp($vld_ssn));
const $reg_cph				= (new RegExp($vld_cph));
const $reg_card				= (new RegExp($vld_card));
const $reg_mmyy				= (new RegExp($vld_mmyy));
const $reg_drvg				= (new RegExp($vld_drvg));
const $reg_car				= (new RegExp($vld_car));
const $reg_bizr				= (new RegExp($vld_bizr));
const $reg_acct				= (new RegExp($vld_acct));
const $reg_pst				= (new RegExp($vld_pst));
const $reg_int				= (new RegExp($vld_int));
const $reg_addr				= (new RegExp($vld_addr));
const $reg_addr_dtl			= (new RegExp($vld_addr_dtl));
const $rplc_nm				= (/[^a-zA-Zㄱ-ㅎ가-힣 ]/g);
const $rplc_email			= (/[^0-9a-zA-Z@._-]/g);
const $rplc_id				= (/[^0-9a-z]/g);
const $rplc_paspt			= (/[^0-9mMsSrRoOdD]/g);
const $rplc_ip				= (/[^0-9.]/g);
const $rplc_ssn				= (/[^0-9]/g);
const $rplc_cph				= (/[^0-9]/g);
const $rplc_card			= (/[^0-9]/g);
const $rplc_mmyy			= (/[^0-9]/g);
const $rplc_drvg			= (/[^0-9]/g);
const $rplc_car				= (/[^0-9ㄱ-ㅎ가-힣]/g);
const $rplc_bizr			= (/[^0-9]/g);
const $rplc_acct			= (/[^0-9-]/g);
const $rplc_pst				= (/[^0-9-]/g);
const $rplc_int				= (/[^0-9]/g);
const $rplc_addr			= (/[^0-9a-zA-Zㄱ-ㅎ가-힣#*)(_+=,.'\s-]/g);
const $rplc_addr_dtl		= (/[^0-9a-zA-Zㄱ-ㅎ가-힣#*)(_+=,.'\s-]/g);
const fmtUtil = {
	fn_vld_nm				: function(vo) {return ($reg_nm.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_email			: function(vo) {return ($reg_email.test(	(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_id				: function(vo) {return ($reg_id.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_paspt			: function(vo) {return ($reg_paspt.test(	(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_ip				: function(vo) {return ($reg_ip.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_ssn				: function(vo) {return ($reg_ssn.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_cph				: function(vo) {return ($reg_cph.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_card				: function(vo) {return ($reg_card.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_mmyy				: function(vo) {return ($reg_mmyy.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_drvg				: function(vo) {return ($reg_drvg.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_car				: function(vo) {return ($reg_car.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_bizr				: function(vo) {return ($reg_bizr.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_acct				: function(vo) {return ($reg_acct.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_pst				: function(vo) {return ($reg_pst.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_int				: function(vo) {return ($reg_int.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_addr				: function(vo) {return ($reg_addr.test(		(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_vld_addr_dtl			: function(vo) {return ($reg_addr_dtl.test(	(typeof vo == "string") ? (vo) : ($(vo).val())));},
	fn_rplc_nm				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_nm,			"").replace(/[\s][\s]/g, " ")							);},
	fn_rplc_email			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_email,		"").replace(/[\@][\@]/g, "@").replace(/[\.][\.]/g, ".")	);},
	fn_rplc_id				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_id,			"")														);},
	fn_rplc_paspt			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_paspt,		"")														);},
	fn_rplc_ip				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_ip,			"").replace(/[\.][\.]/g, ".")							);},
	fn_rplc_ssn				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_ssn,			"")														);},
	fn_rplc_cph				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_cph,			"")														);},
	fn_rplc_card			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_card,		"").replace(/[\-][\-]/g, "-")							);},
	fn_rplc_mmyy			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_mmyy,		"")														);},
	fn_rplc_drvg			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_drvg,		"").replace(/[\-][\-]/g, "-")							);},
	fn_rplc_car				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_car,			"")														);},
	fn_rplc_bizr			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_bizr,		"").replace(/[\-][\-]/g, "-")							);},
	fn_rplc_acct			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_acct,		"").replace(/[\-][\-]/g, "-")							);},
	fn_rplc_pst				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_pst,			"").replace(/[\-][\-]/g, "-")							);},
	fn_rplc_int				: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_int,			"").replace(/(^0+)/g, ""	)							);},
	fn_rplc_addr			: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_addr,		"").replace(/[\s][\s]/g, " ")							);},
	fn_rplc_addr_dtl		: function(vo) {return (((typeof vo == "object") ? ($(vo).val()) : (vo)).replace($rplc_addr_dtl,	"").replace(/[\s][\s]/g, " ")							);},
	fn_input_nm				: function(vo) {$(vo).val(fmtUtil.fn_rplc_nm(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "30"));(fmtUtil.fn_vld(vo, $vld_nm));																	},
	fn_input_email			: function(vo) {$(vo).val(fmtUtil.fn_rplc_email(	vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "50"));(fmtUtil.fn_vld(vo, $vld_email));																},
	fn_input_id				: function(vo) {$(vo).val(fmtUtil.fn_rplc_id(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "20"));(fmtUtil.fn_vld(vo, $vld_id));																	},
	fn_input_paspt			: function(vo) {$(vo).val(fmtUtil.fn_rplc_paspt(	vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "8" ));(fmtUtil.fn_vld(vo, $vld_paspt));																},
	fn_input_ip				: function(vo) {$(vo).val(fmtUtil.fn_rplc_ip(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "15"));(fmtUtil.fn_vld(vo, $vld_ip));	(fmtUtil.fn_msg(vo, "xxx.xxx.xxx.xxx 형태로 입력해 주세요."));		},
	fn_input_ssn			: function(vo) {$(vo).val(fmtUtil.fn_rplc_ssn(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "13"));(fmtUtil.fn_vld(vo, $vld_ssn));	(fmtUtil.fn_msg(vo, "xxxxxx-nnnnnn 형태로 입력해 주세요."));			},
	fn_input_cph			: function(vo) {$(vo).val(fmtUtil.fn_rplc_cph(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "11"));(fmtUtil.fn_vld(vo, $vld_cph));	(fmtUtil.fn_msg(vo, "010-xxxx-nnnn 형태로 입력해 주세요."));			},
	fn_input_card			: function(vo) {$(vo).val(fmtUtil.fn_rplc_card(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "16"));(fmtUtil.fn_vld(vo, $vld_card));	(fmtUtil.fn_msg(vo, "xxxx-nnnn-xxxx-nnnn 형태로 입력해 주세요."));	},
	fn_input_mmyy			: function(vo) {$(vo).val(fmtUtil.fn_rplc_mmyy(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "4" ));(fmtUtil.fn_vld(vo, $vld_mmyy));	(fmtUtil.fn_msg(vo, "mm/yy 형태로 입력해 주세요."));					},
	fn_input_drvg			: function(vo) {$(vo).val(fmtUtil.fn_rplc_drvg(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "12"));(fmtUtil.fn_vld(vo, $vld_drvg));	(fmtUtil.fn_msg(vo, "xx-nn-xxxxxx-nn 형태로 입력해 주세요."));		},
	fn_input_car			: function(vo) {$(vo).val(fmtUtil.fn_rplc_car(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "7" ));(fmtUtil.fn_vld(vo, $vld_car));																	},
	fn_input_bizr			: function(vo) {$(vo).val(fmtUtil.fn_rplc_bizr(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "10"));(fmtUtil.fn_vld(vo, $vld_bizr));	(fmtUtil.fn_msg(vo, "xxx-nn-xxxxx 형태로 입력해 주세요."));			},
	fn_input_acct			: function(vo) {$(vo).val(fmtUtil.fn_rplc_acct(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "30"));(fmtUtil.fn_vld(vo, $vld_acct));																	},
	fn_input_pst			: function(vo) {$(vo).val(fmtUtil.fn_rplc_pst(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "7" ));(fmtUtil.fn_vld(vo, $vld_pst));																	},
	fn_input_int			: function(vo) {$(vo).val(fmtUtil.fn_rplc_int(		vo));						(fmtUtil.fn_max(vo, "9" ));(fmtUtil.fn_vld(vo, $vld_int));																	},
	fn_input_addr			: function(vo) {$(vo).val(fmtUtil.fn_rplc_addr(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "50"));																									},
	fn_input_addr_dtl		: function(vo) {$(vo).val(fmtUtil.fn_rplc_addr_dtl(	vo));						(fmtUtil.fn_max(vo, "99"));																									},
	fn_fmt_ssn				: function(vo) {return (fmtUtil.fn_fmt(fmtUtil.fn_rplc_ssn(vo), 6, "-"));														},
	fn_fmt_cph				: function(vo) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_cph(vo), 3, "-"), (8), "-"));								},
	fn_fmt_card				: function(vo) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_card(vo), 4, "-"), (9), "-"), (14), "-"));	},
	fn_fmt_mmyy				: function(vo) {return (fmtUtil.fn_fmt((fmtUtil.fn_rplc_mmyy(vo)), 2, "/"));													},
	fn_fmt_drvg				: function(vo) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_drvg(vo), 2, "-"), (5), "-"), (12), "-"));	},
	fn_fmt_bizr				: function(vo) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_bizr(vo), 3, "-"), (6), "-"));							},
	fn_fmt_int				: function(vo) {return (fmtUtil.fn_rplc_int(vo).replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));								},
	fn_input_fmt_ssn		: function(vo) {$(vo).val(fmtUtil.fn_fmt_ssn(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "14"));(fmtUtil.fn_vld(vo, $vld_ssn));	(fmtUtil.fn_msg(vo, "xxxxxx-nnnnnn 형태로 입력해 주세요."));			(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_ssn(vo)));},
	fn_input_fmt_cph		: function(vo) {$(vo).val(fmtUtil.fn_fmt_cph(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "13"));(fmtUtil.fn_vld(vo, $vld_cph));	(fmtUtil.fn_msg(vo, "010-xxxx-nnnn 형태로 입력해 주세요."));			(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_cph(vo)));},
	fn_input_fmt_card		: function(vo) {$(vo).val(fmtUtil.fn_fmt_card(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "19"));(fmtUtil.fn_vld(vo, $vld_card));	(fmtUtil.fn_msg(vo, "xxxx-nnnn-xxxx-nnnn 형태로 입력해 주세요."));	(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_card(vo)));},
	fn_input_fmt_mmyy		: function(vo) {$(vo).val(fmtUtil.fn_fmt_mmyy(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "5" ));(fmtUtil.fn_vld(vo, $vld_mmyy));	(fmtUtil.fn_msg(vo, "mm/yy 형태로 입력해 주세요."));					(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_mmyy(vo)));},
	fn_input_fmt_drvg		: function(vo) {$(vo).val(fmtUtil.fn_fmt_drvg(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "15"));(fmtUtil.fn_vld(vo, $vld_drvg));	(fmtUtil.fn_msg(vo, "xx-nn-xxxxxx-nn 형태로 입력해 주세요."));		(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_drvg(vo)));},
	fn_input_fmt_bizr		: function(vo) {$(vo).val(fmtUtil.fn_fmt_bizr(		vo));(fmtUtil.fn_req(vo));	(fmtUtil.fn_max(vo, "12"));(fmtUtil.fn_vld(vo, $vld_bizr));	(fmtUtil.fn_msg(vo, "xxx-nn-xxxxx 형태로 입력해 주세요."));			(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_bizr(vo)));},
	fn_input_fmt_int		: function(vo) {$(vo).val(fmtUtil.fn_fmt_int(		vo));						(fmtUtil.fn_max(vo, "12"));(fmtUtil.fn_vld(vo, $vld_int));																	(fmtUtil.fn_rplc(vo, fmtUtil.fn_rplc_int(vo)));},
	fn_fmt					: function(v, len, rplc) {
		if ((v.length) > len) {
			var lst = (v.substring(len));
			if (lst != rplc) {
				v = ((v.substring(0, len)) + (rplc) + (lst));
			}
		}
		return (v.replace(((rplc) + (rplc)), rplc));
	},
	fn_req					: function(o)		{((!o.hasAttribute("required")) ? ($(o).prop("required", true)) : (""));},
	fn_max					: function(o, max)	{((!o.hasAttribute("maxlength") || $(o).prop("maxlength") != max) ? ($(o).prop("maxlength", max)) : (""));},
	fn_vld					: function(o, vld)	{((!o.hasAttribute("vld") || $(o).prop("vld") != vld) ? ($(o).attr("vld", vld)) : (""));},	
	fn_rplc					: function(o, rplc)	{((!o.hasAttribute("rplc") || $(o).prop("rplc") != rplc) ? ($(o).attr("rplc", rplc)) : (""));},
	fn_msg					: function(o, msg) 	{((!o.hasAttribute("msg") || $(o).attr("msg") != msg) ? ($(o).attr("msg", msg)) : (""));},
	fn_reset				: function() {
		$("input[type=text].input_is_nm"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_nm(this));			});
		$("input[type=text].input_is_email"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_email(this));		});
		$("input[type=text].input_is_id"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_id(this));			});
		$("input[type=text].input_is_paspt"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_paspt(this));		});
		$("input[type=text].input_is_ip"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_ip(this));			});
		$("input[type=text].input_is_ssn"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_ssn(this));		});
		$("input[type=text].input_is_cph"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_cph(this));		});
		$("input[type=text].input_is_card"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_card(this));		});
		$("input[type=text].input_is_mmyy"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_mmyy(this));		});
		$("input[type=text].input_is_drvg"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_drvg(this));		});
		$("input[type=text].input_is_car"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_car(this));		});
		$("input[type=text].input_is_bizr"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_bizr(this));		});
		$("input[type=text].input_is_acct"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_acct(this));		});
		$("input[type=text].input_is_pst"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_pst(this));		});
		$("input[type=text].input_is_int"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_int(this));		});
		$("input[type=text].input_is_addr"		).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_addr(this));		});
		$("input[type=text].input_is_addr_dtl"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_addr_dtl(this));	});
		$("input[type=text].input_is_fmt_ssn"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_ssn(this));	});
		$("input[type=text].input_is_fmt_cph"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_cph(this));	});
		$("input[type=text].input_is_fmt_card"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_card(this));	});
		$("input[type=text].input_is_fmt_mmyy"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_mmyy(this));	});
		$("input[type=text].input_is_fmt_drvg"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_drvg(this));	});
		$("input[type=text].input_is_fmt_bizr"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_bizr(this));	});
		$("input[type=text].input_is_fmt_int"	).each(			function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_int(this));	});
		$("input[type=text].input_is_nm"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_nm(this));			});
		$("input[type=text].input_is_email"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_email(this));		});
		$("input[type=text].input_is_id"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_id(this));			});
		$("input[type=text].input_is_paspt"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_paspt(this));		});
		$("input[type=text].input_is_ip"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_ip(this));			});
		$("input[type=text].input_is_ssn"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_ssn(this));		});
		$("input[type=text].input_is_cph"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_cph(this));		});
		$("input[type=text].input_is_card"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_card(this));		});
		$("input[type=text].input_is_mmyy"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_mmyy(this));		});
		$("input[type=text].input_is_drvg"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_drvg(this));		});
		$("input[type=text].input_is_car"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_car(this));		});
		$("input[type=text].input_is_bizr"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_bizr(this));		});
		$("input[type=text].input_is_acct"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_acct(this));		});
		$("input[type=text].input_is_pst"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_pst(this));		});
		$("input[type=text].input_is_int"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_int(this));		});
		$("input[type=text].input_is_addr"		).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_addr(this));		});
		$("input[type=text].input_is_addr_dtl"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_addr_dtl(this));	});
		$("input[type=text].input_is_fmt_ssn"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_ssn(this));	});
		$("input[type=text].input_is_fmt_cph"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_cph(this));	});
		$("input[type=text].input_is_fmt_card"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_card(this));	});
		$("input[type=text].input_is_fmt_mmyy"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_mmyy(this));	});
		$("input[type=text].input_is_fmt_drvg"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_drvg(this));	});
		$("input[type=text].input_is_fmt_bizr"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_bizr(this));	});
		$("input[type=text].input_is_fmt_int"	).on("input",	function() {$(this).prop("oninput",	fmtUtil.fn_input_fmt_int(this));	});
	}
};
$(document).ready(function() {
	fmtUtil.fn_reset();
});