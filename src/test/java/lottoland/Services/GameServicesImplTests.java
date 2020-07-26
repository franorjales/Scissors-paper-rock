package lottoland.Services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.eq;
import static org.hamcrest.MatcherAssert.assertThat; 
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;

import lottoland.Exceptions.NoUserException;
import lottoland.Model.Match;
import lottoland.Repository.GameRepositoryImpl;
import lottoland.Services.GameServicesImpl;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class GameServicesImplTests {
	
	@Mock
    private static GameRepositoryImpl gRepository;
	
	@InjectMocks
	private GameServicesImpl gService;
	

	private static Game testUserGame = new Game();
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
	private static final String TEST_USER_NAME_NO_INSERTED = "TEST_USER_NAME_NO_INSERTED";
	private static final String RESTART_TEST_USER_NAME = "RESTART_TEST_USER_NAME";
	private static final String TEST_USER_NAME_ONE_MATCH = "TEST_USER_NAME_ONE_MATCH";

	/* TODO
	 *  Constants -> NO_USER_EXCEPTION_MESSAGE = "Does not exist a user with the given ID"
	 *			  ->  WINNER_PLAYER_ONE_ID
	 *			  ->  WINNER_PLAYER_TWO_ID
	 *			  ->  DRAW_ID
	 *			  ->  SCISSORS_VALUE
	 *			  ->  ROCK_VALUE
	 *			  ->  PAPER_VALUE
	 *
	 *
	 *	Beans -> Game 
	 *		  -> Match
	 *		  -> Player
	 *
	 *	GameServiceImplementation methods -> playMatch(user)
	 *									  -> playMatch(user, playerOneValue)
	 *									  -> restartUser()
	*/
	
	
	@BeforeAll
	public static void setMockOutput() {
		
		int numberOfMatchesForNMatchesTest = 3;
		
		for(int i= 0; i < numberOfMatchesForNMatchesTest; i++) {
			testUserGame.addMatch(new Match());
		}

        when(gRepository.checkUser(not(is(oneOf(TEST_USER_NAME, RESTART_TEST_USER_NAME)))).thenAnswer(false));
        when(gRepository.checkUser(isNull())).thenReturn(false);
        when(gRepository.checkUser(eq(TEST_USER_NAME)).thenReturn(true));
        when(gRepository.checkUser(eq(RESTART_TEST_USER_NAME)).thenReturn(true));
        when(gRepository.addNewGame(eq(TEST_USER_NAME_NO_INSERTED)).thenReturn(new Game(TEST_USER_NAME_NO_INSERTED)));
        when(gRepository.updateGame(any(Game.class))).thenAnswer(i -> i.getArgumentAt(0, Game.class).getUser().equals(TEST_USER_NAME) ? testUserGame : i.getArgumentAt(0, Game.class));
        when(gRepository.getGame(eq(TEST_USER_NAME)).thenReturn(new Game(TEST_USER_NAME)));

	}
		
	@Test
	@Order(1)
	public void playGameForNoInsertedUserThenItMustBeReturnedANewGameWithOneMatchPlayedAndTheUserID() {
		int one = 1;
		Game g = gService.playMatch(TEST_USER_NAME_NO_INSERTED);
		assertEquals(one, g.getNumberOfmatchesPlayeds(),"There must be one played matches");
		assertThat(g, is(not(emptyOrNullString())), "There must be an user setted in the game");
	}
	
	@Test
	@Order(2)
    public void doARestartThenZeroResultsShouldBeReturned() {
		int zero = 0;
		assertEquals(zero, gService.restartUserGame(RESTART_TEST_USER_NAME).getNumberOfmatchesPlayeds(),"There must be zero played matches");
	}
	
	@Test
	@Order(3)
    public void doARestartWithAnInvalidUserThenNoUserExceptionShouldBeThrown() {
		int zero = 0;
		NoUserException e = assertThrows(NoUserException.class, gService.restartUserGame(null), "There must be an exception passing a null user to the restart game function");

		assertThat(e, "The message must be: "+ Constants.NO_USER_EXCEPTION_MESSAGE).hasMessageThat().contains(Constants.NO_USER_EXCEPTION_MESSAGE);
		assertEquals(zero, gService.restartUserGame(null).getNumberOfmatchesPlayeds(),"There must be zero played matches");
	}
	
	@Test
	@Order(4)
    public void playAGameThenItMustReturnOneRecordWithPlayerOneChoosePlayerTwoChooseAndTheResultOfTheMatch() {
		int numberOfMatches = 1;
		Game userGame = gService.playMatch(TEST_USER_NAME_NO_INSERTED, SCISSORS_VALUE);
		
		assertEquals(numberOfMatches, userGame.getNumberOfmatchesPlayeds(),"There must be " + numberOfMatches + " matches already played");
		assertEquals(Constants.SCISSORS_VALUE, userGame.getMatchsInfo.get(0).getPlayerOneChoose(),"The player one choose must be scissors");
		assertEquals(Constants.ROCK_VALUE, userGame.getMatchsInfo.get(0).getPlayerTwoChoose(),"The player two choose must be rock");
		assertEquals(Constants.WINNER_PLAYER_TWO_ID, userGame.getMatchsInfo.get(0).getWinner,"The winner must be player two");		
	}
	
	@Test
	@Order(5)
    public void playingNMatchesThenNMatchesMustBeReturned() {
		int numberOfMatches = testUserGame.getMatches().size();
		Game userGame = new Game();
		for(int i = 0; i < numberOfMatches; i++) {
			userGame = gService.playMatch(TEST_USER_NAME);
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
				
		assertEquals(Constants.DRAW_ID, gService.calculateMatch(Constants.ROCK_VALUE, Constants.ROCK_VALUE).getWinner(),"It must be a draw");
	}
	
	@Test
	@Order(8)
    public void givenPaperforPlayerOneThenHeWin() {
			
		assertEquals(Constants.WINNER_PLAYER_ONE_ID, gService.calculateMatch(Constants.ROCK_VALUE, Constants.ROCK_VALUE).getWinner(),"Player one must wins");
	}
	


}
