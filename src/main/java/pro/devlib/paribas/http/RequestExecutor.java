package pro.devlib.paribas.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HttpMessage;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import pro.devlib.paribas.exception.ServiceTemporarilyUnavailableException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class RequestExecutor {

  private final CookieHandler cookieHandler;

  public RequestExecutor(CookieHandler cookieHandler) {
    this.cookieHandler = cookieHandler;
  }

  public String executeGetRequest(String url, String referer) throws IOException {
    HttpGet request = new HttpGet(url);
    CookieStore newCookieStore = new BasicCookieStore();
    HttpClient client = buildHttpClient(newCookieStore);

    setHeaders(request, referer);
    HttpResponse response = client.execute(request);

    log.info("Sending 'GET' request to URL : " + url);
    log.info("Response Code : " + response.getStatusLine().getStatusCode());

    cookieHandler.addAndMerge(newCookieStore.getCookies());

    return extractHtmlFromResponse(response);
  }

  public String executePostRequest(String url, String referer, Map<String, String> parameters) throws IOException {
    HttpPost request = new HttpPost(url);
    CookieStore newCookieStore = new BasicCookieStore();
    HttpClient client = buildHttpClient(newCookieStore);

    setHeaders(request, referer);
    setParametersToRequest(request, parameters);

    HttpResponse response = client.execute(request);

    log.info("Sending 'POST' request to URL : " + url);
    log.info("Response Code : " + response.getStatusLine().getStatusCode());

    cookieHandler.addAndMerge(newCookieStore.getCookies());

    return extractHtmlFromResponse(response);
  }

  private CloseableHttpClient buildHttpClient(CookieStore newCookieStore) {
    return HttpClientBuilder.create()
            .setDefaultCookieStore(newCookieStore)
            .setRedirectStrategy(new CustomRedirectStrategy())
            .build();
  }

  private void setParametersToRequest(HttpPost request, Map<String, String> parameters) throws UnsupportedEncodingException {
    List<NameValuePair> urlParameters = new ArrayList<>();
    parameters.forEach((n, v) -> urlParameters.add(new BasicNameValuePair(n, v)));
    request.setEntity(new UrlEncodedFormEntity(urlParameters));
  }

  private String extractHtmlFromResponse(HttpResponse response) throws IOException {
    String html = IOUtils.toString(response.getEntity().getContent(), getContentEncoding(response));
    checkHtmlFromResponse(html);
    return html;
  }

  private void checkHtmlFromResponse(String html) {
    if (html.contains("chwilowo niedostępne") || html.contains("nieoczekiwany błąd")) throw new ServiceTemporarilyUnavailableException();
  }

  private Charset getContentEncoding(HttpResponse response) {
    String charset = "";
    try {
      if (response.getEntity().getContentType() != null) {
        final HeaderElement values[] = response.getEntity().getContentType().getElements();
        if (values.length > 0) {
          final NameValuePair param = values[0].getParameterByName("charset");
          if (param != null) {
            charset = param.getValue();
          }
        }
      }
      return Charset.forName(charset.toUpperCase());
    } catch (Exception e) {
      return StandardCharsets.UTF_8;
    }
  }

  private void setHeaders(HttpMessage message, String referer) {
    message.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
    message.setHeader("Accept-Encoding", "gzip, deflate, br");
    message.setHeader("Accept-Language", "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4");
    message.setHeader("Cache-Control", "max-age=0");
    message.setHeader("Connection", "keep-alive");
    message.setHeader("Content-Type", "application/x-www-form-urlencoded");
    message.setHeader("Host", "planet.bgzbnpparibas.pl");
    message.setHeader("Origin", "https://login.bgzbnpparibas.pl");
    message.setHeader("Upgrade-Insecure-Requests", "1");
    message.setHeader("Referer", referer);
    message.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
    message.setHeader("Cookie", defaultCookie() + cookieHandler.getCookiesAsString());
  }

  private String defaultCookie() {
    return "smsessioncount=1; smsession=1497468966792; " +
            "ADRUM=s=1497472907924&r=https%3A%2F%2Fplanet.bgzbnpparibas.pl%2Fhades%2Fdo%2FLogout%3F0; " +
            "id=tps://www.bgzbnpparibas.pl/klienci-indywidualni; dcsource=direct; dcmedium=none; " +
            "dctraffic=direct / none; _ga=GA1.2.128065153.1497468967; _gid=GA1.2.995572744.1497468967; ";
  }

}
