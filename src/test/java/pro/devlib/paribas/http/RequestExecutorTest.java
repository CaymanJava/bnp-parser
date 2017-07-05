package pro.devlib.paribas.http;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

public class RequestExecutorTest {

  //This test will work only when the page "http://devlib.pro" is accessible.

  private CookieHandler cookieHandler;
  private RequestExecutor requestExecutor;

  @Before
  public void init() {
    this.cookieHandler = new CookieHandler();
    this.requestExecutor = new RequestExecutor(cookieHandler);
  }

  @Test
  public void executeGetRequest() throws Exception {
    cookieHandler.clear();
    String urlForGetRequest = "http://devlib.pro/index";
    String html = requestExecutor.executeGetRequest(urlForGetRequest, "");
    assertTrue(html.contains("<title>DevLib. The Best Choice on the Web.</title>"));
    assertTrue(html.contains("<a href=\"/\" id=\"main_page\">"));
    assertTrue(html.contains("<a href=\"publishers\" id=\"publishers_page\">"));
    assertTrue(html.contains("<label for=\"search_query_block\">"));
    assertTrue(html.contains("<div id=\"categories_block_left\" class=\"block\">"));
    cookieHandler.clear();
  }

  @Test
  public void executePostRequest() throws Exception {
    cookieHandler.clear();
    Map<String, String> params = new HashMap<>();
    String keyword = "java";
    params.put("keyword", keyword);
    String urlForPostRequest = "http://devlib.pro/search";
    String html = requestExecutor.executePostRequest(urlForPostRequest, "", params);
    assertTrue(html.contains("<div class=\"product_sort\">"));
    assertTrue(html.contains("<ul class=\"product_view\">"));
    assertTrue(html.contains("<form id=\"more_book_div\" class=\"product_compare button\">"));
    cookieHandler.clear();
  }

}
