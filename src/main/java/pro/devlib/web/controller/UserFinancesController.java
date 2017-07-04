package pro.devlib.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pro.devlib.dto.UserCredentialDto;
import pro.devlib.model.UserFinances;
import pro.devlib.service.UserFinancesService;

@RestController
@Scope("session")
@Slf4j
public class UserFinancesController {

  private final UserFinancesService userFinancesService;

  @Autowired
  public UserFinancesController(UserFinancesService userFinancesService) {
    this.userFinancesService = userFinancesService;
  }

  @RequestMapping(value = "finances", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  public UserFinances getUserFinances(@RequestBody UserCredentialDto userCredential) {
    log.info("Receive request with user credential. Login: '" + userCredential.getLogin() + "', password: '" + userCredential.getPassword() + "'");
    return userFinancesService.getUserFinances(userCredential.getLogin(), userCredential.getPassword());
  }
  
}
