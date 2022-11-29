package sec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        int i = 0;
        while (i < 10) {
            String password = "infinity";
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassowrd = passwordEncoder.encode(password);
            System.out.println(hashedPassowrd);
            i++;
        }
    }
}
