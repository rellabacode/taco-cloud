package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;

    public static final String DEF_USERS_BY_USERNAME_QUERY =
            "select username,password,enabled " +
                    "from users " +
                    "where username = ?";
    public static final String DEF_AUTHORITIES_BY_USERNAME_QUERY =
            "select username,authority " +
                    "from authorities " +
                    "where username = ?";
    public static final String DEF_GROUP_AUTHORITIES_BY_USERNAME_QUERY =
            "select g.id, g.group_name, ga.authority " +
                    "from groups g, group_members gm, group_authorities ga " +
                    "where gm.username = ? " +
                    "and g.id = ga.group_id " +
                    "and g.id = gm.group_id";

    @Bean
    public PasswordEncoder encoder() {
        return new StandardPasswordEncoder("53cr3t");
    }

    @Override //global security
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
//                .and()
//                .ignoring()
//                .antMatchers("/register/**");
    }

    @Override //resource level
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/design", "/orders/**")
                        .access("hasRole('ROLE_USER')")
                    .antMatchers("/", "/**")
                        .permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .usernameParameter("user")
                    .passwordParameter("pwd")
                .defaultSuccessUrl("/design", true)
                .and()
                    .logout().logoutSuccessUrl("/");

        http.csrf().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("buzz")
//                .password("{noop}infinity")
//                .authorities("ROLE_USER")
//                .and()
//                .withUser("woody")
//                .password("{noop}bullseye")
//                .authorities("ROLE_USER");

//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(DEF_USERS_BY_USERNAME_QUERY)
//                .authoritiesByUsernameQuery(DEF_AUTHORITIES_BY_USERNAME_QUERY)
//                .passwordEncoder(new BCryptPasswordEncoder());

//        auth
//                .ldapAuthentication()
//                .userSearchFilter("(uid={0})")
//                .contextSource()
//                .url("ldap://localhost:8389/dc=breadcrumbdata,dc=com");

        auth
                .userDetailsService(userDetailsService);
//                .passwordEncoder(encoder());

    }
}
