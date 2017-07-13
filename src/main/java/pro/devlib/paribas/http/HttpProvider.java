package pro.devlib.paribas.http;


import org.xml.sax.SAXException;
import pro.devlib.paribas.exception.ServiceTemporarilyUnavailableException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class HttpProvider {

  public abstract String executeGetRequest(String url, String referer) throws IOException, SAXException;

  public abstract String executePostRequest(String url, String referer, Map<String, String> parameters) throws IOException, SAXException;

  protected Map<String, String> getDefaultHeaders(String referer) {
    Map<String, String> defaultHeaders = new HashMap<>();
    defaultHeaders.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    defaultHeaders.put("Accept-Encoding", "gzip, deflate, br");
    defaultHeaders.put("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    defaultHeaders.put("Cache-Control", "max-age=0");
    defaultHeaders.put("Connection", "keep-alive");
    defaultHeaders.put("Content-Type", "application/x-www-form-urlencoded");
    defaultHeaders.put("Host", "planet.bgzbnpparibas.pl");
    defaultHeaders.put("Origin", "https://login.bgzbnpparibas.pl");
    defaultHeaders.put("Upgrade-Insecure-Requests", "1");
    defaultHeaders.put("Referer", referer);
    defaultHeaders.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    return defaultHeaders;
  }

  protected String defaultCookie() {
    return "smsessioncount=1; smsession=1497468966792; " +
            "ADRUM=s=1497472907924&r=https%3A%2F%2Fplanet.bgzbnpparibas.pl%2Fhades%2Fdo%2FLogout%3F0; " +
            "id=tps://www.bgzbnpparibas.pl/klienci-indywidualni; dcsource=direct; dcmedium=none; " +
            "dctraffic=direct / none; _ga=GA1.2.128065153.1497468967; _gid=GA1.2.995572744.1497468967; ";
  }

  protected String checkAndReturnHtmlFromResponse(String html) {
    if (html.contains("chwilowo niedostępne") || html.contains("nieoczekiwany błąd")) throw new ServiceTemporarilyUnavailableException();
    return html;
  }

}
