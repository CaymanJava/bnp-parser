package pro.devlib.paribas.parser;

import org.junit.Test;
import pro.devlib.paribas.dto.StatementDto;
import pro.devlib.paribas.model.Amount;
import pro.devlib.paribas.model.Transaction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TransactionParserTest {

  private TransactionParser transactionParser = new TransactionParser();

  @Test
  public void parseSysDateAndTemplatesIdSize() throws Exception {
    String statementHtml = new String(Files.readAllBytes(Paths.get("src/test/resources/statement.html")));
    String expectedSysDate = "23.06.2017";
    int expectedTemplatesIdSize = 28;

    StatementDto statementDto = transactionParser.parseSysDateAndTemplatesIdSize(statementHtml);
    assertEquals(expectedSysDate, statementDto.getSystemDate());
    assertEquals(expectedTemplatesIdSize, statementDto.getTemplatesIdSize());
  }

  @Test
  public void parseTransactions() throws Exception {
    String transactionsHtml = new String(Files.readAllBytes(Paths.get("src/test/resources/transactions.html")));
    Transaction expectedFirstTransaction = buildFirstTransaction();
    Transaction expectedSecondTransaction = buildSecondTransaction();
    Transaction expectedThirdTransaction = buildThirdTransaction();

    List<Transaction> transactions = transactionParser.parseTransactions(transactionsHtml);
    assertEquals(3, transactions.size());
    assertEquals(expectedFirstTransaction, transactions.get(0));
    assertEquals(expectedSecondTransaction, transactions.get(1));
    assertEquals(expectedThirdTransaction, transactions.get(2));
  }

  private Transaction buildFirstTransaction() {
    return Transaction
            .builder()
            .date(LocalDate.of(2017, 6, 30))
            .description("DEBIT CARD NON-CASH TRANS. 557515------9823 Warszawa AUCHAN Wola POL 54,58 PLN 2017-06-29 PSD5339236058730")
            .amount(new Amount(-54.58, "PLN"))
            .balanceAfterOperation(new Amount(227497.33, "PLN"))
            .build();
  }

  private Transaction buildSecondTransaction() {
    return Transaction
            .builder()
            .date(LocalDate.of(2017, 6, 30))
            .description("DEBIT CARD NON-CASH TRANS. 557515------9823 4,390000000 35314369001 PAYPAL -ETG POL 42,47 USD 2017-06-28 PSD5336577954008 exchange rate 4.3900 amount** 37.56 EUR")
            .amount(new Amount(-164.89, "PLN"))
            .balanceAfterOperation(new Amount(32551.91, "PLN"))
            .build();
  }

  private Transaction buildThirdTransaction() {
    return Transaction
            .builder()
            .date(LocalDate.of(2017, 6, 30))
            .description("DEBIT CARD NON-CASH TRANS. 557515------9823 WARSZAWA KSH SALONIK 097780 POL 3,39 PLN 2017-06-28 PSD5335318451629")
            .amount(new Amount(-3.39, "PLN"))
            .balanceAfterOperation(new Amount(32716.8, "PLN"))
            .build();
  }

}
