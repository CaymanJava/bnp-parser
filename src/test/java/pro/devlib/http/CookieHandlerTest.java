package pro.devlib.http;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.util.Arrays;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class CookieHandlerTest {
    private final Cookie cookie1 = new BasicClientCookie("key1", "value1");
    private final Cookie cookie2 = new BasicClientCookie("key2", "value2");
    private final Cookie cookie3 = new BasicClientCookie("key3", "value3");
    private final Cookie cookie4 = new BasicClientCookie("key4", "value4");
    private final Cookie cookie5 = new BasicClientCookie("key5", "value5");
    private final Cookie cookie6 = new BasicClientCookie("key1", "newValue1");
    private final Cookie cookie7 = new BasicClientCookie("key2", "newValue2");


    @Autowired
    private CookieHandler cookieHandler;



    @Test
    public void addMergeAndGetTest() throws Exception {
        cookieHandler.addAndMerge(Arrays.asList(cookie1, cookie2, cookie3));
        cookieHandler.addAndMerge(Arrays.asList(cookie4, cookie5, cookie6, cookie7));

        String cookieAsString = cookieHandler.getCookiesAsString();

        assertTrue(cookieAsString.contains("key1=newValue1"));
        assertTrue(cookieAsString.contains("key2=newValue2"));
        assertTrue(cookieAsString.contains("key3=value3"));
        assertTrue(cookieAsString.contains("key4=value4"));
        assertTrue(cookieAsString.contains("key5=value5"));
        assertFalse(cookieAsString.contains("key1=value1"));
        assertFalse(cookieAsString.contains("key2=value2"));

        cookieHandler.clear();
    }

    @Test
    public void clear() throws Exception {
        cookieHandler.addAndMerge(Arrays.asList(cookie1, cookie2, cookie3));
        cookieHandler.clear();
        assertTrue(cookieHandler.getCookiesAsString().isEmpty());
    }
}