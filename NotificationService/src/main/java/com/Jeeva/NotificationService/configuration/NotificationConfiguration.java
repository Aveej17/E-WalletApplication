package com.Jeeva.NotificationService.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Configuration
public class NotificationConfiguration {
    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    };


}
