package carbon.cellgeekster.User.Authentication.Service;

import carbon.cellgeekster.User.Authentication.Model.AuthenticationToken;
import carbon.cellgeekster.User.Authentication.Model.User;
import carbon.cellgeekster.User.Authentication.Model.dto.SignInInput;
import carbon.cellgeekster.User.Authentication.Model.dto.SignUpOutput;
import carbon.cellgeekster.User.Authentication.Repository.IUserRepo;
import carbon.cellgeekster.User.Authentication.Service.emailSender.EmailHandler;
import carbon.cellgeekster.User.Authentication.Service.hashing.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    IUserRepo iUserRepo;

    @Autowired
    AuthenticationService authenticationService;
    public SignUpOutput signUpUser(User user) {

        boolean signUpStatus = true;
        String signUpStatusMessage = null;

        // checking if user has provided a null email Id
        String currentUserEmail = user.getUserEmail();
        if(currentUserEmail == null){
            signUpStatus = false;
            signUpStatusMessage = "Provided Email is Invalid!!";
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        // To check is this user is Already Exists
        User existingUser = iUserRepo.findFirstByUserEmail(currentUserEmail);
        if(existingUser != null){
            signUpStatusMessage = "Email Already Registered!!";
            signUpStatus = false;
            return new SignUpOutput(signUpStatus, signUpStatusMessage);
        }

        // User registeration occurs here
        // hash the password: Encrypt the password and store in the database


            try {
                String encryptedPassword = PasswordEncryptor.encryptPassword(user.getUserPassword());

                // save the user with the new encrypted password
                user.setUserPassword(encryptedPassword);
                iUserRepo.save(user);
                return new SignUpOutput(signUpStatus, "User Registered Successfully!!");

            } catch (NoSuchAlgorithmException e) {
                signUpStatus = false;
                signUpStatusMessage = "Internal error occurred during sign up";
                return new SignUpOutput(signUpStatus, signUpStatusMessage);
            }
    }


    public String signInUser(SignInInput signInInput) {

        // check if provided email is null
        String signInEmail = signInInput.getEmail();
        if (signInEmail == null) {
            return "Invalid Email!!";
        }

        // checking if the email is registered or not
        User existingUser = iUserRepo.findFirstByUserEmail(signInEmail);
        if (existingUser == null) {
            return "Email Not Registered!!";
        }

        // If user is registered
        // match password
        // hash the password: encrypt the provided password and then match

        try {
            String encryptPassword = PasswordEncryptor.encryptPassword(signInInput.getPassword());
            if (existingUser.getUserPassword().equals(encryptPassword)) {

                AuthenticationToken authenticationToken = new AuthenticationToken(existingUser);
                authenticationService.saveAuthToken(authenticationToken);
                EmailHandler.sendEmail("pulkitmittal194@gmail.com", "Token Value", authenticationToken.getTokenValue());
                return "token has been sent to your email!!";
            }
            else{
                return "Invalid Credentials!!";
            }


        } catch (Exception e) {
            return "Something went wrong!!";
        }

    }

    public String signOutUser(String email) {
        User user = iUserRepo.findFirstByUserEmail(email);
        AuthenticationToken token = authenticationService.findFirstByUser(user);
        authenticationService.removeToken(token);
        return "user has signed out successfully!!";
    }

    public String fetchData() {
        try {
            // Specify the URL of the API endpoint you want to call
            URL url = new URL("https://api.publicapis.org/entries");

            // Open a connection to the URL
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method to GET
            connection.setRequestMethod("GET");

            // Read the response from the API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            // Print the response
            System.out.println("API Response: " + response.toString());

            // Close the connection
            connection.disconnect();

            // return the data fetched from URL
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Something went wrong!!";
    }
}















