package pro.devlib.paribas.service;


import lombok.extern.slf4j.Slf4j;
import pro.devlib.paribas.dto.LoginResponseDto;
import pro.devlib.paribas.exception.BadCredentialsException;
import pro.devlib.paribas.http.HttpProvider;
import pro.devlib.paribas.parser.PasswordPageParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class PasswordService {

  private final static String SSO_URL = "https://login.bgzbnpparibas.pl/sso";
  private final static String DISPATCHER_APP_URL = "https://planet.bgzbnpparibas.pl/hades/do/DispatcherApp";
  private final static String WELCOME_MESSAGE_URL = "https://planet.bgzbnpparibas.pl/hades/do/WelcomeMessage";
  private final static String REDIRECT_SSO_URL = "https://planet.bgzbnpparibas.pl/retail/RedirectSSO?P_RELOAD=Y";
  private final static String INDEX_URL = "https://planet.bgzbnpparibas.pl/retail/index.jsp";
  private final HttpProvider httpProvider;
  private final PasswordPageParser passwordPageParser;
  private final PasswordEncoder passwordEncoder;

  PasswordService(HttpProvider httpProvider) {
    this.httpProvider = httpProvider;
    this.passwordPageParser = new PasswordPageParser();
    this.passwordEncoder = new PasswordEncoder();
  }

  void providePassword(LoginResponseDto loginResponseDto, String login, String password) {
    log.info("Start providing password on the page.");
    String encodedPassword = passwordEncoder.encodePassword(login, password,
            loginResponseDto.getPasswordSymbols(), loginResponseDto.getLoginMask());
    log.info("Password has been encoded.");
    Map<String, String> parameters = createParamMap(loginResponseDto, encodedPassword);
    providePassword(parameters);
  }

  private void providePassword(Map<String, String> parameters)  {
    try {
      String htmlFromPasswordPageResponse = httpProvider.executePostRequest(SSO_URL, SSO_URL, parameters);
      String samlUrl = passwordPageParser.extractSamlUrlFromPasswordPage(htmlFromPasswordPageResponse);

      httpProvider.executeGetRequest(samlUrl, SSO_URL);
      httpProvider.executeGetRequest(DISPATCHER_APP_URL, SSO_URL);
      httpProvider.executeGetRequest(WELCOME_MESSAGE_URL, DISPATCHER_APP_URL);
      httpProvider.executeGetRequest(REDIRECT_SSO_URL, DISPATCHER_APP_URL);
      httpProvider.executeGetRequest(INDEX_URL, "");
    } catch (Exception e) {
      throw new BadCredentialsException();
    }
  }

  private Map<String, String> createParamMap(LoginResponseDto loginResponseDto, String encodedPassword) {
    Map<String, String> parameters = getPasswordSymbolsMap(loginResponseDto.getPasswordSymbols());

    parameters.put("sid", loginResponseDto.getSid());
    parameters.put("flow_id", loginResponseDto.getFlowId());
    parameters.put("state_id", loginResponseDto.getStateId());
    parameters.put("action_token", loginResponseDto.getActionToken());
    parameters.put("action", loginResponseDto.getAction());
    parameters.put("p_mask", loginResponseDto.getLoginMask());
    parameters.put("p_passmasked_bis", encodedPassword);

    return parameters;
  }

  private Map<String, String> getPasswordSymbolsMap(List<String> passwordSymbols) {
    Map<String, String> result = new HashMap<>();
    for (int i = 0; i < passwordSymbols.size(); i++) {
      result.put("PASSFIELD" + (i + 1), passwordSymbols.get(i));
    }
    return result;
  }

}
