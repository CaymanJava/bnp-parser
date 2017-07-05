package pro.devlib.paribas.parser;

import org.junit.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.TestCase.assertEquals;

public class PasswordPageParserTest {

  @Test
  public void extractSamlUrlFromPasswordPage() throws Exception {
    PasswordPageParser passwordPageParser = new PasswordPageParser();
    String html = new String(Files.readAllBytes(Paths.get("src/test/resources/passwordPage.html")));
    String expectedSamlUrl = "https://planet.bgzbnpparibas.pl/hades/RedirectSaml?SAMLart=AAQAAC4H9dHvCEfgFKKhkn6P2cSY7OyEAAACAQAjg7DGwvlQTkXP9dmbwQE%3D";
    String samlUrl = passwordPageParser.extractSamlUrlFromPasswordPage(html);
    assertEquals(expectedSamlUrl, samlUrl);
  }

}
