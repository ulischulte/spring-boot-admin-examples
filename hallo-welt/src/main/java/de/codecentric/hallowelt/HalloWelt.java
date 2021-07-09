package de.codecentric.hallowelt;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HalloWelt {

  @GetMapping
  public String hallo() {
    return "Hallo Welt!";
  }

}
