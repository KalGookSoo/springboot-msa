package com.kalgooksoo.menu.config;

import com.kalgooksoo.core.principal.HeaderPrincipalProvider;
import com.kalgooksoo.core.principal.PrincipalProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PrincipalConfig {

    @Bean
    public PrincipalProvider principalProvider() {
        return new HeaderPrincipalProvider();
    }

}