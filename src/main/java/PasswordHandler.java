import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHandler {

    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String new_pwd = passwordEncoder.encode(password);
        return new_pwd;
    }

    //compare given password with the one in the database
    public static boolean checkPassword(String passwordToVerify, String storedHashedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = passwordEncoder.matches(passwordToVerify, storedHashedPassword);
        return isPasswordMatch;
    }
}
