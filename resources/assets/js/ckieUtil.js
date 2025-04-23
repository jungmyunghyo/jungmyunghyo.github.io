const ckieUtil = {
	fn_et : function(v) {
		return ((v == null || v == undefined) || (typeof v == "string" && !v.trim().length) || ((typeof v == "object") && ((Array.isArray(v) && !v.length) || (!Object.keys(v).length))) || (typeof v == "boolean" && !v));
	},
	fn_set : function() {
		var args	= (ckieUtil.fn_set.arguments);
		var len		= (args.length);
		var k		= (args[0]);
		var v		= (args[1]);
		var dt		= (new Date());
		((len > 2 && args[2] > 0) ? (dt.setFullYear(	dt.getFullYear()	+ args[2])) : (""));
		((len > 3 && args[3] > 0) ? (dt.setMonth(		dt.getMonth()		+ args[3])) : (""));
		((len > 4 && args[4] > 0) ? (dt.setDate(		dt.getDate()		+ args[4])) : (""));
		((len > 5 && args[5] > 0) ? (dt.setHours(		dt.getHours()		+ args[5])) : (""));
		((len > 6 && args[6] > 0) ? (dt.setMinutes(		dt.getMinutes()		+ args[6])) : (""));
		((len > 7 && args[7] > 0) ? (dt.setSeconds(		dt.getSeconds()		+ args[7])) : (""));
		document.cookie = ((encodeURIComponent(k)) + ("=") + (encodeURIComponent(v)) + ("; expires=") + (dt.toUTCString()) + ("; path=/"));
	},
	fn_get : function() {
		var args	= (ckieUtil.fn_get.arguments);
		var len		= (args.length);
		var k		= (args[0]);
		var fn		= ((len > 1) ? (args[1]) : (null));
		var v		= (document.cookie.match(("(^|;) ?") + (k) + ("=([^;]*)(;|$)")));
		return ((len > 1) ? (fn((!ckieUtil.fn_et(v) && v.length > 2) ? (v[2]) : (null))) : ((!ckieUtil.fn_et(v) && v.length > 2) ? (v[2]) : (null)));
	},
	fn_del : function(k) {
		ckieUtil.fn_set(k, null);
	}
};