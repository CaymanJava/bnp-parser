package pro.devlib.paribas.service;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class PasswordEncoderTest {

  @Test
  public void encodePassword() throws Exception {
    PasswordEncoder passwordEncoder = new PasswordEncoder();
    String login = "tratata";
    String password = "ar@31ga!v3";
    String loginMask = "c8ffffff855ae6f6f7ff";
    List<String> passwordSymbols = Arrays.asList("*", "", "", "", "*", "*", "*", "*", "*", "");
    String expectedEncodedPassword = "50cf80ea4bc05e09654eb38d4fc9bef2b78967d5";
    String encodedPassword = passwordEncoder.encodePassword(login, password, passwordSymbols, loginMask);
    assertEquals(expectedEncodedPassword, encodedPassword);
  }

}
