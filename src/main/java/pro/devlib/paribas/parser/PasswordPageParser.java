package pro.devlib.paribas.parser;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;

@Slf4j
public class PasswordPageParser {

  public String extractSamlUrlFromPasswordPage(String html) {
    log.info("Try to extract Saml url from HTML.");
    return Jsoup.parse(html)
            .select("body")
            .attr("onload")
            .replaceAll("window.top.location.href", "")
            .replaceFirst("=", "")
            .replaceAll("\"", "")
            .replaceAll(";", "")
            .trim();
  }

}
