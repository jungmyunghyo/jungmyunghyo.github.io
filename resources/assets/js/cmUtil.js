const $ver				= ("?ver=20250404");
const $site_url			= ("https://www.naver.com");
const $app_url			= ("app://");
const $and_str			= ("https://play.google.com/store/apps/details?id=aaaaaaaaaaaa");
const $ios_str			= ("https://apps.apple.com/kr/app/aaaaaaaaaaaa");
const cmUtil = {
	fn_is_ios : function() {
		if ((/iphone|ipad|ipod|android/i.test(navigator.userAgent.toLowerCase())) == true) {
			var varUA = navigator.userAgent.toLowerCase();
			return (varUA.indexOf("iphone") > -1 || varUA.indexOf("ipad") > -1 || varUA.indexOf("ipod") > -1);
		}
		return (false);
	},
	fn_link : function() {
		var args	= (cmUtil.fn_link.arguments);
		var len		= (args.length);
		var url		= (args[0]);
		var trgt	= ((len > 1) ? (args[1]) : (""));
		var param	= ((len > 2) ? (args[2]) : (null));
		if (trgt == "_blank") {
			window.open("about:blank").location.href = (($site_url) + ((param == null) ? (url) : ((url) + (param))));
		} else if (trgt == "_self") {
			location.href = ((param == null) ? (url) : ((url) + (param)));
		} else if (trgt == "_app") {
			try {
				location.href = (($app_url) + (url));
			} catch (e) {
			} finally {
				setTimeout(() => {((cmUtil.fn_is_ios) ? (location.href = ($ios_str)) : (location.href = ($and_str)))}, 3000);
			}
		} else {
			location.href = ((param == null) ? (url) : ((url) + (param)));
		}
	},
	fn_call_flutter : function() {
		var args	= (cmUtil.fn_call_flutter.arguments);
		var len		= (args.length);
		var trgt	= (args[0]);
		try {
			if (len > 1) {
				window.flutter_inappwebview.callHandler(trgt, args[1]);
			} else {
				window.flutter_inappwebview.callHandler(trgt);
			}
		} catch (e) {
		}
	}
};