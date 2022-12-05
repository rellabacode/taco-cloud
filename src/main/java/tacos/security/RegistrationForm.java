package tacos.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.User;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class RegistrationForm {
    private String username;

    private boolean newUser;

    private String password;
    private String confirm;

    private boolean passwordmatch;

    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;

    private String mobile;

    private Map<String, String> errors = new HashMap<>();

    public RegistrationForm() {

    }

    public RegistrationForm(String username, String password, boolean passwordmatch, boolean newUser, String confirm, String fullname,
                            String street, String city, String state, String zip, String mobile, Map<String, String> errors) {
        this.username = username;
        this.password = password;
        this.confirm = confirm;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.mobile = mobile;
        this.errors = errors;
        this.passwordmatch = passwordmatch;
        this.newUser = newUser;
    }

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, mobile);
    }

    public boolean hasErrors(String fieldName) {
        Object typeValue = null;
        try {
            typeValue = this.getClass().getDeclaredField(fieldName).get(this);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }

        log.info("has " + fieldName + " any error? the answer is " + !validField(typeValue));
        return !validField(typeValue);
    }

    private boolean validField(Object fieldValue) {
        if (fieldValue instanceof String) {
            String value = (String) fieldValue;
            return value != null && !value.isEmpty();
        } else if (fieldValue instanceof Boolean) {
            Boolean value = (Boolean) fieldValue;
            return value.booleanValue() == true;
        }
        return false;
    }

    public Map<String, String> getErrors() {
        Map<String, String> errorsLocal = new HashMap<>();

        if (hasErrors("fullname")) errorsLocal.put("fullname", "Fullname is required");
        if (hasErrors("username")) errorsLocal.put("username", "Username is required");

        if (hasErrors("newUser")) errorsLocal.put("newUser", "User already exists");

        if (hasErrors("password")) errorsLocal.put("password", "Password is required");
        if (hasErrors("confirm")) errorsLocal.put("confirm", "Password Confirm is required");
        if (!password.equals(confirm))
            errorsLocal.put("passwordmatch", "Passwords does not match");

        if (hasErrors("street"))
            errorsLocal.put("street", "Street is required");

        Pattern pattern = Pattern.compile("^\\+?(6\\d{2}|7[1-9]\\d{1})\\d{6}$");
        Matcher matcher = pattern.matcher(mobile);

        if (!matcher.matches()) errorsLocal.put("mobile", "A valid mobile phone is required");

        log.info(errorsLocal.toString());

        this.errors = errorsLocal;
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isPasswordmatch() {
        return passwordmatch;
    }

    public void setPasswordmatch(boolean passwordmatch) {
        this.passwordmatch = passwordmatch;
    }

    public boolean isNewUser() {
        return newUser;
    }

    public void setNewUser(boolean newUser) {
        this.newUser = newUser;
    }
}
