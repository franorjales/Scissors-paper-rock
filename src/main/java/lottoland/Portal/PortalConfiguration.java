package lottoland.Portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lottoland.Interfaces.IGameRepository;
import lottoland.Interfaces.IGameServices;
import lottoland.Repository.GameRepositoryImpl;
import lottoland.Services.GameServicesImpl;

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
