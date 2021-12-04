package amzx.app.configuration;

import amzx.app.data.service.UserService;
import amzx.app.web.security.AuthEntryPoint;
import amzx.app.web.security.AuthenticationJwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationWebConfiguration extends WebSecurityConfigurerAdapter
{

    private final UserService                  userService;
    private final AuthEntryPoint               authEntryPoint;
    private final AuthenticationJwtTokenFilter authenticationJwtTokenFilter;
    private final BCryptPasswordEncoder        bCryptPasswordEncoder;

    @Autowired
    public ApplicationWebConfiguration(
            UserService userService,
            AuthEntryPoint authEntryPoint,
            AuthenticationJwtTokenFilter authenticationJwtTokenFilter,
            BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userService = userService;
        this.authEntryPoint = authEntryPoint;
        this.authenticationJwtTokenFilter = authenticationJwtTokenFilter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userService)
                .passwordEncoder(this.bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(this.authEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/v1/keywords/**").permitAll()
                .anyRequest().authenticated();

        http
                .addFilterBefore(this.authenticationJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}