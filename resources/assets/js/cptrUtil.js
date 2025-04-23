/*
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
*/
const cptrUtil = {
	fn_get : function() {
		var args	= (cptrUtil.fn_get.arguments);
		var len		= (args.length);
		var trgt	= ((len > 0) ? (args[0]) : (""));
		var nm		= ((len > 1) ? (args[1]) : ("captured_image"));
		var tp		= ((len > 2) ? (args[2]) : ("png"));
		var html	= ((!len) ? (document.querySelector("body")) : ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : ((document.getElementsByClassName(trgt) != null) ? (document.getElementsByClassName(trgt)[0]) : (document.querySelector("html")))));
		html2canvas(html, {
			allowTaint		: (true),
			useCORS			: (true),
			ignoreElements	: (true),
			logging			: (false)
		}).then(canvas => {
			var dt		= (new Date());
			var href	= (canvas.toDataURL(("image/") + (tp)));
			var ext		= ((".") + (tp));
			var ttl		= ((nm) + ("_") + ((dt.getFullYear()) + (cptrUtil.fn_zero(dt.getMonth() + 1)) + (cptrUtil.fn_zero(dt.getDate()))) + ("_") + ((cptrUtil.fn_zero(dt.getHours())) + (cptrUtil.fn_zero(dt.getMinutes())) + (cptrUtil.fn_zero(dt.getSeconds()))) + (ext));
			try {
				window.flutter_inappwebview.callHandler("saveImageHandler", ({"href":href,"ttl":ttl,"ext":ext}));
			} catch (e) {
				var link		= (document.createElement("a"));
				link.href		= (href);
				link.download	= (ttl);
				link.click();
			}
		});
	},
	fn_zero : function(v) {
		return ((v < 10) ? (("0") + (v)) : (v));
	}
};