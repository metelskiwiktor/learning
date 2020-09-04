package pl.wiktor.learning.api;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.wiktor.learning.api.dto.request.user.CreateUser;
import pl.wiktor.learning.api.dto.response.user.UserView;
import pl.wiktor.learning.domain.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserView createUser(@RequestBody @Valid CreateUser createUser){
        return userService.createUser(
                createUser.getPassword(),
                createUser.getName(),
                createUser.getEmail()
        );
    }
}
