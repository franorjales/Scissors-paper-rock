package chusco.portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import chusco.interfaces.IGameRepository;
import chusco.interfaces.IGameServices;
import chusco.repository.GameRepositoryImpl;
import chusco.services.GameServicesImpl;

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
