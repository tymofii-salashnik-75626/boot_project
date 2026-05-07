package pl.vistula.webintro.demo;

import java.util.Arrays;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

  @GetMapping("/")
  String indexPage() {
    return """
    <html>
      <body>
      <p>Demo project from Vistula students!</p>
      <p>Click <a href="/hello">here</a> to open the example of generated JSON </p>
      </body>
    </html>
    """;
  }

  @GetMapping("/hello")
  HelloResponse hello() {
    HelloResponse response = new HelloResponse();
    response.setMessage("Hello from Spring Boot!");
    response.setTeam("BinaryBears");
    response.setStudentIndexes(Arrays.asList(75626, 75381, 77332));
    return response;
  }    
    
}
