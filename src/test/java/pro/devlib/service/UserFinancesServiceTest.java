package pro.devlib.service;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.model.Account;
import pro.devlib.model.UserFinances;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
public class UserFinancesServiceTest {

  // To run this test fill in login and password.
  // Make sure that user has accounts, cards and every account has at least one transaction.

  @Autowired
  private UserFinancesService userFinancesService;

  @Test
  @Ignore
  public void getUserFinances() throws Exception {
    String login = "";
    String password = "";

    UserFinances userFinances = userFinancesService.getUserFinances(login, password);
    assertTrue(userFinances != null);
    assertTrue(userFinances.getAccounts() != null);
    assertTrue(userFinances.getAccounts().size() > 0);
    assertTrue(userFinances.getCards().size() > 0);

    for (Account account : userFinances.getAccounts()) {
      assertTrue(account.getTransactions().size() > 0);
    }
  }
}
