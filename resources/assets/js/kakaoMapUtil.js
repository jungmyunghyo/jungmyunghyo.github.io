/*
https://apis.map.kakao.com/web/sample
https://apis.map.kakao.com/web/documentation
*/
/*
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=aaaaaaaaaa&libraries=services,clusterer"></script>
*/
let kakaoMapUtil = {
	_map		: (null),
	_min_lv		: (3),
	_max_lv		: (15),
	_cntr_lat	: ("37.5565789226072"),
	_cntr_lot	: ("126.959278026157"),
	_pst		: ([]),
	_mrk		: ([]),
	fn_get : function() {
		var args	= (kakaoMapUtil.fn_get.arguments);
		var len		= (args.length);
		var trgt	= (args[0]);
		var bf		= ((!!$("#" + (trgt)).length) ? ("#" + (trgt)) : ("." + (trgt)));
		$(bf).empty();
		var html	= ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : (document.getElementsByClassName(trgt)[0]));
		var ops = {
			center					: (kakaoMapUtil.fn_lat_lot(kakaoMapUtil._cntr_lat, kakaoMapUtil._cntr_lot)),
			level					: (kakaoMapUtil._min_lv),
			maxLevel				: (kakaoMapUtil._max_lv),
			draggable				: (false),
			zoomable				: (false),
			scrollwheel				: (false),
			disableDoubleClick		: (true),
			disableDoubleClickZoom	: (true),
			keyboardShortcuts		: (false)
		};
		kakaoMapUtil._map = (new kakao.maps.Map(html, ops));
		setTimeout(() => kakaoMapUtil.fn_resz(trgt, ((len > 1) ? (args[1]) : (window.innerHeight)), ((len > 2) ? (args[2]) : (window.innerWidth)), ((len > 3) ? (args[3]) : ("0"))), 500);
		setTimeout(() => kakaoMapUtil._map.relayout(), 600);
		setTimeout(() => kakaoMapUtil.fn_hndlr(), 700);
		setTimeout(() => kakaoMapUtil.fn_gps(kakaoMapUtil.fn_gps_y, kakaoMapUtil.fn_gps_n), 800);
	},
	fn_gps : function(fn_y, fn_n) {
		if ("geolocation" in navigator && navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(fn_y, fn_n, ({enableHighAccuracy:(true),timeout:(3000),maximumAge:(0)}));
		} else {
			fn_n();
		}
	},
	fn_gps_y : function(position) {
		if (position != null) {
			kakaoMapUtil.fn_move(kakaoMapUtil._min_lv, (kakaoMapUtil.fn_lat_lot(position.coords.latitude, position.coords.longitude)));
		}
		kakaoMapUtil.fn_data();
	},
	fn_gps_n : function() {
		kakaoMapUtil.fn_data();
	},
	fn_data : function() {
		kakaoMapUtil.fn_prvnt(false);
		$.ajax({
			url			: "/json/csCpInfoPage/csGroupList",
			data		: {},
			type		: "get",
			dataType	: "json",
			contentType	: "application/json; charset=UTF-8",
			success:function(data) {
				if (data != null && data.list != null && data.cnt != null && data.cnt > 0) {
					$(data.list).each(function() {
						kakaoMapUtil._pst.push({"latLot":(kakaoMapUtil.fn_lat_lot((this.gps.split(",")[0]), (this.gps.split(",")[1])))});
					});
				}
				kakaoMapUtil.fn_del_mrk();
			}
		});
	},
	fn_del_mrk : function() {
		$(kakaoMapUtil._mrk).each(function(i) {
			kakaoMapUtil._mrk[i].setMap(null);
		});
		kakaoMapUtil._mrk = ([]);
		kakaoMapUtil.fn_add_mrk();
	},
	fn_add_mrk : function() {
		$(kakaoMapUtil._pst).each(function() {
			kakaoMapUtil._mrk.push(kakaoMapUtil.fn_mrk(this));
		});
		kakaoMapUtil.fn_prvnt(true);
	},
	fn_mrk : function(obj) {
		var mrk = (new kakao.maps.Marker({
			map			: (kakaoMapUtil._map),
			position	: (obj.latLot)
		}));
		kakao.maps.event.addListener(mrk, "click", function() {
			kakaoMapUtil.fn_move(kakaoMapUtil._min_lv, obj.latLot);
		});
		return (mrk);
	},
	fn_resz : function(trgt, h, w, z) {
		var bf		= ((!!$("#" + (trgt)).length) ? ("#" + (trgt)) : ("." + (trgt)));
		$(bf).css("height",		(h));
		$(bf).css("width",		(w));
		$(bf).css("z-index",	(z));
	},
	fn_lat_lot : function(lat, lot) {
		return (new kakao.maps.LatLng(((lat == null || lat == "" || lat == undefined) ? (kakaoMapUtil._cntr_lat) : (lat)), ((lot == null || lot == "" || lot == undefined) ? (kakaoMapUtil._cntr_lot) : (lot))));
	},
	fn_move : function(lv, latLot) {
		kakaoMapUtil._map.jump(latLot, lv);
	},
	fn_hndlr : function() {
		kakao.maps.event.addListener(kakaoMapUtil._map, "zoom_changed", function() {
		});
		kakao.maps.event.addListener(kakaoMapUtil._map, "dragend", function() {
		});
	},
	fn_prvnt : function(tp) {
		kakaoMapUtil._map.setDraggable(tp);
		kakaoMapUtil._map.setZoomable(tp);
		kakaoMapUtil._map.setKeyboardShortcuts(tp);
	}
};
let kakaoClstrMapUtil = {
	_map		: (null),
	_min_lv		: (3),
	_max_lv		: (15),
	_clstr		: (null),
	_clstr_lv	: (8),
	_pst		: ([]),
	_mrk		: ([]),
	fn_get : function() {
		var args	= (kakaoClstrMapUtil.fn_get.arguments);
		var len		= (args.length);
		var trgt	= (args[0]);
		var bf		= ((!!$("#" + (trgt)).length) ? ("#" + (trgt)) : ("." + (trgt)));
		$(bf).empty();
		var html	= ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : (document.getElementsByClassName(trgt)[0]));
		var ops = {
			center					: (kakaoMapUtil.fn_lat_lot(kakaoMapUtil._cntr_lat, kakaoMapUtil._cntr_lot)),
			level					: (kakaoClstrMapUtil._min_lv),
			maxLevel				: (kakaoClstrMapUtil._max_lv),
			draggable				: (false),
			zoomable				: (false),
			scrollwheel				: (false),
			disableDoubleClick		: (true),
			disableDoubleClickZoom	: (true),
			keyboardShortcuts		: (false)
		};
		kakaoClstrMapUtil._map = (new kakao.maps.Map(html, ops));
		setTimeout(() => kakaoMapUtil.fn_resz(trgt, ((len > 1) ? (args[1]) : (window.innerHeight)), ((len > 2) ? (args[2]) : (window.innerWidth)), ((len > 3) ? (args[3]) : ("0"))), 500);
		setTimeout(() => kakaoClstrMapUtil._map.relayout(), 600);
		setTimeout(() => kakaoClstrMapUtil.fn_hndlr(), 700);
		setTimeout(() => kakaoClstrMapUtil.fn_clstr(), 800);
		setTimeout(() => kakaoMapUtil.fn_gps(kakaoClstrMapUtil.fn_gps_y, kakaoClstrMapUtil.fn_gps_n), 900);
	},
	fn_clstr : function() {
		kakaoClstrMapUtil._clstr = (new kakao.maps.MarkerClusterer({map:(kakaoClstrMapUtil._map),averageCenter:(true),minLevel:(kakaoClstrMapUtil._clstr_lv)}));
	},
	fn_gps_y : function(position) {
		if (position != null) {
			kakaoClstrMapUtil.fn_move(kakaoClstrMapUtil._min_lv, (kakaoMapUtil.fn_lat_lot(position.coords.latitude, position.coords.longitude)));
		}
		kakaoClstrMapUtil.fn_data();
	},
	fn_gps_n : function() {
		kakaoClstrMapUtil.fn_data();
	},
	fn_data : function() {
		kakaoClstrMapUtil.fn_prvnt(false);
		$.ajax({
			url			: "/json/csCpInfoPage/csGroupList",
			data		: {},
			type		: "get",
			dataType	: "json",
			contentType	: "application/json; charset=UTF-8",
			success:function(data) {
				if (data != null && data.list != null && data.cnt != null && data.cnt > 0) {
					$(data.list).each(function() {
						kakaoClstrMapUtil._pst.push({"latLot":(kakaoMapUtil.fn_lat_lot((this.gps.split(",")[0]), (this.gps.split(",")[1])))});
					});
				}
				kakaoClstrMapUtil.fn_del_mrk();
			}
		});
	},
	fn_del_mrk : function() {
		$(kakaoClstrMapUtil._mrk).each(function(i) {
			kakaoClstrMapUtil._mrk[i].setMap(null);
		});
		kakaoClstrMapUtil._mrk = ([]);
		kakaoClstrMapUtil._clstr.clear(); 
		kakaoClstrMapUtil.fn_add_mrk();
	},
	fn_add_mrk : function() {
		$(kakaoClstrMapUtil._pst).each(function() {
			kakaoClstrMapUtil._mrk.push(kakaoClstrMapUtil.fn_mrk(this));
		});
		kakaoClstrMapUtil._clstr.addMarkers(kakaoClstrMapUtil._mrk);
		kakaoClstrMapUtil.fn_prvnt(true);
	},
	fn_mrk : function(obj) {
		var mrk = (new kakao.maps.Marker({
			map			: (kakaoClstrMapUtil._map),
			position	: (obj.latLot)
		}));
		kakao.maps.event.addListener(mrk, "click", function() {
			kakaoClstrMapUtil.fn_move(kakaoClstrMapUtil._min_lv, obj.latLot);
		});
		return (mrk);
	},
	fn_move : function(lv, latLot) {
		kakaoClstrMapUtil._map.jump(latLot, lv);
	},
	fn_hndlr : function() {
		kakao.maps.event.addListener(kakaoClstrMapUtil._map, "zoom_changed", function() {
		});
		kakao.maps.event.addListener(kakaoClstrMapUtil._map, "dragend", function() {
		});
	},
	fn_prvnt : function(tp) {
		kakaoClstrMapUtil._map.setDraggable(tp);
		kakaoClstrMapUtil._map.setZoomable(tp);
		kakaoClstrMapUtil._map.setKeyboardShortcuts(tp);
	}
};
let kakaoStaticMapUtil = {
	_map	: (null),
	_lv		: (3),
	fn_get : function() {
		var args	= (kakaoStaticMapUtil.fn_get.arguments);
		var len		= (args.length);
		var trgt	= (args[0]);
		var lat		= (args[1]);
		var lot		= (args[2]);
		var bf		= ((!!$("#" + (trgt)).length) ? ("#" + (trgt)) : ("." + (trgt)));
		$(bf).empty();
		$(bf).css("height",		((len > 3) ? (args[3]) : (window.innerHeight)));
		$(bf).css("width",		((len > 4) ? (args[4]) : (window.innerWidth)));
		$(bf).css("z-index",	((len > 5) ? (args[5]) : ("0")));
		var html	= ((document.getElementById(trgt) != null) ? (document.getElementById(trgt)) : (document.getElementsByClassName(trgt)[0]));
		var ops = {
			center	: (new kakao.maps.LatLng(lat, lot)),
			level	: (kakaoStaticMapUtil._lv),
			marker	: ({position:(new kakao.maps.LatLng(lat, lot))})
		};
		kakaoStaticMapUtil._map = (new kakao.maps.StaticMap(html, ops));
		setTimeout(() => $(bf).find("a").attr("href", "javascript:void(0);"), 100);
		setTimeout(() => $(bf).find("a").attr("target", ""), 200);
	}
};
let kakaoGeocoderUtil = {
	fn_get_addr : function(lat, lot, fn) {
		(new kakao.maps.services.Geocoder()).coord2RegionCode(lot, lat, function(rst, stat) {
			(((rst == null || rst == "" || rst == undefined) || (stat == null || stat == "" || stat == undefined)) ? ("") : ((stat == kakao.maps.services.Status.OK && rst.length > 0) ? (fn(rst)) : ("")));
		});
	},
	fn_get_gps : function(addr, fn) {
		(new kakao.maps.services.Geocoder()).addressSearch(addr, function(rst, stat) {
			(((rst == null || rst == "" || rst == undefined) || (stat == null || stat == "" || stat == undefined)) ? ("") : ((stat == kakao.maps.services.Status.OK && rst.length == 1) ? (fn(rst)) : ("")));
		});
	}
};