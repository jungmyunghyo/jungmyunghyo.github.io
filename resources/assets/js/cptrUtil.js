/*
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
*/
const cptrUtil = {
	fn_get : function() {
		var args	= (cptrUtil.fn_get.arguments);
		var len		= (args.length);
		var id		= (args[0]);
		var nm		= ((len > 1) ? (args[1]) : ("captured"));
		var tp		= ((len > 2) ? (args[2]) : ("png"));
		html2canvas(document.getElementById(id), {
			allowTaint		: (true),
			useCORS			: (true),
			ignoreElements	: (true),
			logging			: (false)
		}).then(canvas => {
			var dt		= (new Date());
			var m		= (dt.getMonth() + 1);
			var d		= (dt.getDate());
			var h		= (dt.getHours());
			var i		= (dt.getMinutes());
			var s		= (dt.getSeconds());
			var href	= (canvas.toDataURL(("image/") + (tp)));
			var ext		= ((".") + (tp));
			var ttl		= ((nm) + ("_") + ((dt.getFullYear()) + ((m < 10) ? (("0") + (m)) : (m)) + ((d < 10) ? (("0") + (d)) : (d))) + ("_") + (((h < 10) ? (("0") + (h)) : (h)) + ((i < 10) ? (("0") + (i)) : (i)) + ((s < 10) ? (("0") + (s)) : (s))) + (ext));
			try {
				window.flutter_inappwebview.callHandler("saveImageHandler", ({"href":href,"ttl":ttl,"ext":ext}));
			} catch (e) {
				var link		= (document.createElement("a"));
				link.href		= (href);
				link.download	= (ttl);
				link.click();
			}
		});
	}
};