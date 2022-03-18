package ua.com.alevel.config;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @RequestMapping("/open")
    public String showHomePage () {
        return "displaying the open page contents";
    }

    @RequestMapping("/protected")
    public String protectedPage () {
        return "displaying the protected page contents";
    }
}
