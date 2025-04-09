const $reg_nm			= (new RegExp("((^[가-힣]{2,18}$)|(^[a-zA-Z ]{2,}$))"));
const $reg_email		= (new RegExp("(^[0-9a-zA-Z._-]{4,20}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+$)|(^[0-9a-zA-Z._-]{4,20}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+[.][a-zA-Z]+$)"));
const $reg_id			= (new RegExp("^[0-9a-z]{4,20}$"));
const $reg_paspt		= (new RegExp("^[mMsSrRoOdD][0-9]{7}$"));
const $reg_ip			= (new RegExp("^[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}$"));
const $reg_ssn			= (new RegExp("(^[0-9]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28)|(29)|(30)|(31))]{2}[-][1-4]{1}[0-9]{6}$)|(^[0-9]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28)|(29)|(30)|(31))]{2}[1-4]{1}[0-9]{6}$)"));
const $reg_cph			= (new RegExp("(^[((010)|(017)|(019))]{3}[-][0-9]{4}[-][0-9]{4}$)|(^[((010)|(017)|(019))]{3}[0-9]{8}$)"));
const $reg_card			= (new RegExp("(^[0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}$)|(^[0-9]{16}$)"));
const $reg_drvg			= (new RegExp("(^[((11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28))]{2}[-][0-9]{2}[-][0-9]{6}[-][0-9]{2}$)|(^[((11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28))]{2}[0-9]{2}[0-9]{6}[0-9]{2}$)"));
const $reg_bizr			= (new RegExp("(^[1-9]{1}[0-9]{2}[0-9]{2}[0-9]{5}$)|(^[1-9]{1}[0-9]{2}[-][0-9]{2}[-][0-9]{5}$)"));
const $reg_acct			= (new RegExp("(^[0-9-]{7,19}[0-9]$)|(^[0-9]{8,20}$)"));
const $reg_pst			= (new RegExp("(^[0-9]{5}$)|(^[0-9]{3}[-][0-9]{3}$)"));
const $reg_int			= (new RegExp("(^[0]$)|(^[1-9]{1}[0-9]+$)"));
const $reg_addr			= (new RegExp("^[0-9a-zA-Z가-힣#*)(_+=,.' -]+$"));
const $rplc_nm			= (/[^a-zA-Zㄱ-ㅎ가-힣 ]/g);
const $rplc_email		= (/[^0-9a-zA-Z@._-]/g);
const $rplc_id			= (/[^0-9a-z]/g);
const $rplc_paspt		= (/[^0-9mMsSrRoOdD]/g);
const $rplc_ip			= (/[^0-9.]/g);
const $rplc_ssn			= (/[^0-9]/g);
const $rplc_cph			= (/[^0-9]/g);
const $rplc_card		= (/[^0-9]/g);
const $rplc_drvg		= (/[^0-9]/g);
const $rplc_bizr		= (/[^0-9]/g);
const $rplc_acct		= (/[^0-9-]/g);
const $rplc_pst			= (/[^0-9-]/g);
const $rplc_int			= (/[^0-9]/g);
const $rplc_fst_int		= (/(^0+)/g);
const $rplc_addr		= (/[^0-9a-zA-Zㄱ-ㅎ가-힣#*)(_+=,.' -]/g);
const fmtUtil = {
	fn_is_nm : function(v) {
		return ($reg_nm.test(v));
	},
	fn_is_email : function(v) {
		return ($reg_email.test(v));
	},
	fn_is_id : function(v) {
		return ($reg_id.test(v));
	},
	fn_is_paspt : function(v) {
		return ($reg_paspt.test(v));
	},
	fn_is_ip : function(v) {
		return ($reg_ip.test(v));
	},
	fn_is_ssn : function(v) {
		return ($reg_ssn.test(v));
	},
	fn_is_cph : function(v) {
		return ($reg_cph.test(v));
	},
	fn_is_card : function(v) {
		return ($reg_card.test(v));
	},
	fn_is_drvg : function(v) {
		return ($reg_drvg.test(v));
	},
	fn_is_bizr : function(v) {
		return ($reg_bizr.test(v));
	},
	fn_is_acct : function(v) {
		return ($reg_acct.test(v));
	},
	fn_is_pst : function(v) {
		return ($reg_pst.test(v));
	},
	fn_is_int : function(v) {
		return ($reg_int.test(v));
	},
	fn_is_addr : function(v) {
		return ($reg_addr.test(v));
	},
	fn_rplc_nm : function(v) {
		return (v.replace($rplc_nm, "").replace("  ", " "));
	},
	fn_rplc_email : function(v) {
		return (v.replace($rplc_email, "").replace("@@", "@").replace("..", "."));
	},
	fn_rplc_id : function(v) {
		return (v.replace($rplc_id, ""));
	},
	fn_rplc_paspt : function(v) {
		return (v.replace($rplc_paspt, ""));
	},
	fn_rplc_ip : function(v) {
		return (v.replace($rplc_ip, "").replace("..", "."));
	},
	fn_rplc_ssn : function(v) {
		return (v.replace($rplc_ssn, ""));
	},
	fn_rplc_cph : function(v) {
		return (v.replace($rplc_cph, ""));
	},
	fn_rplc_card : function(v) {
		return (v.replace($rplc_card, "").replace("--", "-"));
	},
	fn_rplc_drvg : function(v) {
		return (v.replace($rplc_drvg, "").replace("--", "-"));
	},
	fn_rplc_bizr : function(v) {
		return (v.replace($rplc_bizr, "").replace("--", "-"));
	},
	fn_rplc_acct : function(v) {
		return (v.replace($rplc_acct, "").replace("--", "-"));
	},
	fn_rplc_pst : function(v) {
		return (v.replace($rplc_pst, "").replace("--", "-"));
	},
	fn_rplc_int : function(v) {
		v = (v.replace($rplc_int, "").replace($rplc_fst_int, ""));
		return ((v == "") ? ("0") : (v));
	},
	fn_rplc_addr : function(v) {
		return (v.replace($rplc_addr, "").replace("  ", " "));
	},
	fn_fmt : function(v, len, rplc) {
		if ((v.length) > len) {
			var lst = (v.substring(len));
			if (lst != rplc) {
				v = ((v.substring(0, len)) + (rplc) + (lst));
			}
		}
		return (v.replace(((rplc) + (rplc)), rplc));
	},
	fn_fmt_ssn : function(v) {
		v = (fmtUtil.fn_rplc_ssn(v));
		v = (fmtUtil.fn_fmt(v, 6, "-"))
		return (v);
	},
	fn_fmt_cph : function(v) {
		v = (fmtUtil.fn_rplc_cph(v));
		v = (fmtUtil.fn_fmt(v, 3, "-"));
		v = (fmtUtil.fn_fmt(v, (3 + 1 + 4), "-"));
		return (v);
	},
	fn_fmt_card : function(v) {
		v = (fmtUtil.fn_rplc_card(v));
		v = (fmtUtil.fn_fmt(v, 4, "-"));
		v = (fmtUtil.fn_fmt(v, (4 + 1 + 4), "-"));
		v = (fmtUtil.fn_fmt(v, (4 + 1 + 4 + 1 + 4), "-"));
		return (v);
	},
	fn_fmt_drvg : function(v) {
		v = (fmtUtil.fn_rplc_drvg(v));
		v = (fmtUtil.fn_fmt(v, 2, "-"));
		v = (fmtUtil.fn_fmt(v, (2 + 1 + 2), "-"));
		v = (fmtUtil.fn_fmt(v, (2 + 1 + 2 + 1 + 6), "-"));
		return (v);
	},
	fn_fmt_bizr : function(v) {
		v = (fmtUtil.fn_rplc_bizr(v));
		v = (fmtUtil.fn_fmt(v, 3, "-"));
		v = (fmtUtil.fn_fmt(v, (3 + 1 + 2), "-"));
		v = (fmtUtil.fn_fmt(v, (3 + 1 + 2 + 1 + 5), "-"));
		return (v);
	},
	fn_fmt_int : function(v) {
		v = (fmtUtil.fn_rplc_int(v));
		v = (v.replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","));
		return (v);
	},
	fn_input_nm : function(o) {
		$(o).val(fmtUtil.fn_rplc_nm($(o).val()));
	},
	fn_input_email : function(o) {
		$(o).val(fmtUtil.fn_rplc_email($(o).val()));
	},
	fn_input_id : function(o) {
		$(o).val(fmtUtil.fn_rplc_id($(o).val()));
	},
	fn_input_paspt : function(o) {
		$(o).val(fmtUtil.fn_rplc_paspt($(o).val()));
	},
	fn_input_ip : function(o) {
		$(o).val(fmtUtil.fn_rplc_ip($(o).val()));
	},
	fn_input_ssn : function(o) {
		$(o).val(fmtUtil.fn_rplc_ssn($(o).val()));
	},
	fn_input_cph : function(o) {
		$(o).val(fmtUtil.fn_rplc_cph($(o).val()));
	},
	fn_input_card : function(o) {
		$(o).val(fmtUtil.fn_rplc_card($(o).val()));
	},
	fn_input_drvg : function(o) {
		$(o).val(fmtUtil.fn_rplc_drvg($(o).val()));
	},
	fn_input_bizr : function(o) {
		$(o).val(fmtUtil.fn_rplc_bizr($(o).val()));
	},
	fn_input_acct : function(o) {
		$(o).val(fmtUtil.fn_rplc_acct($(o).val()));
	},
	fn_input_pst : function(o) {
		$(o).val(fmtUtil.fn_rplc_pst($(o).val()));
	},
	fn_input_int : function(o) {
		$(o).val(fmtUtil.fn_rplc_int($(o).val()));
	},
	fn_input_addr : function(o) {
		$(o).val(fmtUtil.fn_rplc_addr($(o).val()));
	},
	fn_input_fmt_ssn : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "14") {
			$(o).attr("maxlength", "14");
		}
		$(o).val(fmtUtil.fn_fmt_ssn($(o).val()));
	},
	fn_input_fmt_cph : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "13") {
			$(o).attr("maxlength", "13");
		}
		$(o).val(fmtUtil.fn_fmt_cph($(o).val()));
	},
	fn_input_fmt_card : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "19") {
			$(o).attr("maxlength", "19");
		}
		$(o).val(fmtUtil.fn_fmt_card($(o).val()));
	},
	fn_input_fmt_drvg : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "15") {
			$(o).attr("maxlength", "15");
		}
		$(o).val(fmtUtil.fn_fmt_drvg($(o).val()));
	},
	fn_input_fmt_bizr : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "12") {
			$(o).attr("maxlength", "12");
		}
		$(o).val(fmtUtil.fn_fmt_bizr($(o).val()));
	},
	fn_input_fmt_int : function(o) {
		if (!o.hasAttribute("maxlength") || $(o).attr("maxlength") != "12") {
			$(o).attr("maxlength", "12");
		}
		$(o).val(fmtUtil.fn_fmt_int($(o).val()));
	}
};