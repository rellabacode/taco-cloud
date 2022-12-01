package tacos;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;
    private String userPassword;
    private String fullName;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public User(String username, String password, String fullname, String street,
                String city, String state, String zip, String phoneNumber) {
        this.userName = username;
        this.userPassword = password;
        this.fullName = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public void setPassword(String password) {
        this.userPassword = password;
    }

    public void setFullname(String fullname) {
        this.fullName = fullname;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public String getPassword() {
        return userPassword;
    }

    public String getFullname() {
        return fullName;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        return roles;
    }


}
