const szUtil = {
	fn_get : function() {
		var args	= (szUtil.fn_get.arguments);
		var len		= (args.length);
		var tp		= (args[0]);
		var pst		= ((len > 1) ? (args[1]) : (""));
		if (pst == "scn") {
			return ((tp == "h") ? (window.screen.height) : ((tp == "w") ? (window.screen.width) : (0)));
		} else if (pst == "out") {
			return ((tp == "h") ? (window.outerHeight) : ((tp == "w") ? (window.outerWidth) : (0)));
		} else if (pst == "inn") {
			return ((tp == "h") ? (window.innerHeight) : ((tp == "w") ? (window.innerWidth) : (0)));
		}
		var html = ((len > 2) ? ((document.getElementById(args[2]) != null) ? (document.getElementById(args[2])) : ((document.getElementsByClassName(args[2]) != null) ? (document.getElementsByClassName(args[2])[0]) : (document.querySelector("html")))) : (document.querySelector("body")));
		if (pst == "clt") {
			return ((tp == "h") ? (html.clientHeight) : ((tp == "w") ? (html.clientWidth) : (0)));
		} else if (pst == "off") {
			return ((tp == "h") ? (html.offsetHeight) : ((tp == "w") ? (html.offsetWidth) : (0)));
		} else if (pst == "scl") {
			return ((tp == "h") ? (html.scrollHeight) : ((tp == "w") ? (html.scrollWidth) : (0)));
		}
		return ((tp == "h") ? (html.style.height) : ((tp == "w") ? (html.style.width) : (0)));
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