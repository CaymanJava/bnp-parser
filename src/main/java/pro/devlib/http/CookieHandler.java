package pro.devlib.http;


import org.apache.http.cookie.Cookie;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Scope("session")
public class CookieHandler {

  private Map<String, Cookie> cookies;

  public CookieHandler() {
    this.cookies = new HashMap<>();
  }

  void addAndMerge(List<Cookie> newCookies) {
    newCookies.forEach(c -> cookies.put(c.getName(), c));
  }

  String getCookiesAsString() {
    StringBuilder result = new StringBuilder();
    for (Cookie cookie : cookies.values()) {
      result.append(cookie.getName());
      result.append("=");
      result.append(cookie.getValue());
      result.append("; ");
    }
    return result.toString();
  }

  public void clear() {
    cookies.clear();
  }

}
