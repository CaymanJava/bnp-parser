package pro.devlib.parser;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pro.devlib.config.SpringApplicationConfig;
import pro.devlib.dto.StatementDto;
import pro.devlib.model.Amount;
import pro.devlib.model.Transaction;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SpringApplicationConfig.class})
@Slf4j
public class TransactionParserTest {

    @Autowired
    private TransactionParser transactionParser;
    private String statementHtml;
    private String transactionsHtml;
    private String expectedSysDate;
    private int expectedTemplatesIdSize;
    private Transaction expectedFirstTransaction;
    private Transaction expectedSecondTransaction;
    private Transaction expectedThirdTransaction;

    @Before
    public void init() throws Exception{
        statementHtml = new String(Files.readAllBytes(Paths.get("src/test/resources/statement.html")));
        transactionsHtml = new String(Files.readAllBytes(Paths.get("src/test/resources/transactions.html")));
        expectedSysDate = "23.06.2017";
        expectedTemplatesIdSize = 28;
        expectedFirstTransaction = buildFirstTransaction();
        expectedSecondTransaction = buildSecondTransaction();
        expectedThirdTransaction = buildThirdTransaction();

    }

    @Test
    public void parseSysDateAndTemplatesIdSize() throws Exception {
        StatementDto statementDto = transactionParser.parseSysDateAndTemplatesIdSize(statementHtml);
        assertTrue(statementDto.getSystemDate().equals(expectedSysDate));
        assertTrue(statementDto.getTemplatesIdSize() == expectedTemplatesIdSize);
    }

    @Test
    public void parseTransactions() throws Exception {
        List<Transaction> transactions = transactionParser.parseTransactions(transactionsHtml);
        assertTrue(transactions.size() == 3);
        assertTrue(transactions.get(0).equals(expectedFirstTransaction));
        assertTrue(transactions.get(1).equals(expectedSecondTransaction));
        assertTrue(transactions.get(2).equals(expectedThirdTransaction));
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