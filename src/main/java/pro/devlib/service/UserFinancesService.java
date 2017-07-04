package pro.devlib.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pro.devlib.dto.DesktopInfoDto;
import pro.devlib.dto.LoginResponseDto;
import pro.devlib.http.CookieHandler;
import pro.devlib.model.UserFinances;
import pro.devlib.service.paribas.*;

import java.util.Map;

@Service
@Scope("session")
@Slf4j
public class UserFinancesService {

  private final StartPageService startPageService;
  private final LoginService loginService;
  private final PasswordService passwordService;
  private final DesktopService desktopService;
  private final TransactionService transactionService;
  private final CookieHandler cookieHandler;

  @Autowired
  public UserFinancesService(StartPageService startPageService, LoginService loginService,
                             PasswordService passwordService, DesktopService desktopService,
                             TransactionService transactionService, CookieHandler cookieHandler) {
    this.startPageService = startPageService;
    this.loginService = loginService;
    this.passwordService = passwordService;
    this.desktopService = desktopService;
    this.transactionService = transactionService;
    this.cookieHandler = cookieHandler;
  }

  public UserFinances getUserFinances(String login, String password) {
    Map<String, String> parametersFromStartPage = startPageService.getParametersFromStartPage();
    LoginResponseDto loginResponseDto = loginService.login(parametersFromStartPage, login);
    passwordService.providePassword(loginResponseDto, login, password);
    DesktopInfoDto desktopInformation = desktopService.getDesktopInformation();
    transactionService.setTransactionsToAccounts(desktopInformation);
    cookieHandler.clear();
    return convertDesktopInfoToUserFinances(desktopInformation);
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
