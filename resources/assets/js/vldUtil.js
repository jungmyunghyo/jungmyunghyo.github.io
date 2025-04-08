const $alrt_txt = ("필수 값을 입력해주세요.");
const vldUtil = {
	fn_et : function(v) {
		return (((v == null || v == "" || v == undefined) || (v != null && typeof v == "object" && !Object.keys(v).length)) ? (true) : (false));
	},
	fn_alrt : function() {
		var args	= (vldUtil.fn_alrt.arguments);
		var len		= (args.length);
		var obj		= (args[0]);
		var msg		= ((len > 1) ? (args[1]) : ($alrt_txt));
		if (vldUtil.fn_et($(obj).val())) {
			alert((vldUtil.fn_et($(obj).attr("placeholder")) ? (msg) : ($(obj).attr("placeholder"))));
			$(obj).focus();
			return (false);
		}
		return (true);
	},
	fn_vld : function() {
		var f		= (true);
		var args	= (vldUtil.fn_vld.arguments);
		var len		= (args.length);
		var type	= (args[0]);
		var prnt	= ((len > 1) ? (args[1]) : (null));
		var tgrt	= ((len > 2) ? (args[2]) : ("#"));
		if (type == "txt") {
			((len == 1) ? $("input[type=text]:required") : ($((tgrt) + (prnt)).find("input[type=text]:required"))).each(function() {
				if (f) {f = (vldUtil.fn_alrt($(this)));}
			});
		} else if (type == "radio") {
			((len == 1) ? $("input[type=radio]:checked") : ($((tgrt) + (prnt)).find("input[type=radio]:checked"))).each(function() {
				if ($(this).prop("required") && f) {f = (vldUtil.fn_alrt($(this)));}
			});
		} else if (type == "chk") {
			((len == 1) ? $("input[type=checkbox]:required") : ($((tgrt) + (prnt)).find("input[type=checkbox]:required"))).each(function() {
				if (f && !$(this).prop("checked")) {
					alert((vldUtil.fn_et($(this).attr("placeholder")) ? ($alrt_txt) : ($(this).attr("placeholder"))));
					$(this).focus();
					f = (false);
				}
			});
		} else if (type == "selt") {
			((len == 1) ? $("select:required") : ($((tgrt) + (prnt)).find("select:required"))).each(function() {
				if (f) {f = (vldUtil.fn_alrt($(this)));}
			});
		}
		return (f);
	},
	fn_param : function() {
		var args	= (vldUtil.fn_param.arguments);
		var len		= (args.length);
		var type	= (args[0]);
		var obj		= (args[1]);
		var param	= (args[2]);
		var attr	= ((len > 3) ? (args[3]) : ("id"));
		var els		= ((len > 4) ? (args[4]) : (null));
		var key		= ($(obj).attr(attr));
		var val		= ($(obj).val());
		if (type == "txt") {
			return ((vldUtil.fn_et(els) && vldUtil.fn_et(val)) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + (key) + ("=") + ((vldUtil.fn_et(val)) ? (els) : (val))));
		} else if (type == "radio") {
			return ((vldUtil.fn_et(els) && vldUtil.fn_et(val)) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + (key) + ("=") + ((vldUtil.fn_et(val)) ? (els) : (val))));
		} else if (type == "chk") {
			return ((vldUtil.fn_et(els) && !$(obj).prop("checked")) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + (key) + ("=") + ((!$(obj).prop("checked")) ? (els) : (val))));
		} else if (type == "selt") {
			return ((vldUtil.fn_et(els) && vldUtil.fn_et(val)) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + (key) + ("=") + ((vldUtil.fn_et(val)) ? (els) : (val))));
		}
		return ("");
	}
};