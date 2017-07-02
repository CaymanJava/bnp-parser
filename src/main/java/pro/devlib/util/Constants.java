package pro.devlib.util;


import java.time.format.DateTimeFormatter;

public class Constants {
    final static String HEX_CHARS = "0123456789abcdef";

    public final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public final static String EXECUTE_REQUEST_EXCEPTION_MESSAGE = "Exception during executing request.";
    public final static String MASK_PARSE_EXCEPTION_MESSAGE = "Exception, during parsing password mask";
    public final static String EMPTY_FORM_EXCEPTION_MESSAGE = "Couldn't find mask in HTML.";
    public final static String HTML_EXTRACT_EXCEPTION_MESSAGE = "Exception during extract HTML from response.";
    public final static String UNSUPPORTED_REQUEST_PARAM_EXCEPTION_MESSAGE = "Exception during adding parameters to request.";
    public final static String START_PAGE_PARSE_EXCEPTION_MESSAGE = "Couldn't parse start page.";
    public final static String DESKTOP_PAGE_PARSE_EXCEPTION_MESSAGE = "Couldn't parse desktop page.";
    public final static String STATEMENT_PAGE_PARSE_EXCEPTION_MESSAGE = "Couldn't parse statement page.";
    public final static String SAML_URL_EXCEPTION_MESSAGE = "Exception during parsing Saml url from password page.";
    public final static String EMPTY = "";
    public final static String SPACE = " ";
    public final static String COMA = ",";
    public final static String POINT = ".";
    public final static String SID = "sid";
    public final static String FLOW_ID = "flow_id";
    public final static String STATE_ID = "state_id";
    public final static String ACTION_TOKEN = "action_token";
    public final static String ACTION = "action";
    public final static String P_MASK = "p_mask";
    public final static String P_PASS_MASKED_BIS = "p_passmasked_bis";
    public final static String BTN_NEXT = "btn_next";
    public final static String NEXT = "next";
    public final static String PASS_FIELD = "PASSFIELD";
    public final static String RND = "rnd";
    public final static String ACC_DETAILS = "ACC_DETAILS#";
    public final static String WHICH_ACCOUNTS_LIST = "whitchAccountsList";
    public final static String ACCOUNTS = "accounts";
    public final static String TASK = "task";
    public final static String EXECUTE = "EXECUTE";
    public final static String ASCENDING = "ascending";
    public final static String TRUE = "true";
    public final static String FALSE = "false";
    public final static String METHOD_NAME = "methodName";
    public final static String CURRENT_POSITION = "currPosition";
    public final static String ZERO = "0";
    public final static String P_ACTUAL_TEMPLATE_ID = "p_actual_template_id";
    public final static String P_SYS_DATE = "p_sys_date";
    public final static String P_TEMPLATE_ID = "p_template_id";
    public final static String M_HIDE_OVERNIGHT = "m_hide_overnight";
    public final static String SIZE_PER_WINDOWS = "sizePerWindow";
    public final static String THOUSAND = "1000";
}
