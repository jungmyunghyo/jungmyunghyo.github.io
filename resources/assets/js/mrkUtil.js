const mrkUtil = {
	fn_et : function(v) {
		return ((v == null || v == undefined) || (typeof v == "string" && !v.trim().length) || ((typeof v == "object") && ((Array.isArray(v) && !v.length) || (!Object.keys(v).length))) || (typeof v == "boolean" && !v));
	},
	fn_key : function(o, bf, attr) {
		return ((mrkUtil.fn_et(bf)) ? ($(o).attr(attr)) : ((bf) + ($(o).attr(attr).substring(0, 1).toUpperCase()) + ($(o).attr(attr).substring(1))));
	},
	fn_val : function(o, tp) {
		return ((tp == "text") ? ((o.hasAttribute("rplc")) ? ($(o).attr("rplc")) : ($(o).val())) : ((tp == "checkbox") ? (($(o).prop("checked")) ? ($(o).val()) : ((o.hasAttribute("els")) ? ($(o).attr("els")) : (""))) : ($(o).val())));
	},
	fn_params : function() {
		var args	= (mrkUtil.fn_params.arguments);
		var len		= (args.length);
		var bf		= ((len > 0) ? (args[0]) : (""));
		var attr	= ((len > 1) ? (args[1]) : ("id"));
		var prnt	= ((len > 2) ? (args[2]) : (null));
		var param	= ("");
		((len < 3) ? ($("input[type=text]")) : ($(prnt).find("input[type=text]"))).each(function() {param += (mrkUtil.fn_param(param, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "text"))));});
		((len < 3) ? ($("input[type=radio]:checked")) : ($(prnt).find("input[type=radio]:checked"))).each(function() {param += (mrkUtil.fn_param(param, (mrkUtil.fn_key(this, bf, attr)), ($(this).val())));});
		((len < 3) ? ($("input[type=checkbox]")) : ($(prnt).find("input[type=checkbox]"))).each(function() {param += (mrkUtil.fn_param(param, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "checkbox"))));});
		((len < 3) ? ($("select")) : ($(prnt).find("select"))).each(function() {param += (mrkUtil.fn_param(param, (mrkUtil.fn_key(this, bf, attr)), ($(this).val())));});
		return (param);
	},
	fn_param : function(param, k, v) {
		return ((mrkUtil.fn_et(v)) ? ("") : (((mrkUtil.fn_et(param)) ? ("?") : ("&")) + (k) + ("=") + (v)));
	},
	fn_maps : function() {
		var args	= (mrkUtil.fn_maps.arguments);
		var len		= (args.length);
		var bf		= ((len > 0) ? (args[0]) : (""));
		var attr	= ((len > 1) ? (args[1]) : ("id"));
		var prnt	= ((len > 2) ? (args[2]) : (null));
		var map		= ({});
		((len < 3) ? ($("input[type=text]")) : ($(prnt).find("input[type=text]"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "text")));});
		((len < 3) ? ($("input[type=radio]:checked")) : ($(prnt).find("input[type=radio]:checked"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 3) ? ($("input[type=checkbox]")) : ($(prnt).find("input[type=checkbox]"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "checkbox")));});
		((len < 3) ? ($("select")) : ($(prnt).find("select"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 3) ? ($("textarea")) : ($(prnt).find("textarea"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		return (map);
	},
	fn_map : function(map, k, v) {
		return ((mrkUtil.fn_et(v)) ? ({}) : (map[k] = (v)));
	},
	fn_jsons : function() {
		var args	= (mrkUtil.fn_jsons.arguments);
		var len		= (args.length);
		var bf		= ((len > 0) ? (args[0]) : (""));
		var attr	= ((len > 1) ? (args[1]) : ("id"));
		var prnt	= ((len > 2) ? (args[2]) : (null));
		var map		= ({});
		((len < 3) ? ($("input[type=text]")) : ($(prnt).find("input[type=text]"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "text")));});
		((len < 3) ? ($("input[type=radio]:checked")) : ($(prnt).find("input[type=radio]:checked"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 3) ? ($("input[type=checkbox]")) : ($(prnt).find("input[type=checkbox]"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "checkbox")));});
		((len < 3) ? ($("select")) : ($(prnt).find("select"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 3) ? ($("textarea")) : ($(prnt).find("textarea"))).each(function() {mrkUtil.fn_map(map, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		return (JSON.stringify(map));
	},
	fn_forms : function() {
		var args	= (mrkUtil.fn_forms.arguments);
		var len		= (args.length);
		var mthd	= (args[0]);
		var nm		= (args[1]);
		var actn	= (args[2]);
		var bf		= ((len > 3) ? (args[3]) : (""));
		var attr	= ((len > 4) ? (args[4]) : ("id"));
		var prnt	= ((len > 5) ? (args[5]) : (null));
		var form	= (document.createElement("form"));
		form.setAttribute("method", mthd);
		form.setAttribute("id", nm);
		form.setAttribute("name", nm);
		form.setAttribute("action", actn);
		form.setAttribute("charset", "UTF-8");
		form.setAttribute("style", "display:none;");
		((len < 6) ? ($("input[type=text]")) : ($(prnt).find("input[type=text]"))).each(function() {form = mrkUtil.fn_form(form, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "text")));});
		((len < 6) ? ($("input[type=radio]:checked")) : ($(prnt).find("input[type=radio]:checked"))).each(function() {form = mrkUtil.fn_form(form, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 6) ? ($("input[type=checkbox]")) : ($(prnt).find("input[type=checkbox]"))).each(function() {form = mrkUtil.fn_form(form, (mrkUtil.fn_key(this, bf, attr)), (mrkUtil.fn_val(this, "checkbox")));});
		((len < 6) ? ($("select")) : ($(prnt).find("select"))).each(function() {form = mrkUtil.fn_form(form, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		((len < 6) ? ($("textarea")) : ($(prnt).find("textarea"))).each(function() {form = mrkUtil.fn_form(form, (mrkUtil.fn_key(this, bf, attr)), ($(this).val()));});
		return (form);
	},
	fn_form : function(form, k, v) {
		if (mrkUtil.fn_et(v)) {
			return (form);
		}
		var fld		= (document.createElement("input"));
		fld.setAttribute("type", "hidden");
		fld.setAttribute("name", k);
		fld.setAttribute("value", v);
		form.appendChild(fld);
		return (form);
	},
	fn_html_input : function() {
		var args	= (mrkUtil.fn_html_input.arguments);
		var len		= (args.length);
		var tp		= (args[0]);
		var nm		= (args[1]);
		var data	= (args[2]);
		var bf		= ((len > 4) ? (args[3]) : (""));
		var af		= ((len > 4) ? (args[4]) : (""));
		var html	= ("");
		((typeof data == "string") ? $(data.split(",")) : $(data)).each(function(i) {
			var k = ((mrkUtil.fn_et(this.k)) ? (this) : (this.k));
			var v = ((mrkUtil.fn_et(this.v)) ? (this) : (this.v));
			if (tp == "radio") {
				html += (bf);
				html += (("<input type='radio' name='" + (nm) + "' value='" + (v) + "'" + ((i == 0) ? (" checked") : ("")) + "/>") + (k));
				html += (af);
			} else if (tp == "checkbox") {
				html += (bf);
				html += (("<input type='checkbox' name='" + (nm) + "' value='" + (v) + "'/>") + (k));
				html += (af);
			} else if (tp == "checkboxall") {
				html += (bf);
				html += (("<input type='checkbox' name='" + (nm) + "' value='" + (v) + "' checked/>") + (k));
				html += (af);
			} else if (tp == "selectbox") {
				html += ((i == 0) ? ("<select name='" + (nm) + "'>") : (""));
				html += ("<option value='" + (v) + "'>" + (k) + "</option>");
				html += (((i + 1) == (((typeof data == "string") ? $(data.split(",")) : $(data)).length)) ? ("</select>") : (""));
			}
		});
		return (html);
	}
};