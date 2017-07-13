package pro.devlib.paribas.http.httpunit;


import com.meterware.httpunit.*;
import com.meterware.httpunit.cookies.CookieProperties;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import pro.devlib.paribas.http.HttpProvider;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class HttpUnitProvider extends HttpProvider {

  private WebConversation webConversation;

  public HttpUnitProvider() {
    this.webConversation = createWebConversation();
    setDefaultCookies();
  }

  @Override
  public String executeGetRequest(String url, String referer) throws IOException, SAXException {
    log.info("Sending 'GET' request to URL : " + url);
    GetMethodWebRequest request = new GetMethodWebRequest(url);
    setDefaultHeaders(request, referer);
    WebResponse response = webConversation.getResponse(request);
    return checkAndReturnHtmlFromResponse(response.getText());
  }

  @Override
  public String executePostRequest(String url, String referer, Map<String, String> parameters) throws IOException, SAXException {
    log.info("Sending 'POST' request to URL : " + url);
    WebRequest request = new PostMethodWebRequest(url);
    setDefaultHeaders(request, referer);
    parameters.forEach(request::setParameter);
    WebResponse response = webConversation.getResponse(request);
    return checkAndReturnHtmlFromResponse(response.getText());
  }

  private void setDefaultHeaders(WebRequest request, String referer) {
    Map<String, String> defaultHeaders = getDefaultHeaders(referer);
    defaultHeaders.forEach(request::setHeaderField);
  }

  private void setDefaultCookies() {
    Arrays.stream(defaultCookie().split("; "))
            .forEach(c -> {
              String[] split = c.trim().split("=");
              webConversation.putCookie(split[0].trim(), split[1].trim());
            });
  }

  private static WebConversation createWebConversation() {
    HttpUnitOptions.setDefaultCharacterSet("UTF8");
    HttpUnitOptions.setScriptingEnabled(false);
    CookieProperties.setDomainMatchingStrict(false);

    WebConversation agent = new WebConversation();

    ClientProperties properties = agent.getClientProperties();
    properties.setIframeSupported(false);
    properties.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    properties.setAutoRefresh(false);

    return agent;
  }

}
