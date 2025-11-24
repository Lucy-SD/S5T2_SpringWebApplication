package carpincha.dOutermostLayer.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/public")
    public String publicMethod() {
        return "Endpoint público.";
    }

    @GetMapping("/protected")
    public String protectedMethod() {
        return "Endpoint protegido.";
    }
}
