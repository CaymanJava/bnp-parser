package pro.devlib.parser;

import org.junit.Before;
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

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class LoginPageParserTest {

    @Autowired
    private LoginPageParser loginPageParser;
    private String html;
    private List<String> expectedPasswordSymbols;
    private String expectedFlowId;
    private String expectedStateId;
    private String expectedActionToken;
    private String expectedAction;
    private String expectedSid;
    private String expectedLoginMask;

    @Before
    public void init() throws Exception {
        html = new String(Files.readAllBytes(Paths.get("src/test/resources/loginPage.html")));
        expectedPasswordSymbols = Arrays.asList("*", "", "*", "*", "*", "", "*", "*", "", "");
        expectedFlowId = "sso-H6ZhmbHiPQczHjgWUfYBAh";
        expectedStateId = "PWD";
        expectedActionToken = "h87HLqTstixXr1ADaQrHGb";
        expectedAction = "next";
        expectedSid = "I020023CiIvoHxNAR7WqyaoPSkZQ9fxcnOHZBvW0";
        expectedLoginMask = "31ff90d28eff395bffff";
    }

    @Test
    public void parseLoginPage() throws Exception {
        LoginResponseDto loginResponseDto = loginPageParser.parseLoginPage(html);
        assertTrue(loginResponseDto.getPasswordSymbols().size() == 10);
        assertTrue(loginResponseDto.getPasswordSymbols().equals(expectedPasswordSymbols));
        assertTrue(loginResponseDto.getFlowId().equals(expectedFlowId));
        assertTrue(loginResponseDto.getStateId().equals(expectedStateId));
        assertTrue(loginResponseDto.getActionToken().equals(expectedActionToken));
        assertTrue(loginResponseDto.getAction().equals(expectedAction));
        assertTrue(loginResponseDto.getSid().equals(expectedSid));
        assertTrue(loginResponseDto.getLoginMask().equals(expectedLoginMask));
    }
}