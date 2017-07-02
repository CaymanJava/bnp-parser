package pro.devlib.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class PasswordEncoderTest {

    private String login = "tratata";
    private String password = "ar@31ga!v3";
    private String loginMask = "c8ffffff855ae6f6f7ff";
    private List<String> passwordSymbols = Arrays.asList("*", "", "", "", "*", "*", "*", "*", "*", "");
    private String expectedEncodedPassword = "50cf80ea4bc05e09654eb38d4fc9bef2b78967d5";

    @Test
    public void encodePassword() throws Exception {
        String encodedPassword = PasswordEncoder.encodePassword(login, password, passwordSymbols, loginMask);
        assertTrue(expectedEncodedPassword.equals(encodedPassword));
    }

}