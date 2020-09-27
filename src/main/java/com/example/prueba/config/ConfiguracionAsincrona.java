package com.example.prueba.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;


/**
 * Clase que nos permite tener multiples hilos en tareas especiales asincronas
 */
@Configuration
@EnableAsync
public class ConfiguracionAsincrona {


    Logger logger = LoggerFactory.getLogger(ConfiguracionAsincrona.class);
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(2);
        executor.setQueueCapacity(15);
        executor.setThreadNamePrefix("empleadoThead-");
        logger.info("ThreadPoolTaskExecutor set");
        executor.initialize();
        return executor;


    }
}
