const ckieUtil = {
	fn_set_s : function(k, v, s) {
		try {
			var dt = ($dt);
			dt.setTime((dt.getTime()) + (s * 1000));
			document.cookie = ((encodeURIComponent(k)) + ("=") + (encodeURIComponent(v)) + ("; expires=") + (dt.toUTCString()) + ("; path=/"));
		} catch (e) {
		}
	},
	fn_set_i : function(k, v, i) {
		ckieUtil.fn_set_s(k, v, (i * 60));
	},
	fn_set_h : function(k, v, h) {
		ckieUtil.fn_set_i(k, v, (h * 60));
	},
	fn_set_d : function(k, v, d) {
		ckieUtil.fn_set_h(k, v, (d * 24));
	},
	fn_get : function(k) {
		try {
			var v = (document.cookie.match(("(^|;) ?") + (k) + ("=([^;]*)(;|$)")));
			return ((v) ? (v[2]) : (null));
		} catch (e) {
			return (null);
		}
	},
	fn_del : function(k) {
		try {
			var dt = ($dt);
			dt.setTime((dt.getTime()) - (10 * 1000));
			document.cookie = ((encodeURIComponent(k)) + ("=") + (null) + ("; expires=") + (dt.toUTCString()) + ("; path=/"));
		} catch (e) {
		}
	},
	fn_call : function(k, fn) {
		try {
			if (ckieUtil.fn_get(k) == null) {
				return (fn());
			}
		} catch (e) {
		}
	},
	fn_prs : function(k, fn) {
		try {
			return (fn(ckieUtil.fn_get(k)));
		} catch (e) {
		}
	}
};