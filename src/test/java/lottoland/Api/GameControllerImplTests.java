package lottoland.Api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.containsString;

import lottoland.Commons.Constants;
import lottoland.Exceptions.*;
import lottoland.Interfaces.IGameServices;
import lottoland.Model.*;
import lottoland.Portal.PortalApplication;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = PortalApplication.class)
@AutoConfigureMockMvc
public class GameControllerImplTests {

	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Autowired
	private MockMvc mvc;
    
    @Mock
    private IGameServices gService;
    
    @InjectMocks
    private GameControllerImpl gController;

	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	private static final String TEST_USER_NAME_NO_INSERTED = "TEST_USER_NAME_NO_INSERTED";

	
    @BeforeEach
    public void setup() {

        MockitoAnnotations.initMocks(this);

        this.mvc = MockMvcBuilders.standaloneSetup(gController).build();

    }
		
	@Test
	@Order(1)
	public void playMatchWithUserThenMustBeReturnedAGame() {
		String result = null;
		try {
	        doReturn(new Game(TEST_USER_NAME)).when(gService).playMatch(anyString());
			result = mvc.perform(get("/api/playMatch")
				      .contentType(MediaType.APPLICATION_JSON)
				      .param("user", TEST_USER_NAME))
				      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			fail("There has been an UnsupportedEncodingException sending the request");
		} catch (Exception e) {
			fail("There has been an Exception sending the request" );

		}
		
		if(result == null) {
			fail("The mvc request should return a game, not null");
		}
		
		GameDTO testGame = null;
		try {
			testGame = objectMapper.readValue(result, GameDTO.class);
		} catch (JsonProcessingException e) {
			fail("There has been an Exception parsing the response: " + result + " ///// " +e.getMessage());
		}
		
		if(testGame == null) {
			fail("The mapper function must return a game, not null ");
		}
		
		assertEquals(TEST_USER_NAME, testGame.getUser(),"A game with TEST_USER_NAME must be returned");

	}
	
	@Test
	@Order(2)
	public void playMatchWithNullUserThenMustBeReturnedAGame() {

		String result = null;
		try {
	        when(gService.playMatch(isNull())).thenReturn(new Game(TEST_USER_NAME));
			result = mvc.perform(get("/api/playMatch")
				      .contentType(MediaType.APPLICATION_JSON))
				      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		} catch (Exception e) {
			e.getStackTrace();
			fail("There has been an Exception sending the request" + e.getMessage());
		}
		
		if(result == null) {
			fail("The mvc request should return a game, not null");
		}
		
		GameDTO testGame = null;
		try {
			testGame = objectMapper.readValue(result, GameDTO.class);
		} catch (JsonProcessingException e) {
			fail("There has been an Exception parsing the response " + result);
		}
		
		if(testGame == null) {
			fail("The mapper function must return a game, not null ");
		}

		assertEquals(TEST_USER_NAME, testGame.getUser(), "A game with user must be returned " + testGame.getUser());

	}
	
	@Test
	@Order(3)
	public void restartAUserGameThenAEmptyGameShouldBeReturned() {
		
		int zero = 0;
		
		String result = null;
		try {
	        when(gService.restartUserGame(TEST_USER_NAME)).thenReturn(new Game(TEST_USER_NAME));
			result = mvc.perform(get("/api/restartGame")
				      .contentType(MediaType.APPLICATION_JSON)
				      .param("user", TEST_USER_NAME))
				      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		} catch (Exception e) {
			fail("There has been an Exception sending the request");
		}
		
		if(result == null) {
			fail("The mvc request should return a game, not null");
		}
		
		GameDTO testGame = null;
		try {
			testGame = objectMapper.readValue(result, GameDTO.class);
		} catch (JsonProcessingException e) {
			fail("There has been an Exception parsing the response ");
		}
		
		if(testGame == null) {
			fail("The mapper function must return a game, not null ");
		}
		
		assertEquals(zero, testGame.getNumberOfmatchesPlayeds(),"There must be zero matcheds played");

	}
	
	@Test
	@Order(4)
	public void restartANotInsertedUserGameThenAnExceptionMustBeThrown() {
		
		try {
	        when(gService.restartUserGame(eq(TEST_USER_NAME_NO_INSERTED))).thenThrow(new NoUserException(Constants.NO_USER_EXCEPTION_MESSAGE));
			mvc.perform(get("/api/restartGame")
				      .contentType(MediaType.APPLICATION_JSON)
				      .param("user", TEST_USER_NAME_NO_INSERTED))
				      .andExpect(status().isNotFound())
				      .andExpect(status().reason(Constants.NO_USER_EXCEPTION_MESSAGE));
		} catch (Exception e1) {
			fail("There has been an Exception sending the request");
		}

	}
	
}
