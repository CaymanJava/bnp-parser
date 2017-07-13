package pro.devlib.paribas.service;


import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import pro.devlib.paribas.http.HttpProvider;
import pro.devlib.paribas.parser.StartPageParser;

import java.io.IOException;
import java.util.Map;

@Slf4j
class StartPageService {

  private final static String START_PAGE_URL = "https://planet.bgzbnpparibas.pl/hades/do/Login";
  private final static String START_PAGE_REFERER = "https://planet.bgzbnpparibas.pl";
  private final HttpProvider httpProvider;
  private final StartPageParser startPageParser;

  StartPageService(HttpProvider httpProvider) {
    this.httpProvider = httpProvider;
    this.startPageParser = new StartPageParser();
  }

  Map<String, String> getParametersFromStartPage() throws IOException, SAXException {
    String htmlFromStartPage = httpProvider.executeGetRequest(START_PAGE_URL, START_PAGE_REFERER);
    log.info("Get parameters from start page.");
    return startPageParser.parseStartPage(htmlFromStartPage);
  }

}
