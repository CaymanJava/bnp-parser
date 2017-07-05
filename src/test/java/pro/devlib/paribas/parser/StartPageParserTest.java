package pro.devlib.paribas.parser;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

public class StartPageParserTest {

  @Test
  public void parseStartPage() throws Exception {
    StartPageParser startPageParser = new StartPageParser();
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