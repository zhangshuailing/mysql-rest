package nk.gk.wyl.mysql.config.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(1)
public class InitRunner {

    @Bean
    public int run(){
        return 0;
    }

}
