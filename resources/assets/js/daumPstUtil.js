/*
https://postcode.map.daum.net/guide
*/
/*
<script type="text/javascript" src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
*/
const daumPstUtil = {
	fn_et : function(v) {
		return ((v == null || v == undefined) || (typeof v == "string" && !v.trim().length) || ((typeof v == "object") && ((Array.isArray(v) && !v.length) || (!Object.keys(v).length))) || (typeof v == "boolean" && !v));
	},
	fn_trgt : function(trgt) {
		return ((daumPstUtil.fn_et(trgt)) ? ((document.querySelector("body") != null) ? (document.querySelector("body")) : (document.querySelector("html"))) : ((trgt.indexOf("#") > -1 || trgt.indexOf(".") > -1) ? ((document.querySelector(trgt) != null) ? (document.querySelector(trgt)) : (daumPstUtil.fn_trgt(null))) : ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : ((!!document.getElementsByClassName(trgt).length) ? (document.getElementsByClassName(trgt)[0]) : (daumPstUtil.fn_trgt(null))))));
	},
	fn_get : function() {
		var args	= (daumPstUtil.fn_get.arguments);
		var len		= (args.length);
		var trgt	= (args[0]);
		var fn		= (args[1]);
		var html	= (daumPstUtil.fn_trgt(trgt));
		$(html).empty();
		$(html).css("height",	((len > 2) ? (args[2]) : (window.innerHeight)));
		$(html).css("width",	((len > 3) ? (args[3]) : (window.innerWidth)));
		$(html).css("z-index",	((len > 4) ? (args[4]) : ("0")));
		daum.postcode.load(function() {
			(new daum.Postcode({
				oncomplete			: function(rst) {fn(rst);},
				height				: ("100%"),
				width				: ("100%"),
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