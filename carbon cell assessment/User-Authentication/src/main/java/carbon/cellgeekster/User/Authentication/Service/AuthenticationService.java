package carbon.cellgeekster.User.Authentication.Service;

import carbon.cellgeekster.User.Authentication.Model.AuthenticationToken;
import carbon.cellgeekster.User.Authentication.Model.User;
import carbon.cellgeekster.User.Authentication.Repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    IAuthenticationRepo iAuthenticationRepo;
    public void saveAuthToken(AuthenticationToken authenticationToken) {
        iAuthenticationRepo.save(authenticationToken);
    }

    public boolean authenticate(String email, String tokenValue) {
        AuthenticationToken authToken = iAuthenticationRepo.findFirstByTokenValue(tokenValue);
        if(authToken == null) {
            return false;
        }
        String tokenConnectedEmail = authToken.getUser().getUserEmail();
        if(!tokenConnectedEmail.equals(email)) {
            return false;
        }
        return true;
    }

    public AuthenticationToken findFirstByUser(User user) {
        return iAuthenticationRepo.findFirstByUser(user);
    }

    public void removeToken(AuthenticationToken token) {
        iAuthenticationRepo.delete(token);
    }
}
