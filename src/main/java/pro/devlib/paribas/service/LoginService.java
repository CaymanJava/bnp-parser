package pro.devlib.paribas.service;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import pro.devlib.paribas.dto.LoginResponseDto;
import pro.devlib.paribas.exception.BadCredentialsException;
import pro.devlib.paribas.http.HttpProvider;
import pro.devlib.paribas.parser.LoginPageParser;

import java.io.IOException;
import java.util.Map;

@Slf4j
class LoginService {

  private final static String LOGIN_URL = "https://login.bgzbnpparibas.pl/login/Redirect";
  private final static String SSO_URL = "https://login.bgzbnpparibas.pl/sso";
  private final HttpProvider httpProvider;
  private final LoginPageParser loginPageParser;

  LoginService(HttpProvider httpProvider) {
    this.httpProvider = httpProvider;
    this.loginPageParser = new LoginPageParser();
  }

  LoginResponseDto login(Map<String, String> parameters, String login) {
    log.info("Try to login on BNP Paribas website.");
    parameters.put("p_alias", login);
    String htmlFromSsoRequest = provideLogin(parameters);
    return loginPageParser.parseLoginPage(htmlFromSsoRequest);
  }

  private String provideLogin(Map<String, String> parameters) {
    try {
      httpProvider.executePostRequest(LOGIN_URL, "", parameters);
      return httpProvider.executeGetRequest(SSO_URL, LOGIN_URL);
    } catch (IOException | SAXException e) {
      throw new BadCredentialsException();
    }
  }

}
