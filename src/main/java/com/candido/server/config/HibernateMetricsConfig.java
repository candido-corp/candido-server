package com.candido.server.config;

import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.binder.jpa.HibernateQueryMetrics;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateMetricsConfig {

    @Bean
    MeterBinder hibernateQueryMetrics(EntityManagerFactory entityManager) {
        return new HibernateQueryMetrics(entityManager.unwrap(SessionFactoryImplementor.class), "mySess", Tags.empty());
    }
}