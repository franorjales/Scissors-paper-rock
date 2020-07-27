package lottoland.Services;

import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.emptyOrNullString;
import org.springframework.boot.test.context.SpringBootTest;
import lottoland.Commons.Constants;
import lottoland.Exceptions.*;
import lottoland.Interfaces.*;
import lottoland.Model.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameServicesImplTests {
	
	@Mock
    private static IGameRepository gRepository;
	
	@InjectMocks
	private IGameServices gService;
	
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	private static final String TEST_USER_NAME_NO_INSERTED = "TEST_USER_NAME_NO_INSERTED";
	private static final String RESTART_TEST_USER_NAME = "RESTART_TEST_USER_NAME";
	private static IGame testUserGame = new Game(TEST_USER_NAME);
	
	@BeforeAll
	public static void setMockOutput() throws NoGameFoundException {
		
		int numberOfMatchesForNMatchesTest = 3;
		
		for(int i= 0; i < numberOfMatchesForNMatchesTest; i++) {
			testUserGame.addMatch(new Match(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE, Constants.WINNER_PLAYER_TWO_ID));
		}

        when(gRepository.checkUser(anyString())).thenAnswer(new Answer<Boolean>() {

			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
			    Boolean result=false;
			    
			    String argument = (String)invocation.getArgument(0);
			    
			    if(argument == null) {
			    	return false;
			    }
			    
			    switch (argument) {
			        case "TEST_USER_NAME":
			        case "RESTART_TEST_USER_NAME":
			            result = true; 
			            break;
			        default:
			            result = false;
			            break;
			    }
			    return result;
			}
        });
        when(gRepository.addNewGame()).thenReturn(new Game(TEST_USER_NAME_NO_INSERTED));
		when(gRepository.getGame(eq(TEST_USER_NAME))).thenReturn(new Game(TEST_USER_NAME));

	}
		
	@Test
	@Order(1)
	public void playGameForNoInsertedUserThenItMustBeReturnedANewGameWithOneMatchPlayedAndTheUserID() {
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
		NoUserException e = assertThrows(NoUserException.class, () -> gService.restartUserGame(null), "There must be an exception passing a null user to the restart game function");
		assertEquals(Constants.NO_USER_EXCEPTION_MESSAGE, e.getMessage(), "The message must be: "+ Constants.NO_USER_EXCEPTION_MESSAGE);
	}
	
	@Test
	@Order(4)
    public void playAGameThenItMustReturnOneRecordWithPlayerOneChoosePlayerTwoChooseAndTheResultOfTheMatch() {
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
    public void playingNMatchesThenNMatchesMustBeReturned() {
		int numberOfMatches = testUserGame.getNumberOfmatchesPlayeds();
		IGame userGame = null;
		for(int i = 0; i < numberOfMatches; i++) {
			try {
				userGame = gService.playMatch(TEST_USER_NAME);
			} catch (NoGameFoundException e) {
				fail("An exception has occurred playing a match");
			}
		}
		
		if(userGame == null) {
			fail("The method playMatch() must return a game");
		}
		
		assertEquals(numberOfMatches, userGame.getNumberOfmatchesPlayeds(),"There must be " + numberOfMatches + " matches already played");

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
			
		assertEquals(Constants.WINNER_PLAYER_ONE_ID, gService.calculateMatch(Constants.ROCK_VALUE, Constants.ROCK_VALUE).getWinner(),"Player one must wins");
	}
	


}
