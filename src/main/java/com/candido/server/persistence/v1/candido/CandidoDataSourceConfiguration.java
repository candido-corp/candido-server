package com.candido.server.persistence.v1.candido;

import com.candido.server.persistence.v1._common.CommonDataSourceConfigurationConstant;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashMap;

import static com.candido.server.persistence.v1.candido.CandidoDataSourceConfigurationConstant.*;

@Configuration
@ConfigurationProperties(CONFIGURATION_PROPERTIES)
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = REPOSITORY_BASE_PACKAGES,
        entityManagerFactoryRef = ENTITY_MANAGER,
        transactionManagerRef = TRANSACTION_MANAGER
)
@Order(CommonDataSourceConfigurationConstant.ORDER_CANDIDODB)
public class CandidoDataSourceConfiguration extends HikariDataSource {

    @Autowired
    Environment environment;

    @Bean(name = DATASOURCE)
    public HikariDataSource candidoDataSource() {
        return new HikariDataSource(this);
    }

    @Bean(name = ENTITY_MANAGER)
    public LocalContainerEntityManagerFactoryBean candidoEntityManager(
            @Qualifier(DATASOURCE) final HikariDataSource hikariDataSource
    ) {
        LocalContainerEntityManagerFactoryBean localContainer =
            new LocalContainerEntityManagerFactoryBean();

        localContainer.setDataSource(hikariDataSource);
        localContainer.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainer.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        localContainer.setPackagesToScan(MODEL_PACKAGE);

        HashMap<String, String> properties = new HashMap<>();
        properties.put(
                CommonDataSourceConfigurationConstant.HIBERNATE_SHOW_SQL,
                environment.getProperty(CommonDataSourceConfigurationConstant.COMMON_PROPERTIES_SHOW_SQL)
        );
        properties.put(
                CommonDataSourceConfigurationConstant.HIBERNATE_JTA_PLATFORM,
                environment.getProperty(CONFIGURATION_PROPERTIES +  ".jta-platform")
        );
        localContainer.setJpaPropertyMap(properties);

        return localContainer;
    }

    @Bean(name = TRANSACTION_MANAGER)
    public PlatformTransactionManager candidoTransactionManager(
            @Qualifier(ENTITY_MANAGER) EntityManagerFactory candidoEntityManager
    ) {
        return new JpaTransactionManager(candidoEntityManager);
    }

}
