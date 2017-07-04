package pro.devlib.service.paribas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class PasswordEncoderTest {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Test
  public void encodePassword() throws Exception {
    String login = "tratata";
    String password = "ar@31ga!v3";
    String loginMask = "c8ffffff855ae6f6f7ff";
    List<String> passwordSymbols = Arrays.asList("*", "", "", "", "*", "*", "*", "*", "*", "");
    String expectedEncodedPassword = "50cf80ea4bc05e09654eb38d4fc9bef2b78967d5";
    String encodedPassword = passwordEncoder.encodePassword(login, password, passwordSymbols, loginMask);
    assertEquals(expectedEncodedPassword, encodedPassword);
  }
}
