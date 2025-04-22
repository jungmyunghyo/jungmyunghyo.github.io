const dtUtil = {
	fn_get_yyyy		: function() {
		var args	= (dtUtil.fn_get_yyyy.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setFullYear(	dt.getFullYear()	+ args[0])) : (""));
		((len > 1 && args[1] > 0) ? (dt.setMonth(		dt.getMonth()		+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setDate(		dt.getDate()		+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setHours(		dt.getHours()		+ args[3])) : (""));
		((len > 4 && args[4] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[4])) : (""));
		((len > 5 && args[5] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[5])) : (""));
		return (dt.getFullYear());
	},
	fn_get_m		: function() {
		var args	= (dtUtil.fn_get_m.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setMonth(		dt.getMonth()		+ args[0])) : (""));
		((len > 1 && args[1] > 0) ? (dt.setDate(		dt.getDate()		+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setHours(		dt.getHours()		+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[3])) : (""));
		((len > 4 && args[4] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[4])) : (""));
		return (dt.getMonth() + 1);
	},
	fn_get_mm		: function() {
		var args	= (dtUtil.fn_get_mm.arguments);
		var len		= (args.length);
		return (dtUtil.fn_zero(dtUtil.fn_get_m(((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0)), ((len > 4) ? (args[4]) : (0)))));
	},
	fn_get_d		: function() {
		var args	= (dtUtil.fn_get_d.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setDate(		dt.getDate()		+ args[0])) : (""));
		((len > 1 && args[1] > 0) ? (dt.setHours(		dt.getHours()		+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[3])) : (""));
		return (dt.getDate());
	},
	fn_get_dd		: function() {
		var args	= (dtUtil.fn_get_dd.arguments);
		var len		= (args.length);
		return (dtUtil.fn_zero(dtUtil.fn_get_d(((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0)))));
	},
	fn_get_h		: function() {
		var args	= (dtUtil.fn_get_h.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setHours(		dt.getHours()		+ args[0])) : (""));
		((len > 1 && args[1] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[2])) : (""));
		return (dt.getHours());
	},
	fn_get_hh		: function() {
		var args	= (dtUtil.fn_get_hh.arguments);
		var len		= (args.length);
		return (dtUtil.fn_zero(dtUtil.fn_get_h(((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)))));
	},
	fn_get_i		: function() {
		var args	= (dtUtil.fn_get_i.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[0])) : (""));
		((len > 1 && args[1] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[1])) : (""));
		return (dt.getMinutes());
	},
	fn_get_ii		: function() {
		var args	= (dtUtil.fn_get_ii.arguments);
		var len		= (args.length);
		return (dtUtil.fn_zero(dtUtil.fn_get_i(((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)))));
	},
	fn_get_s		: function() {
		var args	= (dtUtil.fn_get_s.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		((len > 0 && args[0] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[0])) : (""));
		return (dt.getSeconds());
	},
	fn_get_ss		: function() {
		var args	= (dtUtil.fn_get_ss.arguments);
		var len		= (args.length);
		return (dtUtil.fn_zero(dtUtil.fn_get_s(((len > 0) ? (args[0]) : (0)))));
	},
	fn_fmt_ymd		: function() {
		var args	= (dtUtil.fn_fmt_ymd.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		var rst		= ("");
		var tp		= ((len > 0) ? (args[0]) : (""));
		((len > 1 && args[1] > 0) ? (dt.setFullYear(	dt.getFullYear()	+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setMonth(		dt.getMonth()		+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setDate(		dt.getDate()		+ args[3])) : (""));
		((len > 4 && args[4] > 0) ? (dt.setHours(		dt.getHours()		+ args[4])) : (""));
		((len > 5 && args[5] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[5])) : (""));
		((len > 6 && args[6] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[6])) : (""));
		rst			= ((rst) + (dt.getFullYear()) + ((tp == "kor") ? ("년") : ((tp == "db") ? ("-") : (tp))));
		rst			= ((rst) + (dtUtil.fn_zero(dt.getMonth() + 1)) + ((tp == "kor") ? ("월") : ((tp == "db") ? ("-") : (tp))));
		rst			= ((rst) + (dtUtil.fn_zero(dt.getDate())) + ((tp == "kor") ? ("일") : ("")));
		return (rst);
	},
	fn_fmt_his		: function() {
		var args	= (dtUtil.fn_fmt_his.arguments);
		var len		= (args.length);
		var dt		= (new Date());
		var rst		= ("");
		var tp		= ((len > 0) ? (args[0]) : (""));
		((len > 1 && args[1] > 0) ? (dt.setHours(		dt.getHours()		+ args[1])) : (""));
		((len > 2 && args[2] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[3])) : (""));
		rst			= ((rst) + (dtUtil.fn_zero(dt.getHours())) + ((tp == "kor") ? ("시") : ((tp == "db") ? (":") : (tp))));
		rst			= ((rst) + (dtUtil.fn_zero(dt.getMinutes())) + ((tp == "kor") ? ("분") : ((tp == "db") ? (":") : (tp))));
		rst			= ((rst) + (dtUtil.fn_zero(dt.getSeconds())) + ((tp == "kor") ? ("초") : ("")));
		return (rst);
	},
	fn_fmt_ymdhis	: function() {
		var args	= (dtUtil.fn_fmt_ymdhis.arguments);
		var len		= (args.length);
		return ((dtUtil.fn_fmt_ymd(args[0], ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0)), ((len > 4) ? (args[4]) : (0)), ((len > 5) ? (args[5]) : (0)), ((len > 6) ? (args[6]) : (0)))) + (" ") + (dtUtil.fn_fmt_his(args[0], ((len > 4) ? (args[4]) : (0)), ((len > 5) ? (args[5]) : (0)), ((len > 6) ? (args[6]) : (0)))));
	},
	fn_get_ymd		: function() {
		var args	= (dtUtil.fn_get_ymd.arguments);
		var len		= (args.length);
		return (dtUtil.fn_fmt_ymd("", ((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0)), ((len > 4) ? (args[4]) : (0)), ((len > 5) ? (args[5]) : (0))).replace(/[^0-9]/g, ""));
	},
	fn_get_his		: function() {
		var args	= (dtUtil.fn_get_his.arguments);
		var len		= (args.length);
		return (dtUtil.fn_fmt_his("", ((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0))).replace(/[^0-9]/g, ""));
	},
	fn_get_ymdhis	: function() {
		var args	= (dtUtil.fn_get_ymdhis.arguments);
		var len		= (args.length);
		return (dtUtil.fn_fmt_ymdhis("", ((len > 0) ? (args[0]) : (0)), ((len > 1) ? (args[1]) : (0)), ((len > 2) ? (args[2]) : (0)), ((len > 3) ? (args[3]) : (0)), ((len > 4) ? (args[4]) : (0)), ((len > 5) ? (args[5]) : (0))).replace(/[^0-9]/g, ""));
	},
	fn_zero			: function(v) {
		return ((v < 10) ? (("0") + (v)) : (v));
	},
	fn_prs			: function(dt) {
		var v		= (dt.replace(/[^0-9]/g, ""));
		var len		= (v.length);
		var rst		= ("");
		rst			= ((rst) + ("")		+ ((len <= 4)	? ("2025")	: (v.substring(0, 4))));
		rst			= ((rst) + ("-")	+ ((len <= 6)	? ("01")	: (v.substring(4, 6))));
		rst			= ((rst) + ("-")	+ ((len <= 8)	? ("01")	: (v.substring(6, 8))));
		rst			= ((rst) + (" ")	+ ((len <= 10)	? ("00")	: (v.substring(8, 10))));
		rst			= ((rst) + (":")	+ ((len <= 12)	? ("00")	: (v.substring(10, 12))));
		rst			= ((rst) + (":")	+ ((len <= 14)	? ("00")	: (v.substring(12, 14))));
		return (rst);
	},
	fn_diff			: function() {
		var args	= (dtUtil.fn_diff.arguments);
		var len		= (args.length);
		var tp		= (args[0]);
		var t1		= ((new Date(dtUtil.fn_prs(args[1])).getTime()));
		var t2		= ((new Date(dtUtil.fn_prs((len > 2) ? (args[2]) : (dtUtil.fn_get_ymdhis()))).getTime()));
		if (tp == "d") {
			return (Math.floor((t2 - t1) / (24 * 60 * 60 * 1000)));
		} else if (tp == "h") {
			return (Math.floor((t2 - t1) / (60 * 60 * 1000)));
		} else if (tp == "i") {
			return (Math.floor((t2 - t1) / (60 * 1000)));
		} else if (tp == "s") {
			return (Math.floor((t2 - t1) / (1000)));
		} else if (tp == "is") {
			var i	= (Math.floor((t2 - t1) / (60 * 1000)));
			var s	= (Math.floor((t2 - t1 - (i * 60 * 1000)) / (1000)));
			return ((i) + (":") + (dtUtil.fn_zero(s)));
		} else if (tp == "his") {
			var h	= (Math.floor((t2 - t1) / (60 * 60 * 1000)));
			var i	= (Math.floor((t2 - t1 - (h * 60 * 60 * 1000)) / (60 * 1000)));
			var s	= (Math.floor((t2 - t1 - (h * 60 * 60 * 1000) - (i * 60 * 1000)) / (1000)));
			return ((h) + (":") + (dtUtil.fn_zero(i)) + (":") + (dtUtil.fn_zero(s)));
		} else if (tp == "dhis") {
			var d	= (Math.floor((t2 - t1) / (24 * 60 * 60 * 1000)));
			var h	= (Math.floor((t2 - t1 - (d * 24 * 60 * 60 * 1000)) / (60 * 60 * 1000)));
			var i	= (Math.floor((t2 - t1 - (d * 24 * 60 * 60 * 1000) - (h * 60 * 60 * 1000)) / (60 * 1000)));
			var s	= (Math.floor((t2 - t1 - (d * 24 * 60 * 60 * 1000) - (h * 60 * 60 * 1000) - (i * 60 * 1000)) / (1000)));
			return ((d) + (":") + (dtUtil.fn_zero(h)) + (":") + (dtUtil.fn_zero(i)) + (":") + (dtUtil.fn_zero(s)));
		}
		return (t2 - t1);
	}
};
const dtCalUtil = {
	_fmt 			: ("yy-mm-dd"),
	_img 			: ("https://jqueryui.com/resources/demos/datepicker/images/calendar.gif"),
	_y_sf 			: ("년"),
	_mm_txt_arr		: (["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]),
	_dd_txt_arr		: (["일","월","화","수","목","금","토"]),
	_rd_yn			: (true),
	_chg_ym_yn		: (true),
	_fst_d			: (1),
	_prev			: ("이전"),
	_next			: ("다음"),
	_oth_m_yn		: (true),
	fn_prs			: function(o) {
		try {return ($.datepicker.parseDate((dtCalUtil._fmt), (o.value)));} catch (e) {return (null);}
	},
	fn_set			: function() {
		var args	= (dtCalUtil.fn_set.arguments);
		var len		= (args.length);
		var sId		= (args[0]);
		var sAdd	= ((len > 1) ? (args[1]) : ("0D"));
		var eAdd	= ((len > 2) ? (args[2]) : ("0D"));
		var eId		= ((len > 3) ? (args[3]) : (null));
		if (len > 3) {
			$(dtCalUtil.fn_get(sId, sAdd, eAdd)).datepicker("setDate", (sAdd)).on("change", function() {
				$(eId).datepicker("option", "minDate", (dtCalUtil.fn_prs(this)));
			});
			$(dtCalUtil.fn_get(eId, sAdd, eAdd)).datepicker("setDate", (eAdd)).on("change", function() {
				$(sId).datepicker("option", "maxDate", (dtCalUtil.fn_prs(this)));
			});
		} else {
			dtCalUtil.fn_get(sId, sAdd, eAdd);
		}
	},
	fn_get			: function(id, sAdd, eAdd) {
		return (
			$(id).datepicker({
				buttonImage			: (dtCalUtil._img),
				buttonImageOnly		: (true),
				buttonText			: (null),
				changeYear			: (dtCalUtil._chg_ym_yn),
				changeMonth			: (dtCalUtil._chg_ym_yn),
				dateFormat			: (dtCalUtil._fmt),
				yearSuffix			: (dtCalUtil._y_sf),
				monthNames			: (dtCalUtil._mm_txt_arr),
				monthNamesShort		: (dtCalUtil._mm_txt_arr),
				dayNames			: (dtCalUtil._dd_txt_arr),
				dayNamesMin			: (dtCalUtil._dd_txt_arr),
				dayNamesShort		: (dtCalUtil._dd_txt_arr),
				firstDay			: (dtCalUtil._fst_d),
				prevText			: (dtCalUtil._prev),
				nextText			: (dtCalUtil._next),
				minDate				: (sAdd),
				maxDate				: (eAdd),
				showOtherMonths		: (dtCalUtil._oth_m_yn),
				selectOtherMonths	: (dtCalUtil._oth_m_yn),
				showMonthAfterYear	: (true),
				showOn				: ("both"),
				showWeek			: (false)
			}).datepicker("setDate", ("0D")).attr("readonly", dtCalUtil._rd_yn)
		);
	}
};