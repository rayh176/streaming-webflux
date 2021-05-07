package com.streaming.core.config;

import com.streaming.core.RepositoryPackage;
import com.streaming.core.repos.PlayerRepository;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.ReactiveTransactionManager;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableR2dbcRepositories(basePackages = {"com.streaming.core.repos"},
        basePackageClasses = {PlayerRepository.class, RepositoryPackage.class},
        includeFilters= @ComponentScan.Filter
                (type = FilterType.ASSIGNABLE_TYPE, classes = R2dbcRepository.class)
)
public class R2DBCConfig extends AbstractR2dbcConfiguration {
    @Bean
    public H2ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .url("r2dbc:h2:mem:./data/db")
                        .username("sa")
                        .build()
        );
    }

    @Bean
    public R2dbcCustomConversions r2dbcCustomConversions() {
        List<Converter> converters = new ArrayList<>();
        converters.add(new LocalDateTimeConverter());
        return new R2dbcCustomConversions(converters);
    }

    @Bean
    ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    public static class LocalDateTimeConverter implements Converter <LocalDateTime, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(LocalDateTime localDateTime) {
            return LocalDateTime.MAX.atZone(TimeZone.getDefault().toZoneId());
        }
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

}
