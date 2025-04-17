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
const $rplc_fst_int			= (/(^0+)/g);
const $rplc_addr			= (/[^0-9a-zA-Zㄱ-ㅎ가-힣#*)(_+=,.'\s-]/g);
const $rplc_addr_dtl		= (/[^0-9a-zA-Zㄱ-ㅎ가-힣#*)(_+=,.'\s-]/g);
const fmtUtil = {
	fn_vld_nm				: function(v) {return ((new RegExp($vld_nm)).test(v));},
	fn_rplc_nm				: function(v) {return (v.replace($rplc_nm, "").replace(/[\s][\s]/g, " "));},
	fn_input_nm				: function(o) {$(o).val(fmtUtil.fn_rplc_nm($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "30"));		(fmtUtil.fn_vld(o, $vld_nm));			(fmtUtil.fn_rplc(o, $rplc_nm));},
	
	fn_vld_email			: function(v) {return ((new RegExp($vld_email)).test(v));},
	fn_rplc_email			: function(v) {return (v.replace($rplc_email, "").replace(/[\@][\@]/g, "@").replace(/[\.][\.]/g, "."));},
	fn_input_email			: function(o) {$(o).val(fmtUtil.fn_rplc_email($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "50"));		(fmtUtil.fn_vld(o, $vld_email));},
	
	fn_vld_id				: function(v) {return ((new RegExp($vld_id)).test(v));},
	fn_rplc_id				: function(v) {return (v.replace($rplc_id, ""));},
	fn_input_id				: function(o) {$(o).val(fmtUtil.fn_rplc_id($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "20"));		(fmtUtil.fn_vld(o, $vld_id));			(fmtUtil.fn_rplc(o, $rplc_id));},
	
	fn_vld_paspt			: function(v) {return ((new RegExp($vld_paspt)).test(v));},
	fn_rplc_paspt			: function(v) {return (v.replace($rplc_paspt, ""));},
	fn_input_paspt			: function(o) {$(o).val(fmtUtil.fn_rplc_paspt($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "8"));		(fmtUtil.fn_vld(o, $vld_paspt));		(fmtUtil.fn_rplc(o, $rplc_paspt));},
	
	fn_vld_ip				: function(v) {return ((new RegExp($vld_ip)).test(v));},
	fn_rplc_ip				: function(v) {return (v.replace($rplc_ip, "").replace(/[\.][\.]/g, "."));},
	fn_input_ip				: function(o) {$(o).val(fmtUtil.fn_rplc_ip($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "15"));		(fmtUtil.fn_vld(o, $vld_ip));			(fmtUtil.fn_rplc(o, $rplc_ip));				(fmtUtil.fn_msg(o, "xxx.xxx.xxx.xxx 형태로 입력해 주세요."));},
	
	fn_vld_ssn				: function(v) {return ((new RegExp($vld_ssn)).test(v));},
	fn_rplc_ssn				: function(v) {return (v.replace($rplc_ssn, ""));},
	fn_fmt_ssn				: function(v) {return (fmtUtil.fn_fmt((fmtUtil.fn_rplc_ssn(v)), 6, "-"));},
	fn_input_ssn			: function(o) {$(o).val(fmtUtil.fn_rplc_ssn($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "13"));		(fmtUtil.fn_vld(o, $vld_ssn));			(fmtUtil.fn_rplc(o, $rplc_ssn));},
	fn_input_fmt_ssn		: function(o) {$(o).val(fmtUtil.fn_fmt_ssn($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "14"));		(fmtUtil.fn_vld(o, $vld_ssn));			(fmtUtil.fn_rplc(o, $rplc_ssn));			(fmtUtil.fn_msg(o, "xxxxxx-nnnnnn 형태로 입력해 주세요."));},
	
	fn_vld_cph				: function(v) {return ((new RegExp($vld_cph)).test(v));},
	fn_rplc_cph				: function(v) {return (v.replace($rplc_cph, ""));},
	fn_fmt_cph				: function(v) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_cph(v), 3, "-"), (8), "-"));},
	fn_input_cph			: function(o) {$(o).val(fmtUtil.fn_rplc_cph($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "11"));		(fmtUtil.fn_vld(o, $vld_cph));			(fmtUtil.fn_rplc(o, $rplc_cph));},
	fn_input_fmt_cph		: function(o) {$(o).val(fmtUtil.fn_fmt_cph($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "13"));		(fmtUtil.fn_vld(o, $vld_cph));			(fmtUtil.fn_rplc(o, $rplc_cph));			(fmtUtil.fn_msg(o, "010-xxxx-nnnn 형태로 입력해 주세요."));},
	
	fn_vld_card				: function(v) {return ((new RegExp($vld_card)).test(v));},
	fn_rplc_card			: function(v) {return (v.replace($rplc_card, "").replace(/[\-][\-]/g, "-"));},
	fn_fmt_card				: function(v) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_card(v), 4, "-"), (9), "-"), (14), "-"));},
	fn_input_card			: function(o) {$(o).val(fmtUtil.fn_rplc_card($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "16"));		(fmtUtil.fn_vld(o, $vld_card));			(fmtUtil.fn_rplc(o, $rplc_card));},
	fn_input_fmt_card		: function(o) {$(o).val(fmtUtil.fn_fmt_card($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "19"));		(fmtUtil.fn_vld(o, $vld_card));			(fmtUtil.fn_rplc(o, $rplc_card));			(fmtUtil.fn_msg(o, "xxxx-nnnn-xxxx-nnnn 형태로 입력해 주세요."));},
	
	fn_vld_mmyy				: function(v) {return ((new RegExp($vld_mmyy)).test(v));},
	fn_rplc_mmyy			: function(v) {return (v.replace($rplc_mmyy, ""));},
	fn_fmt_mmyy				: function(v) {return (fmtUtil.fn_fmt((fmtUtil.fn_rplc_mmyy(v)), 2, "/"));},
	fn_input_mmyy			: function(o) {$(o).val(fmtUtil.fn_rplc_mmyy($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "4"));		(fmtUtil.fn_vld(o, $vld_mmyy));			(fmtUtil.fn_rplc(o, $rplc_mmyy));},
	fn_input_fmt_mmyy		: function(o) {$(o).val(fmtUtil.fn_fmt_mmyy($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "5"));		(fmtUtil.fn_vld(o, $vld_mmyy));			(fmtUtil.fn_rplc(o, $rplc_mmyy));		(fmtUtil.fn_msg(o, "mm/yy 형태로 입력해 주세요."));},
	
	fn_vld_drvg				: function(v) {return ((new RegExp($vld_drvg)).test(v));},
	fn_rplc_drvg			: function(v) {return (v.replace($rplc_drvg, "").replace(/[\-][\-]/g, "-"));},
	fn_fmt_drvg				: function(v) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_drvg(v), 2, "-"), (5), "-"), (12), "-"));},
	fn_input_drvg			: function(o) {$(o).val(fmtUtil.fn_rplc_drvg($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "12"));		(fmtUtil.fn_vld(o, $vld_drvg));			(fmtUtil.fn_rplc(o, $rplc_drvg));},
	fn_input_fmt_drvg		: function(o) {$(o).val(fmtUtil.fn_fmt_drvg($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "15"));		(fmtUtil.fn_vld(o, $vld_drvg));			(fmtUtil.fn_rplc(o, $rplc_drvg));			(fmtUtil.fn_msg(o, "xx-nn-xxxxxx-nn 형태로 입력해 주세요."));},
	
	fn_vld_car				: function(v) {return ((new RegExp($vld_car)).test(v));},
	fn_rplc_car				: function(v) {return (v.replace($rplc_car, ""));},
	fn_input_car			: function(o) {$(o).val(fmtUtil.fn_rplc_car($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "7"));		(fmtUtil.fn_vld(o, $vld_car));			(fmtUtil.fn_rplc(o, $rplc_car));},
	
	fn_vld_bizr				: function(v) {return ((new RegExp($vld_bizr)).test(v));},
	fn_rplc_bizr			: function(v) {return (v.replace($rplc_bizr, "").replace(/[\-][\-]/g, "-"));},
	fn_fmt_bizr				: function(v) {return (fmtUtil.fn_fmt(fmtUtil.fn_fmt(fmtUtil.fn_rplc_bizr(v), 3, "-"), (6), "-"));},
	fn_input_bizr			: function(o) {$(o).val(fmtUtil.fn_rplc_bizr($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "10"));		(fmtUtil.fn_vld(o, $vld_bizr));			(fmtUtil.fn_rplc(o, $rplc_bizr));},
	fn_input_fmt_bizr		: function(o) {$(o).val(fmtUtil.fn_fmt_bizr($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "12"));		(fmtUtil.fn_vld(o, $vld_bizr));			(fmtUtil.fn_rplc(o, $rplc_bizr));			(fmtUtil.fn_msg(o, "xxx-nn-xxxxx 형태로 입력해 주세요."));},
	
	fn_vld_acct				: function(v) {return ((new RegExp($vld_acct)).test(v));},
	fn_rplc_acct			: function(v) {return (v.replace($rplc_acct, "").replace(/[\-][\-]/g, "-"));},
	fn_input_acct			: function(o) {$(o).val(fmtUtil.fn_rplc_acct($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "30"));		(fmtUtil.fn_vld(o, $vld_acct));			(fmtUtil.fn_rplc(o, $rplc_acct));},
	
	fn_vld_pst				: function(v) {return ((new RegExp($vld_pst)).test(v));},
	fn_rplc_pst				: function(v) {return (v.replace($rplc_pst, "").replace(/[\-][\-]/g, "-"));},
	fn_input_pst			: function(o) {$(o).val(fmtUtil.fn_rplc_pst($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "7"));		(fmtUtil.fn_vld(o, $vld_pst));			(fmtUtil.fn_rplc(o, $rplc_pst));},
	
	fn_vld_int				: function(v) {return ((new RegExp($vld_int)).test(v));},
	fn_rplc_int				: function(v) {return (v.replace($rplc_int, "").replace($rplc_fst_int, ""));},
	fn_fmt_int				: function(v) {return (fmtUtil.fn_rplc_int(v).replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));},
	fn_input_int			: function(o) {$(o).val(fmtUtil.fn_rplc_int($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "9"));		(fmtUtil.fn_vld(o, $vld_int));			(fmtUtil.fn_rplc(o, $rplc_int));},
	fn_input_fmt_int		: function(o) {$(o).val(fmtUtil.fn_fmt_int($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "12"));		(fmtUtil.fn_vld(o, $vld_int));			(fmtUtil.fn_rplc(o, $rplc_int));},
	
	fn_vld_addr				: function(v) {return ((new RegExp($vld_addr)).test(v));},
	fn_rplc_addr			: function(v) {return (v.replace($rplc_addr, "").replace(/[\s][\s]/g, " "));},
	fn_input_addr			: function(o) {$(o).val(fmtUtil.fn_rplc_addr($(o).val()));			(fmtUtil.fn_req(o));		(fmtUtil.fn_max(o, "50"));												(fmtUtil.fn_rplc(o, $rplc_addr));},
	
	fn_vld_addr_dtl			: function(v) {return ((new RegExp($vld_addr_dtl)).test(v));},
	fn_rplc_addr_dtl		: function(v) {return (v.replace($rplc_addr_dtl, "").replace(/[\s][\s]/g, " "));},
	fn_input_addr_dtl		: function(o) {$(o).val(fmtUtil.fn_rplc_addr_dtl($(o).val()));									(fmtUtil.fn_max(o, "100"));												(fmtUtil.fn_rplc(o, $rplc_addr_dtl));},
	
	fn_fmt					: function(v, len, rplc) {
		if ((v.length) > len) {
			var lst = (v.substring(len));
			if (lst != rplc) {
				v = ((v.substring(0, len)) + (rplc) + (lst));
			}
		}
		return (v.replace(((rplc) + (rplc)), rplc));
	},
	fn_req					: function(o) {			((!o.hasAttribute("required")) ? ($(o).prop("required", true)) : (""));},
	fn_max					: function(o, max) {	((!o.hasAttribute("maxlength") || $(o).prop("maxlength") != max) ? ($(o).prop("maxlength", max)) : (""));},
	fn_vld					: function(o, vld) {	((!o.hasAttribute("vld") || $(o).prop("vld") != vld) ? ($(o).attr("vld", vld)) : (""));},
	fn_rplc					: function(o, rplc) {	((!o.hasAttribute("rplc") || $(o).attr("rplc") != rplc) ? ($(o).attr("rplc", rplc)) : (""));},
	fn_msg					: function(o, msg) {	((!o.hasAttribute("msg") || $(o).attr("msg") != msg) ? ($(o).attr("msg", msg)) : (""));},
	fn_reset				: function() {
		$("input[type=text].input_is_nm"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_nm(this));});
		$("input[type=text].input_is_email"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_email(this));});
		$("input[type=text].input_is_id"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_id(this));});
		$("input[type=text].input_is_paspt"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_paspt(this));});
		$("input[type=text].input_is_ip"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_ip(this));});
		$("input[type=text].input_is_ssn"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_ssn(this));});
		$("input[type=text].input_is_cph"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_cph(this));});
		$("input[type=text].input_is_card"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_card(this));});
		$("input[type=text].input_is_mmyy"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_mmyy(this));});
		$("input[type=text].input_is_drvg"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_drvg(this));});
		$("input[type=text].input_is_car"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_car(this));});
		$("input[type=text].input_is_bizr"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_bizr(this));});
		$("input[type=text].input_is_acct"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_acct(this));});
		$("input[type=text].input_is_pst"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_pst(this));});
		$("input[type=text].input_is_int"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_int(this));});
		$("input[type=text].input_is_addr"		).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_addr(this));});
		$("input[type=text].input_is_addr_dtl"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_addr_dtl(this));});
		$("input[type=text].input_is_fmt_ssn"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_ssn(this));});
		$("input[type=text].input_is_fmt_cph"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_cph(this));});
		$("input[type=text].input_is_fmt_card"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_card(this));});
		$("input[type=text].input_is_fmt_mmyy"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_mmyy(this));});
		$("input[type=text].input_is_fmt_drvg"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_drvg(this));});
		$("input[type=text].input_is_fmt_bizr"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_bizr(this));});
		$("input[type=text].input_is_fmt_int"	).each(			function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_int(this));});
		$("input[type=text].input_is_nm"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_nm(this));});
		$("input[type=text].input_is_email"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_email(this));});
		$("input[type=text].input_is_id"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_id(this));});
		$("input[type=text].input_is_paspt"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_paspt(this));});
		$("input[type=text].input_is_ip"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_ip(this));});
		$("input[type=text].input_is_ssn"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_ssn(this));});
		$("input[type=text].input_is_cph"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_cph(this));});
		$("input[type=text].input_is_card"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_card(this));});
		$("input[type=text].input_is_mmyy"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_mmyy(this));});
		$("input[type=text].input_is_drvg"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_drvg(this));});
		$("input[type=text].input_is_car"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_car(this));});
		$("input[type=text].input_is_bizr"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_bizr(this));});
		$("input[type=text].input_is_acct"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_acct(this));});
		$("input[type=text].input_is_pst"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_pst(this));});
		$("input[type=text].input_is_int"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_int(this));});
		$("input[type=text].input_is_addr"		).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_addr(this));});
		$("input[type=text].input_is_addr_dtl"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_addr_dtl(this));});
		$("input[type=text].input_is_fmt_ssn"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_ssn(this));});
		$("input[type=text].input_is_fmt_cph"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_cph(this));});
		$("input[type=text].input_is_fmt_card"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_card(this));});
		$("input[type=text].input_is_fmt_mmyy"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_mmyy(this));});
		$("input[type=text].input_is_fmt_drvg"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_drvg(this));});
		$("input[type=text].input_is_fmt_bizr"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_bizr(this));});
		$("input[type=text].input_is_fmt_int"	).on("input",	function() {$(this).prop("oninput", fmtUtil.fn_input_fmt_int(this));});
	}
};
$(document).ready(function() {
	fmtUtil.fn_reset();
});