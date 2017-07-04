package pro.devlib.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class StartPageParserTest {

  @Autowired
  private StartPageParser startPageParser;

  @Test
  public void parseStartPage() throws Exception {
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/startPage.html")));
    String expectedFlowId = "entry-5324dgL33qzcH4I3oQjALb";
    String expectedAction = "next";
    String expectedStateId = "LOGIN";
    String expectedActionToken = "JBxykBHVq8CDfZbbgivvl";
    String expectedSid = "I020023YMk5KrpDs7QXhzmEQ47UsEDcMBRccHiKe";
    String expectedBtnNext = "next";

    Map<String, String> params = startPageParser.parseStartPage(html);
    assertEquals(6, params.size());
    assertEquals(expectedFlowId, params.get("flow_id"));
    assertEquals(expectedAction, params.get("action"));
    assertEquals(expectedStateId, params.get("state_id"));
    assertEquals(expectedActionToken, params.get("action_token"));
    assertEquals(expectedActionToken, params.get("action_token"));
    assertEquals(expectedSid, params.get("sid"));
    assertEquals(expectedBtnNext, params.get("btn_next"));
  }
}