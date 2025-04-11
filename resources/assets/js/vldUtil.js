const vldUtil = {
	fn_et : function(v) {
		return (((v == null || v == "" || v == undefined) || (v != null && typeof v == "object" && !Object.keys(v).length)) ? (true) : (false));
	},
	fn_alrt : function() {
		var args	= (vldUtil.fn_alrt.arguments);
		var len		= (args.length);
		var obj		= (args[0]);
		var type	= ((len > 0) ? (args[1]) : ("text"));
		if ((type == "text" || type == "textarea") && (vldUtil.fn_et($(obj).val()))) {
			alert((vldUtil.fn_et($(obj).attr("placeholder")) ? ("필수 값을 입력해주세요.") : ($(obj).attr("placeholder"))));
			$(obj).focus();
			return (false);
		} else if ((type == "radio" || type == "select") && (vldUtil.fn_et($(obj).val()))) {
			alert((vldUtil.fn_et($(obj).attr("placeholder")) ? ("필수 값을 선택해주세요.") : ($(obj).attr("placeholder"))));
			$(obj).focus();
			return (false);
		} else if ((type == "checkbox") && (!$(this).prop("checked"))) {
			alert((vldUtil.fn_et($(obj).attr("placeholder")) ? ("필수 값을 선택해주세요.") : ($(obj).attr("placeholder"))));
			$(obj).focus();
			return (false);
		} else if (type == "validation") {
			alert((vldUtil.fn_et($(obj).attr("msg")) ? ("필수 값을 확인해주세요.") : ($(obj).attr("msg"))));
			$(obj).focus();
			return (false);
		}
		return (true);
	},
	fn_vld : function() {
		var args	= (vldUtil.fn_vld.arguments);
		var len		= (args.length);
		var prnt	= ((len > 0) ? (args[0]) : (null));
		var tgrt	= ((len > 1) ? (args[1]) : ("#"));
		var f		= (true);
		((len < 1) ? $("input[type=text]:required") : ($((tgrt) + (prnt)).find("input[type=text]:required"))).each(function() {
			if (f) {
				f = (vldUtil.fn_alrt($(this), "text"));
				if (f && this.hasAttribute("vld")) {
					var v = ($(this).val());
					if (this.hasAttribute("rplc")) {
						v = (v.replace($(this).attr("rplc"), ""));
					}
					f = (((new RegExp($(this).attr("vld"))).test(v)) ? (true) : (vldUtil.fn_alrt(this, "validation")));
				}
			}
		});
		((len < 1) ? $("input[type=radio]:checked") : ($((tgrt) + (prnt)).find("input[type=radio]:checked"))).each(function() {
			if (f && $(this).prop("required")) {f = (vldUtil.fn_alrt($(this), "radio"));} else {return (false);}
		});
		((len < 1) ? $("input[type=checkbox]:required") : ($((tgrt) + (prnt)).find("input[type=checkbox]:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt($(this), "checkbox"));} else {return (false);}
		});
		((len < 1) ? $("select:required") : ($((tgrt) + (prnt)).find("select:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt($(this), "select"));} else {return (false);}
		});
		((len < 1) ? $("textarea:required") : ($((tgrt) + (prnt)).find("textarea:required"))).each(function() {
			if (f) {f = (vldUtil.fn_alrt($(this), "textarea"));} else {return (false);}
		});
		return (f);
	},
	fn_param : function() {
		var args	= (vldUtil.fn_param.arguments);
		var len		= (args.length);
		var attr	= ((len > 0) ? (args[0]) : ("id"));
		var prnt	= ((len > 1) ? (args[1]) : (null));
		var tgrt	= ((len > 2) ? (args[2]) : ("#"));
		var param	= ("");
		((len < 2) ? $("input[type=text]") : ($((tgrt) + (prnt)).find("input[type=text]"))).each(function() {
			param += ((vldUtil.fn_et($(this).val())) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + ($(this).attr(attr)) + ("=") + ($(this).val())));
		});
		((len < 2) ? $("input[type=radio]:checked") : ($((tgrt) + (prnt)).find("input[type=radio]:checked"))).each(function() {
			param += ((vldUtil.fn_et($(this).val())) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + ($(this).attr(attr)) + ("=") + ($(this).val())));
		});
		((len < 2) ? $("input[type=checkbox]") : ($((tgrt) + (prnt)).find("input[type=checkbox]"))).each(function() {
			param += (((vldUtil.fn_et(param)) ? ("?") : ("&")) + ($(this).attr(attr)) + ("=") + (($(this).prop("checked")) ? ($(this).val()) : ("")));
		});
		((len < 2) ? $("select") : ($((tgrt) + (prnt)).find("select"))).each(function() {
			param += ((vldUtil.fn_et($(this).val())) ? ("") : (((vldUtil.fn_et(param)) ? ("?") : ("&")) + ($(this).attr(attr)) + ("=") + ($(this).val())));
		});
		return (param);
	},
	fn_map : function() {
		var args	= (vldUtil.fn_map.arguments);
		var len		= (args.length);
		var attr	= ((len > 0) ? (args[0]) : ("id"));
		var prnt	= ((len > 1) ? (args[1]) : (null));
		var tgrt	= ((len > 2) ? (args[2]) : ("#"));
		var map		= ({});
		((len < 2) ? $("input[type=text]") : ($((tgrt) + (prnt)).find("input[type=text]"))).each(function() {
			map[($(this).attr(attr))] = ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val()));
		});
		((len < 2) ? $("input[type=radio]:checked") : ($((tgrt) + (prnt)).find("input[type=radio]:checked"))).each(function() {
			map[($(this).attr(attr))] = ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val()));
		});
		((len < 2) ? $("input[type=checkbox]") : ($((tgrt) + (prnt)).find("input[type=checkbox]"))).each(function() {
			map[($(this).attr(attr))] = (($(this).prop("checked")) ? ($(this).val()) : (""));
		});
		((len < 2) ? $("select") : ($((tgrt) + (prnt)).find("select"))).each(function() {
			map[($(this).attr(attr))] = ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val()));
		});
		((len < 2) ? $("textarea") : ($((tgrt) + (prnt)).find("textarea"))).each(function() {
			map[($(this).attr(attr))] = ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val()));
		});
		return (map);
	},
	fn_form : function() {
		var args	= (vldUtil.fn_form.arguments);
		var len		= (args.length);
		var mthd	= (args[0]);
		var nm		= (args[1]);
		var actn	= (args[2]);
		var attr	= ((len > 3) ? (args[3]) : ("id"));
		var prnt	= ((len > 4) ? (args[4]) : (null));
		var tgrt	= ((len > 5) ? (args[5]) : ("#"));
		var form	= (document.createElement("form"));
		form.setAttribute("method", mthd);
		form.setAttribute("id", nm);
		form.setAttribute("name", nm);
		form.setAttribute("action", actn);
		form.setAttribute("charset", "UTF-8");
		((len < 5) ? $("input[type=text]") : ($((tgrt) + (prnt)).find("input[type=text]"))).each(function() {
			var fld = (document.createElement("input"));
			fld.setAttribute("type", ("hidden"));
			fld.setAttribute("name", ($(this).attr(attr)));
			fld.setAttribute("value", ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val())));
			form.appendChild(fld);
		});
		((len < 5) ? $("input[type=radio]:checked") : ($((tgrt) + (prnt)).find("input[type=radio]:checked"))).each(function() {
			var fld = (document.createElement("input"));
			fld.setAttribute("type", ("hidden"));
			fld.setAttribute("name", ($(this).attr(attr)));
			fld.setAttribute("value", ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val())));
			form.appendChild(fld);
		});
		((len < 5) ? $("input[type=checkbox]") : ($((tgrt) + (prnt)).find("input[type=checkbox]"))).each(function() {
			var fld = (document.createElement("input"));
			fld.setAttribute("type", ("hidden"));
			fld.setAttribute("name", ($(this).attr(attr)));
			fld.setAttribute("value", (($(this).prop("checked")) ? ($(this).val()) : ("")));
			form.appendChild(fld);
		});
		((len < 5) ? $("select") : ($((tgrt) + (prnt)).find("select"))).each(function() {
			var fld = (document.createElement("input"));
			fld.setAttribute("type", ("hidden"));
			fld.setAttribute("name", ($(this).attr(attr)));
			fld.setAttribute("value", ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val())));
			form.appendChild(fld);
		});
		((len < 5) ? $("textarea") : ($((tgrt) + (prnt)).find("textarea"))).each(function() {
			var fld = (document.createElement("input"));
			fld.setAttribute("type", ("hidden"));
			fld.setAttribute("name", ($(this).attr(attr)));
			fld.setAttribute("value", ((vldUtil.fn_et($(this).val())) ? ("") : ($(this).val())));
			form.appendChild(fld);
		});
		return (form);
	}
};