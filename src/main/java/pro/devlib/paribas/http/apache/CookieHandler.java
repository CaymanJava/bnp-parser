package pro.devlib.paribas.http.apache;


import org.apache.http.cookie.Cookie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

  void clear() {
    cookies.clear();
  }

}
