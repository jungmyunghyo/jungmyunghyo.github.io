const vldUtil = {
	fn_et : function(v) {
		return ((v == null || v == undefined) || (typeof v == "string" && !v.trim().length) || ((typeof v == "object") && ((Array.isArray(v) && !v.length) || (!Object.keys(v).length))) || (typeof v == "boolean" && !v));
	},
	fn_alrt : function(o, tp) {
		var v		= ($(o).val());
		var msg		= ((o.hasAttribute("placeholder")) ? ($(o).attr("placeholder")) : ((o.hasAttribute("msg")) ? ($(o).attr("msg")) : ("필수 값을 확인해주세요.")));
		var f		= (!(((tp == "text" || tp == "textarea" || tp == "radio" || tp == "select") && (vldUtil.fn_et(v))) || (tp == "checkbox" && !$(o).prop("checked")) || (tp == "validation")));
		((!f) ? (alert(msg)) : (""));
		((!f) ? ($(o).focus()) : (""));
		return (f);
	},
	fn_vld : function() {
		var args	= (vldUtil.fn_vld.arguments);
		var len		= (args.length);
		var prnt	= ((!!len) ? (args[0]) : (null));
		var f		= (true);
		((!len) ? ($("input[type=text]")) : ($(prnt).find("input[type=text]"))).each(function() {
			if (f && this.hasAttribute("required")) {f = (vldUtil.fn_alrt(this, "text"));}
			if (f && this.hasAttribute("vld")) {f = (((new RegExp($(this).attr("vld"))).test($(this).val())) ? (true) : (vldUtil.fn_alrt(this, "validation")));}
		});
		((!len) ? ($("input[type=radio]:checked")) : ($(prnt).find("input[type=radio]:checked"))).each(function() {
			if (f && $(this).prop("required")) {f = (vldUtil.fn_alrt(this, "radio"));}
		});
		((!len) ? ($("input[type=checkbox]:required")) : ($(prnt).find("input[type=checkbox]:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt(this, "checkbox"));}
		});
		((!len) ? ($("select:required")) : ($(prnt).find("select:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt(this, "select"));}
		});
		((!len) ? ($("textarea:required")) : ($(prnt).find("select:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt(this, "textarea"));}
		});
	}
};