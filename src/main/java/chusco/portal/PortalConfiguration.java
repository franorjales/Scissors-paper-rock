package chusco.portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chusco.interfaces.IGameRepository;
import chusco.interfaces.IGameServices;
import chusco.repository.GameRepositoryImpl;
import chusco.services.GameServicesImpl;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class PortalConfiguration {

    @Bean
    public IGameRepository gRepository() {
        return new GameRepositoryImpl();
    }
    
    @Bean
    public IGameServices gService() {
        return new GameServicesImpl(new GameRepositoryImpl());
    }
	

    
    
}
