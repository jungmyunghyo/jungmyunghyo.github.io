/*
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
*/
const cptrUtil = {
	fn_get : function(id, nm) {
		try {
			html2canvas(document.getElementById(id), {
				allowTaint			: true,
				useCORS				: true,
				ignoreElements		: true,
				logging				: false
			}).then(canvas => {
				var href		= (canvas.toDataURL("image/png"));
				var ext			= (".png");
				var ttl			= ((nm) + ("_") + (($dt_yyyy) + ($dt_mm) + ($dt_dd)) + ("_") + (($dt_hh) + ($dt_ii) + ($dt_ss)) + (ext));
				try {
					window.flutter_inappwebview.callHandler("saveImageHandler", ({"href":href,"ttl":ttl,"ext":ext}));
				} catch (e) {
					var link		= (document.createElement("a"));
					link.href		= (href);
					link.download	= (ttl);
					link.click();
				}
			});
		} catch (e) {
		}
	}
};