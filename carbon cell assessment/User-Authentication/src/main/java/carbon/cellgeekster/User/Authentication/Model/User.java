package carbon.cellgeekster.User.Authentication.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String userName;

    @Pattern(regexp = "^.+@gmail\\.com$")
    @Column(unique = true)
    private String userEmail;
    private String userPassword;
    private String userContactNo;
}
