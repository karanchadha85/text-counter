package com.kc.intelliment.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import com.kc.intelliment.security.WebSecurityConfig;

@Configuration
@ComponentScan(basePackages = {"com.kc.intelliment.rest","com.kc.intelliment.model","com.kc.intelliment.security"})
@Import({WebSecurityConfig.class })
public class AppConfig {

	@Value("classpath:MyPassage.txt")
    private Resource passageResource;

    @Bean
    public String passageText() throws IOException{
        try(InputStream is = passageResource.getInputStream()) {
            return StreamUtils.copyToString(is, Charset.forName("UTF-8"));
        }
    }
}
