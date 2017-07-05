package pro.devlib.paribas.service;

import lombok.extern.slf4j.Slf4j;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.parser.DesktopInformationParser;
import pro.devlib.paribas.http.RequestExecutor;

import java.io.IOException;

@Slf4j
class DesktopService {

  private final static String DESKTOP_URL = "https://planet.bgzbnpparibas.pl/retail/do/desktop";
  private final RequestExecutor requestExecutor;
  private final DesktopInformationParser desktopInformationParser;

  DesktopService(RequestExecutor requestExecutor) {
    this.requestExecutor = requestExecutor;
    this.desktopInformationParser = new DesktopInformationParser();
  }

  DesktopInfoDto getDesktopInformation() throws IOException {
    String desktopHtml = requestExecutor.executeGetRequest(DESKTOP_URL, "");
    return desktopInformationParser.parseDesktopHtml(desktopHtml);
  }

}
