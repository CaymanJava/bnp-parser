package pro.devlib.service.paribas;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.dto.LoginResponseDto;
import pro.devlib.parser.PasswordPageParser;
import pro.devlib.http.RequestExecutor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Scope("session")
@Slf4j
public class PasswordService {

  private final String ssoUrl;
  private final String dispatcherAppUrl;
  private final String welcomeMessageUrl;
  private final String redirectSsoUrl;
  private final String indexUrl;
  private final RequestExecutor requestExecutor;
  private final PasswordPageParser passwordPageParser;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public PasswordService(@Value("${sso.url}") String ssoUrl,
                         @Value("${dispatcher.app.url}") String dispatcherAppUrl,
                         @Value("${welcome.message.url}") String welcomeMessageUrl,
                         @Value("${redirect.sso.url}") String redirectSsoUrl,
                         @Value("${index.url}") String indexUrl,
                         RequestExecutor requestExecutor, PasswordPageParser passwordPageParser,
                         PasswordEncoder passwordEncoder) {
    this.ssoUrl = ssoUrl;
    this.dispatcherAppUrl = dispatcherAppUrl;
    this.welcomeMessageUrl = welcomeMessageUrl;
    this.redirectSsoUrl = redirectSsoUrl;
    this.indexUrl = indexUrl;
    this.requestExecutor = requestExecutor;
    this.passwordPageParser = passwordPageParser;
    this.passwordEncoder = passwordEncoder;
  }

  public void providePassword(LoginResponseDto loginResponseDto, String login, String password) {
    log.info("Start providing password on the page.");
    String encodedPassword = passwordEncoder.encodePassword(login, password,
            loginResponseDto.getPasswordSymbols(), loginResponseDto.getLoginMask());
    log.info("Password has been encoded.");
    Map<String, String> parameters = createParamMap(loginResponseDto, encodedPassword);
    String htmlFromPasswordPageResponse = requestExecutor.executePostRequest(ssoUrl, ssoUrl, parameters);
    String samlUrl = passwordPageParser.extractSamlUrlFromPasswordPage(htmlFromPasswordPageResponse);

    requestExecutor.executeGetRequest(samlUrl, ssoUrl);
    requestExecutor.executeGetRequest(dispatcherAppUrl, ssoUrl);
    requestExecutor.executeGetRequest(welcomeMessageUrl, dispatcherAppUrl);
    requestExecutor.executeGetRequest(redirectSsoUrl, dispatcherAppUrl);
    requestExecutor.executeGetRequest(indexUrl, "");
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
