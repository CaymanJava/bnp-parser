package pro.devlib.paribas.service;

import org.junit.Ignore;
import org.junit.Test;
import pro.devlib.paribas.exception.BadCredentialsException;
import pro.devlib.paribas.exception.IncorrectParameterException;
import pro.devlib.paribas.model.Account;
import pro.devlib.paribas.model.UserFinances;

import static junit.framework.TestCase.assertTrue;

public class BnpParibasServiceTest {

  // To run those tests fill in login and password.
  // Make sure that user has accounts, cards and every account has at least one transaction.

  @Test
  @Ignore
  public void getUserFinancesWithHttpUnit() throws Exception {
    getUserFinances(true);
  }

  @Test
  @Ignore
  public void getUserFinancesWithApache() throws Exception {
    getUserFinances(false);
  }

  @Test(expected = BadCredentialsException.class)
  public void badCredentialsTest() throws Exception {
    String login = "blabla";
    String password = "blabla123!";
    BnpParibasService bnpParibasService = new BnpParibasService(login, password);
    bnpParibasService.execute();
  }

  @Test(expected = IncorrectParameterException.class)
  public void badMonthsToParseParameterTest() throws Exception {
    String login = "blabla";
    String password = "blabla123!";
    BnpParibasService bnpParibasService = new BnpParibasService(login, password);
    bnpParibasService.setMonthsToParse(-10);
    bnpParibasService.execute();
  }

  private void getUserFinances(boolean useHttpUnit) throws Exception {
    String login = "";
    String password = "";
    BnpParibasService bnpParibasService = new BnpParibasService(login, password);
    bnpParibasService.setUseHttpUnit(useHttpUnit);
    bnpParibasService.setMonthsToParse(6);
    UserFinances userFinances = bnpParibasService.execute();

    assertTrue(userFinances != null);
    assertTrue(userFinances.getAccounts() != null);
    assertTrue(userFinances.getAccounts().size() > 0);
    assertTrue(userFinances.getCards().size() > 0);

    for (Account account : userFinances.getAccounts()) {
      assertTrue(account.getTransactions().size() > 0);
    }
  }

}
