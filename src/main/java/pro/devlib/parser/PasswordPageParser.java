package pro.devlib.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import pro.devlib.exception.ParsePageException;


@Component
@Slf4j
public class PasswordPageParser {

  private final static String SAML_URL_EXCEPTION_MESSAGE = "Exception during parsing Saml url from password page.";

  public String extractSamlUrlFromPasswordPage(String html) {
    log.info("Try to extract Saml url from HTML.");
    try {
      String samlUrl = Jsoup.parse(html)
              .select("body")
              .attr("onload")
              .replaceAll("window.top.location.href", "")
              .replaceFirst("=", "")
              .replaceAll("\"", "")
              .replaceAll(";", "")
              .trim();
      if (samlUrl.isEmpty()) throw new ParsePageException(SAML_URL_EXCEPTION_MESSAGE);
      return samlUrl;
    } catch (Exception e) {
      throw new ParsePageException(SAML_URL_EXCEPTION_MESSAGE, e);
    }
  }

}
