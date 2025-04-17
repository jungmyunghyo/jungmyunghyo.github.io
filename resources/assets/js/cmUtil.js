const $ver			= ("?ver=20250404");
const $site_url		= ("https://www.naver.com");
const $app_url		= ("app://");
const $and_str		= ("https://play.google.com/store/apps/details?id=aaaaaaaaaaaa");
const $ios_str		= ("https://apps.apple.com/kr/app/aaaaaaaaaaaa");
const $user_agnt	= (navigator.userAgent.toLowerCase());
const cmUtil = {
	fn_move			: function() {
		var args	= (cmUtil.fn_move.arguments);
		var len		= (args.length);
		var url		= (args[0]);
		var trgt	= ((len > 1) ? (args[1]) : (""));
		var param	= ((len > 2) ? (args[2]) : (""));
		if (trgt == "_blank") {
			window.open("about:blank").location.href = (($site_url) + (url) + (param));
		} else if (trgt == "_self") {
			location.href = ((url) + (param));
		} else if (trgt == "_app") {
			try {
				location.href = (($app_url) + (url));
			} catch (e) {
				setTimeout(() => {((cmUtil.fn_is_ios) ? (location.href = ($ios_str)) : (location.href = ($and_str)))}, 3000);
			}
		} else {
			location.href = ((url) + (param));
		}
	},
	fn_is_web		: function() {return (!/iphone|ipad|ipod|android|blackberry|opera mini|iemobile/i.test($user_agnt));},
	fn_is_mob		: function() {return (/iphone|ipad|ipod|android|blackberry|opera mini|iemobile/i.test($user_agnt));},
	fn_is_and		: function() {return (/android/i.test($user_agnt));},
	fn_is_ios		: function() {return (/iphone|ipad|ipod/i.test($user_agnt));},
	fn_is_flutter	: function() {try {window.flutter_inappwebview.callHandler();return (true);} catch (e) {return (false);}},
	fn_is_native	: function() {try {window.webkit.messageHandlers.HandlerName.postMessage();return (true);} catch (e) {return (false);}},
	fn_call_native	: function() {
		var args	= (cmUtil.fn_call_native.arguments);
		try {((args.length > 1) ? (window.webkit.messageHandlers.HandlerName.postMessage(args[1])) : (window.webkit.messageHandlers.HandlerName.postMessage()));} catch (e) {}
	},
	fn_call_flutter	: function() {
		var args	= (cmUtil.fn_call_flutter.arguments);
		try {((args.length > 1) ? (window.flutter_inappwebview.callHandler(args[0], args[1])) : (window.flutter_inappwebview.callHandler(args[0])));} catch (e) {}
	}
};