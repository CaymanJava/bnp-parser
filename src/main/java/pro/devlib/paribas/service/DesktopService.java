package pro.devlib.paribas.service;

import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXException;
import pro.devlib.paribas.dto.DesktopInfoDto;
import pro.devlib.paribas.http.HttpProvider;
import pro.devlib.paribas.parser.DesktopInformationParser;

import java.io.IOException;

@Slf4j
class DesktopService {

  private final static String DESKTOP_URL = "https://planet.bgzbnpparibas.pl/retail/do/desktop";
  private final HttpProvider httpProvider;
  private final DesktopInformationParser desktopInformationParser;

  DesktopService(HttpProvider httpProvider) {
    this.httpProvider = httpProvider;
    this.desktopInformationParser = new DesktopInformationParser();
  }

  DesktopInfoDto getDesktopInformation() throws IOException, SAXException {
    String desktopHtml = httpProvider.executeGetRequest(DESKTOP_URL, "");
    return desktopInformationParser.parseDesktopHtml(desktopHtml);
  }

}
