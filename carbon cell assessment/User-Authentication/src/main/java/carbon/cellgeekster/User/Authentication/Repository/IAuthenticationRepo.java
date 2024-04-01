package carbon.cellgeekster.User.Authentication.Repository;

import carbon.cellgeekster.User.Authentication.Model.AuthenticationToken;
import carbon.cellgeekster.User.Authentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken, Integer> {

    AuthenticationToken findFirstByTokenValue(String tokenValue);

    AuthenticationToken findFirstByUser(User user);
}
