package springsecuritysample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springsecuritysample.security.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String user() {
        String userName = userService.getAuthenticatedUser().getUsername();
        return String.format("%sのUSER画面です", userName);
    }
}
