package com.funeral.wulinacademy.web.config;

import com.funeral.wulinacademy.web.common.factory.YamlPropertiesSourceFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * for business
 *
 * @author FuneralObjects
 * CreateTime 2019-04-12 16:57
 */
@Component
@PropertySource(factory = YamlPropertiesSourceFactory.class,value = "classpath:service-config.yml")
@ConfigurationProperties(prefix = "security")
@Data
public class ServiceSecurityConfig {
    private Csrf csrf = new Csrf();
    private Login login = new Login();
    private User user = new User();

    @Data
    public class Csrf{
        private Token token = new Token();

        @Data
        public class Token{
            private String inHeaderName;
            private String inParamName;
            private String stranger;
        }
    }

    @Data
    public class Login{
        private String usernameOfParamName;
        private String passwordOfParamName;
    }

    @Data
    public class User{
        private String username;
        private String password;
    }

}
