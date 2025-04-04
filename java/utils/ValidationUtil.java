import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;

public class ValidationUtil {
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_str_y						= ("Y");
	private static final String						_str_n						= ("N");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_not_in						= (-1);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final List<String>				_l_y_n						= (List.of(_str_y, _str_n));
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_sz_def						= (0);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean has(Object o) {
		if (o != null) {
			if (o instanceof String) {
				return (o.toString().trim().length() > 0);
			} else if (o instanceof Map<?, ?>) {
				return (!((Map<?, ?>) o).isEmpty());
			} else if (o instanceof List<?>) {
				return (!((List<?>) o).isEmpty());
			} else if (o instanceof Object[]) {
				return (((Object[]) o).length > 0);
			} else if (o instanceof Boolean) {
				return ((Boolean) o);
			} else if (o instanceof JSONObject) {
				return (!((JSONObject) o).isEmpty());
			}
		}
		return (false);
	}
	public static boolean mHas(Map<String, Object> m, List<?> kL) {
		int sz = ((m != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			int a;
			for (a=0; a<sz; a++) {
				if (!has(m.get(kL.get(a)))) {
					return (false);
				}
			}
			return (true);
		}
		return (false);
	}
	public static boolean mHas(Map<String, Object> m, String kS) {
		return (m != null && kS != null && has(m.get(kS)));
	}
	public static boolean joHas(JSONObject j, List<?> kL) {
		int sz = ((j != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			int a;
			for (a=0; a<sz; a++) {
				if (!has(j.get(kL.get(a)))) {
					return (false);
				}
			}
			return (true);
		}
		return (false);
	}
	public static boolean joHas(JSONObject j, String kS) {
		return (j != null && kS != null && has(j.get(kS)));
	}
	public static boolean reqParamHas(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getParameterNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			int a;
			for (a=0; a<sz; a++) {
				if (!has(r.getParameter(kL.get(a).toString()))) {
					return (false);
				}
			}
			return (true);
		}
		return (false);
	}
	public static boolean reqParamHas(HttpServletRequest r, String kS) {
		return (r != null && r.getParameterNames() != null && has(r.getParameter(kS)));
	}
	public static boolean reqHdrHas(HttpServletRequest r, List<?> kL) {
		int sz = ((r != null && r.getHeaderNames() != null && kL != null) ? (kL.size()) : (_sz_def));
		if (sz > 0) {
			int a;
			for (a=0; a<sz; a++) {
				if (!has(r.getHeader(kL.get(a).toString()))) {
					return (false);
				}
			}
			return (true);
		}
		return (false);
	}
	public static boolean reqHdrHas(HttpServletRequest r, String kS) {
		return (r != null && r.getHeaderNames() != null && has(r.getHeader(kS)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean cons(Object kO, Object vO) {
		return (kO != null && vO != null && kO.toString().contains(vO.toString()));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean strt(Object kO, Object vO) {
		return (kO != null && vO != null && kO.toString().startsWith(vO.toString()));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean end(Object kO, Object vO) {
		return (kO != null && vO != null && kO.toString().endsWith(vO.toString()));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean len(Object kO, Object vO) {
		return (kO != null && vO != null && kO.toString().length() == Integer.parseInt(vO.toString()));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean eq(Object kO, Object vO) {
		return (kO != null && vO != null && kO.equals(vO));
	}
	public static boolean mEq(Map<String, Object> m, String kS, Object vO) {
		return (m != null && kS != null && eq(m.get(kS), vO));
	}
	public static boolean joEq(JSONObject j, String kS, Object vO) {
		return (j != null && kS != null && eq(j.get(kS), vO));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean eqOr(Object kO, List<?> vL) {
		return (kO != null && vL != null && vL.indexOf(kO) > _not_in);
	}
	public static boolean mEqOr(Map<String, Object> m, String kS, List<?> vL) {
		return (m != null && kS != null && eqOr(m.get(kS), vL));
	}
	public static boolean joEqOr(JSONObject j, String kS, List<?> vL) {
		return (j != null && kS != null && eqOr(j.get(kS), vL));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean yOrN(Object kO) {
		return (kO != null && _l_y_n.indexOf(kO) > _not_in);
	}
	public static boolean mYn(Map<String, Object> m, String kS) {
		return (m != null && kS != null && yOrN(m.get(kS)));
	}
	public static boolean joYn(JSONObject j, String kS) {
		return (j != null && kS != null && yOrN(j.get(kS)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean yAndHas(Object kO, Object vO) {
		return (kO != null && vO != null && kO.equals(_str_y) && has(vO));
	}
	public static boolean mYAndHas(Map<String, Object> m, String kS, String vS) {
		return (m != null && kS != null && vS != null && yAndHas(m.get(kS), m.get(vS)));
	}
	public static boolean joYAndHas(JSONObject j, String kS, String vS) {
		return (j != null && kS != null && vS != null && yAndHas(j.get(kS), j.get(vS)));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static boolean xlsx(Workbook wb) {
		return (wb != null && wb.getNumberOfSheets() > 0);
	}
	public static boolean xlsx(Sheet st) {
		return (st != null && st.getPhysicalNumberOfRows() > 0);
	}
	public static boolean xlsx(Row rw) {
		return (rw != null && rw.getPhysicalNumberOfCells() > 0);
	}
	public static boolean xlsx(Cell c) {
		if (c != null) {
			try {
				return (has(c.getStringCellValue()));
			} catch (Exception e) {
			} finally {
				try {
					return (has(c.getNumericCellValue()));
				} catch (Exception e) {
				}
			}
		}
		return (false);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
}