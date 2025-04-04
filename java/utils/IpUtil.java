import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_str_cma					= (",");
	private static final String						_str_col					= (":");
	private static final String						_str_null					= (null);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_spl_cma					= (",");
	private static final String						_spl_col					= (":");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_not_in						= (-1);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final List<String>				_ip_hdr_l					= (List.of("X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-RealIP", "REMOTE_ADDR"));
	private static final List<String>				_ip_npe_l					= (List.of("0:0:0:0:0:0:0:1", "null", "unknown"));
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String getIp(HttpServletRequest r) {
		String ip = (_str_null);
		boolean is = (false);
		if (r != null) {
			if (r.getHeaderNames() != null) {
				for (String k : _ip_hdr_l) {
					if (!is) {
						ip = (r.getHeader(k));
						is = (ip != null && !(_ip_npe_l.indexOf(ip) > _not_in));
					} else {
						break;
					}
				}
			}
			if (!is) {
				ip = (r.getRemoteAddr());
				is = (ip != null && !(_ip_npe_l.indexOf(ip) > _not_in));
			}
		}
		return ((!is) ? (getIp()) : (getIp(ip)));
	}
	public static String getIp() {
		try {
			String ip = (InetAddress.getLocalHost().getHostAddress());
			return (getIp((ip != null && !(_ip_npe_l.indexOf(ip) > _not_in)) ? (ip) : (InetAddress.getLoopbackAddress().getHostAddress())));
		} catch (UnknownHostException e) {
		}
		return (_str_null);
	}
	private static String getIp(String ip) {
		if (ip != null) {
			if (ip.contains(_str_cma)) {
				ip = (ip.split(_spl_cma)[0]);
			}
			return ((!(_ip_npe_l.indexOf(ip) > _not_in) && ip.contains(_str_col)) ? (ip.split(_spl_col)[0]) : (ip));
		}
		return (ip);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
}