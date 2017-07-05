package pro.devlib.paribas.service;


import lombok.extern.slf4j.Slf4j;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.dto.StatementDto;
import pro.devlib.paribas.http.RequestExecutor;
import pro.devlib.paribas.model.Account;
import pro.devlib.paribas.model.Transaction;
import pro.devlib.paribas.parser.TransactionParser;

import java.io.IOException;
import java.util.*;

@Slf4j
class TransactionService {

  private final static String DESKTOP_URL = "https://planet.bgzbnpparibas.pl/retail/do/desktop";
  private final static String DESKTOP_REFERER = "https://planet.bgzbnpparibas.pl/retail/do/desktop?open=true&param=";
  private final static String STATEMENT_URL_WITH_PARAMS = "https://planet.bgzbnpparibas.pl/retail/do/statementList?open=true&param=";
  private final static String STATEMENT_URL = "https://planet.bgzbnpparibas.pl/retail/do/statementList";
  private final RequestExecutor requestExecutor;
  private final TransactionParser transactionParser;

  TransactionService(RequestExecutor requestExecutor) {
    this.requestExecutor = requestExecutor;
    this.transactionParser = new TransactionParser();
  }

  void setTransactionsToAccounts(DesktopInfoDto desktopInformation) throws IOException {
    List<Account> accounts = desktopInformation.getAccounts();
    for (int accountNumber = 0; accountNumber < accounts.size(); accountNumber++) {
      List<Transaction> accountTransactions = getAccountTransactions(accountNumber, desktopInformation.getRndParameter());
      accounts.get(accountNumber).setTransactions(accountTransactions);
    }
  }

  private List<Transaction> getAccountTransactions(int accountNumber, String rndParameter) throws IOException {
    Map<String, String> paramsForDesktopRequest = createParamsForDesktopRequest(accountNumber, rndParameter);
    requestExecutor.executePostRequest(DESKTOP_URL, DESKTOP_REFERER, paramsForDesktopRequest);
    String statementWithParamsHtml = requestExecutor.executeGetRequest(STATEMENT_URL_WITH_PARAMS, DESKTOP_URL);
    StatementDto statementDto = transactionParser.parseSysDateAndTemplatesIdSize(statementWithParamsHtml);

    List<Transaction> transactions = new ArrayList<>();
    for (int templateId = 0; templateId < statementDto.getTemplatesIdSize(); templateId++) {
      transactions.addAll(getTransactionsFromTemplate(statementDto, templateId));
    }
    return transactions;
  }

  private List<Transaction> getTransactionsFromTemplate(StatementDto statementDto, int templateId) {
    try {
      Map<String, String> paramsForStatementRequest = createParamsForStatementRequest(statementDto.getSystemDate(), templateId);
      String statementHtml = requestExecutor.executePostRequest(STATEMENT_URL, STATEMENT_URL, paramsForStatementRequest);
      return transactionParser.parseTransactions(statementHtml);
    } catch (Exception e) {
      log.warn("Can't execute request to statement");
      return Collections.emptyList();
    }
  }

  private Map<String, String> createParamsForDesktopRequest(int accountNumber, String rndParameter) {
    Map<String, String> parameters = new HashMap<>();
    parameters.put("rnd", rndParameter);
    parameters.put("task", "ACC_DETAILS#" + accountNumber);
    parameters.put("whitchAccountsList", "accounts");
    return parameters;
  }

  private Map<String, String> createParamsForStatementRequest(String systemDate, int templateId) {
    String actualTemplateId = String.valueOf(templateId <= 0 ? 0 : templateId - 1);
    Map<String, String> parameters = new HashMap<>();
    parameters.put("task", "EXECUTE");
    parameters.put("ascending", "true");
    parameters.put("methodName", "");
    parameters.put("currPosition", "0");
    parameters.put("p_actual_template_id", actualTemplateId);
    parameters.put("p_sys_date", systemDate);
    parameters.put("p_template_id", String.valueOf(templateId));
    parameters.put("m_hide_overnight", "false");
    parameters.put("sizePerWindow", "1000");
    return parameters;
  }

}
