package pro.devlib.paribas.http.httpunit;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.cookies.CookieProperties;

class WebConversationFactory {

  static WebConversation createWebConversation() {
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
