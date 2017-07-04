package pro.devlib.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.dto.LoginResponseDto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class LoginPageParserTest {

  @Autowired
  private LoginPageParser loginPageParser;

  @Test
  public void parseLoginPage() throws Exception {
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/loginPage.html")));
    List<String> expectedPasswordSymbols = Arrays.asList("*", "", "*", "*", "*", "", "*", "*", "", "");
    String expectedFlowId = "sso-H6ZhmbHiPQczHjgWUfYBAh";
    String expectedStateId = "PWD";
    String expectedActionToken = "h87HLqTstixXr1ADaQrHGb";
    String expectedAction = "next";
    String expectedSid = "I020023CiIvoHxNAR7WqyaoPSkZQ9fxcnOHZBvW0";
    String expectedLoginMask = "31ff90d28eff395bffff";

    LoginResponseDto loginResponseDto = loginPageParser.parseLoginPage(html);
    assertEquals(10, loginResponseDto.getPasswordSymbols().size());
    assertEquals(expectedPasswordSymbols, loginResponseDto.getPasswordSymbols());
    assertEquals(expectedFlowId, loginResponseDto.getFlowId());
    assertEquals(expectedStateId, loginResponseDto.getStateId());
    assertEquals(expectedActionToken, loginResponseDto.getActionToken());
    assertEquals(expectedAction, loginResponseDto.getAction());
    assertEquals(expectedSid, loginResponseDto.getSid());
    assertEquals(expectedLoginMask, loginResponseDto.getLoginMask());
  }
}
