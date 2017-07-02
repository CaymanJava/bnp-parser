package pro.devlib.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import pro.devlib.exception.ParsePageException;

import java.util.HashMap;
import java.util.Map;

import static pro.devlib.util.Constants.*;

@Component
public class StartPageParser {

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

            result.put(SID, sid);
            result.put(FLOW_ID, flowId);
            result.put(STATE_ID, stateId);
            result.put(ACTION_TOKEN, actionToken);
            result.put(ACTION, action);
            result.put(BTN_NEXT, NEXT);

            return result;
        } catch (Exception e) {
            throw new ParsePageException(START_PAGE_PARSE_EXCEPTION_MESSAGE, e);
        }
    }
}
