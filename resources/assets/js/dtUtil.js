const $dt				= (new Date());
const $dt_yyyy			= ($dt.getFullYear());
const $dt_m				= ($dt.getMonth() + 1);
const $dt_mm			= (($dt_m < 10) ? (("0") + ($dt_m)) : ($dt_m));
const $dt_d				= ($dt.getDate());
const $dt_dd			= (($dt_d < 10) ? (("0") + ($dt_d)) : ($dt_d));
const $dt_yyyymmdd		= (($dt_yyyy) + ("-") + ($dt_mm) + ("-") + ($dt_dd));
const $dt_h				= ($dt.getHours());
const $dt_hh			= (($dt_h < 10) ? (("0") + ($dt_h)) : ($dt_h));
const $dt_i				= ($dt.getMinutes());
const $dt_ii			= (($dt_i < 10) ? (("0") + ($dt_i)) : ($dt_i));
const $dt_s				= ($dt.getSeconds());
const $dt_ss			= (($dt_s < 10) ? (("0") + ($dt_s)) : ($dt_s));
const dtUtil = {
	_fmt 			: ("yy-mm-dd"),
	_img 			: (("https://jqueryui.com/resources/demos/datepicker/images/calendar.gif") + ($ver)),
	_y_sf 			: ("년"),
	_mm_txt_arr		: (["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"]),
	_dd_txt_arr		: (["일","월","화","수","목","금","토"]),
	_rd_yn			: (true),
	_chg_ym_yn		: (true),
	_fst_d			: (1),
	_prev			: ("이전"),
	_next			: ("다음"),
	_oth_m_yn		: (true),
	fn_get_dt		: function(o) {
		try {return ($.datepicker.parseDate((dtUtil._fmt), (o.value)));} catch (e) {return (null);}
	},
	fn_get2			: function(sId, sAdd, eId, eAdd) {
		$(dtUtil.fn_get1(sId, sAdd, eAdd)).datepicker("setDate", (sAdd)).on("change", function() {
			$("#" + (eId)).datepicker("option", "minDate", (dtUtil.fn_get_dt(this)));
		});
		$(dtUtil.fn_get1(eId, sAdd, eAdd)).datepicker("setDate", (eAdd)).on("change", function() {
			$("#" + (sId)).datepicker("option", "maxDate", (dtUtil.fn_get_dt(this)));
		});
	},
	fn_get1			: function(id, sAdd, eAdd) {
		return (
			$("#" + (id)).datepicker({
				buttonImage			: (dtUtil._img),
				buttonImageOnly		: (true),
				buttonText			: (null),
				changeYear			: (dtUtil._chg_ym_yn),
				changeMonth			: (dtUtil._chg_ym_yn),
				dateFormat			: (dtUtil._fmt),
				yearSuffix			: (dtUtil._y_sf),
				monthNames			: (dtUtil._mm_txt_arr),
				monthNamesShort		: (dtUtil._mm_txt_arr),
				dayNames			: (dtUtil._dd_txt_arr),
				dayNamesMin			: (dtUtil._dd_txt_arr),
				dayNamesShort		: (dtUtil._dd_txt_arr),
				firstDay			: (dtUtil._fst_d),
				prevText			: (dtUtil._prev),
				nextText			: (dtUtil._next),
				minDate				: (sAdd),
				maxDate				: (eAdd),
				showOtherMonths		: (dtUtil._oth_m_yn),
				selectOtherMonths	: (dtUtil._oth_m_yn),
				showMonthAfterYear	: (true),
				showOn				: ("both"),
				showWeek			: (false)
			}).datepicker("setDate", ("0D")).attr("readonly", dtUtil._rd_yn)
		);
	}
};