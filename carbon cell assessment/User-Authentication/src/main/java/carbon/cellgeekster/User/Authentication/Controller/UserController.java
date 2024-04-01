package carbon.cellgeekster.User.Authentication.Controller;

import carbon.cellgeekster.User.Authentication.Model.User;
import carbon.cellgeekster.User.Authentication.Model.dto.SignInInput;
import carbon.cellgeekster.User.Authentication.Model.dto.SignUpOutput;
import carbon.cellgeekster.User.Authentication.Service.AuthenticationService;
import carbon.cellgeekster.User.Authentication.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationService authenticationService;

    // API to Sign Up
    @PostMapping("user/signUp")
    public SignUpOutput signUpUser(@RequestBody @Valid User user){
        return userService.signUpUser(user);
    }

    // API to log in
    @PostMapping("user/signIn")
    public String signInUser(@RequestBody @Valid SignInInput signInInput){
        return userService.signInUser(signInInput);
    }

    // API to log out
    @DeleteMapping("user/signOut")
    public String signOutUser(String email, String tokenValue){
        if(authenticationService.authenticate(email, tokenValue)){
            return userService.signOutUser(email);
        }
        else{
            return "Non-Authenticated User!!";
        }
    }

    // API for data retrieval
    @GetMapping("data/publicAPI/email/{email}/token/{tokenValue}")
    public String fetchData (String email, String tokenValue){
        if(authenticationService.authenticate(email, tokenValue)) {
            return userService.fetchData();
        }
        return "UnAuthenticated User Activity!!";
    }

}
