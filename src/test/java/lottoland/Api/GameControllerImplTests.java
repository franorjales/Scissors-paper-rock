package lottoland.Api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.hasSize;

import lottoland.Commons.Constants;
import lottoland.Exceptions.NoUserException;
import lottoland.Model.Game;
import lottoland.Model.Match;
import lottoland.Repository.GameRepositoryImpl;
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
	private GameControllerImpl gController;
	
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	
	@BeforeAll
	public static void setMockOutput() {

        when(gService.playMatch(anyString()).thenAnswer(i -> new Game(i.getArgumentAt(0, String.class))));
        when(gService.playMatch(isNull()).thenAnswer(new Game(TEST_USER_NAME)));
        when(gService.restartUserGame(TEST_USER_NAME).thenThrow(i -> new Game(TEST_USER_NAME)));
        when(gService.restartUserGame(is(not(eq(TEST_USER_NAME)))).thenThrow(new NoUserException()));

	}
	
	@Test
	@Order(1)
	public void playMatchWithUserThenMustBeReturnedAGame() {
		
		String result = mvc.perform(get("/api/playMatch")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("user='TEST_USER_NAME'"))
			      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		Game testGame = objectMapper.readValue(result, Game.class);
		
		assertEquals(TEST_USER_NAME, testGame.getUser(),"A game with TEST_USER_NAME must be returned");

	}
	
	@Test
	@Order(2)
	public void playMatchWithNullUserThenMustBeReturnedAGame() {

		String result = mvc.perform(get("/api/playMatch")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("user='null'"))
			      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		Game testGame = objectMapper.readValue(result, Game.class);
		
		assertThat("A game with user of "+ Integer.toString(Constants.USER_ID_LENGTH)+"  must be returned", testGame.getUser(), hasSize(Constants.USER_ID_LENGTH));

	}
	
	@Test
	@Order(3)
	public void restartAUserGameThenAEmptyGameShouldBeReturned() {
		
		int zero = 0;
		
		String result = mvc.perform(get("/api/restartGame")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("user='TEST_USER_NAME'"))
			      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		Game testGame = objectMapper.readValue(result, Game.class);
		
		assertEquals(zero, testgame.getNumberOfmatchesPlayeds(),"There must be zero matcheds played");

	}
	
	@Test
	@Order(4)
	public void restartANotInsertedUserGameThenAnExceptionMustBeThrown() {
		
		String result = mvc.perform(get("/api/restartGame")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content("user='null'"))
			      .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		NoUserException e = objectMapper.readValue(result, NoUserException.class);
		
		assertEquals(Constants.NO_USER_EXCEPTION_MESSAGE, e.getMessage(), "The message must be: "+ Constants.NO_USER_EXCEPTION_MESSAGE);

	}
	
}
