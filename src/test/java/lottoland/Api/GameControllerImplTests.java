package lottoland.Api;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.io.UnsupportedEncodingException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.fail;
import lottoland.Commons.Constants;
import lottoland.Exceptions.*;
import lottoland.Interfaces.IGameController;
import lottoland.Model.*;
import lottoland.Services.GameServicesImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameControllerImplTests {
	
	@Autowired
	private ObjectMapper objectMapper;
	
    @Autowired
	private MockMvc mvc;

	@Mock
    private static GameServicesImpl gService;
	
	@InjectMocks
	private IGameController gController;
	
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	
	@BeforeAll
	public static void setMockOutput() throws NoUserException, NoGameFoundException {

        when(gService.playMatch(anyString())).thenAnswer(i -> new Game(i.getArgument(0)));
        when(gService.playMatch(isNull())).thenReturn(new Game(TEST_USER_NAME));
        when(gService.restartUserGame(TEST_USER_NAME)).thenReturn(new Game(TEST_USER_NAME));
        when(gService.restartUserGame(not(eq(TEST_USER_NAME)))).thenThrow(new NoUserException(Constants.NO_USER_EXCEPTION_MESSAGE));

	}
	
	@Test
	@Order(1)
	public void playMatchWithUserThenMustBeReturnedAGame() {
		
		String result = null;
		try {
			result = mvc.perform(get("/api/playMatch")
				      .contentType(MediaType.APPLICATION_JSON)
				      .param("user", TEST_USER_NAME))
				      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		} catch (UnsupportedEncodingException e) {
			fail("There has been an UnsupportedEncodingException sending the request");
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
		
		assertEquals(TEST_USER_NAME, testGame.getUser(),"A game with TEST_USER_NAME must be returned");

	}
	
	@Test
	@Order(2)
	public void playMatchWithNullUserThenMustBeReturnedAGame() {

		String result = null;
		try {
			result = mvc.perform(get("/api/playMatch")
				      .contentType(MediaType.APPLICATION_JSON)
				      .param("user", "null"))
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

		assertEquals(Constants.USER_ID_LENGTH, testGame.getUser().length(), "A game with user of "+ Integer.toString(Constants.USER_ID_LENGTH)+"  must be returned");

	}
	
	@Test
	@Order(3)
	public void restartAUserGameThenAEmptyGameShouldBeReturned() {
		
		int zero = 0;
		
		String result = null;
		try {
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
		
		String result = null;
		try {
			result = mvc.perform(get("/api/restartGame")
				      .contentType(MediaType.APPLICATION_JSON)
				      .content("user='null'"))
				      .andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
		} catch (Exception e1) {
			fail("There has been an Exception sending the request");
		}
		
		if(result == null) {
			fail("The mvc request should return an exception, not null");
		}
		
		assertEquals(Constants.NO_USER_EXCEPTION_MESSAGE, result, "The message must be: "+ Constants.NO_USER_EXCEPTION_MESSAGE);

	}
	
}
