package chusco.configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import chusco.interfaces.IGameRepository;
import chusco.interfaces.IGameServices;
import chusco.repository.GameRepositoryImpl;
import chusco.services.GameServicesImpl;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public IGameRepository gRepository() {
        return Mockito.mock(GameRepositoryImpl.class);
    }
    
    @Bean
    @Primary
    public IGameServices gService() {
        return Mockito.mock(GameServicesImpl.class);
    }
}
