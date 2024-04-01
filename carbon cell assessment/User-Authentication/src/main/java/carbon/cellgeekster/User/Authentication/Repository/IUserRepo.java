package carbon.cellgeekster.User.Authentication.Repository;

import carbon.cellgeekster.User.Authentication.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepo extends JpaRepository<User, Integer> {
    User findFirstByUserEmail(String currentUserEmail);
}
