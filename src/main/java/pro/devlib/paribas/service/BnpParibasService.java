package pro.devlib.paribas.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.dto.LoginResponseDto;
import pro.devlib.paribas.exception.IncorrectParameterException;
import pro.devlib.paribas.exception.ServiceUnavailableException;
import pro.devlib.paribas.http.*;
import pro.devlib.paribas.http.apache.ApacheHttpProvider;
import pro.devlib.paribas.http.apache.CookieHandler;
import pro.devlib.paribas.http.httpunit.HttpUnitProvider;
import pro.devlib.paribas.model.UserFinances;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class BnpParibasService {

  private final String login;
  private final String password;

  @Setter
  private boolean useHttpUnit = false;
  @Setter
  private int monthsToParse = 12;

  private StartPageService startPageService;
  private LoginService loginService;
  private PasswordService passwordService;
  private DesktopService desktopService;
  private TransactionService transactionService;

  public BnpParibasService(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public UserFinances execute() {
    try {
      init();
      checkMonthToParseParameter();
      Map<String, String> parametersFromStartPage = startPageService.getParametersFromStartPage();
      LoginResponseDto loginResponseDto = loginService.login(parametersFromStartPage, login);
      passwordService.providePassword(loginResponseDto, login, password);
      DesktopInfoDto desktopInformation = desktopService.getDesktopInformation();
      transactionService.setTransactionsToAccounts(desktopInformation, monthsToParse);
      return convertDesktopInfoToUserFinances(desktopInformation);
    } catch (IOException | SAXException e) {
      throw new ServiceUnavailableException(e);
    }
  }

  private void init() {
    HttpProvider httpProvider = initHttpProvider();
    this.startPageService = new StartPageService(httpProvider);
    this.loginService = new LoginService(httpProvider);
    this.passwordService = new PasswordService(httpProvider);
    this.desktopService = new DesktopService(httpProvider);
    this.transactionService = new TransactionService(httpProvider);
  }

  private HttpProvider initHttpProvider() {
    if (useHttpUnit) return new HttpUnitProvider();
    return new ApacheHttpProvider(new CookieHandler());
  }

  private void checkMonthToParseParameter() {
    if (monthsToParse <= 0) throw new IncorrectParameterException();
  }

  private UserFinances convertDesktopInfoToUserFinances(DesktopInfoDto desktopInformation) {
    return UserFinances
            .builder()
            .sumAmount(desktopInformation.getSumAmount())
            .accounts(desktopInformation.getAccounts())
            .cards(desktopInformation.getCards())
            .build();
  }

}
