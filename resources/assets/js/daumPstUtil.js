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
		var trgt	= (args[0]);
		var fn		= (args[1]);
		var bf		= ((!!$("#" + (trgt)).length) ? ("#" + (trgt)) : ("." + (trgt)));
		$(bf).empty();
		$(bf).css("height",		((len > 2) ? (args[2]) : (window.innerHeight)));
		$(bf).css("width",		((len > 3) ? (args[3]) : (window.innerWidth)));
		$(bf).css("z-index",	((len > 4) ? (args[4]) : ("0")));
		var html	= ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : ((document.getElementsByClassName(trgt) != null) ? (document.getElementsByClassName(trgt)[0]) : (document.querySelector("body"))));
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