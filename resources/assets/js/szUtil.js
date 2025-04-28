const szUtil = {
	fn_et : function(v) {
		return ((v == null || v == undefined) || (typeof v == "string" && !v.trim().length) || ((typeof v == "object") && ((Array.isArray(v) && !v.length) || (!Object.keys(v).length))) || (typeof v == "boolean" && !v));
	},
	fn_trgt : function(trgt) {
		return ((szUtil.fn_et(trgt)) ? ((document.querySelector("body") != null) ? (document.querySelector("body")) : (document.querySelector("html"))) : ((trgt.indexOf("#") > -1 || trgt.indexOf(".") > -1) ? ((document.querySelector(trgt) != null) ? (document.querySelector(trgt)) : (szUtil.fn_trgt(null))) : ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : ((!!document.getElementsByClassName(trgt).length) ? (document.getElementsByClassName(trgt)[0]) : (szUtil.fn_trgt(null))))));
	},
	fn_get : function() {
		var args	= (szUtil.fn_get.arguments);
		var len		= (args.length);
		var tp		= ((len > 0) ? (args[0]) : (""));
		var pst		= ((len > 1) ? (args[1]) : (""));
		var trgt	= ((len > 2) ? (args[2]) : (""));
		if (pst == "scn") {
			return ((tp == "h") ? (window.screen.height) : ((tp == "w") ? (window.screen.width) : ([window.screen.width, window.screen.height])));
		} else if (pst == "out") {
			return ((tp == "h") ? (window.outerHeight) : ((tp == "w") ? (window.outerWidth) : ([window.outerWidth, window.outerHeight])));
		} else if (pst == "inn") {
			return ((tp == "h") ? (window.innerHeight) : ((tp == "w") ? (window.innerWidth) : ([window.innerWidth, window.innerHeight])));
		}
		var html = (szUtil.fn_trgt(trgt));
		if (pst == "clt") {
			return ((tp == "h") ? (html.clientHeight) : ((tp == "w") ? (html.clientWidth) : ([html.clientWidth, html.clientHeight])));
		} else if (pst == "off") {
			return ((tp == "h") ? (html.offsetHeight) : ((tp == "w") ? (html.offsetWidth) : ([html.offsetWidth, html.offsetHeight])));
		} else if (pst == "scl") {
			return ((tp == "h") ? (html.scrollHeight) : ((tp == "w") ? (html.scrollWidth) : ([html.scrollWidth, html.scrollHeight])));
		}
		return ((tp == "h") ? (html.style.height) : ((tp == "w") ? (html.style.width) : ([html.style.width, html.style.height])));
	},
	fn_get_h : function() {
		var args	= (szUtil.fn_get_h.arguments);
		var len		= (args.length);
		return ((len > 1) ? (szUtil.fn_get("h", args[0], args[1])) : ((!!len) ? (szUtil.fn_get("h", args[0])) : (szUtil.fn_get("h"))));
	},
	fn_get_w : function() {
		var args	= (szUtil.fn_get_w.arguments);
		var len		= (args.length);
		return ((len > 1) ? (szUtil.fn_get("w", args[0], args[1])) : ((!!len) ? (szUtil.fn_get("w", args[0])) : (szUtil.fn_get("w"))));
	}
};