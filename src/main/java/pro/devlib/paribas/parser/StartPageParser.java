package pro.devlib.paribas.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class StartPageParser {

  public Map<String, String> parseStartPage(String html) {
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
  }

}
