import java.util.regex.Pattern;

public class MaskUtil {
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_str_null					= (null);
	private static final String						_str_mask_ptrn				= ("*");
	private static final String						_str_blk					= ("");
	private static final String						_str_spc					= (" ");
	private static final String						_str_dcml					= (".");
	private static final String						_str_minus					= ("-");
	private static final String						_str_slash					= ("/");
	private static final String						_str_at						= ("@");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_spl_blk					= ("");
	private static final String						_spl_spc					= (" ");
	private static final String						_spl_dcml					= ("\\.");
	private static final String						_spl_minus					= ("-");
	private static final String						_spl_slash					= ("/");
	private static final String						_spl_at						= ("@");
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_sub_strt					= (0);
	private static final int						_not_in						= (-1);
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_nm						= ("((^[가-힣]{2,18}$)|(^[a-zA-Z ]{2,}$))");
	private static final Pattern					_ptrn_nm					= (Pattern.compile(_reg_nm));
	private static final String						_rplc_nm					= ("(?<=[a-zA-Z가-힣]+)[a-zA-Z가-힣]");
	private static boolean ptrnNm(Object o) {
		return (_ptrn_nm.matcher(String.valueOf(o)).matches());
	}
	public static String nm(Object o) {
		if (ptrnNm(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_spc) > _not_in);
			String[] sA = (s.split((is) ? (_spl_spc) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_spc));
					sb.append(sA[a].replaceAll(_rplc_nm, _str_mask_ptrn));
				} else {
					sb.append((sb.isEmpty()) ? (sA[a]) : ((sz == 2) ? (_str_mask_ptrn) : ((a == (sz - 1)) ? (sA[a]) : (_str_mask_ptrn))));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_alw_email					= ("[0-9a-zA-Z._-]");
	private static final int						_alw_email_strt				= (4);
	private static final int						_alw_email_end				= (20);
	private static final String						_reg_email					= ("(^" + (_alw_email) + "{" + (_alw_email_strt) + "," + (_alw_email_end) + "}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+$)|(^" + (_alw_email) + "{" + (_alw_email_strt) + "," + (_alw_email_end) + "}[@][0-9a-zA-Z_-]+[.][a-zA-Z]+[.][a-zA-Z]+$)");
	private static final Pattern					_ptrn_email					= (Pattern.compile(_reg_email));
	private static final int						_loc_email					= (_alw_email_strt);
	private static final String						_rplc_email					= ("(?<=[" + (_alw_email) + "]{" + (_loc_email) + "})[" + (_alw_email) + "]");
	private static boolean ptrnEmail(Object o) {
		return (_ptrn_email.matcher(String.valueOf(o)).matches());
	}
	public static String email(Object o) {
		if (ptrnEmail(o)) {
			String[] sA = (o.toString().split(_spl_at));
			return ((sA[0].replaceAll(_rplc_email, _str_mask_ptrn)) + (_str_at) + (sA[1]));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_alw_id						= ("[0-9a-z]");
	private static final int						_alw_id_strt				= (4);
	private static final int						_alw_id_end					= (20);
	private static final String						_reg_id						= ("^" + (_alw_id) + "{" + (_alw_id_strt) + "," + (_alw_id_end) + "}$");
	private static final Pattern					_ptrn_id					= (Pattern.compile(_reg_id));
	private static final int						_loc_id						= (_alw_id_strt);
	private static final String						_rplc_id					= ("(?<=[" + (_alw_id) + "]{" + (_loc_id) + "})[" + (_alw_id) + "]");
	private static boolean ptrnId(Object o) {
		return (_ptrn_id.matcher(String.valueOf(o)).matches());
	}
	public static String id(Object o) {
		return ((ptrnId(o)) ? (o.toString().replaceAll(_rplc_id, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_paspt					= ("^[mMsSrRoOdD][0-9]{7}$");
	private static final Pattern					_ptrn_paspt					= (Pattern.compile(_reg_paspt));
	private static final String						_rplc_paspt					= ("(?<=[mMsSrRoOdD][0-9]{2}[0-9]+)[0-9]");
	private static boolean ptrnPaspt(Object o) {
		return (_ptrn_paspt.matcher(String.valueOf(o)).matches());
	}
	public static String paspt(Object o) {
		return ((ptrnPaspt(o)) ? (o.toString().replaceAll(_rplc_paspt, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_ip						= ("^[0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}$");
	private static final Pattern					_ptrn_ip					= (Pattern.compile(_reg_ip));
	private static final int						_loc_ip						= (0);
	private static final String						_rplc_ip					= ("[0-9]");
	private static boolean ptrnIp(Object o) {
		return (_ptrn_ip.matcher(String.valueOf(o)).matches());
	}
	public static String ip(Object o) {
		if (ptrnIp(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			String[] sA = (s.split(_spl_dcml));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				sb.append((sb.isEmpty()) ? (_str_blk) : (_str_dcml)).append((a == _loc_ip) ? (sA[a].replaceAll(_rplc_ip, _str_mask_ptrn)) : (sA[a]));
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_rplc_addr					= ("[0-9]");
	public static String addr(Object o) {
		return ((o != null) ? (o.toString().replaceAll(_rplc_addr, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_rplc_addr_dtl				= ("[0-9]");
	public static String addrDtl(Object o) {
		return ((o != null) ? (o.toString().replaceAll(_rplc_addr_dtl, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final int						_loc_ci						= (6);
	private static final String						_rplc_ci					= ("(?<=[^*]{" + (_loc_ci) + "}[^*]+)[^*]");
	public static String ci(Object o) {
		return ((o != null) ? (o.toString().replaceAll(_rplc_ci, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_alw_ssn_m					= ("((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))");
	private static final String						_alw_ssn_d					= ("((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28)|(29)|(30)|(31))");
	private static final String						_alw_ssn_s					= ("1-4");
	private static final String						_reg_ssn					= ("(^[0-9]{2}[" + (_alw_ssn_m) + "]{2}[" + (_alw_ssn_d) + "]{2}[-][" + (_alw_ssn_s) + "]{1}[0-9]{6}$)|(^[0-9]{2}[" + (_alw_ssn_m) + "]{2}[" + (_alw_ssn_d) + "]{2}[" + (_alw_ssn_s) + "]{1}[0-9]{6}$)");
	private static final Pattern					_ptrn_ssn					= (Pattern.compile(_reg_ssn));
	private static final String						_rplc_ssn					= ("((?<=[0-9]{7})[0-9])|((?<=[0-9]{6}[-][0-9]+)[0-9])");
	private static boolean ptrnSsn(Object o) {
		return (_ptrn_ssn.matcher(String.valueOf(o)).matches());
	}
	public static String ssn(Object o) {
		return ((ptrnSsn(o)) ? (o.toString().replaceAll(_rplc_ssn, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_alw_cph_fst				= ("((010)|(017)|(019))");
	private static final String						_reg_cph					= ("(^[" + (_alw_cph_fst) + "]{3}[-][0-9]{4}[-][0-9]{4}$)|(^[" + (_alw_cph_fst) + "]{3}[0-9]{8}$)");
	private static final Pattern					_ptrn_cph					= (Pattern.compile(_reg_cph));
	private static final int						_loc_cph					= (1);
	private static final String						_rplc_cph					= ("[0-9]");
	private static boolean ptrnCph(Object o) {
		return (_ptrn_cph.matcher(String.valueOf(o)).matches());
	}
	public static String cph(Object o) {
		if (ptrnCph(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_minus) > _not_in);
			String[] sA = (s.split((is) ? (_spl_minus) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_minus));
					sb.append((a == _loc_cph) ? (sA[a].replaceAll(_rplc_cph, _str_mask_ptrn)) : (sA[a]));
				} else {
					sb.append(((_loc_cph * 4) <= (a + 1) && (a + 1) < ((_loc_cph + 1) * 4)) ? (_str_mask_ptrn) : (sA[a]));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_card					= ("(^[0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}[-][0-9]{3,4}$)|(^[0-9]{16}$)");
	private static final Pattern					_ptrn_card					= (Pattern.compile(_reg_card));
	private static final String						_rplc_card					= ("[0-9]");
	private static boolean ptrnCard(Object o) {
		return (_ptrn_card.matcher(String.valueOf(o)).matches());
	}
	public static String card(Object o) {
		if (ptrnCard(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_minus) > _not_in);
			String[] sA = (s.split((is) ? (_spl_minus) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_minus));
					sb.append((a == 0) ? (sA[a]) : ((a == 3) ? ((sA[a].substring(_sub_strt, (sA[a].length() - 1))) + (_str_mask_ptrn)) : (sA[a].replaceAll(_rplc_card, _str_mask_ptrn))));
				} else {
					sb.append(((a <= 3) || (12 <= a && a <= 14)) ? (sA[a]) : (_str_mask_ptrn));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_mmyy					= ("(^[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[0-9]{2}$)|(^[((01)|(02)|(03)|(04)|(05)|(06)|(07)|(08)|(09)|(10)|(11)|(12))]{2}[/][0-9]{2}$)");
	private static final Pattern					_ptrn_mmyy					= (Pattern.compile(_reg_mmyy));
	private static final String						_rplc_mmyy					= ("[0-9]");
	private static boolean ptrnMmyy(Object o) {
		return (_ptrn_mmyy.matcher(String.valueOf(o)).matches());
	}
	public static String mmyy(Object o) {
		if (ptrnMmyy(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_slash) > _not_in);
			String[] sA = (s.split((is) ? (_spl_slash) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_slash));
					sb.append(sA[a].replaceAll(_rplc_mmyy, _str_mask_ptrn));
				} else {
					sb.append(sA[a].replaceAll(_rplc_mmyy, _str_mask_ptrn));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_alw_drvg_d					= ("((11)|(12)|(13)|(14)|(15)|(16)|(17)|(18)|(19)|(20)|(21)|(22)|(23)|(24)|(25)|(26)|(27)|(28))");
	private static final String						_reg_drvg					= ("(^[" + (_alw_drvg_d) + "]{2}[-][0-9]{2}[-][0-9]{6}[-][0-9]{2}$)|(^[" + (_alw_drvg_d) + "]{2}[0-9]{2}[0-9]{6}[0-9]{2}$)");
	private static final Pattern					_ptrn_drvg					= (Pattern.compile(_reg_drvg));
	private static final String						_rplc_drvg					= ("[0-9]");
	private static boolean ptrnDrvg(Object o) {
		return (_ptrn_drvg.matcher(String.valueOf(o)).matches());
	}
	public static String drvg(Object o) {
		if (ptrnDrvg(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_minus) > _not_in);
			String[] sA = (s.split((is) ? (_spl_minus) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_minus));
					sb.append((a < 2) ? (sA[a]) : ((a == 2) ? ((sA[a].substring(_sub_strt, 1)) + (sA[a].substring(1).replaceAll(_rplc_drvg, _str_mask_ptrn))) : (sA[a].replaceAll(_rplc_drvg, _str_mask_ptrn))));
				} else {
					sb.append(((sz - 7) > a) ? (sA[a]) : (_str_mask_ptrn));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_car					= ("^[0-9]{2}[가-힣][0-9]{4}$");
	private static final Pattern					_ptrn_car					= (Pattern.compile(_reg_car));
	private static final String						_rplc_car					= ("[0-9]");
	private static boolean ptrnCar(Object o) {
		return (_ptrn_car.matcher(String.valueOf(o)).matches());
	}
	public static String car(Object o) {
		if (ptrnCar(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			String[] sA = (s.split(_spl_blk));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				sb.append((a < 3) ? (sA[a]) : (sA[a].replaceAll(_rplc_car, _str_mask_ptrn)));
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_bizr					= ("(^[1-9]{1}[0-9]{2}[0-9]{2}[0-9]{5}$)|(^[1-9]{1}[0-9]{2}[-][0-9]{2}[-][0-9]{5}$)");
	private static final Pattern					_ptrn_bizr					= (Pattern.compile(_reg_bizr));
	private static final String						_rplc_bizr					= ("[0-9]");
	private static boolean ptrnBizr(Object o) {
		return (_ptrn_bizr.matcher(String.valueOf(o)).matches());
	}
	public static String bizr(Object o) {
		if (ptrnBizr(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_minus) > _not_in);
			String[] sA = (s.split((is) ? (_spl_minus) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_minus));
					sb.append((a == 2) ? (sA[a].replaceAll(_rplc_bizr, _str_mask_ptrn)) : (sA[a]));
				} else {
					sb.append(((sz - 5) > a) ? (sA[a]) : (_str_mask_ptrn));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_acct					= ("(^[0-9-]{7,19}[0-9]$)|(^[0-9]{8,20}$)");
	private static final Pattern					_ptrn_acct					= (Pattern.compile(_reg_acct));
	private static final int						_loc_acct					= (5);
	private static final String						_rplc_acct					= ("[0-9]");
	private static boolean ptrnAcct(Object o) {
		return (_ptrn_acct.matcher(String.valueOf(o)).matches());
	}
	public static String acct(Object o) {
		if (ptrnAcct(o)) {
			String s = (o.toString());
			StringBuffer sb = (new StringBuffer(s.length()));
			boolean is = (s.indexOf(_str_minus) > _not_in);
			String[] sA = (s.split((is) ? (_spl_minus) : (_spl_blk)));
			int sz = (sA.length);
			for (int a=0; a<sz; a++) {
				if (is) {
					sb.append((sb.isEmpty()) ? (_str_blk) : (_str_minus));
					sb.append((a == (sz - 1)) ? (sA[a].replaceAll(_rplc_acct, _str_mask_ptrn)) : (sA[a]));
				} else {
					sb.append(((sz - _loc_acct) > a) ? (sA[a]) : (_str_mask_ptrn));
				}
			}
			return (String.valueOf(sb));
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	private static final String						_reg_pst					= ("(^[0-9]{5}$)|(^[0-9]{3}[-][0-9]{3}$)");
	private static final Pattern					_ptrn_pst					= (Pattern.compile(_reg_pst));
	private static final String						_rplc_pst					= ("((?<=[0-9]{3})[0-9])|((?<=[0-9]{3}[0-9-]+)[0-9])");
	private static boolean ptrnPst(Object o) {
		return (_ptrn_pst.matcher(String.valueOf(o)).matches());
	}
	public static String pst(Object o) {
		return ((ptrnPst(o)) ? (o.toString().replaceAll(_rplc_pst, _str_mask_ptrn)) : (_str_null));
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
	public static String txt(Object o) {
		if (o != null) {
			String s = (o.toString().trim());
			if (s.length() > 0) {
				StringBuffer sb = (new StringBuffer(s.length()));
				String[] sA = (s.split(_spl_spc));
				int sz = (sA.length);
				for (int a=0; a<sz; a++) {
					s = (sA[a].trim());
					if (s.length() > 0) {
						if (ptrnEmail(s)) {
							sb.append(email(s)).append(_str_spc);
						} else if (ptrnPaspt(s)) {
							sb.append(paspt(s)).append(_str_spc);
						} else if (ptrnIp(s)) {
							sb.append(ip(s)).append(_str_spc);
						} else if (ptrnSsn(s)) {
							sb.append(ssn(s)).append(_str_spc);
						} else if (ptrnCph(s)) {
							sb.append(cph(s)).append(_str_spc);
						} else if (ptrnCard(s)) {
							sb.append(card(s)).append(_str_spc);
						} else if (ptrnDrvg(s)) {
							sb.append(drvg(s)).append(_str_spc);
						} else if (ptrnBizr(s)) {
							sb.append(bizr(s)).append(_str_spc);
						} else if (ptrnAcct(s)) {
							sb.append(acct(s)).append(_str_spc);
						} else if (ptrnPst(s)) {
							sb.append(pst(s)).append(_str_spc);
						} else {
							sb.append(s).append(_str_spc);
						}
					}
				}
				return (String.valueOf(sb).trim());
			}
		}
		return (_str_null);
	}
	/** --------------------------------------------------------------------------------------------------------------------------------------------------------------- **/
}