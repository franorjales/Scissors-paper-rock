package lottoland.Configuration;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import lottoland.Interfaces.IGameRepository;
import lottoland.Interfaces.IGameServices;
import lottoland.Repository.GameRepositoryImpl;
import lottoland.Services.GameServicesImpl;

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
