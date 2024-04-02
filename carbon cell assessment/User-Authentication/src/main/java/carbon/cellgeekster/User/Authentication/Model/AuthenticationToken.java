package carbon.cellgeekster.User.Authentication.Model;

import carbon.cellgeekster.User.Authentication.Service.utilityClasses.JwtTokenUtility;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tokenId;

    private String tokenValue;

    private LocalDateTime tokenCreationDateTime;

    private String signedInEmail;

    // one to one mapping with user as every user can only sign in once
    @OneToOne
    @JoinColumn(name = "fk_user_Id")
    User user;


    // parameterized constructor that initializes value for every variable of object
    public AuthenticationToken(User existingUser, String tokenValue){
        this.user = existingUser;
        this.signedInEmail = existingUser.getUserEmail();
        this.tokenValue = tokenValue;
        this.tokenCreationDateTime = LocalDateTime.now();

    }
}
