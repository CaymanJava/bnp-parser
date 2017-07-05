package pro.devlib.paribas.parser;

import org.junit.Test;
import pro.devlib.paribas.dto.LoginResponseDto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class LoginPageParserTest {

  @Test
  public void parseLoginPage() throws Exception {
    LoginPageParser loginPageParser = new LoginPageParser();
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
