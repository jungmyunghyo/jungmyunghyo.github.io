/*
https://postcode.map.daum.net/guide
*/
/*
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
*/
const daumPstUtil = {
	fn_get : function(id, fn) {
		var html = (document.getElementById(id));
		daum.postcode.load(function() {
			(new daum.Postcode({
				oncomplete			: function() {fn($(this));},
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
		$("#" + (id)).css("height", window.innerHeight);
		$("#" + (id)).css("width", window.innerWidth);
	}
};