package controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class TestController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("test","varianle");
        return "salama";
    }
}
