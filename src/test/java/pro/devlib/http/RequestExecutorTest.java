package pro.devlib.http;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class RequestExecutorTest {

    //This test will work only when the page "http://devlib.pro" is accessible.

    private final String urlForGetRequest = "http://devlib.pro/index";
    private final String urlForPostRequest = "http://devlib.pro/search";
    private final String keyword = "java";

    @Autowired
    private CookieHandler cookieHandler;

    @Autowired
    private RequestExecutor requestExecutor;

    @Test
    public void executeGetRequest() throws Exception {
        cookieHandler.clear();
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
        params.put("keyword", keyword);
        String html = requestExecutor.executePostRequest(urlForPostRequest, "", params);
        assertTrue(html.contains("<div class=\"product_sort\">"));
        assertTrue(html.contains("<ul class=\"product_view\">"));
        assertTrue(html.contains("<form id=\"more_book_div\" class=\"product_compare button\">"));
        cookieHandler.clear();
    }
}