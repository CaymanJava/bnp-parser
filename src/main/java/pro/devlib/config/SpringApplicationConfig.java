package pro.devlib.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


@SpringBootApplication
@ComponentScan(value={"pro.devlib.parser.**", "pro.devlib.http.**", "pro.devlib.service.**", "pro.devlib.web.**"})
public class SpringApplicationConfig {

    public static void main(String[] args) throws Exception{
        SpringApplication.run(SpringApplicationConfig.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }
}
