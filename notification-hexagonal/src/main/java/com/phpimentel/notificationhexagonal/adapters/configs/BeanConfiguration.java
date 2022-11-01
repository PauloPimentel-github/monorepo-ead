package com.phpimentel.notificationhexagonal.adapters.configs;

import com.phpimentel.notificationhexagonal.NotificationHexagonalApplication;
import com.phpimentel.notificationhexagonal.core.ports.NotificationPersistencePort;
import com.phpimentel.notificationhexagonal.core.services.NotificationServicePortImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = NotificationHexagonalApplication.class)
public class BeanConfiguration {

    @Bean
    public NotificationServicePortImpl notificationServicePortImpl(NotificationPersistencePort persistencePort) {
        return new NotificationServicePortImpl(persistencePort);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
