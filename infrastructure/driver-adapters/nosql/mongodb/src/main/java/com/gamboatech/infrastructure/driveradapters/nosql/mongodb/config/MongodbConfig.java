package com.gamboatech.infrastructure.driveradapters.nosql.mongodb.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"com.gamboatech.infrastructure.driveradapter.nosql.mongodb"})
public class MongodbConfig  extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    public String mongoUri;
    @Value("${spring.data.mongodb.database}")
    public String databaseName;

    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.applyConnectionString(new ConnectionString(mongoUri));
    }

    @Bean
    public MongoTemplate mongoTemplate(MongoDatabaseFactory databaseFactory,
                                       MappingMongoConverter mongoConverter) {
        return new MongoTemplate(databaseFactory, mongoConverter);
    }

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }
}
