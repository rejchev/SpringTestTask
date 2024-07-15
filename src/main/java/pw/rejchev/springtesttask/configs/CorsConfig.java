package pw.rejchev.springtesttask.configs;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@Getter
@Setter
@AllArgsConstructor(onConstructor_ = @Autowired)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorsConfig implements WebMvcConfigurer {

    static Logger logger = LoggerFactory.getLogger(CorsConfig.class);

    static final String ENV_DEVELOPMENT = "dev";

    ServerProperties serverProperties;

    Environment env;

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {

        if (env.acceptsProfiles(Profiles.of(ENV_DEVELOPMENT))) {

            logger.debug("Register CORS configuration");

            registry.addMapping("/api/**")
                    .allowedOrigins("http://localhost:" + serverProperties.getPort() + "/")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    }
}
