package digitalLibrary.controller;//package digitalLibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getMain(){
        return "1main_page";
    }
}