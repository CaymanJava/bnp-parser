package pro.devlib.paribas.service;

import org.junit.Ignore;
import org.junit.Test;
import pro.devlib.paribas.exception.BadCredentialsException;
import pro.devlib.paribas.model.Account;
import pro.devlib.paribas.model.UserFinances;

import static junit.framework.TestCase.assertTrue;

public class BnpParibasServiceTest {

  // To run this test fill in login and password.
  // Make sure that user has accounts, cards and every account has at least one transaction.

  @Test
  @Ignore
  public void getUserFinances() throws Exception {
    String login = "";
    String password = "";
    BnpParibasService bnpParibasService = new BnpParibasService(login, password);
    UserFinances userFinances = bnpParibasService.execute();

    assertTrue(userFinances != null);
    assertTrue(userFinances.getAccounts() != null);
    assertTrue(userFinances.getAccounts().size() > 0);
    assertTrue(userFinances.getCards().size() > 0);

    for (Account account : userFinances.getAccounts()) {
      assertTrue(account.getTransactions().size() > 0);
    }
  }

  @Test(expected = BadCredentialsException.class)
  public void badCredentialTest() throws Exception {
    String login = "blabla";
    String password = "blabla123!";
    BnpParibasService bnpParibasService = new BnpParibasService(login, password);
    UserFinances userFinances = bnpParibasService.execute();
  }

}
