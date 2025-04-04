import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ValueUtil {
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_str_blk					= ("");
	private static final String						_str_zero					= ("0");
	private static final String						_str_spc					= (" ");
	private static final String						_str_dcml					= (".");
	private static final String						_str_cma					= (",");
	private static final String						_str_eq						= ("=");
	private static final String						_str_and					= ("&");
	private static final String						_str_qt						= ("?");
	private static final String						_str_l_strt					= ("[");
	private static final String						_str_l_end					= ("]");
	private static final String						_str_a_strt					= ("{");
	private static final String						_str_a_end					= ("}");
	private static final String						_str_to						= ("=>");
	private static final String						_str_y						= ("Y");
	private static final String						_str_n						= ("N");
	private static final String						_str_null					= (null);
	private static final String						_str_charset				= ("UTF-8");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_spl_cma					= (",");
	private static final String						_spl_and					= ("&");
	private static final String						_spl_eq						= ("=");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_sub_strt					= (0);
	private static final int						_not_in						= (-1);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_int_def					= (99);
	private static final String						_str_int_def				= ("98");
	private static final long						_long_def					= (88);
	private static final String						_str_long_def				= ("87");
	private static final double						_dobl_def					= (77.77);
	private static final String						_str_dobl_def				= ("76.76");
	private static final BigDecimal					_dcml_def					= (new BigDecimal("66.66"));
	private static final String						_str_dcml_def				= ("65.65");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_fmt_int					= ("%,d");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_rplc_int					= ("[^0-9.E]");
	private static final String						_rplc_cma					= ("\\B(?=([0-9]{3})+(?![0-9]))");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_int					= ("^[0-9]{1,10}$");
	private static final String						_reg_dobl					= ("^[0-9]*[.][0-9]*$");
	private static final String						_reg_dcml					= ("^[0-9]+[.][0-9]+[E][0-9]+$");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final Pattern					_ptrn_int					= (Pattern.compile(_reg_int));
	private static final Pattern					_ptrn_dobl					= (Pattern.compile(_reg_dobl));
	private static final Pattern					_ptrn_dcml					= (Pattern.compile(_reg_dcml));
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_sz_def						= (0);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final List<String>				_s_l_def					= (new ArrayList<>(0));
	private static final String[]					_s_a_def					= (new String[0]);
	private static final Map<String, Object>		_m_def						= (new HashMap<String, Object>(0));
	private static final JSONObject					_jo_def						= (new JSONObject());
	private static final List<Map<String, Object>>	_m_l_def					= (new ArrayList<Map<String, Object>>(0));
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static boolean ptrnInt(Object o) {
		return (_ptrn_int.matcher(o.toString()).matches());
	}
	private static boolean ptrnDobl(Object o) {
		return (_ptrn_dobl.matcher(o.toString()).matches());
	}
	private static boolean ptrnDcml(Object o) {
		return (_ptrn_dcml.matcher(o.toString()).matches());
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static int prsInt(Object o) {
		String rS = (o.toString().replaceAll(_rplc_int, _str_blk));
		return ((ptrnInt(rS)) ? (Integer.parseInt(rS)) : ((!rS.equals(_str_dcml) && ((ptrnDcml(rS)) || (ptrnDobl(rS)))) ? ((int) Double.parseDouble(rS)) : (_int_def)));
	}
	private static long prsLong(Object o) {
		String rS = (o.toString().replaceAll(_rplc_int, _str_blk));
		return ((ptrnInt(rS)) ? (Long.parseLong(rS)) : ((!rS.equals(_str_dcml) && ((ptrnDcml(rS)) || (ptrnDobl(rS)))) ? ((long) Double.parseDouble(rS)) : (_long_def)));
	}
	private static String prsNum(Object o) {
		String rS = (o.toString().replaceAll(_rplc_int, _str_blk));
		return ((ptrnInt(rS)) ? (rS) : ((!rS.equals(_str_dcml) && ((ptrnDcml(rS)) || (ptrnDobl(rS)))) ? (String.valueOf((long) Double.parseDouble(rS))) : (_str_long_def)));
	}
	private static double prsDobl(Object o) {
		String rS = (o.toString().replaceAll(_rplc_int, _str_blk));
		return (((ptrnInt(rS)) || (!rS.equals(_str_dcml) && ((ptrnDcml(rS)) || (ptrnDobl(rS))))) ? (Double.parseDouble(rS)) : (_dobl_def));
	}
	private static BigDecimal prstrtDcml(Object o) {
		String rS = (o.toString().replaceAll(_rplc_int, _str_blk));
		return (((ptrnInt(rS)) || (!rS.equals(_str_dcml) && ((ptrnDcml(rS)) || (ptrnDobl(rS))))) ? (BigDecimal.valueOf(Double.parseDouble(rS))) : (_dcml_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static String cmaInt(Object o) {
		return (String.format(_fmt_int, (prsInt(o))));
	}
	private static String cmaLong(Object o) {
		return (String.format(_fmt_int, (prsLong(o))));
	}
	private static String cmaNum(Object o) {
		return (prsNum(o).replaceAll(_rplc_cma, _str_cma));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static String zeroLft(Object o, int len) {
		String rS = (o.toString());
		return ((rS.length() > len) ? (rS.substring(_sub_strt, len)) : ((rS.length() == len) ? (rS) : (String.format("%" + len + "s", (rS)).replace(_str_spc, _str_zero))));
	}
	private static String zeroRght(Object o, int len) {
		String rS = (o.toString());
		return ((rS.length() > len) ? (rS.substring(_sub_strt, len)) : ((rS.length() == len) ? (rS) : (String.format("%-" + len + "s", (rS)).replace(_str_spc, _str_zero))));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static String strtDcml(Object o) {
		String rS = (o.toString());
		return (rS.substring(_sub_strt, rS.indexOf(_str_dcml)));
	}
	private static String endDcml(Object o) {
		String rS = (o.toString());
		return (rS.substring(rS.indexOf(_str_dcml) + 1));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static int toInt(Object o) {
		return ((o != null) ? (prsInt(o)) : (_int_def));
	}
	public static int mToInt(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toInt(m.get(k))) : (_int_def));
	}
	public static int joToInt(JSONObject j, String k) {
		return ((j != null && k != null) ? (toInt(j.get(k))) : (_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toIntAsStr(Object o) {
		return ((o != null) ? (String.valueOf(prsInt(o))) : (_str_int_def));
	}
	public static String mToIntAsStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toIntAsStr(m.get(k))) : (_str_int_def));
	}
	public static String joToIntAsStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toIntAsStr(j.get(k))) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toIntAsCmaStr(Object o) {
		return ((o != null) ? (cmaInt(o)) : (_str_int_def));
	}
	public static String mToIntAsCmaStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toIntAsCmaStr(m.get(k))) : (_str_int_def));
	}
	public static String joToIntAsCmaStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toIntAsCmaStr(j.get(k))) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static long toLong(Object o) {
		return ((o != null) ? (prsLong(o)) : (_long_def));
	}
	public static long mToLong(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toLong(m.get(k))) : (_long_def));
	}
	public static long joToLong(JSONObject j, String k) {
		return ((j != null && k != null) ? (toLong(j.get(k))) : (_long_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toLongAsStr(Object o) {
		return ((o != null) ? (String.valueOf(prsLong(o))) : (_str_long_def));
	}
	public static String mToLongAsStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toLongAsStr(m.get(k))) : (_str_long_def));
	}
	public static String joToLongAsStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toLongAsStr(j.get(k))) : (_str_long_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toLongAsCmaStr(Object o) {
		return ((o != null) ? (cmaLong(o)) : (_str_long_def));
	}
	public static String mToLongAsCmaStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toLongAsCmaStr(m.get(k))) : (_str_long_def));
	}
	public static String joToLongAsCmaStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toLongAsCmaStr(j.get(k))) : (_str_long_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toNumAsStr(Object o) {
		return ((o != null) ? (prsNum(o)) : (_str_int_def));
	}
	public static String mToNumAsStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toNumAsStr(m.get(k))) : (_str_int_def));
	}
	public static String joToNumAsStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toNumAsStr(j.get(k))) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toNumAsCmaStr(Object o) {
		return ((o != null) ? (cmaNum(o)) : (_str_int_def));
	}
	public static String mToNumAsCmaStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toNumAsCmaStr(m.get(k))) : (_str_int_def));
	}
	public static String joToNumAsCmaStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toNumAsCmaStr(j.get(k))) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toNumAsLftStr(Object o, int len) {
		return ((o != null) ? (zeroLft(prsNum(o), len)) : (_str_int_def));
	}
	public static String mToNumAsLftStr(Map<String, Object> m, String k, int len) {
		return ((m != null && k != null) ? (toNumAsLftStr(m.get(k), len)) : (_str_int_def));
	}
	public static String joToNumAsLftStr(JSONObject j, String k, int len) {
		return ((j != null && k != null) ? (toNumAsLftStr(j.get(k), len)) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toNumAsLftCmaStr(Object o, int len) {
		return ((o != null) ? (cmaNum(zeroLft(prsNum(o), len))) : (_str_int_def));
	}
	public static String mToNumAsLftCmaStr(Map<String, Object> m, String k, int len) {
		return ((m != null && k != null) ? (toNumAsLftCmaStr(m.get(k), len)) : (_str_int_def));
	}
	public static String joToNumAsLftCmaStr(JSONObject j, String k, int len) {
		return ((j != null && k != null) ? (toNumAsLftCmaStr(j.get(k), len)) : (_str_int_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static double toDobl(Object o) {
		return ((o != null) ? (prsDobl(o)) : (_dobl_def));
	}
	public static double mToDobl(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toDobl(m.get(k))) : (_dobl_def));
	}
	public static double joToDobl(JSONObject j, String k) {
		return ((j != null && k != null) ? (toDobl(j.get(k))) : (_dobl_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toDoblAsStr(Object o) {
		return ((o != null) ? (String.valueOf(prsDobl(o))) : (_str_dobl_def));
	}
	public static String mToDoblAsStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toDoblAsStr(m.get(k))) : (_str_dobl_def));
	}
	public static String joToDoblAsStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toDoblAsStr(j.get(k))) : (_str_dobl_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toDcmlAsStr(Object o) {
		return ((o != null) ? (prstrtDcml(o).toString()) : (_str_dcml_def));
	}
	public static String mToDcmlAsStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toDcmlAsStr(m.get(k))) : (_str_dcml_def));
	}
	public static String joToDcmlAsStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toDcmlAsStr(j.get(k))) : (_str_dcml_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toDcmlAsCmaStr(Object o) {
		String rS = ((o != null) ? (prstrtDcml(o).toString()) : (_str_dcml_def));
		return ((cmaInt(strtDcml(rS))) + (_str_dcml) + (endDcml(rS)));
	}
	public static String mToDcmlAsCmaStr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (toDcmlAsCmaStr(m.get(k))) : (_str_dcml_def));
	}
	public static String joToDcmlAsCmaStr(JSONObject j, String k) {
		return ((j != null && k != null) ? (toDcmlAsCmaStr(j.get(k))) : (_str_dcml_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toDcmlAsRghtStr(Object o, int len) {
		String rS = ((o != null) ? (prstrtDcml(o).toString()) : (_str_dcml_def));
		return ((strtDcml(rS)) + (_str_dcml) + (zeroRght(endDcml(rS), len)));
	}
	public static String mToDcmlAsRghtStr(Map<String, Object> m, String k, int len) {
		return ((m != null && k != null) ? (toDcmlAsRghtStr(m.get(k), len)) : (_str_dcml_def));
	}
	public static String joToDcmlAsRghtStr(JSONObject j, String k, int len) {
		return ((j != null && k != null) ? (toDcmlAsRghtStr(j.get(k), len)) : (_str_dcml_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toDcmlAsRghtCmaStr(Object o, int len) {
		String rS = ((o != null) ? (prstrtDcml(o).toString()) : (_str_dcml_def));
		return ((cmaInt(strtDcml(rS))) + (_str_dcml) + (zeroRght(endDcml(rS), len)));
	}
	public static String mToDcmlAsRghtCmaStr(Map<String, Object> m, String k, int len) {
		return ((m != null && k != null) ? (toDcmlAsRghtCmaStr(m.get(k), len)) : (_str_dcml_def));
	}
	public static String joToDcmlAsRghtCmaStr(JSONObject j, String k, int len) {
		return ((j != null && k != null) ? (toDcmlAsRghtCmaStr(j.get(k), len)) : (_str_dcml_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String toStr(Object o, Object def) {
		return ((o != null && o.toString().trim().length() > 0) ? (o.toString().trim()) : ((def != null && def.toString().trim().length() > 0) ? (def.toString().trim()) : (_str_blk)));
	}
	public static String toStr(Object o) {
		return ((o != null && o.toString().trim().length() > 0) ? (o.toString().trim()) : (_str_blk));
	}
	public static String toStr(List<?> vL) {
		return ((vL != null && !vL.isEmpty()) ? (vL.toString()) : ((_str_l_strt) + (_str_l_end)));
	}
	public static String toStr(Object[] vA) {
		return ((vA != null && vA.length > 0) ? (Arrays.asList(vA).toString().replace(_str_l_strt, _str_a_strt).replace(_str_l_end, _str_a_end)) : ((_str_a_strt) + (_str_a_end)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static List<String> toList(List<?> vL) {
		int sz = ((vL != null) ? (vL.size()) : (_sz_def));
		if (sz > 0) {
			List<String> rL = (new ArrayList<>(sz));
			int a;
			Object vO;
			String vS;
			for (a=0; a<sz; a++) {
				vO = (vL.get(a));
				if (vO != null) {
					vS = (vO.toString().trim());
					if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
						rL.add(vS);
					}
				}
			}
			return (rL);
		}
		return (_s_l_def);
	}
	public static List<String> toList(Object[] vA) {
		int sz = ((vA != null) ? (vA.length) : (_sz_def));
		if (sz > 0) {
			List<String> rL = (new ArrayList<>(sz));
			int a;
			Object vO;
			String vS;
			for (a=0; a<sz; a++) {
				vO = (vA[a]);
				if (vO != null) {
					vS = (vO.toString().trim());
					if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
						rL.add(vS);
					}
				}
			}
			return (rL);
		}
		return (_s_l_def);
	}
	public static List<String> toList(Set<?> vSe) {
		if (vSe != null && !vSe.isEmpty()) {
			Iterator<?> it = (vSe.iterator());
			if (it != null) {
				List<String> rL = (new ArrayList<>(vSe.size()));
				Object vO;
				String vS;
				while (it.hasNext()) {
					vO = (it.next());
					if (vO != null) {
						vS = (vO.toString().trim());
						if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
							rL.add(vS);
						}
					}
				}
				return (rL);
			}
		}
		return (_s_l_def);
	}
	public static List<String> toList(Enumeration<?> vEe) {
		if (vEe != null) {
			Iterator<?> it = (vEe.asIterator());
			if (it != null) {
				List<String> rL = (new ArrayList<>());
				Object vO;
				String vS;
				while (it.hasNext()) {
					vO = (it.next());
					if (vO != null) {
						vS = (vO.toString().trim());
						if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
							rL.add(vS);
						}
					}
				}
				return (rL);
			}
		}
		return (_s_l_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String[] toArr(List<?> vL) {
		int sz = ((vL != null) ? (vL.size()) : (_sz_def));
		if (sz > 0) {
			List<String> rL = (new ArrayList<>(sz));
			int a;
			Object vO;
			String vS;
			for (a=0; a<sz; a++) {
				vO = (vL.get(a));
				if (vO != null) {
					vS = (vO.toString().trim());
					if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
						rL.add(vS);
					}
				}
			}
			sz = (rL.size());
			if (sz > 0) {
				String[] rA = (new String[sz]);
				for (a=0; a<sz; a++) {
					rA[a] = (rL.get(a));
				}
				return (rA);
			}
		}
		return (_s_a_def);
	}
	public static String[] toArr(Object[] vA) {
		int sz = ((vA != null) ? (vA.length) : (_sz_def));
		if (sz > 0) {
			List<String> rL = (new ArrayList<>(sz));
			int a;
			Object vO;
			String vS;
			for (a=0; a<sz; a++) {
				vO = (vA[a]);
				if (vO != null) {
					vS = (vO.toString().trim());
					if (vS.length() > 0 && !(rL.indexOf(vS) > _not_in)) {
						rL.add(vS);
					}
				}
			}
			sz = (rL.size());
			if (sz > 0) {
				String[] rA = (new String[sz]);
				for (a=0; a<sz; a++) {
					rA[a] = (rL.get(a));
				}
				return (rA);
			}
		}
		return (_s_a_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String[] splToArr(Object o) {
		return ((o != null) ? (toArr(o.toString().split(_spl_cma))) : (_s_a_def));
	}
	public static String[] mSplToArr(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (splToArr(m.get(k))) : (_s_a_def));
	}
	public static String[] joSplToArr(JSONObject j, String k) {
		return ((j != null && k != null) ? (splToArr(j.get(k))) : (_s_a_def));
	}
	public static String[] reqParamSplToArr(HttpServletRequest r, String k) {
		return ((r != null && k != null) ? (splToArr(r.getParameter(k))) : (_s_a_def));
	}
	public static String[] reqHdrSplToArr(HttpServletRequest r, String k) {
		return ((r != null && k != null) ? (splToArr(r.getHeader(k))) : (_s_a_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static Map<String, Object> mToM(Map<String, Object> m, List<?> kL) {
		int sz = ((m != null && !m.isEmpty() && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			Map<String, Object> rM = (new HashMap<String, Object>(sz));
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (m.get(kO));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rM.put(kS, vS);
							}
						}
					}
				}
			}
			return (rM);
		}
		return (_m_def);
	}
	public static Map<String, Object> mToM(Map<String, Object> m) {
		return ((m != null && !m.isEmpty()) ? (mToM(m, toList(m.keySet()))) : (_m_def));
	}
	public static Map<String, Object> joToM(JSONObject j, List<?> kL) {
		int sz = ((j != null && !j.isEmpty() && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			Map<String, Object> rM = (new HashMap<String, Object>(sz));
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (j.get(kO));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rM.put(kS, vS);
							}
						}
					}
				}
			}
			return (rM);
		}
		return (_m_def);
	}
	public static Map<String, Object> joToM(JSONObject j) {
		return ((j != null && !j.isEmpty()) ? (joToM(j, toList(j.keySet()))) : (_m_def));
	}
	public static Map<String, Object> reqParamToM(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getParameterNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			Map<String, Object> rM = (new HashMap<String, Object>(sz));
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (r.getParameter(kS));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rM.put(kS, vS);
							}
						}
					}
				}
			}
			return (rM);
		}
		return (_m_def);
	}
	public static Map<String, Object> reqParamToM(HttpServletRequest r) {
		return ((r != null && r.getParameterNames() != null) ? (reqParamToM(r, toList(r.getParameterNames()))) : (_m_def));
	}
	public static Map<String, Object> reqHdrToM(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getHeaderNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			Map<String, Object> rM = (new HashMap<String, Object>(sz));
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (r.getHeader(kS));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rM.put(kS, vS);
							}
						}
					}
				}
			}
			return (rM);
		}
		return (_m_def);
	}
	public static Map<String, Object> reqHdrToM(HttpServletRequest r) {
		return ((r != null && r.getHeaderNames() != null) ? (reqHdrToM(r, toList(r.getHeaderNames()))) : (_m_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static JSONObject mToJo(Map<String, Object> m, List<?> kL) {
		int sz = ((m != null && !m.isEmpty() && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			JSONObject rJo = (new JSONObject());
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (m.get(kO));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rJo.put(kS, vS);
							}
						}
					}
				}
			}
			return (rJo);
		}
		return (_jo_def);
	}
	public static JSONObject mToJo(Map<String, Object> m) {
		return ((m != null && !m.isEmpty()) ? (mToJo(m, toList(m.keySet()))) : (_jo_def));
	}
	public static JSONObject joToJo(JSONObject j, List<?> kL) {
		int sz = ((j != null && !j.isEmpty() && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			JSONObject rJo = (new JSONObject());
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (j.get(kO));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rJo.put(kS, vS);
							}
						}
					}
				}
			}
			return (rJo);
		}
		return (_jo_def);
	}
	public static JSONObject joToJo(JSONObject j) {
		return ((j != null && !j.isEmpty()) ? (joToJo(j, toList(j.keySet()))) : (_jo_def));
	}
	public static JSONObject reqParamToJo(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getParameterNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			JSONObject rJo = (new JSONObject());
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (r.getParameter(kS));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rJo.put(kS, vS);
							}
						}
					}
				}
			}
			return (rJo);
		}
		return (_jo_def);
	}
	public static JSONObject reqParamToJo(HttpServletRequest r) {
		return ((r != null && r.getParameterNames() != null) ? (reqParamToJo(r, toList(r.getParameterNames()))) : (_jo_def));
	}
	public static JSONObject reqHdrToJo(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getHeaderNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			JSONObject rJo = (new JSONObject());
			int a;
			Object kO, vO;
			String kS, vS;
			for (a=0; a<sz; a++) {
				kO = (kL.get(a));
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (r.getHeader(kS));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								rJo.put(kS, vS);
							}
						}
					}
				}
			}
			return (rJo);
		}
		return (_jo_def);
	}
	public static JSONObject reqHdrToJo(HttpServletRequest r) {
		return ((r != null && r.getHeaderNames() != null) ? (reqHdrToJo(r, toList(r.getHeaderNames()))) : (_jo_def));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String mToJoStr(Map<String, Object> m, List<String> kL) {
		return (mToJo(m, kL).toJSONString());
	}
	public static String mToJoStr(Map<String, Object> m) {
		return (mToJo(m).toJSONString());
	}
	public static String joToJoStr(JSONObject j, List<String> kL) {
		return (joToJo(j, kL).toJSONString());
	}
	public static String joToJoStr(JSONObject j) {
		return (joToJo(j).toJSONString());
	}
	public static String reqParamToJoStr(HttpServletRequest r, List<String> kL) {
		return (reqParamToJo(r, kL).toJSONString());
	}
	public static String reqParamToJoStr(HttpServletRequest r) {
		return (reqParamToJo(r).toJSONString());
	}
	public static String reqHdrToJoStr(HttpServletRequest r, List<String> kL) {
		return (reqHdrToJo(r, kL).toJSONString());
	}
	public static String reqHdrToJoStr(HttpServletRequest r) {
		return (reqHdrToJo(r).toJSONString());
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static JSONObject toJo(Object o) {
		if (o != null) {
			String s = (o.toString().trim());
			if (s.length() > 0) {
				JSONObject rJo;
				try {
					rJo = ((JSONObject) (new JSONParser()).parse(s));
					if (rJo != null && !rJo.isEmpty()) {
						return (joToJo(rJo));
					}
				} catch (ParseException e) {
				} finally {
					String[] sA = (s.split(_spl_cma));
					int sz = ((sA != null) ? (sA.length) : (_sz_def));
					if (sz > 0) {
						rJo = (new JSONObject());
						int a;
						String[] kvA;
						String k, v;
						for (a=0; a<sz; a++) {
							s = (sA[a]);
							s = ((a == 0) ? (s.substring(1)) : (s));
							s = ((a == (sz - 1)) ? (s.substring(_sub_strt, (s.length() - 1))) : (s));
							kvA = (s.trim().split(_spl_eq));
							if (kvA != null && kvA.length == 2) {
								k = (kvA[0]);
								v = (kvA[1]);
								if (k != null && v != null && k.trim().length() > 0 && v.trim().length() > 0) {
									rJo.put(k.trim(), v.trim());
								}
							}
						}
						if (rJo != null && !rJo.isEmpty()) {
							return (joToJo(rJo));
						}
					}
				}
			}
		}
		return (_jo_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String yElsN(Object o) {
		return ((o != null && o.toString().trim().equals(_str_y)) ? (_str_y) : (_str_n));
	}
	public static String mYElsN(Map<String, Object> m, String k) {
		return ((m != null && k != null) ? (yElsN(m.get(k))) : (_str_n));
	}
	public static String joYElsN(JSONObject j, String k) {
		return ((j != null && k != null) ? (yElsN(j.get(k))) : (_str_n));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String yElsNull(Object o, Object v) {
		return ((o != null && o.toString().trim().equals(_str_y)) ? (toStr(v)) : (_str_null));
	}
	public static String mYElsNull(Map<String, Object> m, String k, String v) {
		return ((m != null && k != null && v != null) ? (yElsNull(m.get(k), m.get(v))) : (_str_null));
	}
	public static String joYElsNull(JSONObject j, String k, String v) {
		return ((j != null && k != null && v != null) ? (yElsNull(j.get(k), j.get(v))) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String yElsDef(Object o, Object v, Object def) {
		return ((o != null && o.toString().trim().equals(_str_y)) ? (toStr(v)) : (toStr(def)));
	}
	public static String mYElsDef(Map<String, Object> m, String k, String v, Object def) {
		return ((m != null && k != null && v != null) ? (yElsDef(m.get(k), m.get(v), def)) : (toStr(def)));
	}
	public static String joYElsDef(JSONObject j, String k, String v, Object def) {
		return ((j != null && k != null && v != null) ? (yElsDef(j.get(k), j.get(v), def)) : (toStr(def)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String orElsNull(Object[] eq, Object o, Object v) {
		return ((eq != null && o != null && Arrays.asList(eq).contains(o.toString().trim())) ? (toStr(v)) : (_str_null));
	}
	public static String mOrElsNull(Object[] eq, Map<String, Object> m, String k, String v) {
		return ((m != null && k != null && v != null) ? (orElsNull(eq, m.get(k), m.get(v))) : (_str_null));
	}
	public static String joOrElsNull(Object[] eq, JSONObject j, String k, String v) {
		return ((j != null && k != null && v != null) ? (orElsNull(eq, j.get(k), j.get(v))) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String orElsDef(Object[] eq, Object o, Object v, Object def) {
		return ((eq != null && o != null && Arrays.asList(eq).contains(o.toString().trim())) ? (toStr(v)) : (toStr(def)));
	}
	public static String mOrElsDef(Object[] eq, Map<String, Object> m, String k, String v, Object def) {
		return ((m != null && k != null && v != null) ? (orElsDef(eq, m.get(k), m.get(v), def)) : (toStr(def)));
	}
	public static String joOrElsDef(Object[] eq, JSONObject j, String k, String v, Object def) {
		return ((j != null && k != null && v != null) ? (orElsDef(eq, j.get(k), j.get(v), def)) : (toStr(def)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static int xlsxToInt(Cell c) {
		if (c != null) {
			try {
				return (toInt(c.getStringCellValue()));
			} catch (Exception e) {
			} finally {
				try {
					return (toInt(c.getNumericCellValue()));
				} catch (Exception e) {
				}
			}
		}
		return (_int_def);
	}
	public static String xlsxToStr(Cell c) {
		if (c != null) {
			try {
				return (toStr(c.getStringCellValue()));
			} catch (Exception e) {
			} finally {
				try {
					return (toStr(c.getNumericCellValue()));
				} catch (Exception e) {
				}
			}
		}
		return (_str_blk);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static Map<String, Object> mkMsg(Map<String, Object> mM, Map<String, Object> aM) {
		int sz = ((mM != null && aM != null && !aM.isEmpty()) ? (mM.size()) : (_sz_def));
		if (sz > 0) {
			Map<String, Object> rM = (new HashMap<String, Object>(sz));
			String mV;
			for (String mK : mM.keySet()) {
				if (mK != null && mM.containsKey(mK) && mM.get(mK) != null) {
					mV = (mM.get(mK).toString());
					for (String aK : aM.keySet()) {
						if (aK != null && aM.containsKey(aK) && aM.get(aK) != null) {
							mV = (mV.replace("#{" + aK + "}", (aM.get(aK).toString())));
						}
					}
					rM.put(mK, mV);
				}
			}
			return (rM);
		}
		return (_m_def);
	}
	public static List<Map<String, Object>> mkMsg(Map<String, Object> mM, List<Map<String, Object>> aL) {
		int sz = ((mM != null && !mM.isEmpty() && aL != null) ? (aL.size()) : (_sz_def));
		if (sz > 0) {
			List<Map<String, Object>> rL = (new ArrayList<Map<String, Object>>(sz));
			for (Map<String, Object> aM : aL) {
				rL.add(mkMsg(mM, aM));
			}
			return (rL);
		}
		return (_m_l_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String mToParam(Map<String, Object> m) {
		int sz = ((m != null) ? (m.size()) : (_sz_def));
		if (sz > 0) {
			Object vO;
			String kS, vS;
			StringBuffer sb = (new StringBuffer(256));
			for (Object kO : m.keySet()) {
				if (kO != null) {
					kS = (kO.toString().trim());
					if (kS.length() > 0) {
						vO = (m.get(kO));
						if (vO != null) {
							vS = (vO.toString().trim());
							if (vS.length() > 0) {
								sb.append((sb.isEmpty()) ? (_str_qt) : (_str_and)).append(kS).append(_str_eq).append(vS);
							}
						}
					}
				}
			}
			return (sb.toString());
		}
		return (_str_blk);
	}
	public static String reqParamToParam(HttpServletRequest r) {
		return (mToParam(reqParamToM(r)));
	}
	public static String toEncParam(Object o) {
		if (o != null) {
			String s = (o.toString().trim());
			if (s.length() > 0) {
				try {
					return (URLEncoder.encode(s, _str_charset));
				} catch (Exception e) {
				}
			}
		}
		return (_str_blk);
	}
	public static String mToEncParam(Map<String, Object> m) {
		return (toEncParam(mToParam(m)));
	}
	public static String reqParamToEncParam(HttpServletRequest r) {
		return (toEncParam(reqParamToParam(r)));
	}
	public static String toDecParam(Object o) {
		if (o != null) {
			String s = (o.toString().trim());
			if (s.length() > 0) {
				try {
					return (URLDecoder.decode(s, _str_charset));
				} catch (Exception e) {
				}
			}
		}
		return (_str_blk);
	}
	public static Map<String, Object> paramToM(Object o) {
		if (o != null) {
			String s = (o.toString().trim());
			if (s.length() > 0) {
				s = (s.replace(_str_qt, _str_blk));
				String[] aA = (s.split(_spl_and));
				int sz = ((aA != null) ? (aA.length) : (_sz_def));
				if (sz > 0) {
					String[] eA;
					Map<String, Object> rM = (new HashMap<String, Object>(sz));
					String k, v;
					for (String eS : aA) {
						if (eS != null && eS.trim().length() > 0) {
							eA = (eS.split(_spl_eq));
							if (eA != null && eA.length == 2) {
								k = (eA[0]);
								v = (eA[1]);
								if (k != null && k.trim().length() > 0 && v != null && v.trim().length() > 0) {
									rM.put(k.trim(), v.trim());
								}
							}
						}
					}
					return (rM);
				}
			}
		}
		return (_m_def);
	}
	public static Map<String, Object> encParamToM(Object o) {
		return (paramToM(toDecParam(o)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String avg(List<?> vL) {
		int sz = ((vL != null) ? (vL.size()) : (_sz_def));
		if (sz > 0) {
			long sum = (_long_def);
			int a;
			for (a=0; a<sz; a++) {
				sum += (toLong(vL.get(a)));
			}
			return (toIntAsStr(sum / sz));
		}
		return (_str_long_def);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String cnt(List<?> vL) {
		int sz = ((vL != null) ? (vL.size()) : (_sz_def));
		if (sz > 0) {
			JSONObject rJo = (new JSONObject());
			int a;
			Object vO;
			for (a=0; a<sz; a++) {
				vO = (vL.get(a));
				if (vO != null && !rJo.containsKey(vO)) {
					rJo.put(vO, String.valueOf(Collections.frequency(vL, vO)));
				}
			}
			return (rJo.toJSONString());
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String cmpr(Map<String, Object> mM, Map<String, Object> cM) {
		JSONObject rJo = (new JSONObject());
		StringBuffer sb;
		String mV, cV;
		if (mM != null && !mM.isEmpty() && cM != null && !cM.isEmpty()) {
			for (String mK : mM.keySet()) {
				if (mK != null) {
					mV = (String.valueOf(mM.get(mK)));
					cV = (String.valueOf(cM.get(mK)));
					if (!mV.equals(cV)) {
						sb = (new StringBuffer(256));
						sb.append(mV).append(_str_to).append(cV);
						rJo.put(mK, String.valueOf(sb));
					}
				}
			}
			for (String cK : cM.keySet()) {
				if (cK != null && !rJo.containsKey(cK)) {
					mV = (String.valueOf(mM.get(cK)));
					cV = (String.valueOf(cM.get(cK)));
					if (!mV.equals(cV)) {
						sb = (new StringBuffer(256));
						sb.append(mV).append(_str_to).append(cV);
						rJo.put(cK, String.valueOf(sb));
					}
				}
			}
		} else if (mM != null && !mM.isEmpty() && (cM == null || (cM != null && cM.isEmpty()))) {
			for (String mK : mM.keySet()) {
				if (mK != null) {
					mV = (String.valueOf(mM.get(mK)));
					cV = (String.valueOf(_str_null));
					if (!mV.equals(cV)) {
						sb = (new StringBuffer(256));
						sb.append(mV).append(_str_to).append(cV);
						rJo.put(mK, String.valueOf(sb));
					}
				}
			}
		} else if ((mM == null || (mM != null && mM.isEmpty()) && cM != null && !cM.isEmpty())) {
			for (String cK : cM.keySet()) {
				if (cK != null) {
					mV = (String.valueOf(_str_null));
					cV = (String.valueOf(cM.get(cK)));
					if (!mV.equals(cV)) {
						sb = (new StringBuffer(256));
						sb.append(mV).append(_str_to).append(cV);
						rJo.put(cK, String.valueOf(sb));
					}
				}
			}
		}
		return ((rJo != null && !rJo.isEmpty()) ? (rJo.toJSONString()) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
}