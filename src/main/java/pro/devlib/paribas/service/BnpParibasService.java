package pro.devlib.paribas.service;

import lombok.extern.slf4j.Slf4j;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.dto.LoginResponseDto;
import pro.devlib.paribas.exception.ServiceUnavailableException;
import pro.devlib.paribas.http.CookieHandler;
import pro.devlib.paribas.http.RequestExecutor;
import pro.devlib.paribas.model.UserFinances;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class BnpParibasService {

  private final String login;
  private final String password;

  private StartPageService startPageService;
  private LoginService loginService;
  private PasswordService passwordService;
  private DesktopService desktopService;
  private TransactionService transactionService;
  private CookieHandler cookieHandler;

  public BnpParibasService(String login, String password) {
    this.login = login;
    this.password = password;
    init();
  }

  public UserFinances execute() {
    try {
      Map<String, String> parametersFromStartPage = startPageService.getParametersFromStartPage();
      LoginResponseDto loginResponseDto = loginService.login(parametersFromStartPage, login);
      passwordService.providePassword(loginResponseDto, login, password);
      DesktopInfoDto desktopInformation = desktopService.getDesktopInformation();
      transactionService.setTransactionsToAccounts(desktopInformation);
      cookieHandler.clear();
      return convertDesktopInfoToUserFinances(desktopInformation);
    } catch (IOException e) {
      throw new ServiceUnavailableException(e);
    }
  }

  private void init() {
    this.cookieHandler = new CookieHandler();
    RequestExecutor requestExecutor = new RequestExecutor(cookieHandler);
    this.startPageService = new StartPageService(requestExecutor);
    this.loginService = new LoginService(requestExecutor);
    this.passwordService = new PasswordService(requestExecutor);
    this.desktopService = new DesktopService(requestExecutor);
    this.transactionService = new TransactionService(requestExecutor);
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
