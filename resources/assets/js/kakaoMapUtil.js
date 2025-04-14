/*
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=aaaaaaaaaa&libraries=services,clusterer"></script>
*/
let kakaoMapUtil = {
	_map				: (null),
	_min_lv				: (3),
	_max_lv				: (15),
	_clstr				: (null),
	_clstr_lv			: (8),
	_cntr_lat			: ("37.5565789226072"),
	_cntr_lot			: ("126.959278026157"),
	_pst				: ([]),
	_mrk				: ([]),
	fn_get				: function(id) {
		$("#" + (id)).empty();
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
		kakaoMapUtil._map = (new kakao.maps.Map(document.getElementById(id), ops));
		setTimeout(() => kakaoMapUtil.fn_resz(id), 500);
		setTimeout(() => kakaoMapUtil.fn_hndlr(), 600);
		setTimeout(() => kakaoMapUtil.fn_clstr(), 700);
		setTimeout(() => kakaoMapUtil.fn_gps(), 800);
	},
	fn_clstr			: function() {
		var ops = {
			map				: (kakaoMapUtil._map),
			averageCenter	: (true),
			minLevel		: (kakaoMapUtil._clstr_lv)
		};
		kakaoMapUtil._clstr = (new kakao.maps.MarkerClusterer(ops));
	},
	fn_gps				: function() {
		var f = ("geolocation" in navigator && navigator.geolocation);
		if (f) {
			var ops = {
				enableHighAccuracy		: (true),
				timeout					: (3000),
				maximumAge				: (0)
			};
			navigator.geolocation.getCurrentPosition(kakaoMapUtil.fn_gps_y, kakaoMapUtil.fn_gps_n, ops);
		} else {
			kakaoMapUtil.fn_gps_n();
		}
	},
	fn_gps_y			: function(position) {
		if (position != null) {
			kakaoMapUtil.fn_move(kakaoMapUtil._min_lv, (kakaoMapUtil.fn_lat_lot(position.coords.latitude, position.coords.longitude)));
		}
		kakaoMapUtil.fn_data();
	},
	fn_gps_n			: function() {
		kakaoMapUtil.fn_data();
	},
	fn_data				: function() {
		kakaoMapUtil.fn_prvnt(false);
		$.ajax({
				url				: "/json/csCpInfoPage/csGroupList?groupBy=sid",
				data			: {},
				type			: "get",
				dataType		: "json",
				contentType		: "application/json; charset=UTF-8",
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
	fn_del_mrk			: function() {
		$(kakaoMapUtil._mrk).each(function(i) {
			kakaoMapUtil._mrk[i].setMap(null);
		});
		kakaoMapUtil._mrk = ([]);
		kakaoMapUtil._clstr.clear();
		kakaoMapUtil.fn_add_mrk();
	},
	fn_add_mrk			: function() {
		$(kakaoMapUtil._pst).each(function() {
			kakaoMapUtil._mrk.push(kakaoMapUtil.fn_mrk(this));
		});
		kakaoMapUtil._clstr.addMarkers(kakaoMapUtil._mrk);
		kakaoMapUtil.fn_prvnt(true);
	},
	fn_mrk				: function(obj) {
		var mrk = new kakao.maps.Marker({
			map			: (kakaoMapUtil._map),
			position	: (obj.latLot)
		});
		kakao.maps.event.addListener(mrk, "click", function() {
			kakaoMapUtil.fn_move(kakaoMapUtil._min_lv, obj.latLot);
		});
		return (mrk);
	},
	fn_resz				: function(id) {
		$("#" + (id)).css("height", window.innerHeight);
		$("#" + (id)).css("width", window.innerWidth);
		$("#" + (id)).css("z-index", "0");
		setTimeout(() => kakaoMapUtil._map.relayout(), 100);
	},
	fn_lat_lot			: function(lat, lot) {
		return (new kakao.maps.LatLng(((lat == null || lat == "" || lat == undefined) ? (kakaoMapUtil._cntr_lat) : (lat)), ((lot == null || lot == "" || lot == undefined) ? (kakaoMapUtil._cntr_lot) : (lot))));
	},
	fn_move				: function(lv, latLot) {
		kakaoMapUtil._map.jump(latLot, lv);
	},
	fn_hndlr			: function() {
		kakao.maps.event.addListener(kakaoMapUtil._map, "zoom_changed", function() {
		});
		kakao.maps.event.addListener(kakaoMapUtil._map, "dragend", function() {
		});
	},
	fn_prvnt			: function(tp) {
		kakaoMapUtil._map.setDraggable(tp);
		kakaoMapUtil._map.setZoomable(tp);
		kakaoMapUtil._map.setKeyboardShortcuts(tp);
	}
};
let kakaoStaticMapUtil = {
	_map				: (null),
	_lv					: (3),
	fn_get				: function(id, lat, lot) {
		$("#" + (id)).empty();
		$("#" + (id)).css("height", window.innerHeight);
		$("#" + (id)).css("width", window.innerHeight);
		$("#" + (id)).css("z-index", "0");
		var ops = {
			center		: (new kakao.maps.LatLng(lat, lot)),
			level		: (kakaoStaticMapUtil._lv),
			marker		: ({position:(new kakao.maps.LatLng(lat, lot))})
		};
		kakaoStaticMapUtil._map = (new kakao.maps.StaticMap(document.getElementById(id), ops));
		setTimeout(() => $("#" + (id)).find("a").attr("href", "javascript:void(0);"), 100);
		setTimeout(() => $("#" + (id)).find("a").attr("target", ""), 200);
	}
};