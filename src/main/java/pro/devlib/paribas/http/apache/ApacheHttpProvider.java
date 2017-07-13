package pro.devlib.paribas.http.apache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import pro.devlib.paribas.http.HttpProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ApacheHttpProvider extends HttpProvider {

  private final CookieHandler cookieHandler;

  public ApacheHttpProvider(CookieHandler cookieHandler) {
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
    return checkAndReturnHtmlFromResponse(html);
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
    Map<String, String> defaultHeaders = getDefaultHeaders(referer);
    defaultHeaders.forEach(message::setHeader);
    message.setHeader("Cookie", defaultCookie() + cookieHandler.getCookiesAsString());
  }

}
