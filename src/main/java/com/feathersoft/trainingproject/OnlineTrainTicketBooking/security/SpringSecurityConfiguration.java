package com.feathersoft.trainingproject.OnlineTrainTicketBooking.security;

import com.feathersoft.trainingproject.OnlineTrainTicketBooking.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfiguration {
    @Autowired
    private JwtAuthFilter authFilter;

    @Bean
    //authentication
    public UserDetailsService userDetailsService(){
        return  new UserInfoUserDetailsService();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(customizer-> customizer
                                .requestMatchers("/user/register-user","/user/manage-profile/**",
                                        "/train/add-train","train/search-train/**","/train/update-train-details/**",
                                        "/train/change-ticket-price/**","/train/check-available-seats/**",
                                        "/user/change-email","/user/change-password",
                                        "user/delete-user/**",
                                "/booking/book-ticket","/booking/display-all-booking-by-train/**","/booking/cancel-booking/**",
                                        "/booking/display-all-bookings","/booking/accept-booking/**","/booking/reject-booking/**",
                                        "/booking/display-all-booking-by-user/**").permitAll()
                                .requestMatchers("train/add-train")
                                .hasAuthority("ADMIN")

//                                .requestMatchers("/employees/update/**")
//                                .hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
//
//                                .requestMatchers("/employees/downloadProfileImage")
//                                .hasAuthority("ROLE_USER")
                        .anyRequest().hasAnyAuthority("USER","ADMIN")
                )
                .sessionManagement(customizer->
                        customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                       .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return security.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){

        return  new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }
   @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();

    }

}
