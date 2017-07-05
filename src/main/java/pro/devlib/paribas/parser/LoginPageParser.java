package pro.devlib.paribas.parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import pro.devlib.paribas.dto.LoginResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class LoginPageParser {

  public LoginResponseDto parseLoginPage(String html) {
    Document ssoHtml = Jsoup.parse(html);
    Elements maskLoginForm = ssoHtml.select("form[name=MaskLoginForm]");
    String flowId = maskLoginForm.select("input[name=flow_id]").attr("value");
    String stateId = maskLoginForm.select("input[name=state_id]").attr("value");
    String actionToken = maskLoginForm.select("input[name=action_token]").attr("value");
    String action = maskLoginForm.select("input[name=action]").attr("value");
    List<String> passwordSymbols = getPasswordSymbols(maskLoginForm);
    String loginMask = getLoginMask(ssoHtml);
    String sid = maskLoginForm.select("input[name=sid]").attr("value");
    return LoginResponseDto
            .builder()
            .passwordSymbols(passwordSymbols)
            .flowId(flowId)
            .stateId(stateId)
            .actionToken(actionToken)
            .action(action)
            .sid(sid)
            .loginMask(loginMask)
            .build();
  }

  private static List<String> getPasswordSymbols(Elements elements) {
    Elements passwordElements = elements.select("input[type=password]");
    return passwordElements.stream().map(e -> e.attr("value")).collect(Collectors.toList());
  }

  private String getLoginMask(Document ssoHtml) {
    Elements scripts = ssoHtml.select("script");
    for (Element element : scripts) {
      String text = element.toString();
      if (text.contains("FILL_IN_PASSWORD_FIELDS_ALERT") && text.contains("loginMask =")) {
        return findLoginMaskInJavaScript(text);
      }
    }
    return "";
  }

  private String findLoginMaskInJavaScript(String javascript) {
    return javascript.split("loginMask =")[1].split(";")[0].replaceAll("\'", "").trim();
  }

}
