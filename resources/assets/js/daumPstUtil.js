/*
https://postcode.map.daum.net/guide
*/
/*
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
*/
const daumPstUtil = {
	fn_get : function() {
		var args	= (daumPstUtil.fn_get.arguments);
		var len		= (args.length);
		var id		= (args[0]);
		var fn		= (args[1]);
		var h		= ((len > 2) ? (args[2]) : (window.innerHeight));
		var w		= ((len > 3) ? (args[3]) : (window.innerWidth));
		var z		= ((len > 4) ? (args[4]) : ("0"));
		$("#" + (id)).empty();
		$("#" + (id)).css("height", h);
		$("#" + (id)).css("width", w);
		$("#" + (id)).css("z-index", z);
		var html = (document.getElementById(id));
		daum.postcode.load(function() {
			(new daum.Postcode({
				oncomplete			: function(rst) {fn(rst);},
				width				: ("100%"),
				height				: ("100%"),
				focusInput			: (false),
				shorthand			: (false),
				autoMappingRoad		: (false),
				autoMappingJibun	: (false),
				maxSuggestItems		: (3),
				showMoreHName		: (true),
				hideMapBtn			: (true),
				hideEngBtn			: (true),
				useBannerLink		: (false)
			}).embed(html));
			html.style.display		= ("block");
		});
	}
};