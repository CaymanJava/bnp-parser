package pro.devlib.parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class StartPageParserTest {

    @Autowired
    private StartPageParser startPageParser;
    private String html;
    private String expectedFlowId;
    private String expectedAction;
    private String expectedStateId;
    private String expectedActionToken;
    private String expectedSid;
    private String expectedBtnNext;

    @Before
    public void init() throws Exception {
        html = new String(Files.readAllBytes(Paths.get("src/test/resources/startPage.html")));
        expectedFlowId = "entry-5324dgL33qzcH4I3oQjALb";
        expectedAction = "next";
        expectedStateId = "LOGIN";
        expectedActionToken = "JBxykBHVq8CDfZbbgivvl";
        expectedSid = "I020023YMk5KrpDs7QXhzmEQ47UsEDcMBRccHiKe";
        expectedBtnNext = "next";
    }

    @Test
    public void parseStartPage() throws Exception {
        Map<String, String> params = startPageParser.parseStartPage(html);
        assertTrue(params.size() == 6);
        assertTrue(params.get("flow_id").equals(expectedFlowId));
        assertTrue(params.get("action").equals(expectedAction));
        assertTrue(params.get("state_id").equals(expectedStateId));
        assertTrue(params.get("action_token").equals(expectedActionToken));
        assertTrue(params.get("sid").equals(expectedSid));
        assertTrue(params.get("btn_next").equals(expectedBtnNext));
    }
}