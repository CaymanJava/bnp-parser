package pro.devlib.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.devlib.exception.ParsePageException;

import java.util.HashMap;
import java.util.Map;

@Component
public class StartPageParser {

  private final static String START_PAGE_PARSE_EXCEPTION_MESSAGE = "Couldn't parse start page.";

  public Map<String, String> parseStartPage(String html) {
    try {
      Map<String, String> result = new HashMap<>();
      Document pageHtml = Jsoup.parse(html);
      Elements loginForm = pageHtml.select("form[name=LoginAliasForm]");

      String sid = loginForm.select("input[name=sid]").attr("value");
      String flowId = loginForm.select("input[name=flow_id]").attr("value");
      String stateId = loginForm.select("input[name=state_id]").attr("value");
      String actionToken = loginForm.select("input[name=action_token]").attr("value");
      String action = loginForm.select("input[name=action]").attr("value");

      result.put("sid", sid);
      result.put("flow_id", flowId);
      result.put("state_id", stateId);
      result.put("action_token", actionToken);
      result.put("action", action);
      result.put("btn_next", "next");

      return result;
    } catch (Exception e) {
      throw new ParsePageException(START_PAGE_PARSE_EXCEPTION_MESSAGE, e);
    }
  }

}
