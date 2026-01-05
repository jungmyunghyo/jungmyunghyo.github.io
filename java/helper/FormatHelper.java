import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class FormatHelper {
	private static final class Constant {
		private static final String OR_SEPARATOR = "|";
		
		private static final String INTEGER_BOUND1 = "0";
		private static final String INTEGER_BOUND2 = "[1-9][0-9]{0,18}";
		private static final String INTEGER_BOUND3 = "[1-9]([,][0-9]{3}){1,6}";
		private static final String INTEGER_BOUND4 = "[1-9][0-9]{1,2}([,][0-9]{3}){1,5}";
		private static final List<String> INTEGER_BOUNDS = List.of(INTEGER_BOUND1, INTEGER_BOUND2, INTEGER_BOUND3, INTEGER_BOUND4);
		private static final Pattern INTEGER_PATTERN = Pattern.compile("^[+-]?(" + String.join(OR_SEPARATOR, INTEGER_BOUNDS) + ")$");
		
		private static final String DOUBLE_BOUND1 = "0([.][0-9]{0,7})?";
		private static final String DOUBLE_BOUND2 = "[1-9][0-9]{0,14}([.][0-9]{0,7})?";
		private static final String DOUBLE_BOUND3 = "[1-9][0-9]{0,2}([,][0-9]{3}){1,4}([.][0-9]{0,7})?";
		private static final String DOUBLE_BOUND4 = "[.][0-9]{1,7}";
		private static final List<String> DOUBLE_BOUNDS = List.of(DOUBLE_BOUND1, DOUBLE_BOUND2, DOUBLE_BOUND3, DOUBLE_BOUND4);
		private static final Pattern DOUBLE_PATTERN = Pattern.compile("^[+-]?(" + String.join(OR_SEPARATOR, DOUBLE_BOUNDS) + ")$");
		
		private static final String YEAR_SHORT_BOUND = "[0-9]{2}";
		private static final String YEAR_BOUND = "(19|20)" + YEAR_SHORT_BOUND;
		private static final String YEAR_WITH_SUFFIX_BOUND = YEAR_BOUND + "(년|[/.-]|[ ])?";
		
		private static final String MONTH_BOUND = "(0[1-9]|1[0-2])";
		private static final String MONTH_WITH_SUFFIX_BOUND = MONTH_BOUND + "(월|[/.-]|[ ])?";
		
		private static final String DAY_BOUND = "(0[1-9]|[1-2][0-9]|3[0-1])";
		private static final String DAY_WITH_SUFFIX_BOUND = DAY_BOUND + "일?";
		
		private static final String HOUR_BOUND = "([0-1][0-9]|2[0-3])";
		private static final String HOUR_WITH_SUFFIX_BOUND = HOUR_BOUND + "(시|[:/.]|[ ])?";
		
		private static final String MINUTE_BOUND = "[0-5][0-9]";
		private static final String MINUTE_WITH_SUFFIX_BOUND = MINUTE_BOUND + "(분|[:/.]|[ ])?";
		
		private static final String SECOND_BOUND = "[0-5][0-9]";
		private static final String SECOND_WITH_SUFFIX_BOUND = SECOND_BOUND + "초?";
		
		private static final Pattern DATE_PATTERN = Pattern.compile("^" + YEAR_WITH_SUFFIX_BOUND + MONTH_WITH_SUFFIX_BOUND + DAY_WITH_SUFFIX_BOUND + "$");
		private static final Pattern DATE_HOUR_MINUTE_PATTERN = Pattern.compile("^" + YEAR_WITH_SUFFIX_BOUND + MONTH_WITH_SUFFIX_BOUND + DAY_WITH_SUFFIX_BOUND + "[ ]?" + HOUR_WITH_SUFFIX_BOUND + MINUTE_WITH_SUFFIX_BOUND + "$");
		private static final Pattern DATE_TIME_PATTERN = Pattern.compile("^" + YEAR_WITH_SUFFIX_BOUND + MONTH_WITH_SUFFIX_BOUND + DAY_WITH_SUFFIX_BOUND + "[ ]?" + HOUR_WITH_SUFFIX_BOUND + MINUTE_WITH_SUFFIX_BOUND + SECOND_WITH_SUFFIX_BOUND + "$");
		private static final Pattern HOUR_MINUTE_PATTERN = Pattern.compile("^" + HOUR_WITH_SUFFIX_BOUND + MINUTE_WITH_SUFFIX_BOUND + "$");
		private static final Pattern TIME_PATTERN = Pattern.compile("^" + HOUR_WITH_SUFFIX_BOUND + MINUTE_WITH_SUFFIX_BOUND + SECOND_WITH_SUFFIX_BOUND + "$");
		
		private static final List<String> FILE_SIZE_UNIT_BOUNDS = Type.FileSize.getSuffixs();
		private static final String FILE_SIZE_UNIT_BOUND = "[ ]?(" + String.join(OR_SEPARATOR, FILE_SIZE_UNIT_BOUNDS) + ")";
		private static final int FILE_SIZE_DECIMAL_LENGTH = 2;
		private static final Pattern FILE_SIZE_INTEGER_PATTERN = Pattern.compile("^[+]?(" + String.join(OR_SEPARATOR, INTEGER_BOUNDS) + ")" + FILE_SIZE_UNIT_BOUND + "$", Pattern.CASE_INSENSITIVE);
		private static final Pattern FILE_SIZE_DOUBLE_PATTERN = Pattern.compile("^[+]?(" + String.join(OR_SEPARATOR, DOUBLE_BOUNDS) + ")" + FILE_SIZE_UNIT_BOUND + "$", Pattern.CASE_INSENSITIVE);
		
		private static final String DOMAIN_PREFIX_BOUND = "[a-zA-Z][0-9a-zA-Z]{0,19}([-][a-zA-Z][0-9a-zA-Z]{0,19}){0,4}";
		private static final String DOMAIN_SUFFIX_BOUND = "([.]" + DOMAIN_PREFIX_BOUND + "){1,7}";
		private static final String DOMAIN_BOUND = DOMAIN_PREFIX_BOUND + DOMAIN_SUFFIX_BOUND;
		
		private static final Pattern URL_LINK_PATTERN = Pattern.compile("^http[s]?://" + DOMAIN_BOUND + "([/][^\\s]*)?$");
		
		private static final List<String> SEQUENCE_CODE_BOUND1 = List.of("A", "D", "G", "J", "M", "P", "S", "V", "Y", "B");
		private static final List<String> SEQUENCE_CODE_BOUND2 = List.of("Z", "W", "T", "Q", "N", "K", "H", "E", "B", "C");
		private static final List<String> SEQUENCE_CODE_BOUND3 = List.of("A", "F", "G", "L", "M", "R", "S", "X", "Y", "C");
		private static final List<String> SEQUENCE_CODE_BOUND4 = List.of("I", "O", "U", "D", "Z", "L", "J", "W", "R", "P");
		private static final int SEQUENCE_CODE_LENGTH = 8;
		private static final Pattern ENCRYPT_SEQUENCE_CODE_PATTERN = Pattern.compile("^[0-9]{1," + SEQUENCE_CODE_LENGTH + "}$");
		private static final int[] SEQUENCE_CODE_EXPOSE_OPTIONS = {2, 3, 4, 5, 6};
		private static final int SEQUENCE_CODE_EXPOSE_LENGTH = SEQUENCE_CODE_EXPOSE_OPTIONS[0];
		private static final Pattern DECRYPT_SEQUENCE_CODE_PATTERN = Pattern.compile("^[0-9A-Z]{" + SEQUENCE_CODE_LENGTH + "}[0-9]$");
		
		private static final String ID_BOUND = "[0-9a-zA-Z]";
		private static final int ID_MINIMUM_LENGTH = 6;
		private static final int ID_MAXIMUM_LENGTH = 20;
		private static final Pattern ID_PATTERN = Pattern.compile("^" + ID_BOUND + "{" + ID_MINIMUM_LENGTH + "," + ID_MAXIMUM_LENGTH + "}$");
		private static final boolean ID_FIXED_MASK_OPTION = true;
		private static final int[] ID_EXPOSE_OPTIONS = {2, 3, 4, 5, 6};
		private static final int ID_EXPOSE_LENGTH = ID_EXPOSE_OPTIONS[0];
		
		private static final String EMAIL_TEXT_BOUND = "[0-9a-zA-Z]+";
		private static final String EMAIL_SEPARATOR_BOUND = "[._+-]";
		private static final String EMAIL_BOUND = EMAIL_TEXT_BOUND + "(" + EMAIL_SEPARATOR_BOUND + EMAIL_TEXT_BOUND + ")*";
		private static final int EMAIL_MINIMUM_LENGTH = 4;
		private static final int EMAIL_MAXIMUM_LENGTH = 30;
		private static final Pattern EMAIL_PATTERN = Pattern.compile("^(?=.{" + EMAIL_MINIMUM_LENGTH + "," + EMAIL_MAXIMUM_LENGTH + "}[@])" + EMAIL_BOUND + "[@]" + DOMAIN_BOUND + "$");
		private static final boolean EMAIL_FIXED_MASK_OPTION = true;
		private static final int[] EMAIL_EXPOSE_OPTIONS = {2, 3, 4, 5, 6};
		private static final int EMAIL_EXPOSE_LENGTH = EMAIL_EXPOSE_OPTIONS[0];
		
		private static final String MOBILE_SHORT_PREFIX = "10";
		private static final String MOBILE_PREFIX = "0" + MOBILE_SHORT_PREFIX;
		private static final String MOBILE_HYPHEN_BOUND = "([-][0-9]{4}){2}";
		private static final String MOBILE_BOUND = "[0-9]{8}";
		private static final List<String> MOBILE_BOUNDS = List.of(MOBILE_HYPHEN_BOUND, MOBILE_BOUND);
		private static final Pattern MOBILE_PATTERN = Pattern.compile("^" + MOBILE_PREFIX + "(" + String.join(OR_SEPARATOR, MOBILE_BOUNDS) + ")$");
		private static final boolean MOBILE_SECOND_EXPOSE_INSTEAD_LAST = true;
		private static final Pattern MOBILE_SHORT_PATTERN = Pattern.compile("^[0-9]{4}[-]?[0-9]{4}$");
		
		private static final String MOBILE_INTERNATIONAL_SIGN_PREFIX = "+";
		private static final String MOBILE_INTERNATIONAL_PREFIX = "82";
		private static final String MOBILE_INTERNATIONAL_HYPHEN_BOUND = "([-]" + MOBILE_SHORT_PREFIX + MOBILE_HYPHEN_BOUND + ")";
		private static final String MOBILE_INTERNATIONAL_BOUND = "(" + MOBILE_SHORT_PREFIX + MOBILE_BOUND + ")";
		private static final List<String> MOBILE_INTERNATIONAL_BOUNDS = List.of(MOBILE_INTERNATIONAL_HYPHEN_BOUND, MOBILE_INTERNATIONAL_BOUND);
		private static final Pattern MOBILE_INTERNATIONAL_PATTERN = Pattern.compile("^[" + MOBILE_INTERNATIONAL_SIGN_PREFIX + "]?" + MOBILE_INTERNATIONAL_PREFIX + "(" + String.join(OR_SEPARATOR, MOBILE_INTERNATIONAL_BOUNDS) + ")$");
		
		private static final Pattern FAX_HYPHEN_PATTERN = Pattern.compile("^0[0-9]{1,3}[-][0-9]{3,4}[-][0-9]{4}$");
		private static final Pattern FAX_PATTERN = Pattern.compile("^0[0-9]{8,11}$");
		private static final int[] FAX_MASK_OPTIONS = {4, 3, 2};
		private static final int FAX_MASK_LENGTH = FAX_MASK_OPTIONS[0];
		
		private static final Pattern LANDLINE_HYPHEN_PATTERN = Pattern.compile("^0[0-9]{1,3}[-][0-9]{3,4}[-][0-9]{4}$");
		private static final Pattern LANDLINE_PATTERN = Pattern.compile("^0[0-9]{8,11}$");
		private static final int[] LANDLINE_MASK_OPTIONS = {4, 3, 2};
		private static final int LANDLINE_MASK_LENGTH = LANDLINE_MASK_OPTIONS[0];
		
		private static final Pattern LANDLINE_SHORT_PATTERN = Pattern.compile("^[0-9]{3,4}[-]?[0-9]{4}$");
		
		private static final String NAME_ENG_TEXT_BOUND = "[a-zA-Z]+";
		private static final String NAME_ENG_SEPARATOR_BOUND = "[.'-]";
		private static final String NAME_ENG_BOUND = NAME_ENG_TEXT_BOUND + "(" + NAME_ENG_SEPARATOR_BOUND + NAME_ENG_TEXT_BOUND + ")*";
		private static final int NAME_MINIMUM_LENGTH = 2;
		private static final int NAME_KOR_MAXIMUM_LENGTH = 10;
		private static final int NAME_ENG_MAXIMUM_LENGTH = 30;
		private static final Pattern NAME_KOR_PATTERN = Pattern.compile("^[가-힣]{" + NAME_MINIMUM_LENGTH + "," + NAME_KOR_MAXIMUM_LENGTH + "}$");
		private static final Pattern NAME_SPACE_ENG_PATTERN = Pattern.compile("^" + NAME_ENG_BOUND + "([ ]" + NAME_ENG_BOUND + "){1,6}$");
		private static final Pattern NAME_ENG_PATTERN = Pattern.compile("^(?=.{" + NAME_MINIMUM_LENGTH + "," + NAME_ENG_MAXIMUM_LENGTH + "}$)" + NAME_ENG_BOUND + "$");
		private static final boolean NAME_FIXED_MASK_OPTION = true;
		private static final int[] NAME_ENG_EXPOSE_OPTIONS = {1, 2, 3, 4};
		private static final int NAME_ENG_EXPOSE_LENGTH = NAME_ENG_EXPOSE_OPTIONS[0];
		
		private static final String NICK_NAME_BOUND = "[0-9a-zA-Z가-힣]";
		private static final int NICK_NAME_MINIMUM_LENGTH = 2;
		private static final int NICK_NAME_MAXIMUM_LENGTH = 20;
		private static final Pattern NICK_NAME_SPACE_PATTERN = Pattern.compile("^" + NICK_NAME_BOUND + "+([ ]" + NICK_NAME_BOUND + "+){1,6}$");
		private static final Pattern NICK_NAME_PATTERN = Pattern.compile("^" + NICK_NAME_BOUND + "{" + NICK_NAME_MINIMUM_LENGTH + "," + NICK_NAME_MAXIMUM_LENGTH + "}$");
		private static final boolean NICK_NAME_FIXED_MASK_OPTION = true;
		private static final int[] NICK_NAME_EXPOSE_OPTIONS = {1, 2, 3, 4};
		private static final int NICK_NAME_EXPOSE_LENGTH = NICK_NAME_EXPOSE_OPTIONS[0];
		
		private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("^[0-9]{3}[-]?[0-9]{2,3}$");
		private static final int[] ZIP_CODE_MASK_OPTIONS = {3, 2, 1};
		private static final int ZIP_CODE_MASK_LENGTH = ZIP_CODE_MASK_OPTIONS[0];
		
		private static final String LATITUDE_BOUND = "[+-]?([0-9]|[1-8][0-9]|90)([.][0-9]{0,7})?";
		private static final Pattern LATITUDE_PATTERN = Pattern.compile("^" + LATITUDE_BOUND + "$");
		
		private static final String LONGITUDE_BOUND = "[+-]?([0-9]|[1-9][0-9]|1[0-7][0-9]|180)([.][0-9]{0,7})?";
		private static final Pattern LONGITUDE_PATTERN = Pattern.compile("^" + LONGITUDE_BOUND + "$");
		
		private static final int GPS_DECIMAL_LENGTH = 6;
		private static final Pattern GPS_PATTERN = Pattern.compile("^" + LATITUDE_BOUND + "[,][ ]?" + LONGITUDE_BOUND + "$");
		private static final int[] GPS_MASK_OPTIONS = {4, 3, 2, 1};
		private static final int GPS_MASK_LENGTH = GPS_MASK_OPTIONS[0];
		
		private static final Pattern DELIVERY_TRACK_INVOICE_PATTERN = Pattern.compile("^[0-9]{8,16}$");
		private static final int[] DELIVERY_TRACK_INVOICE_MASK_OPTIONS = {6, 5, 4, 3, 2};
		private static final int DELIVERY_TRACK_INVOICE_MASK_LENGTH = DELIVERY_TRACK_INVOICE_MASK_OPTIONS[0];
		
		private static final String TAX_INVOICE_HYPHEN_BOUND = "[-][0-9]{8}[-]";
		private static final String TAX_INVOICE_BOUND = "[0-9]{8}";
		private static final List<String> TAX_INVOICE_BOUNDS = List.of(TAX_INVOICE_HYPHEN_BOUND, TAX_INVOICE_BOUND);
		private static final Pattern TAX_INVOICE_PATTERN = Pattern.compile("^" + YEAR_BOUND + MONTH_BOUND + DAY_BOUND + "(" + String.join(OR_SEPARATOR, TAX_INVOICE_BOUNDS) + ")[0-9a-zA-Z]{8}$");
		private static final int[] TAX_INVOICE_MASK_OPTIONS = {4, 3, 2, 1, 0};
		private static final int TAX_INVOICE_MASK_LENGTH = TAX_INVOICE_MASK_OPTIONS[0];
		
		private static final String IP_ADDRESS_BOUND = "([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])";
		private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^" + IP_ADDRESS_BOUND + "([.]" + IP_ADDRESS_BOUND + "){3}$");
		private static final boolean IP_ADDRESS_EXPOSE_THIRD = false;
		
		private static final String HEX_BOUND = "[0-9a-fA-F]";
		
		private static final String MAC_ADDRESS_DOT_BOUND = "([.]" + HEX_BOUND + "{2}){5}";
		private static final String MAC_ADDRESS_COLON_BOUND = "([:]" + HEX_BOUND + "{2}){5}";
		private static final String MAC_ADDRESS_HYPHEN_BOUND = "([-]" + HEX_BOUND + "{2}){5}";
		private static final String MAC_ADDRESS_BOUND = HEX_BOUND + "{10}";
		private static final List<String> MAC_ADDRESS_BOUNDS = List.of(MAC_ADDRESS_DOT_BOUND, MAC_ADDRESS_COLON_BOUND, MAC_ADDRESS_HYPHEN_BOUND, MAC_ADDRESS_BOUND);
		private static final Pattern MAC_ADDRESS_PATTERN = Pattern.compile("^" + HEX_BOUND + "{2}(" + String.join(OR_SEPARATOR, MAC_ADDRESS_BOUNDS) + ")$");
		private static final boolean MAC_ADDRESS_EXPOSE_FOURTH = false;
		
		private static final String UUID_HYPHEN_BOUND = "([-]" + HEX_BOUND + "{4}){3}[-]";
		private static final String UUID_BOUND = HEX_BOUND + "{12}";
		private static final List<String> UUID_BOUNDS = List.of(UUID_HYPHEN_BOUND, UUID_BOUND);
		private static final Pattern UUID_PATTERN = Pattern.compile("^" + HEX_BOUND + "{8}(" + String.join(OR_SEPARATOR, UUID_BOUNDS) + ")" + HEX_BOUND + "{12}$");
		private static final int[] UUID_MASK_OPTIONS = {4, 3, 2, 1, 0};
		private static final int UUID_MASK_LENGTH = UUID_MASK_OPTIONS[0];
		
		private static final List<String> RESIDENT_REGISTRATION_1900S_BOUNDS = List.of("1", "2");
		private static final List<String> RESIDENT_REGISTRATION_2000S_BOUNDS = List.of("3", "4");
		private static final List<String> RESIDENT_REGISTRATION_SEX_BOUNDS = List.of(String.join(OR_SEPARATOR, RESIDENT_REGISTRATION_1900S_BOUNDS), String.join(OR_SEPARATOR, RESIDENT_REGISTRATION_2000S_BOUNDS));
		private static final Pattern RESIDENT_REGISTRATION_PATTERN = Pattern.compile("^" + YEAR_SHORT_BOUND + MONTH_BOUND + DAY_BOUND + "[-]?(" + String.join(OR_SEPARATOR, RESIDENT_REGISTRATION_SEX_BOUNDS) + ")[0-9]{6}$");
		private static final int[] RESIDENT_REGISTRATION_MASK_OPTIONS = {4, 2, 0};
		private static final int RESIDENT_REGISTRATION_MASK_LENGTH = RESIDENT_REGISTRATION_MASK_OPTIONS[0];
		
		private static final List<String> ALIEN_REGISTRATION_1900S_BOUNDS = List.of("5", "6");
		private static final List<String> ALIEN_REGISTRATION_2000S_BOUNDS = List.of("7", "8");
		private static final List<String> ALIEN_REGISTRATION_SEX_BOUNDS = List.of(String.join(OR_SEPARATOR, ALIEN_REGISTRATION_1900S_BOUNDS), String.join(OR_SEPARATOR, ALIEN_REGISTRATION_2000S_BOUNDS));
		private static final Pattern ALIEN_REGISTRATION_PATTERN = Pattern.compile("^" + YEAR_SHORT_BOUND + MONTH_BOUND + DAY_BOUND + "[-]?(" + String.join(OR_SEPARATOR, ALIEN_REGISTRATION_SEX_BOUNDS) + ")[0-9]{6}$");
		
		private static final String BUSINESS_REGISTRATION_HYPHEN_BOUND = "[-][0-9]{2}[-]";
		private static final String BUSINESS_REGISTRATION_BOUND = "[0-9]{2}";
		private static final List<String> BUSINESS_REGISTRATION_BOUNDS = List.of(BUSINESS_REGISTRATION_HYPHEN_BOUND, BUSINESS_REGISTRATION_BOUND);
		private static final Pattern BUSINESS_REGISTRATION_PATTERN = Pattern.compile("^[1-9][0-9]{2}(" + String.join(OR_SEPARATOR, BUSINESS_REGISTRATION_BOUNDS) + ")[0-9]{5}$");
		private static final boolean BUSINESS_REGISTRATION_EXPOSE_SECOND = false;
		
		private static final String CORPORATION_REGISTRATION_HYPHEN_BOUND = "[-][0-9]{2}[-][0-9]{6}[-]";
		private static final String CORPORATION_REGISTRATION_BOUND = "[0-9]{8}";
		private static final List<String> CORPORATION_REGISTRATION_BOUNDS = List.of(CORPORATION_REGISTRATION_HYPHEN_BOUND, CORPORATION_REGISTRATION_BOUND);
		private static final Pattern CORPORATION_REGISTRATION_PATTERN = Pattern.compile("^[0-9]{4}(" + String.join(OR_SEPARATOR, CORPORATION_REGISTRATION_BOUNDS) + ")[0-9]$");
		private static final boolean CORPORATION_REGISTRATION_EXPOSE_SECOND = false;
		
		private static final Pattern BIRTH_DATE_PATTERN = Pattern.compile("^" + YEAR_WITH_SUFFIX_BOUND + MONTH_WITH_SUFFIX_BOUND + DAY_WITH_SUFFIX_BOUND + "$");
		private static final boolean BIRTH_DATE_EXPOSE_SECOND = false;
		
		private static final List<String> BIRTH_DATE_REGISTRATION_SEX_BOUNDS = List.of(String.join(OR_SEPARATOR, RESIDENT_REGISTRATION_SEX_BOUNDS), String.join(OR_SEPARATOR, ALIEN_REGISTRATION_SEX_BOUNDS));
		private static final Pattern BIRTH_DATE_REGISTRATION_PATTERN = Pattern.compile("^" + YEAR_SHORT_BOUND + MONTH_BOUND + DAY_BOUND + "[-]?(" + String.join(OR_SEPARATOR, BIRTH_DATE_REGISTRATION_SEX_BOUNDS) + ")$");
		
		private static final Pattern CAR_PLATE_PATTERN = Pattern.compile("^[0-9]{2,3}[가-힣][0-9]{4}$");
		private static final int[] CAR_PLATE_MASK_OPTIONS = {4, 3, 2};
		private static final int CAR_PLATE_MASK_LENGTH = CAR_PLATE_MASK_OPTIONS[0];
		
		private static final String DRIVER_REGISTRATION_HYPHEN_BOUND = "[-][0-9]{2}[-][0-9]{6}[-]";
		private static final String DRIVER_REGISTRATION_BOUND = "[0-9]{8}";
		private static final List<String> DRIVER_REGISTRATION_BOUNDS = List.of(DRIVER_REGISTRATION_HYPHEN_BOUND, DRIVER_REGISTRATION_BOUND);
		private static final Pattern DRIVER_REGISTRATION_PATTERN = Pattern.compile("^(1[1-9]|2[0-8])(" + String.join(OR_SEPARATOR, DRIVER_REGISTRATION_BOUNDS) + ")[0-9]{2}$");
		private static final boolean DRIVER_REGISTRATION_EXPOSE_SECOND = false;
		
		private static final String CARD_NUMBER_HYPHEN_BOUND = "([-][0-9]{4}){3}";
		private static final String CARD_NUMBER_BOUND = "[0-9]{12}";
		private static final List<String> CARD_NUMBER_BOUNDS = List.of(CARD_NUMBER_HYPHEN_BOUND, CARD_NUMBER_BOUND);
		private static final Pattern CARD_NUMBER_PATTERN = Pattern.compile("^[0-9]{4}(" + String.join(OR_SEPARATOR, CARD_NUMBER_BOUNDS) + ")$");
		private static final boolean CARD_NUMBER_EXPOSE_SECOND_PREFIX = false;
		private static final int[] CARD_NUMBER_MASK_OPTIONS = {4, 3, 2, 1, 0};
		private static final int CARD_NUMBER_MASK_LENGTH = CARD_NUMBER_MASK_OPTIONS[0];
		
		private static final Pattern CARD_EXPIRED_MM_YY_PATTERN = Pattern.compile("^" + MONTH_BOUND + "[/]?" + YEAR_SHORT_BOUND + "$");
		private static final boolean CARD_EXPIRED_MM_YY_EXPOSE_LAST = false;
		
		private static final Pattern ACCOUNT_NUMBER_HYPHEN_PATTERN = Pattern.compile("^[0-9]+([-][0-9]+){1,6}$");
		private static final Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^[0-9]{12,16}$");
		private static final int[] ACCOUNT_NUMBER_EXPOSE_OPTIONS = {4, 5, 6};
		private static final int ACCOUNT_NUMBER_EXPOSE_LENGTH = ACCOUNT_NUMBER_EXPOSE_OPTIONS[0];
		
		private static final String PASSPORT_NUMBER_BOUND1 = "[0-9]{8}";
		private static final String PASSPORT_NUMBER_BOUND2 = "[a-zA-Z][0-9]{7}";
		private static final String PASSPORT_NUMBER_BOUND3 = "[0-9]{1}[a-zA-Z][0-9]{6}";
		private static final String PASSPORT_NUMBER_BOUND4 = "[0-9]{2}[a-zA-Z][0-9]{5}";
		private static final String PASSPORT_NUMBER_BOUND5 = "[0-9]{3}[a-zA-Z][0-9]{4}";
		private static final String PASSPORT_NUMBER_BOUND6 = "[0-9]{4}[a-zA-Z][0-9]{3}";
		private static final String PASSPORT_NUMBER_BOUND7 = "[0-9]{5}[a-zA-Z][0-9]{2}";
		private static final String PASSPORT_NUMBER_BOUND8 = "[0-9]{6}[a-zA-Z][0-9]{1}";
		private static final String PASSPORT_NUMBER_BOUND9 = "[0-9]{7}[a-zA-Z]";
		private static final List<String> PASSPORT_NUMBER_BOUNDS = List.of(PASSPORT_NUMBER_BOUND1, PASSPORT_NUMBER_BOUND2, PASSPORT_NUMBER_BOUND3, PASSPORT_NUMBER_BOUND4, PASSPORT_NUMBER_BOUND5, PASSPORT_NUMBER_BOUND6, PASSPORT_NUMBER_BOUND7, PASSPORT_NUMBER_BOUND8, PASSPORT_NUMBER_BOUND9);
		private static final Pattern PASSPORT_NUMBER_PATTERN = Pattern.compile("^[dDmMoOrRsS](" + String.join(OR_SEPARATOR, PASSPORT_NUMBER_BOUNDS) + ")$");
		private static final int[] PASSPORT_NUMBER_MASK_OPTIONS = {6, 5, 4, 3};
		private static final int PASSPORT_NUMBER_MASK_LENGTH = PASSPORT_NUMBER_MASK_OPTIONS[0];
		
		private static final Pattern BARCODE_HYPHEN_PATTERN = Pattern.compile("^[0-9]{2,3}[-][0-9]{4,6}[-][0-9]{3,5}[-][0-9]$");
		private static final Pattern BARCODE_PATTERN = Pattern.compile("^[0-9]{13}$");
		private static final boolean BARCODE_SECOND_EXPOSE_INSTEAD_THIRD = true;
		
		private static final String COMMON_TEXT_BOUND = "[0-9a-zA-Z가-힣]+";
		private static final String COMMON_SEPARATOR_BOUND = "[.&'/)(_-]";
		private static final String COMMON_BOUND = COMMON_TEXT_BOUND + "(" + COMMON_SEPARATOR_BOUND + COMMON_TEXT_BOUND + ")*";
		
		private static final List<String> DEPARTMENT_BOUNDS = List.of("사업부", "연구소", "본부", "센터", "그룹", "파트", "조직", "팀", "부", "실", "국", "과", "Headquarters", "Organization", "Directorate", "Commission", "Department", "Committee", "Institute", "Division", "Segment", "Council", "Section", "Center", "Sector", "Agency", "Branch", "Office", "Bureau", "Group", "Board", "Unit", "Part", "Team");
		private static final Pattern DEPARTMENT_PATTERN = Pattern.compile("^" + COMMON_BOUND + "([ ]" + COMMON_BOUND + "){0,5}[ ]?(" + String.join(OR_SEPARATOR, DEPARTMENT_BOUNDS) + ")$", Pattern.CASE_INSENSITIVE);
		private static final int[] DEPARTMENT_EXPOSE_OPTIONS = {0, 1, 2, 3, 4};
		private static final int DEPARTMENT_EXPOSE_LENGTH = DEPARTMENT_EXPOSE_OPTIONS[0];
		
		private static final List<String> SCHOOL_BOUNDS = List.of("전문대학교", "어린이집", "초등학교", "고등학교", "유치원", "보육원", "탁아소", "중학교", "대학교", "대학원", "교육원", "연구원", "전문대", "대학", "초", "중", "고", "대", "Kindergarten", "University", "Preschool", "Graduate", "Institute", "Nursery", "Daycare", "School", "Academy", "College");
		private static final Pattern SCHOOL_PATTERN = Pattern.compile("^" + COMMON_BOUND + "([ ]" + COMMON_BOUND + "){0,5}[ ]?(" + String.join(OR_SEPARATOR, SCHOOL_BOUNDS) + ")$", Pattern.CASE_INSENSITIVE);
		private static final int[] SCHOOL_EXPOSE_OPTIONS = {0, 1, 2, 3, 4};
		private static final int SCHOOL_EXPOSE_LENGTH = SCHOOL_EXPOSE_OPTIONS[0];
		
		private static final String HEIGHT_SUFFIX = "cm";
		private static final int HEIGHT_DECIMAL_LENGTH = 2;
		private static final Pattern HEIGHT_PATTERN = Pattern.compile("^[1-9][0-9]{0,2}([.][0-9]{0,7})?([ ]?" + HEIGHT_SUFFIX + ")?$", Pattern.CASE_INSENSITIVE);
		private static final int[] HEIGHT_EXPOSE_OPTIONS = {0, 1, 2, 3};
		private static final int HEIGHT_EXPOSE_LENGTH = HEIGHT_EXPOSE_OPTIONS[0];
		
		private static final String WEIGHT_SUFFIX = "kg";
		private static final int WEIGHT_DECIMAL_LENGTH = 2;
		private static final Pattern WEIGHT_PATTERN = Pattern.compile("^(0|[1-9][0-9]{0,2})([.][0-9]{0,7})?([ ]?" + WEIGHT_SUFFIX + ")?$", Pattern.CASE_INSENSITIVE);
		private static final int[] WEIGHT_EXPOSE_OPTIONS = {0, 1, 2, 3};
		private static final int WEIGHT_EXPOSE_LENGTH = WEIGHT_EXPOSE_OPTIONS[0];
		
		private static final String DOSAGE_SUFFIX = "mg";
		private static final int DOSAGE_DECIMAL_LENGTH = 2;
		private static final Pattern DOSAGE_PATTERN = Pattern.compile("^(0|[1-9][0-9]{0,2})([.][0-9]{0,7})?([ ]?" + DOSAGE_SUFFIX + ")?$", Pattern.CASE_INSENSITIVE);
		private static final int[] DOSAGE_EXPOSE_OPTIONS = {0, 1, 2, 3};
		private static final int DOSAGE_EXPOSE_LENGTH = DOSAGE_EXPOSE_OPTIONS[0];
	}
	public static final class Type {
		public enum Format {
			STRING,
			INTEGER, INTEGER_COMMA,
			DOUBLE, DOUBLE_COMMA,
			DATE, TIME,
			FILE_SIZE,
			URL_LINK,
			ENCRYPT_SEQUENCE_CODE, DECRYPT_SEQUENCE_CODE,
			ID,
			EMAIL,
			MOBILE, MOBILE_INTERNATIONAL,
			FAX, LANDLINE, LANDLINE_SHORT,
			NAME, NICK_NAME,
			ZIP_CODE, ADDRESS, ADDRESS_DETAIL,
			LATITUDE, LONGITUDE, GPS,
			DELIVERY_TRACK_INVOICE, TAX_INVOICE,
			IP_ADDRESS, MAC_ADDRESS, UUID,
			RESIDENT_REGISTRATION, ALIEN_REGISTRATION,
			BUSINESS_REGISTRATION, CORPORATION_REGISTRATION,
			BIRTH_DATE, BIRTH_DATE_REGISTRATION, AGE, AGE_INTERNATIONAL, AGE_REGISTRATION, AGE_INTERNATIONAL_REGISTRATION,
			CAR_PLATE, DRIVER_REGISTRATION,
			CARD_NUMBER, CARD_EXPIRED_MM_YY, ACCOUNT_NUMBER,
			PASSPORT_NUMBER,
			BARCODE,
			DEPARTMENT, SCHOOL,
			HEIGHT, WEIGHT, DOSAGE;
		}
		public enum FileSize {
			TB(1_099_511_627_776L), GB(1_073_741_824L), MB(1_048_576L), KB(1_024L), B(1L);
			private final long bytes;
			FileSize(long bytes) {this.bytes = bytes;}
			
			public static final List<String> getSuffixs() {return Arrays.stream(values()).map(FileSize::name).toList();}
			private static final long getUnit(String suffix) {return valueOf(suffix).bytes;}
			private static final String getFileSizeWithSuffix(long value) {
				FileSize[] units = values();
				int size = units.length - 1;
				for (int i=0; i<size; i++) {
					if (value >= units[i].bytes) {
						return String.format(Util.FILE_SIZE_DECIMAL_COMMA_FORMAT, (double) value / units[i].bytes) + Util.SPACE_SEPARATOR + units[i].name();
					}
				}
				return String.format(Util.NUMBER_COMMA_FORMAT, value) + Util.SPACE_SEPARATOR + B.name();
			}
		}
		private static final Map<String, List<String>> PATTERNS = new HashMap<>(64);
		static {
			PATTERNS.put(Format.INTEGER.name(), List.of(Constant.INTEGER_PATTERN.toString(), Constant.DOUBLE_PATTERN.toString()));
			PATTERNS.put(Format.DOUBLE.name(), List.of(Constant.INTEGER_PATTERN.toString(), Constant.DOUBLE_PATTERN.toString()));
			PATTERNS.put(Format.DATE.name(), List.of(Constant.DATE_TIME_PATTERN.toString(), Constant.DATE_HOUR_MINUTE_PATTERN.toString(), Constant.DATE_PATTERN.toString()));
			PATTERNS.put(Format.TIME.name(), List.of(Constant.TIME_PATTERN.toString(), Constant.HOUR_MINUTE_PATTERN.toString()));
			PATTERNS.put(Format.FILE_SIZE.name(), List.of(Constant.INTEGER_PATTERN.toString(), Constant.FILE_SIZE_INTEGER_PATTERN.toString(), Constant.FILE_SIZE_DOUBLE_PATTERN.toString()));
			PATTERNS.put(Format.URL_LINK.name(), List.of(Constant.URL_LINK_PATTERN.toString()));
			PATTERNS.put(Format.ENCRYPT_SEQUENCE_CODE.name(), List.of(Constant.ENCRYPT_SEQUENCE_CODE_PATTERN.toString()));
			PATTERNS.put(Format.DECRYPT_SEQUENCE_CODE.name(), List.of(Constant.DECRYPT_SEQUENCE_CODE_PATTERN.toString()));
			PATTERNS.put(Format.ID.name(), List.of(Constant.ID_PATTERN.toString()));
			PATTERNS.put(Format.EMAIL.name(), List.of(Constant.EMAIL_PATTERN.toString()));
			PATTERNS.put(Format.MOBILE.name(), List.of(Constant.MOBILE_PATTERN.toString(), Constant.MOBILE_SHORT_PATTERN.toString()));
			PATTERNS.put(Format.MOBILE_INTERNATIONAL.name(), List.of(Constant.MOBILE_INTERNATIONAL_PATTERN.toString(), Constant.MOBILE_SHORT_PATTERN.toString()));
			PATTERNS.put(Format.FAX.name(), List.of(Constant.FAX_HYPHEN_PATTERN.toString(), Constant.FAX_PATTERN.toString()));
			PATTERNS.put(Format.LANDLINE.name(), List.of(Constant.LANDLINE_HYPHEN_PATTERN.toString(), Constant.LANDLINE_PATTERN.toString()));
			PATTERNS.put(Format.LANDLINE_SHORT.name(), List.of(Constant.LANDLINE_SHORT_PATTERN.toString()));
			PATTERNS.put(Format.NAME.name(), List.of(Constant.NAME_KOR_PATTERN.toString(), Constant.NAME_SPACE_ENG_PATTERN.toString(), Constant.NAME_ENG_PATTERN.toString()));
			PATTERNS.put(Format.NICK_NAME.name(), List.of(Constant.NICK_NAME_SPACE_PATTERN.toString(), Constant.NICK_NAME_PATTERN.toString()));
			PATTERNS.put(Format.ZIP_CODE.name(), List.of(Constant.ZIP_CODE_PATTERN.toString()));
			PATTERNS.put(Format.LATITUDE.name(), List.of(Constant.LATITUDE_PATTERN.toString()));
			PATTERNS.put(Format.LONGITUDE.name(), List.of(Constant.LONGITUDE_PATTERN.toString()));
			PATTERNS.put(Format.GPS.name(), List.of(Constant.GPS_PATTERN.toString()));
			PATTERNS.put(Format.DELIVERY_TRACK_INVOICE.name(), List.of(Constant.DELIVERY_TRACK_INVOICE_PATTERN.toString()));
			PATTERNS.put(Format.TAX_INVOICE.name(), List.of(Constant.TAX_INVOICE_PATTERN.toString()));
			PATTERNS.put(Format.IP_ADDRESS.name(), List.of(Constant.IP_ADDRESS_PATTERN.toString()));
			PATTERNS.put(Format.MAC_ADDRESS.name(), List.of(Constant.MAC_ADDRESS_PATTERN.toString()));
			PATTERNS.put(Format.UUID.name(), List.of(Constant.UUID_PATTERN.toString()));
			PATTERNS.put(Format.RESIDENT_REGISTRATION.name(), List.of(Constant.RESIDENT_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.ALIEN_REGISTRATION.name(), List.of(Constant.ALIEN_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.BUSINESS_REGISTRATION.name(), List.of(Constant.BUSINESS_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.CORPORATION_REGISTRATION.name(), List.of(Constant.CORPORATION_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.BIRTH_DATE.name(), List.of(Constant.BIRTH_DATE_PATTERN.toString()));
			PATTERNS.put(Format.BIRTH_DATE_REGISTRATION.name(), List.of(Constant.BIRTH_DATE_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.AGE.name(), List.of(Constant.BIRTH_DATE_PATTERN.toString()));
			PATTERNS.put(Format.AGE_INTERNATIONAL.name(), List.of(Constant.BIRTH_DATE_PATTERN.toString()));
			PATTERNS.put(Format.AGE_REGISTRATION.name(), List.of(Constant.BIRTH_DATE_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.AGE_INTERNATIONAL_REGISTRATION.name(), List.of(Constant.BIRTH_DATE_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.CAR_PLATE.name(), List.of(Constant.CAR_PLATE_PATTERN.toString()));
			PATTERNS.put(Format.DRIVER_REGISTRATION.name(), List.of(Constant.DRIVER_REGISTRATION_PATTERN.toString()));
			PATTERNS.put(Format.CARD_NUMBER.name(), List.of(Constant.CARD_NUMBER_PATTERN.toString()));
			PATTERNS.put(Format.CARD_EXPIRED_MM_YY.name(), List.of(Constant.CARD_EXPIRED_MM_YY_PATTERN.toString()));
			PATTERNS.put(Format.ACCOUNT_NUMBER.name(), List.of(Constant.ACCOUNT_NUMBER_HYPHEN_PATTERN.toString(), Constant.ACCOUNT_NUMBER_PATTERN.toString()));
			PATTERNS.put(Format.PASSPORT_NUMBER.name(), List.of(Constant.PASSPORT_NUMBER_PATTERN.toString()));
			PATTERNS.put(Format.BARCODE.name(), List.of(Constant.BARCODE_HYPHEN_PATTERN.toString(), Constant.BARCODE_PATTERN.toString()));
			PATTERNS.put(Format.DEPARTMENT.name(), List.of(Constant.DEPARTMENT_PATTERN.toString()));
			PATTERNS.put(Format.SCHOOL.name(), List.of(Constant.SCHOOL_PATTERN.toString()));
			PATTERNS.put(Format.HEIGHT.name(), List.of(Constant.HEIGHT_PATTERN.toString()));
			PATTERNS.put(Format.WEIGHT.name(), List.of(Constant.WEIGHT_PATTERN.toString()));
			PATTERNS.put(Format.DOSAGE.name(), List.of(Constant.DOSAGE_PATTERN.toString()));
		}
		
		public static final Map<String, List<String>> getPatterns() {
			return PATTERNS;
		}
		public static final Map<String, List<String>> getPatterns(Format... formats) {
			if (formats == null) {
				return Collections.emptyMap();
			}
			Map<String, List<String>> patterns = new HashMap<>((int) Math.ceil(formats.length / 0.75));
			for (Format format : formats) {
				patterns.put(format.name(), PATTERNS.getOrDefault(format.name(), Collections.emptyList()));
			}
			return patterns;
		}
	}
	public static final class Util {
		private enum Crypt {ENCRYPT, DECRYPT;}
		private enum Position {EXPOSE_START, EXPOSE_LAST, MASK_START, MASK_LAST;}
		
		private static final String STRING_RETURN_DEFAULT = "";
		private static final String STRING_REPLACE_DEFAULT = "";
		
		private static final String NUMBER_PADDING_PREFIX = "0";
		private static final String YEAR_1900S_PREFIX = "19";
		private static final String YEAR_2000S_PREFIX = "20";
		
		private static final String MASK_AS = "*";
		private static final String FIXED_MASK_AS = MASK_AS + MASK_AS + MASK_AS;
		private static final String AT_SEPARATOR = "@";
		private static final String DOT_SEPARATOR = ".";
		private static final String DOT_ESCAPE_SEPARATOR = "\\.";
		private static final String HYPHEN_SEPARATOR = "-";
		private static final String SPACE_SEPARATOR = " ";
		private static final String COMMA_SEPARATOR = ",";
		private static final String SLASH_SEPARATOR = "/";
		private static final String COLON_SEPARATOR = ":";
		
		private static final String NUMBER_COMMA_FORMAT = "%,d";
		private static final String DECIMAL_FORMAT = "%.2f";
		private static final String DECIMAL_COMMA_FORMAT = "%,.2f";
		private static final String FILE_SIZE_DECIMAL_COMMA_FORMAT = "%,." + Constant.FILE_SIZE_DECIMAL_LENGTH + "f";
		private static final String SEQUENCE_CODE_FORMAT = "%" + NUMBER_PADDING_PREFIX + Constant.SEQUENCE_CODE_LENGTH + "d";
		private static final String GPS_DECIMAL_FORMAT = "%." + Constant.GPS_DECIMAL_LENGTH + "f";
		private static final String HEIGHT_DECIMAL_FORMAT = "%." + Constant.HEIGHT_DECIMAL_LENGTH + "f";
		private static final String WEIGHT_DECIMAL_FORMAT = "%." + Constant.WEIGHT_DECIMAL_LENGTH + "f";
		private static final String DOSAGE_DECIMAL_FORMAT = "%." + Constant.DOSAGE_DECIMAL_LENGTH + "f";
		
		private static final DateTimeFormatter DATE_PARSE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
		private static final DateTimeFormatter DATE_FORMAT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		private static final DateTimeFormatter DATE_HOUR_MINUTE_PARSE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		private static final DateTimeFormatter DATE_HOUR_MINUTE_FORMAT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		private static final DateTimeFormatter DATE_TIME_PARSE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		private static final DateTimeFormatter DATE_TIME_FORMAT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		private static final DateTimeFormatter HOUR_MINUTE_PARSE_FORMATTER = DateTimeFormatter.ofPattern("HHmm");
		private static final DateTimeFormatter HOUR_MINUTE_FORMAT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
		private static final DateTimeFormatter TIME_PARSE_FORMATTER = DateTimeFormatter.ofPattern("HHmmss");
		private static final DateTimeFormatter TIME_FORMAT_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
		
		private static final ThreadLocal<SimpleDateFormat> SIMPLE_DATE_TIME_PARSE_FORMATTER = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));
		
		private static final Pattern NUMBER_PATTERN = Pattern.compile("[0-9]");
		private static final Pattern TEXT_PATTERN = Pattern.compile("[0-9a-zA-Z가-힣]");
		private static final Pattern COMMA_PATTERN = Pattern.compile(",");
		private static final Pattern EXCLUDE_DOUBLE_PATTERN = Pattern.compile("[^0-9.]");
		private static final Pattern EXCLUDE_NUMBER_PATTERN = Pattern.compile("[^0-9]");
		private static final Pattern EXCLUDE_NUMBER_ENG_PATTERN = Pattern.compile("[^0-9a-zA-Z]");
		private static final Pattern EXCLUDE_TEXT_PATTERN = Pattern.compile("[^0-9a-zA-Z가-힣]");
		
		public static final String safeParse(Type.Format format, Object object) {
			return safeParse(format, object, false);
		}
		public static final String safeParse(Type.Format format, Object object, boolean maskingRequired) {
			if (object == null) {
				return STRING_RETURN_DEFAULT;
			}
			try {
				return parse(format, object, maskingRequired);
			} catch (Exception e) {
				return parseString(object);
			}
		}
		public static final String parse(Type.Format format, Object object) {
			return parse(format, object, false);
		}
		public static final String parse(Type.Format format, Object object, boolean maskingRequired) {
			if (object == null) {
				if (format == Type.Format.STRING) {
					return STRING_RETURN_DEFAULT;
				}
				throw new NullPointerException();
			}
			String value = object.toString().trim();
			switch (format) {
				case STRING: return parseString(value);
				case INTEGER: return formatLong(value);
				case INTEGER_COMMA: return formatLongComma(value);
				case DOUBLE: return formatDouble(value);
				case DOUBLE_COMMA: return formatDoubleComma(value);
				case DATE: return parseDate(value);
				case TIME: return parseTime(value);
				case FILE_SIZE: return formatFileSize(value);
				case URL_LINK: return parseUrlLink(value);
				case ENCRYPT_SEQUENCE_CODE: return maskingRequired ? maskEncryptSequenceCode(value) : parseEncryptSequenceCode(value);
				case DECRYPT_SEQUENCE_CODE: return maskingRequired ? maskDecryptSequenceCode(value) : parseDecryptSequenceCode(value);
				case ID: return maskingRequired ? maskId(value) : parseId(value);
				case EMAIL: return maskingRequired ? maskEmail(value) : parseEmail(value);
				case MOBILE: return maskingRequired ? maskMobile(value) : parseMobile(value);
				case MOBILE_INTERNATIONAL: return maskingRequired ? maskMobileInternational(value) : parseMobileInternational(value);
				case FAX: return maskingRequired ? maskFax(value) : parseFax(value);
				case LANDLINE: return maskingRequired ? maskLandline(value) : parseLandline(value);
				case LANDLINE_SHORT: return maskingRequired ? maskLandlineShort(value) : parseLandlineShort(value);
				case NAME: return maskingRequired ? maskName(value) : parseName(value);
				case NICK_NAME: return maskingRequired ? maskNickName(value) : parseNickName(value);
				case ZIP_CODE: return maskingRequired ? maskZipCode(value) : parseZipCode(value);
				case ADDRESS: return maskingRequired ? maskAddress(value) : parseAddress(value);
				case ADDRESS_DETAIL: return maskingRequired ? maskAddressDetail(value) : parseAddressDetail(value);
				case LATITUDE: return maskingRequired ? maskLatitude(value) : parseLatitude(value);
				case LONGITUDE: return maskingRequired ? maskLongitude(value) : parseLongitude(value);
				case GPS: return maskingRequired ? maskGps(value) : parseGps(value);
				case DELIVERY_TRACK_INVOICE: return maskingRequired ? maskDeliveryTrackInvoice(value) : parseDeliveryTrackInvoice(value);
				case TAX_INVOICE: return maskingRequired ? maskTaxInvoice(value) : parseTaxInvoice(value);
				case IP_ADDRESS: return maskingRequired ? maskIpAddress(value) : parseIpAddress(value);
				case MAC_ADDRESS: return maskingRequired ? maskMacAddress(value) : parseMacAddress(value);
				case UUID: return maskingRequired ? maskUuid(value) : parseUuid(value);
				case RESIDENT_REGISTRATION: return maskingRequired ? maskResidentRegistration(value) : parseResidentRegistration(value);
				case ALIEN_REGISTRATION: return maskingRequired ? maskAlienRegistration(value) : parseAlienRegistration(value);
				case BUSINESS_REGISTRATION: return maskingRequired ? maskBusinessRegistration(value) : parseBusinessRegistration(value);
				case CORPORATION_REGISTRATION: return maskingRequired ? maskCorporationRegistration(value) : parseCorporationRegistration(value);
				case BIRTH_DATE: return maskingRequired ? maskBirthDate(value) : parseBirthDate(value);
				case BIRTH_DATE_REGISTRATION: return maskingRequired ? maskBirthDateRegistration(value) : parseBirthDateRegistration(value);
				case AGE: return maskingRequired ? maskAge(value) : parseAge(value);
				case AGE_INTERNATIONAL: return maskingRequired ? maskAgeInternational(value) : parseAgeInternational(value);
				case AGE_REGISTRATION: return maskingRequired ? maskAgeRegistration(value) : parseAgeRegistration(value);
				case AGE_INTERNATIONAL_REGISTRATION: return maskingRequired ? maskAgeInternationalRegistration(value) : parseAgeInternationalRegistration(value);
				case CAR_PLATE: return maskingRequired ? maskCarPlate(value) : parseCarPlate(value);
				case DRIVER_REGISTRATION: return maskingRequired ? maskDriverRegistration(value) : parseDriverRegistration(value);
				case CARD_NUMBER: return maskingRequired ? maskCardNumber(value) : parseCardNumber(value);
				case CARD_EXPIRED_MM_YY: return maskingRequired ? maskCardExpiredMmYy(value) : parseCardExpiredMmYy(value);
				case ACCOUNT_NUMBER: return maskingRequired ? maskAccountNumber(value) : parseAccountNumber(value);
				case PASSPORT_NUMBER: return maskingRequired ? maskPassportNumber(value) : parsePassportNumber(value);
				case BARCODE: return maskingRequired ? maskBarcode(value) : parseBarcode(value);
				case DEPARTMENT: return maskingRequired ? maskDepartment(value) : parseDepartment(value);
				case SCHOOL: return maskingRequired ? maskSchool(value) : parseSchool(value);
				case HEIGHT: return maskingRequired ? maskHeight(value) : formatHeight(value);
				case WEIGHT: return maskingRequired ? maskWeight(value) : formatWeight(value);
				case DOSAGE: return maskingRequired ? maskDosage(value) : formatDosage(value);
				default: return parseString(value);
			}
		}
		public static final String parseString(Object value) {
			return value != null && value.toString().trim().length() > 0 ? value.toString() : STRING_RETURN_DEFAULT;
		}
		
		public static final boolean isInteger(String value) {return Constant.INTEGER_PATTERN.matcher(value).matches();}
		public static final boolean isDouble(String value) {return Constant.DOUBLE_PATTERN.matcher(value).matches();}
		public static final long parseLong(String value) {
			return isInteger(value) ? Long.parseLong(removeComma(value)) : isDouble(value) ? (long) Double.parseDouble(removeComma(value)) : throwLongFormatException(value, Type.Format.INTEGER);
		}
		public static final String formatLong(String value) {
			return String.valueOf(parseLong(value));
		}
		public static final String formatLongComma(String value) {
			return String.format(NUMBER_COMMA_FORMAT, parseLong(value));
		}
		public static final double parseDouble(String value) {
			return isDouble(value) ? Double.parseDouble(removeComma(value)) : isInteger(value) ? (double) Long.parseLong(removeComma(value)) : throwDoubleFormatException(value, Type.Format.DOUBLE);
		}
		public static final String formatDouble(String value) {
			return String.format(DECIMAL_FORMAT, parseDouble(value));
		}
		public static final String formatDoubleComma(String value) {
			return String.format(DECIMAL_COMMA_FORMAT, parseDouble(value));
		}
		
		public static final boolean isDate(String value) {return validDateTime(value) || validDateHourMinute(value) || validDate(value);}
		private static final boolean validDateTime(String value) {return Constant.DATE_TIME_PATTERN.matcher(value).matches();}
		private static final boolean validDateHourMinute(String value) {return Constant.DATE_HOUR_MINUTE_PATTERN.matcher(value).matches();}
		private static final boolean validDate(String value) {return Constant.DATE_PATTERN.matcher(value).matches();}
		public static final String parseDate(String value) {
			try {
				return validDateTime(value) ? LocalDateTime.parse(onlyNumber(value), DATE_TIME_PARSE_FORMATTER).format(DATE_TIME_FORMAT_FORMATTER) : validDateHourMinute(value) ? LocalDateTime.parse(onlyNumber(value), DATE_HOUR_MINUTE_PARSE_FORMATTER).format(DATE_HOUR_MINUTE_FORMAT_FORMATTER) : validDate(value) ? LocalDate.parse(onlyNumber(value), DATE_PARSE_FORMATTER).format(DATE_FORMAT_FORMATTER) : throwIllegalArgumentException(value, Type.Format.DATE);
			} catch (DateTimeException e) {
				throw new DateTimeException(getErrorMessage(Type.Format.DATE));
			}
		}
		public static final boolean isTime(String value) {return validTime(value) || validHourMinute(value);}
		private static final boolean validTime(String value) {return Constant.TIME_PATTERN.matcher(value).matches();}
		private static final boolean validHourMinute(String value) {return Constant.HOUR_MINUTE_PATTERN.matcher(value).matches();}
		public static final String parseTime(String value) {
			try {
				return validTime(value) ? LocalTime.parse(onlyNumber(value), TIME_PARSE_FORMATTER).format(TIME_FORMAT_FORMATTER) : validHourMinute(value) ? LocalTime.parse(onlyNumber(value), HOUR_MINUTE_PARSE_FORMATTER).format(HOUR_MINUTE_FORMAT_FORMATTER) : throwIllegalArgumentException(value, Type.Format.TIME);
			} catch (DateTimeException e) {
				throw new DateTimeException(getErrorMessage(Type.Format.TIME));
			}
		}
		public static final String parseDate(Date date) {
			if (date != null) {
				return parseDate(SIMPLE_DATE_TIME_PARSE_FORMATTER.get().format(date));
			}
			throw new NullPointerException();
		}
		
		public static final boolean isFileSize(String value) {return isInteger(value) || validFileSizeInteger(value) || validFileSizeDouble(value);}
		private static final boolean validFileSizeInteger(String value) {return Constant.FILE_SIZE_INTEGER_PATTERN.matcher(value).matches();}
		private static final boolean validFileSizeDouble(String value) {return Constant.FILE_SIZE_DOUBLE_PATTERN.matcher(value).matches();}
		public static final long parseFileSize(String value) {
			String suffix = getSuffix(value.toUpperCase(), Constant.FILE_SIZE_UNIT_BOUNDS);
			return isInteger(value) ? Long.parseLong(removeComma(value)) : validFileSizeInteger(value) ? Long.parseLong(removeComma(value.substring(0, value.length() - suffix.length()).trim())) * Type.FileSize.getUnit(suffix) : validFileSizeDouble(value) ? (long) (Double.parseDouble(removeComma(value.substring(0, value.length() - suffix.length()).trim())) * Type.FileSize.getUnit(suffix)) : throwLongFormatException(value, Type.Format.FILE_SIZE);
		}
		public static final String formatFileSize(String value) {
			return Type.FileSize.getFileSizeWithSuffix(parseFileSize(value));
		}
		
		public static final boolean isUrlLink(String value) {return Constant.URL_LINK_PATTERN.matcher(value).matches();}
		public static final String parseUrlLink(String value) {
			return isUrlLink(value) ? value : throwIllegalArgumentException(value, Type.Format.URL_LINK);
		}
		
		public static final boolean isEncryptSequenceCode(String value) {return Constant.ENCRYPT_SEQUENCE_CODE_PATTERN.matcher(value).matches();}
		public static final String parseEncryptSequenceCode(String value) {
			if (isEncryptSequenceCode(value)) {
				long sequence = Long.parseLong(value);
				if (sequence > 0) {
					value = String.format(SEQUENCE_CODE_FORMAT, sequence);
					StringBuilder result = new StringBuilder(Constant.SEQUENCE_CODE_LENGTH);
					for (int i=0; i<Constant.SEQUENCE_CODE_LENGTH; i++) {
						char c = value.charAt(i);
						result.append(i == 0 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND1, Crypt.ENCRYPT) : i == 2 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND2, Crypt.ENCRYPT) : i == 3 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND3, Crypt.ENCRYPT) : i == 6 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND4, Crypt.ENCRYPT) : c);
					}
					return result.toString() + getCheckSum(value);
				}
			}
			return throwIllegalArgumentException(value, Type.Format.ENCRYPT_SEQUENCE_CODE);
		}
		public static final String maskEncryptSequenceCode(String value) {
			return mask(parseEncryptSequenceCode(value), Constant.SEQUENCE_CODE_EXPOSE_LENGTH, Position.EXPOSE_START);
		}
		
		public static final boolean isDecryptSequenceCode(String value) {return Constant.DECRYPT_SEQUENCE_CODE_PATTERN.matcher(value).matches();}
		public static final String parseDecryptSequenceCode(String value) {
			if (isDecryptSequenceCode(value)) {
				StringBuilder result = new StringBuilder(Constant.SEQUENCE_CODE_LENGTH);
				for (int i=0; i<Constant.SEQUENCE_CODE_LENGTH; i++) {
					char c = value.charAt(i);
					result.append(i == 0 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND1, Crypt.DECRYPT) : i == 2 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND2, Crypt.DECRYPT) : i == 3 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND3, Crypt.DECRYPT) : i == 6 ? parseCrypt(c, Constant.SEQUENCE_CODE_BOUND4, Crypt.DECRYPT) : c);
				}
				if (String.valueOf(value.charAt(Constant.SEQUENCE_CODE_LENGTH)).equals(getCheckSum(result.toString()))) {
					return result.toString();
				}
			}
			return throwIllegalArgumentException(value, Type.Format.DECRYPT_SEQUENCE_CODE);
		}
		public static final String maskDecryptSequenceCode(String value) {
			return mask(parseDecryptSequenceCode(value), Constant.SEQUENCE_CODE_EXPOSE_LENGTH, Position.EXPOSE_START);
		}
		
		public static final boolean isId(String value) {return Constant.ID_PATTERN.matcher(value).matches();}
		public static final String parseId(String value) {
			return isId(value) ? value : throwIllegalArgumentException(value, Type.Format.ID);
		}
		public static final String maskId(String value) {
			return mask(parseId(value), Constant.ID_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.ID_FIXED_MASK_OPTION);
		}
		
		public static final boolean isEmail(String value) {return Constant.EMAIL_PATTERN.matcher(value).matches();}
		public static final String parseEmail(String value) {
			return isEmail(value) ? value : throwIllegalArgumentException(value, Type.Format.EMAIL);
		}
		public static final String maskEmail(String value) {
			String[] values = parseEmail(value).split(AT_SEPARATOR);
			return String.join(AT_SEPARATOR, mask(values[0], Constant.EMAIL_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.EMAIL_FIXED_MASK_OPTION), values[1]);
		}
		
		public static final boolean isMobile(String value) {return Constant.MOBILE_PATTERN.matcher(value).matches();}
		public static final boolean isMobileShort(String value) {return Constant.MOBILE_SHORT_PATTERN.matcher(value).matches();}
		public static final String parseMobile(String value) {
			return isMobile(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 3, 4) : isMobileShort(value) ? parseMobile(Constant.MOBILE_PREFIX + onlyNumber(value)) : throwIllegalArgumentException(value, Type.Format.MOBILE);
		}
		public static final String maskMobile(String value) {
			String[] values = parseMobile(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.MOBILE_SECOND_EXPOSE_INSTEAD_LAST ? values[1] : maskNumber(values[1]), Constant.MOBILE_SECOND_EXPOSE_INSTEAD_LAST ? maskNumber(values[2]) : values[2]);
		}
		
		public static final boolean isMobileInternational(String value) {return Constant.MOBILE_INTERNATIONAL_PATTERN.matcher(value).matches();}
		public static final String parseMobileInternational(String value) {
			return isMobileInternational(value) ? (Constant.MOBILE_INTERNATIONAL_SIGN_PREFIX + concat(onlyNumber(value), HYPHEN_SEPARATOR, 2, 2, 4)) : isMobileShort(value) ? parseMobileInternational(Constant.MOBILE_INTERNATIONAL_PREFIX + Constant.MOBILE_SHORT_PREFIX + onlyNumber(value)) : throwIllegalArgumentException(value, Type.Format.MOBILE_INTERNATIONAL);
		}
		public static final String maskMobileInternational(String value) {
			String[] values = parseMobileInternational(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], values[1], Constant.MOBILE_SECOND_EXPOSE_INSTEAD_LAST ? values[2] : maskNumber(values[2]), Constant.MOBILE_SECOND_EXPOSE_INSTEAD_LAST ? maskNumber(values[3]) : values[3]);
		}
		
		public static final boolean isFax(String value) {return validFaxHyphen(value) || validFax(value);}
		private static final boolean validFaxHyphen(String value) {return Constant.FAX_HYPHEN_PATTERN.matcher(value).matches();}
		private static final boolean validFax(String value) {return Constant.FAX_PATTERN.matcher(value).matches();}
		public static final String parseFax(String value) {
			return isFax(value) ? value : throwIllegalArgumentException(value, Type.Format.FAX);
		}
		public static final String maskFax(String value) {
			value = parseFax(value);
			if (validFaxHyphen(value)) {
				String[] values = value.split(HYPHEN_SEPARATOR);
				return String.join(HYPHEN_SEPARATOR, values[0], values[1], mask(values[2], Constant.FAX_MASK_LENGTH, Position.MASK_LAST));
			}
			return mask(value, Constant.FAX_MASK_LENGTH, Position.MASK_LAST);
		}
		
		public static final boolean isLandline(String value) {return validLandlineHyphen(value) || validLandline(value);}
		private static final boolean validLandlineHyphen(String value) {return Constant.LANDLINE_HYPHEN_PATTERN.matcher(value).matches();}
		private static final boolean validLandline(String value) {return Constant.LANDLINE_PATTERN.matcher(value).matches();}
		public static final String parseLandline(String value) {
			return isLandline(value) ? value : throwIllegalArgumentException(value, Type.Format.LANDLINE);
		}
		public static final String maskLandline(String value) {
			value = parseLandline(value);
			if (validLandlineHyphen(value)) {
				String[] values = value.split(HYPHEN_SEPARATOR);
				return String.join(HYPHEN_SEPARATOR, values[0], values[1], mask(values[2], Constant.LANDLINE_MASK_LENGTH, Position.MASK_LAST));
			}
			return mask(value, Constant.LANDLINE_MASK_LENGTH, Position.MASK_LAST);
		}
		
		public static final boolean isLandlineShort(String value) {return Constant.LANDLINE_SHORT_PATTERN.matcher(value).matches();}
		public static final String parseLandlineShort(String value) {
			return isLandlineShort(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, value.length() - 4) : throwIllegalArgumentException(value, Type.Format.LANDLINE_SHORT);
		}
		public static final String maskLandlineShort(String value) {
			String[] values = parseLandlineShort(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], mask(values[1], Constant.LANDLINE_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final boolean isName(String value) {return validNameKor(value) || validNameEngSpace(value) || validNameEng(value);}
		private static final boolean validNameKor(String value) {return Constant.NAME_KOR_PATTERN.matcher(value).matches();}
		private static final boolean validNameEngSpace(String value) {return Constant.NAME_SPACE_ENG_PATTERN.matcher(value).matches() && validNameEng(onlyNumberEng(value));}
		private static final boolean validNameEng(String value) {return Constant.NAME_ENG_PATTERN.matcher(value).matches();}
		public static final String parseName(String value) {
			return isName(value) ? value : throwIllegalArgumentException(value, Type.Format.NAME);
		}
		public static final String maskName(String value) {
			value = parseName(value);
			if (validNameEngSpace(value)) {
				String[] values = value.split(SPACE_SEPARATOR);
				int size = values.length;
				value = values[0];
				for (int i=1; i<size; i++) {
					value += SPACE_SEPARATOR + mask(values[i], Constant.NAME_ENG_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.NAME_FIXED_MASK_OPTION);
				}
				return value;
			} else if (validNameEng(value)) {
				return mask(value, Constant.NAME_ENG_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.NAME_FIXED_MASK_OPTION);
			}
			return value.substring(0, 1) + mask(value.substring(1), value.length() == Constant.NAME_MINIMUM_LENGTH ? 0 : 1, Position.EXPOSE_LAST, Constant.NAME_FIXED_MASK_OPTION);
		}
		
		public static final boolean isNickName(String value) {return validNickNameSpace(value) || validNickName(value);}
		private static final boolean validNickNameSpace(String value) {return Constant.NICK_NAME_SPACE_PATTERN.matcher(value).matches() && validNickName(onlyText(value));}
		private static final boolean validNickName(String value) {return Constant.NICK_NAME_PATTERN.matcher(value).matches();}
		public static final String parseNickName(String value) {
			return isNickName(value) ? value : throwIllegalArgumentException(value, Type.Format.NICK_NAME);
		}
		public static final String maskNickName(String value) {
			value = parseNickName(value);
			if (validNickNameSpace(value)) {
				String[] values = value.split(SPACE_SEPARATOR);
				int size = values.length;
				value = mask(values[0], Constant.NICK_NAME_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.NICK_NAME_FIXED_MASK_OPTION);
				for (int i=1; i<size; i++) {
					value += SPACE_SEPARATOR + mask(values[i], Constant.NICK_NAME_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.NICK_NAME_FIXED_MASK_OPTION);
				}
				return value;
			}
			return mask(value, Constant.NICK_NAME_EXPOSE_LENGTH, Position.EXPOSE_START, Constant.NICK_NAME_FIXED_MASK_OPTION);
		}
		
		public static final boolean isZipCode(String value) {return Constant.ZIP_CODE_PATTERN.matcher(value).matches();}
		public static final String parseZipCode(String value) {
			return isZipCode(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 3) : throwIllegalArgumentException(value, Type.Format.ZIP_CODE);
		}
		public static final String maskZipCode(String value) {
			String[] values = parseZipCode(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], mask(values[1], Constant.ZIP_CODE_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final String parseAddress(String value) {
			return parseString(value);
		}
		public static final String maskAddress(String value) {
			return maskNumber(parseAddress(value));
		}
		
		public static final String parseAddressDetail(String value) {
			return parseString(value);
		}
		public static final String maskAddressDetail(String value) {
			return maskNumber(parseAddressDetail(value));
		}
		
		public static final boolean isLatitude(String value) {return Constant.LATITUDE_PATTERN.matcher(value).matches();}
		public static final String parseLatitude(String value) {
			return isLatitude(value) ? String.format(GPS_DECIMAL_FORMAT, Double.parseDouble(value)) : throwIllegalArgumentException(value, Type.Format.LATITUDE);
		}
		public static final String maskLatitude(String value) {
			String[] values = parseLatitude(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, values[0], mask(values[1], Constant.GPS_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final boolean isLongitude(String value) {return Constant.LONGITUDE_PATTERN.matcher(value).matches();}
		public static final String parseLongitude(String value) {
			return isLongitude(value) ? String.format(GPS_DECIMAL_FORMAT, Double.parseDouble(value)) : throwIllegalArgumentException(value, Type.Format.LONGITUDE);
		}
		public static final String maskLongitude(String value) {
			String[] values = parseLongitude(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, values[0], mask(values[1], Constant.GPS_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final boolean isGps(String value) {return Constant.GPS_PATTERN.matcher(value).matches();}
		public static final String parseGps(String value) {
			if (isGps(value)) {
				String[] values = value.split(COMMA_SEPARATOR);
				return String.join(COMMA_SEPARATOR, parseLatitude(values[0]), parseLongitude(values[1].trim()));
			}
			return throwIllegalArgumentException(value, Type.Format.GPS);
		}
		public static final String maskGps(String value) {
			String[] values = parseGps(value).split(COMMA_SEPARATOR);
			return String.join(COMMA_SEPARATOR, maskLatitude(values[0]), maskLongitude(values[1]));
		}
		
		public static final boolean isDeliveryTrackInvoice(String value) {return Constant.DELIVERY_TRACK_INVOICE_PATTERN.matcher(value).matches();}
		public static final String parseDeliveryTrackInvoice(String value) {
			return isDeliveryTrackInvoice(value) ? value : throwIllegalArgumentException(value, Type.Format.DELIVERY_TRACK_INVOICE);
		}
		public static final String maskDeliveryTrackInvoice(String value) {
			return mask(parseDeliveryTrackInvoice(value), Constant.DELIVERY_TRACK_INVOICE_MASK_LENGTH, Position.MASK_LAST);
		}
		
		public static final boolean isTaxInvoice(String value) {return Constant.TAX_INVOICE_PATTERN.matcher(value).matches();}
		public static final String parseTaxInvoice(String value) {
			if (isTaxInvoice(value)) {
				value = onlyNumberEng(value);
				throwDateTimeExceptionIfDateAfterToday(value.substring(0, 8), Type.Format.TAX_INVOICE);
				return concat(value, HYPHEN_SEPARATOR, 8, 8);
			}
			return throwIllegalArgumentException(value, Type.Format.TAX_INVOICE);
		}
		public static final String maskTaxInvoice(String value) {
			String[] values = parseTaxInvoice(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], maskNumber(values[1]), mask(values[2], Constant.TAX_INVOICE_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final boolean isIpAddress(String value) {return Constant.IP_ADDRESS_PATTERN.matcher(value).matches();}
		public static final String parseIpAddress(String value) {
			return isIpAddress(value) ? value : throwIllegalArgumentException(value, Type.Format.IP_ADDRESS);
		}
		public static final String maskIpAddress(String value) {
			String[] values = parseIpAddress(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, values[0], values[1], Constant.IP_ADDRESS_EXPOSE_THIRD ? values[2] : maskNumber(values[2]), maskNumber(values[3]));
		}
		
		public static final boolean isMacAddress(String value) {return Constant.MAC_ADDRESS_PATTERN.matcher(value).matches();}
		public static final String parseMacAddress(String value) {
			return isMacAddress(value) ? concat(onlyNumberEng(value), COLON_SEPARATOR, 2, 2, 2, 2, 2) : throwIllegalArgumentException(value, Type.Format.MAC_ADDRESS);
		}
		public static final String maskMacAddress(String value) {
			String[] values = parseMacAddress(value).split(COLON_SEPARATOR);
			return String.join(COLON_SEPARATOR, values[0], values[1], values[2], Constant.MAC_ADDRESS_EXPOSE_FOURTH ? values[3] : maskText(values[3]), maskText(values[4]), maskText(values[5]));
		}
		
		public static final boolean isUuid(String value) {return Constant.UUID_PATTERN.matcher(value).matches();}
		public static final String parseUuid(String value) {
			return isUuid(value) ? concat(onlyNumberEng(value), HYPHEN_SEPARATOR, 8, 4, 4, 4) : throwIllegalArgumentException(value, Type.Format.UUID);
		}
		public static final String maskUuid(String value) {
			String[] values = parseUuid(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, mask(values[0], Constant.UUID_MASK_LENGTH, Position.MASK_LAST), maskText(values[1]), maskText(values[2]), maskText(values[3]), mask(values[4], Constant.UUID_MASK_LENGTH, Position.MASK_START));
		}
		
		public static final boolean isResidentRegistration(String value) {return Constant.RESIDENT_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseResidentRegistration(String value) {
			if (isResidentRegistration(value)) {
				value = onlyNumber(value);
				throwDateTimeExceptionIfDateAfterToday(getYearPrefixBySex(value.substring(6, 7)) + value.substring(0, 6), Type.Format.RESIDENT_REGISTRATION);
				return concat(value, HYPHEN_SEPARATOR, 6);
			}
			return throwIllegalArgumentException(value, Type.Format.RESIDENT_REGISTRATION);
		}
		public static final String maskResidentRegistration(String value) {
			String[] values = parseResidentRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, mask(values[0], Constant.RESIDENT_REGISTRATION_MASK_LENGTH, Position.MASK_LAST), mask(values[1], 1, Position.EXPOSE_START));
		}
		
		public static final boolean isAlienRegistration(String value) {return Constant.ALIEN_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseAlienRegistration(String value) {
			if (isAlienRegistration(value)) {
				value = onlyNumber(value);
				throwDateTimeExceptionIfDateAfterToday(getYearPrefixBySex(value.substring(6, 7)) + value.substring(0, 6), Type.Format.ALIEN_REGISTRATION);
				return concat(value, HYPHEN_SEPARATOR, 6);
			}
			return throwIllegalArgumentException(value, Type.Format.ALIEN_REGISTRATION);
		}
		public static final String maskAlienRegistration(String value) {
			String[] values = parseAlienRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, mask(values[0], Constant.RESIDENT_REGISTRATION_MASK_LENGTH, Position.MASK_LAST), mask(values[1], 1, Position.EXPOSE_START));
		}
		
		public static final boolean isBusinessRegistration(String value) {return Constant.BUSINESS_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseBusinessRegistration(String value) {
			return isBusinessRegistration(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 3, 2) : throwIllegalArgumentException(value, Type.Format.BUSINESS_REGISTRATION);
		}
		public static final String maskBusinessRegistration(String value) {
			String[] values = parseBusinessRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.BUSINESS_REGISTRATION_EXPOSE_SECOND ? values[1] : maskNumber(values[1]), mask(values[2], 4, Position.MASK_START));
		}
		
		public static final boolean isCorporationRegistration(String value) {return Constant.CORPORATION_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseCorporationRegistration(String value) {
			return isCorporationRegistration(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 4, 2, 6) : throwIllegalArgumentException(value, Type.Format.CORPORATION_REGISTRATION);
		}
		public static final String maskCorporationRegistration(String value) {
			String[] values = parseCorporationRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.CORPORATION_REGISTRATION_EXPOSE_SECOND ? values[1] : maskNumber(values[1]), maskNumber(values[2]), maskNumber(values[3]));
		}
		
		public static final boolean isBirthDate(String value) {return Constant.BIRTH_DATE_PATTERN.matcher(value).matches();}
		public static final String parseBirthDate(String value) {
			if (isBirthDate(value)) {
				value = onlyNumber(value);
				throwDateTimeExceptionIfDateAfterToday(value, Type.Format.BIRTH_DATE);
				return concat(value, HYPHEN_SEPARATOR, 4, 2);
			}
			return throwIllegalArgumentException(value, Type.Format.BIRTH_DATE);
		}
		public static final String maskBirthDate(String value) {
			String[] values = parseBirthDate(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.BIRTH_DATE_EXPOSE_SECOND ? values[1] : maskNumber(values[1]), maskNumber(values[2]));
		}
		
		public static final boolean isBirthDateRegistration(String value) {return Constant.BIRTH_DATE_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseBirthDateRegistration(String value) {
			if (isBirthDateRegistration(value)) {
				value = onlyNumber(value);
				value = getYearPrefixBySex(value.substring(6)) + value.substring(0, 6);
				throwDateTimeExceptionIfDateAfterToday(value, Type.Format.BIRTH_DATE);
				return concat(value, HYPHEN_SEPARATOR, 4, 2);
			}
			return throwIllegalArgumentException(value, Type.Format.BIRTH_DATE_REGISTRATION);
		}
		public static final String maskBirthDateRegistration(String value) {
			String[] values = parseBirthDateRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.BIRTH_DATE_EXPOSE_SECOND ? values[1] : maskNumber(values[1]), maskNumber(values[2]));
		}
		
		public static final String parseAge(String value) {
			return getAge(parseBirthDate(value));
		}
		public static final String maskAge(String value) {
			return mask(parseAge(value), 1, Position.EXPOSE_START);
		}
		
		public static final String parseAgeInternational(String value) {
			return getAgeInternational(parseBirthDate(value));
		}
		public static final String maskAgeInternational(String value) {
			return mask(parseAgeInternational(value), 1, Position.EXPOSE_START);
		}
		
		public static final String parseAgeRegistration(String value) {
			return getAge(parseBirthDateRegistration(value));
		}
		public static final String maskAgeRegistration(String value) {
			return mask(parseAgeRegistration(value), 1, Position.EXPOSE_START);
		}
		
		public static final String parseAgeInternationalRegistration(String value) {
			return getAgeInternational(parseBirthDateRegistration(value));
		}
		public static final String maskAgeInternationalRegistration(String value) {
			return mask(parseAgeInternationalRegistration(value), 1, Position.EXPOSE_START);
		}
		
		public static final boolean isCarPlate(String value) {return Constant.CAR_PLATE_PATTERN.matcher(value).matches();}
		public static final String parseCarPlate(String value) {
			return isCarPlate(value) ? value : throwIllegalArgumentException(value, Type.Format.CAR_PLATE);
		}
		public static final String maskCarPlate(String value) {
			return mask(parseCarPlate(value), Constant.CAR_PLATE_MASK_LENGTH, Position.MASK_LAST);
		}
		
		public static final boolean isDriverRegistration(String value) {return Constant.DRIVER_REGISTRATION_PATTERN.matcher(value).matches();}
		public static final String parseDriverRegistration(String value) {
			return isDriverRegistration(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 2, 2, 6) : throwIllegalArgumentException(value, Type.Format.DRIVER_REGISTRATION);
		}
		public static final String maskDriverRegistration(String value) {
			String[] values = parseDriverRegistration(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.DRIVER_REGISTRATION_EXPOSE_SECOND ? values[1] : maskNumber(values[1]), maskNumber(values[2]), maskNumber(values[3]));
		}
		
		public static final boolean isCardNumber(String value) {return Constant.CARD_NUMBER_PATTERN.matcher(value).matches();}
		public static final String parseCardNumber(String value) {
			return isCardNumber(value) ? concat(onlyNumber(value), HYPHEN_SEPARATOR, 4, 4, 4) : throwIllegalArgumentException(value, Type.Format.CARD_NUMBER);
		}
		public static final String maskCardNumber(String value) {
			String[] values = parseCardNumber(value).split(HYPHEN_SEPARATOR);
			return String.join(HYPHEN_SEPARATOR, values[0], Constant.CARD_NUMBER_EXPOSE_SECOND_PREFIX ? mask(values[1], 2, Position.EXPOSE_START) : maskNumber(values[1]), maskNumber(values[2]), mask(values[3], Constant.CARD_NUMBER_MASK_LENGTH, Position.MASK_LAST));
		}
		
		public static final boolean isCardExpiredMmYy(String value) {return Constant.CARD_EXPIRED_MM_YY_PATTERN.matcher(value).matches();}
		public static final String parseCardExpiredMmYy(String value) {
			return isCardExpiredMmYy(value) ? concat(onlyNumber(value), SLASH_SEPARATOR, 2) : throwIllegalArgumentException(value, Type.Format.CARD_EXPIRED_MM_YY);
		}
		public static final String maskCardExpiredMmYy(String value) {
			String[] values = parseCardExpiredMmYy(value).split(SLASH_SEPARATOR);
			return String.join(SLASH_SEPARATOR, maskNumber(values[0]), Constant.CARD_EXPIRED_MM_YY_EXPOSE_LAST ? values[1] : maskNumber(values[1]));
		}
		
		public static final boolean isAccountNumber(String value) {return validAccountNumberHyphen(value) || validAccountNumber(value);}
		private static final boolean validAccountNumberHyphen(String value) {return Constant.ACCOUNT_NUMBER_HYPHEN_PATTERN.matcher(value).matches() && validAccountNumber(onlyNumber(value));}
		private static final boolean validAccountNumber(String value) {return Constant.ACCOUNT_NUMBER_PATTERN.matcher(value).matches();}
		public static final String parseAccountNumber(String value) {
			return isAccountNumber(value) ? value : throwIllegalArgumentException(value, Type.Format.ACCOUNT_NUMBER);
		}
		public static final String maskAccountNumber(String value) {
			value = parseAccountNumber(value);
			if (validAccountNumberHyphen(value)) {
				String[] values = value.split(HYPHEN_SEPARATOR);
				int size = values.length;
				value = values[0];
				for (int i=1; i<size; i++) {
					value += HYPHEN_SEPARATOR + (i == size - 1 ? mask(values[i], Constant.ACCOUNT_NUMBER_EXPOSE_LENGTH, Position.EXPOSE_LAST) : maskNumber(values[i]));
				}
				return value;
			}
			return value.substring(0, 3) + mask(value.substring(3), Constant.ACCOUNT_NUMBER_EXPOSE_LENGTH, Position.EXPOSE_LAST);
		}
		
		public static final boolean isPassportNumber(String value) {return Constant.PASSPORT_NUMBER_PATTERN.matcher(value).matches();}
		public static final String parsePassportNumber(String value) {
			return isPassportNumber(value) ? value : throwIllegalArgumentException(value, Type.Format.PASSPORT_NUMBER);
		}
		public static final String maskPassportNumber(String value) {
			return mask(parsePassportNumber(value), Constant.PASSPORT_NUMBER_MASK_LENGTH, Position.MASK_LAST);
		}
		
		public static final boolean isBarcode(String value) {return validBarcodeHyphen(value) || validBarcode(value);}
		private static final boolean validBarcodeHyphen(String value) {return Constant.BARCODE_HYPHEN_PATTERN.matcher(value).matches() && validBarcode(onlyNumber(value));}
		private static final boolean validBarcode(String value) {return Constant.BARCODE_PATTERN.matcher(value).matches();}
		public static final String parseBarcode(String value) {
			return isBarcode(value) ? value : throwIllegalArgumentException(value, Type.Format.BARCODE);
		}
		public static final String maskBarcode(String value) {
			value = parseBarcode(value);
			if (validBarcodeHyphen(value)) {
				String[] values = value.split(HYPHEN_SEPARATOR);
				return String.join(HYPHEN_SEPARATOR, values[0], Constant.BARCODE_SECOND_EXPOSE_INSTEAD_THIRD ? values[1] : maskNumber(values[1]), Constant.BARCODE_SECOND_EXPOSE_INSTEAD_THIRD ? maskNumber(values[2]) : values[2], maskNumber(values[3]));
			}
			return value.substring(0, 3) + (Constant.BARCODE_SECOND_EXPOSE_INSTEAD_THIRD ? value.substring(3, 7) + maskNumber(value.substring(7, 12)) : maskNumber(value.substring(3, 7)) + value.substring(7, 12)) + maskNumber(value.substring(12));
		}
		
		public static final boolean isDepartment(String value) {return Constant.DEPARTMENT_PATTERN.matcher(value).matches();}
		public static final String parseDepartment(String value) {
			return isDepartment(value) ? value : throwIllegalArgumentException(value, Type.Format.DEPARTMENT);
		}
		public static final String maskDepartment(String value) {
			value = parseDepartment(value);
			String suffix = getSuffix(value, Constant.DEPARTMENT_BOUNDS);
			return mask(value.substring(0, value.length() - suffix.length()), Constant.DEPARTMENT_EXPOSE_LENGTH, Position.EXPOSE_START) + suffix;
		}
		
		public static final boolean isSchool(String value) {return Constant.SCHOOL_PATTERN.matcher(value).matches();}
		public static final String parseSchool(String value) {
			return isSchool(value) ? value : throwIllegalArgumentException(value, Type.Format.SCHOOL);
		}
		public static final String maskSchool(String value) {
			value = parseSchool(value);
			String suffix = getSuffix(value, Constant.SCHOOL_BOUNDS);
			return mask(value.substring(0, value.length() - suffix.length()), Constant.SCHOOL_EXPOSE_LENGTH, Position.EXPOSE_START) + suffix;
		}
		
		public static final boolean isHeight(String value) {return Constant.HEIGHT_PATTERN.matcher(value).matches();}
		public static final double parseHeight(String value) {
			return isHeight(value) ? Double.parseDouble(onlyDouble(value)) : throwDoubleFormatException(value, Type.Format.HEIGHT);
		}
		public static final String formatHeight(String value) {
			return String.format(HEIGHT_DECIMAL_FORMAT, parseHeight(value)) + SPACE_SEPARATOR + Constant.HEIGHT_SUFFIX;
		}
		public static final String maskHeight(String value) {
			String[] values = formatHeight(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, mask(values[0], Constant.HEIGHT_EXPOSE_LENGTH, Position.EXPOSE_START), maskNumber(values[1]));
		}
		
		public static final boolean isWeight(String value) {return Constant.WEIGHT_PATTERN.matcher(value).matches();}
		public static final double parseWeight(String value) {
			return isWeight(value) ? Double.parseDouble(onlyDouble(value)) : throwDoubleFormatException(value, Type.Format.WEIGHT);
		}
		public static final String formatWeight(String value) {
			return String.format(WEIGHT_DECIMAL_FORMAT, parseWeight(value)) + SPACE_SEPARATOR + Constant.WEIGHT_SUFFIX;
		}
		public static final String maskWeight(String value) {
			String[] values = formatWeight(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, mask(values[0], Constant.WEIGHT_EXPOSE_LENGTH, Position.EXPOSE_START), maskNumber(values[1]));
		}
		
		public static final boolean isDosage(String value) {return Constant.DOSAGE_PATTERN.matcher(value).matches();}
		public static final double parseDosage(String value) {
			return isDosage(value) ? Double.parseDouble(onlyDouble(value)) : throwDoubleFormatException(value, Type.Format.DOSAGE);
		}
		public static final String formatDosage(String value) {
			return String.format(DOSAGE_DECIMAL_FORMAT, parseDosage(value)) + SPACE_SEPARATOR + Constant.DOSAGE_SUFFIX;
		}
		public static final String maskDosage(String value) {
			String[] values = formatDosage(value).split(DOT_ESCAPE_SEPARATOR);
			return String.join(DOT_SEPARATOR, mask(values[0], Constant.DOSAGE_EXPOSE_LENGTH, Position.EXPOSE_START), maskNumber(values[1]));
		}
		
		private static final String maskNumber(String value) {return NUMBER_PATTERN.matcher(value).replaceAll(MASK_AS);}
		private static final String maskText(String value) {return TEXT_PATTERN.matcher(value).replaceAll(MASK_AS);}
		private static final String mask(String value, int length, Position positionType) {return mask(value, length, positionType, false);}
		private static final String mask(String value, int length, Position positionType, boolean useFixedMask) {
			int size = value.length();
			int position = size - length;
			switch (positionType) {
				case EXPOSE_START:
					return length <= 0 ? useFixedMask ? FIXED_MASK_AS : maskText(value) : length >= size ? value : value.substring(0, length) + (useFixedMask ? FIXED_MASK_AS : maskText(value.substring(length)));
				case EXPOSE_LAST:
					return length <= 0 ? useFixedMask ? FIXED_MASK_AS : maskText(value) : length >= size ? value : (useFixedMask ? FIXED_MASK_AS : maskText(value.substring(0, position))) + value.substring(position);
				case MASK_START:
					return length <= 0 ? value : length >= size ? useFixedMask ? FIXED_MASK_AS : maskText(value) : (useFixedMask ? FIXED_MASK_AS : maskText(value.substring(0, length))) + value.substring(length);
				case MASK_LAST:
					return length <= 0 ? value : length >= size ? useFixedMask ? FIXED_MASK_AS : maskText(value) : value.substring(0, position) + (useFixedMask ? FIXED_MASK_AS : maskText(value.substring(position)));
				default:
					return value;
			}
		}
		
		private static final String removeComma(String value) {return COMMA_PATTERN.matcher(value).replaceAll(STRING_REPLACE_DEFAULT);}
		private static final String onlyDouble(String value) {return EXCLUDE_DOUBLE_PATTERN.matcher(value).replaceAll(STRING_REPLACE_DEFAULT);}
		private static final String onlyNumber(String value) {return EXCLUDE_NUMBER_PATTERN.matcher(value).replaceAll(STRING_REPLACE_DEFAULT);}
		private static final String onlyNumberEng(String value) {return EXCLUDE_NUMBER_ENG_PATTERN.matcher(value).replaceAll(STRING_REPLACE_DEFAULT);}
		private static final String onlyText(String value) {return EXCLUDE_TEXT_PATTERN.matcher(value).replaceAll(STRING_REPLACE_DEFAULT);}
		
		private static final String concat(String value, String separator, Integer... lengths) {
			int size = lengths.length;
			int start = 0;
			String[] values = new String[size + 1];
			for (int i=0; i<size; i++) {
				int length = lengths[i];
				values[i] = value.substring(start, start + length);
				start += length;
			}
			values[size] = value.substring(start);
			return String.join(separator, values);
		}
		private static final String getCheckSum(String value) {
			int sum = 0;
			for (char digit : value.toCharArray()) {
				sum += Character.getNumericValue(digit);
			}
			return String.valueOf(sum % 10);
		}
		private static final String parseCrypt(char c, List<String> boundList, Crypt cryptType) {
			switch (cryptType) {
				case ENCRYPT:
					return boundList.get(Character.getNumericValue(c));
				case DECRYPT:
					int index = boundList.indexOf(String.valueOf(c));
					if (index == -1) {
						throw new NullPointerException();			
					}
					return String.valueOf(index);
			}
			throw new NullPointerException();
		}
		private static final String getSuffix(String value, List<String> boundList) {
			for (String bound : boundList) {
				if (value.endsWith(bound)) {
					return bound;
				}
			}
			return STRING_RETURN_DEFAULT;
		}
		private static final String getYearPrefixBySex(String sex) {
			return Constant.RESIDENT_REGISTRATION_1900S_BOUNDS.contains(sex) || Constant.ALIEN_REGISTRATION_1900S_BOUNDS.contains(sex) ? YEAR_1900S_PREFIX : YEAR_2000S_PREFIX;
		}
		private static final String getAge(String value) {
			return String.valueOf(LocalDate.now().getYear() - LocalDate.parse(value, DATE_FORMAT_FORMATTER).getYear() + 1);
		}
		private static final String getAgeInternational(String value) {
			return String.valueOf(Period.between(LocalDate.parse(value, DATE_FORMAT_FORMATTER), LocalDate.now()).getYears());
		}
		
		private static final long throwLongFormatException(String value, Type.Format format) {throw value.trim().length() > 0 ? new NumberFormatException(getErrorMessage(format)) : new NullPointerException();}
		private static final double throwDoubleFormatException(String value, Type.Format format) {throw value.trim().length() > 0 ? new NumberFormatException(getErrorMessage(format)) : new NullPointerException();}
		private static final String throwIllegalArgumentException(String value, Type.Format format) {throw value.trim().length() > 0 ? new IllegalArgumentException(getErrorMessage(format)) : new NullPointerException();}
		private static final void throwDateTimeExceptionIfDateAfterToday(String value, Type.Format format) {
			try {
				if (LocalDate.parse(value, DATE_PARSE_FORMATTER).isAfter(LocalDate.now())) {
					throw new DateTimeException(getErrorMessage(format));
				}
			} catch (DateTimeException e) {
				throw new DateTimeException(getErrorMessage(format));
			}
		}
		private static final String getErrorMessage(Type.Format... formats) {return Type.getPatterns(formats).toString();}
	}
}