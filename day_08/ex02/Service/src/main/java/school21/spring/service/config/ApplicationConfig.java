package school21.spring.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("school21.spring.service")
@PropertySource("classpath:db.properties")
public class ApplicationConfig {

    @Autowired
    private Environment env;

    @Bean
    public HikariConfig hikariConfig() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setJdbcUrl(env.getProperty("db.url"));
        hikariConfig.setUsername(env.getProperty("db.user"));
        hikariConfig.setPassword(env.getProperty("db.password"));
        return  hikariConfig;
    }

    @Bean
    public HikariDataSource hikariDataSource(HikariConfig hikariConfig) {
        return  new HikariDataSource(hikariConfig);
    }

    @Bean
    public DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();

        driverManagerDataSource.setUrl(env.getProperty("db.url"));
        driverManagerDataSource.setUsername(env.getProperty("db.user"));
        driverManagerDataSource.setPassword(env.getProperty("db.password"));
        driverManagerDataSource.setDriverClassName(env.getProperty("db.driver.name"));
        return driverManagerDataSource;
    }
}





