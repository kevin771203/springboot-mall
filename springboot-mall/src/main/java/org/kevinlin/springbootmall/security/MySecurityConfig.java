package org.kevinlin.springbootmall.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class MySecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                // 設定 Session 的創建機制
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .csrf(csrf -> csrf.disable())

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults())

                .authorizeHttpRequests(request -> request
                        // 註冊與登入功能開放
                        .requestMatchers("/users/register", "/users/Login").permitAll()

                        // 一般會員可以查詢商品與下訂單
                        .requestMatchers("/products", "/products/{productId}", "/users/{userId}/orders")
                            .hasAnyRole("NORMAL_MEMBER", "ADMIN")  // 👈 合併權限

                        // 管理者才可以操作商品資料
                        .requestMatchers("/products/**").hasRole("ADMIN")

                        // 其他請求一律禁止
                        .anyRequest().denyAll()
                )

                .build();
    }
}
