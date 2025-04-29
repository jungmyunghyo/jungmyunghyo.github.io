let $dim_tmr_cnt = (0);
let $dim_tmr = (null);
const dimUtil = {
	fn_dim : function(f) {
		((f) ? ($("html").append("<div class='dim-layer-bg' style='position:fixed;top:0;left:0;width:100%;height:100%;z-index:999;overflow-x:hidden;overflow-y:auto;'><div style='position:absolute;top:0;left:0;width:100%;height:100%;z-index:999;background:#000;opacity:0.5;'></div></div>")) : ($(".dim-layer-bg").remove()));
	},
	fn_load : function(f) {
		((f) ? ($("html").append("<div class='dim-layer-bg' style='position:fixed;top:0;left:0;width:100%;height:100%;z-index:999;overflow-x:hidden;overflow-y:auto;'><div style='position:absolute;top:0;left:0;width:100%;height:100%;z-index:999;background:#000;opacity:0.5;'><img src='/mobiles/img/loading.gif' style='position:absolute;top:50%;left:50%;width:4rem;height:4rem;z-index:999;margin-top:-2rem;margin-left:-2rem;opacity:0.5;' onerror='this.remove();'></div></div>")) : ($(".dim-layer-bg").remove()));
	},
	fn_time : function(f) {
		$dim_tmr_cnt = (0);
		((f) ? ($("html").append("<div class='dim-layer-bg' style='position:fixed;top:0;left:0;width:100%;height:100%;z-index:999;overflow-x:hidden;overflow-y:auto;'><div style='position:absolute;top:0;left:0;width:100%;height:100%;z-index:999;background:#000;opacity:0.5;'><img src='/mobiles/img/loading.gif' style='position:absolute;top:50%;left:50%;width:4rem;height:4rem;z-index:999;margin-top:-2rem;margin-left:-2rem;opacity:0.5;' onerror='this.remove();'><span class='dim-layer-tmr' style='position:absolute;top:50%;left:50%;width:6rem;height:2rem;z-index:999;margin-top:2rem;margin-left:-0.5rem;opacity:0.5;color:#fff;'>0s.</span></div></div>")) : ($(".dim-layer-bg").remove()));
		(($dim_tmr != null) ? (clearInterval($dim_tmr)) : (""));
		$dim_tmr = ((f) ? (setInterval(() => dimUtil.fn_txt(), 1000)) : (null));
	},
	fn_txt : function() {
		$dim_tmr_cnt++;
		$(".dim-layer-tmr").html(($dim_tmr_cnt > 60) ? ((Math.ceil($dim_tmr_cnt / 60) - 1) + "m." + ($dim_tmr_cnt % 60) + "s.") : ($dim_tmr_cnt + "s."));
		$(".dim-layer-tmr").css("margin-left", (($dim_tmr_cnt > 60) ? ("-1.5rem") : (($dim_tmr_cnt >= 10) ? ("-1rem") : ("-0.5rem"))));
	}
};