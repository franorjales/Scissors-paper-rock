package lottoland.Services;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.hamcrest.CoreMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.emptyOrNullString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import lottoland.Commons.Constants;
import lottoland.Configuration.TestConfig;
import lottoland.Exceptions.*;
import lottoland.Interfaces.*;
import lottoland.Model.*;
import lottoland.Portal.PortalApplication;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@Import(TestConfig.class)
@SpringBootTest(classes = PortalApplication.class)
public class GameServicesImplTests {
		
	@InjectMocks
	private GameServicesImpl gService;
	
	@Mock
    private IGameRepository gRepository;
	
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	private static final String TEST_USER_NAME_NO_INSERTED = "TEST_USER_NAME_NO_INSERTED";
	private static final String RESTART_TEST_USER_NAME = "RESTART_TEST_USER_NAME";
	private static IGame testUserGame = new Game(TEST_USER_NAME);
	
	@BeforeEach
	public void setMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@BeforeAll
	public static void setTestUserGame() throws NoGameFoundException {
		int numberOfMatchesForNMatchesTest = 3;
		for(int i= 0; i < numberOfMatchesForNMatchesTest; i++) {
			testUserGame.addMatch(new Match(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE, Constants.WINNER_PLAYER_TWO_ID));
		}

	}
		
	@Test
	@Order(1)
	public void playGameForNoInsertedUserThenItMustBeReturnedANewGameWithOneMatchPlayedAndTheUserID() {
		when(gRepository.checkUser(anyString())).thenReturn(false);
		when(gRepository.addNewGame()).thenReturn(new Game(TEST_USER_NAME_NO_INSERTED));
		int one = 1;
		IGame testGame = null;
		try {
			testGame = gService.playMatch(TEST_USER_NAME_NO_INSERTED);
		} catch (NoGameFoundException e) {
			fail("An exception has occurred playing a match");
		}
		
		if(testGame == null) {
			fail("The method playMatch() must return a game");
		}
		
		assertEquals(one, testGame.getNumberOfmatchesPlayeds(),"There must be one played matches");
		assertThat("There must be an user setted in the game", testGame.getUser(), is(not(emptyOrNullString())));
	}
	
	@Test
	@Order(2)
    public void doARestartThenZeroResultsShouldBeReturned() {
		when(gRepository.checkUser(RESTART_TEST_USER_NAME)).thenReturn(true);
		int zero = 0;
		try {
			assertEquals(zero, gService.restartUserGame(RESTART_TEST_USER_NAME).getNumberOfmatchesPlayeds(),"There must be zero played matches");
		} catch (NoUserException e) {
			fail("An exception has occurred restarting a user game");
		}
	}
	
	@Test
	@Order(3)
    public void doARestartWithAnInvalidUserThenNoUserExceptionShouldBeThrown() {
		when(gRepository.checkUser(isNull())).thenReturn(false);
		NoUserException e = assertThrows(NoUserException.class, () -> gService.restartUserGame(null), "There must be an exception passing a null user to the restart game function");
		assertEquals(Constants.NO_USER_EXCEPTION_MESSAGE, e.getMessage(), "The message must be: "+ Constants.NO_USER_EXCEPTION_MESSAGE);
	}
	
	@Test
	@Order(4)
    public void playAGameThenItMustReturnOneRecordWithPlayerOneChoosePlayerTwoChooseAndTheResultOfTheMatch() {
		 when(gRepository.addNewGame()).thenReturn(new Game(TEST_USER_NAME_NO_INSERTED));
		int numberOfMatches = 1;
		IGame userGame = null;
		try {
			userGame = gService.playMatch(TEST_USER_NAME_NO_INSERTED, Constants.SCISSORS_VALUE);
		} catch (NoGameFoundException e) {
			fail("An exception has occurred playing a match");
		}
		
		if(userGame == null) {
			fail("The method playMatch() must return a game");
		}
		
		assertEquals(numberOfMatches, userGame.getNumberOfmatchesPlayeds(),"There must be " + numberOfMatches + " matches already played");
		assertEquals(Constants.SCISSORS_VALUE, userGame.getMatches().get(0).getPlayerOneChoose(),"The player one choose must be scissors");
		assertEquals(Constants.ROCK_VALUE, userGame.getMatches().get(0).getPlayerTwoChoose(),"The player two choose must be rock");
		assertEquals(Constants.WINNER_PLAYER_TWO_ID, userGame.getMatches().get(0).getWinner(),"The winner must be player two");		
	}
	
	@Test
	@Order(5)
    public void withNMatchesPlayedplayAnotherMatchThenNMatchesMustBeReturnAGameWithOneMorePlay(){
		int numberOfMatches = testUserGame.getNumberOfmatchesPlayeds();
		IGame userGame = null;
		try {
			 when(gRepository.checkUser(eq(TEST_USER_NAME))).thenReturn(true);
			 when(gRepository.getGame(eq(TEST_USER_NAME))).thenReturn(testUserGame);

			userGame = gService.playMatch(TEST_USER_NAME);

		} catch (NoGameFoundException e) {
			fail("An exception has occurred playing a match");
		}
		
		if(userGame == null) {
			fail("The method playMatch() must return a game");
		}
		
		int totalNumberOfMatches = numberOfMatches+1;
		
		assertEquals(totalNumberOfMatches, userGame.getNumberOfmatchesPlayeds(),"There must be " + totalNumberOfMatches + " matches already played");

	}

	@Test
	@Order(6)
    public void givenScissorsforPlayerOneThenHeLoses() {
		
		assertEquals(Constants.WINNER_PLAYER_TWO_ID, gService.calculateMatch(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE).getWinner(),"Player one must loses");
		
	}
	
	@Test
	@Order(7)
    public void givenRockforPlayerOneThenItsDraw() {
				
		assertEquals(Constants.DRAW_VALUE, gService.calculateMatch(Constants.ROCK_VALUE, Constants.ROCK_VALUE).getWinner(),"It must be a draw");
	}
	
	@Test
	@Order(8)
    public void givenPaperforPlayerOneThenHeWin() {
			
		assertEquals(Constants.WINNER_PLAYER_ONE_ID, gService.calculateMatch(Constants.PAPER_VALUE, Constants.ROCK_VALUE).getWinner(),"Player one must wins");
	}
	


}
