package pro.devlib.paribas.service;


import lombok.extern.slf4j.Slf4j;
import pro.devlib.paribas.parser.StartPageParser;
import pro.devlib.paribas.http.RequestExecutor;

import java.io.IOException;
import java.util.Map;

@Slf4j
class StartPageService {

  private final static String START_PAGE_URL = "https://planet.bgzbnpparibas.pl/hades/do/Login";
  private final static String START_PAGE_REFERER = "https://planet.bgzbnpparibas.pl";
  private final RequestExecutor requestExecutor;
  private final StartPageParser startPageParser;

  StartPageService(RequestExecutor requestExecutor) {
    this.requestExecutor = requestExecutor;
    this.startPageParser = new StartPageParser();
  }

  Map<String, String> getParametersFromStartPage() throws IOException {
    log.info("Get parameters from start page.");
    String htmlFromStartPage = requestExecutor.executeGetRequest(START_PAGE_URL, START_PAGE_REFERER);
    return startPageParser.parseStartPage(htmlFromStartPage);
  }

}
