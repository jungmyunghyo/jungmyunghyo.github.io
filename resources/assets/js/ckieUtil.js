const ckieUtil = {
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
	fn_get : function(k) {
		try {
			var v = (document.cookie.match(("(^|;) ?") + (k) + ("=([^;]*)(;|$)")));
			return ((v == null || v == "" || v == undefined) ? (null) : ((v.length > 2) ? (v[2]) : (null)));
		} catch (e) {
			return (null);
		}
	},
	fn_del : function(k) {
		document.cookie = ((encodeURIComponent(k)) + ("=") + (null) + ("; expires=") + ((new Date()).toUTCString()) + ("; path=/"));
	},
	fn_call : function(k, fn) {
		if (ckieUtil.fn_get(k) == null) {
			return (fn());
		}
	},
	fn_prs : function(k, fn) {
		return (fn(ckieUtil.fn_get(k)));
	}
};