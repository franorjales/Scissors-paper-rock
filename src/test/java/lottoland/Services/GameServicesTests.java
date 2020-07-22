package lottoland.Services;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.boot.test.context.SpringBootTest;

import lottoland.services.GameServices;

@SpringBootTest
public class GameServicesTests {
	
	GameServices gService = new GameServices();
	
	@Test
	public void insertANewUserThenTheNewUserWillExist() {
		//TODO the idea is that it must exist a method that create users with differents ids and return them to the front
	}
	
	@Test
	public void insertNUsersThenCheckTheyAllHaveDifferentIds() {
	}

	@Test
    public void afterPlayNMatchesOfTheGameAndDoARestartThenZeroResultsShouldBeReturned() {
		int numberOfMatches = 3;
		int zero = 0;
		for(int i = 0; i < numberOfMatches; i++) {
			gService.playMatch(TEST_USER_NAME);
		}
		
		gService.restartUserGame(TEST_USER_NAME);
		assertEquals(zero, gService.getGameForUser(TEST_USER_NAME).getNumberOfmatchesPlayeds(),"There must be zero played matches");

	}
	
	@Test
    public void playingNMatchesThenNMatchesMustBeReturned() {
		int numberOfMatches = 3;
		for(int i = 0; i < numberOfMatches; i++) {
			gService.playMatch(TEST_USER_NAME);
		}
		
		assertEquals(numberOfMatches, gService.getGameForUser(TEST_USER_NAME).getNumberOfmatchesPlayeds(),"There must be " + numberOfMatches + " matches already played");

	}

	@Test
    public void givenScissorsforPlayerOneThenHeLoses() {
		
		assertEquals(WINNER_PLAYER_TWO_ID, gService.calculateMatch(SCISSORS_VALUE, ROCK_VALUE).getWinner(),"Player one must loses");
		
	}
	
	@Test
    public void givenRockforPlayerOneThenItsDraw() {
				
		assertEquals(DRAW_ID, gService.calculateMatch(ROCK_VALUE, ROCK_VALUE).getWinner(),"It must be a draw");
	}
	
	@Test
    public void givenPaperforPlayerOneThenHeWin() {
			
		assertEquals(WINNER_PLAYER_ONE_ID, gService.calculateMatch(ROCK_VALUE, ROCK_VALUE).getWinner(),"Player one must wins");
	}
	
	
	@Test
    public void playAGameThenItMustReturnOneRecordWithPlayerOneChoosePlayerTwoChooseAndTheResultOfTheMatch() {
		int numberOfMatches = 1;
		gService.playMatch(TEST_USER_NAME, SCISSORS_VALUE, ROCK_VALUE);
		
		assertEquals(numberOfMatches, gService.getGameForUser(TEST_USER_NAME).getNumberOfmatchesPlayeds(),"There must be " + numberOfMatches + " matches already played");
		assertEquals(SCISSORS_VALUE, gService.getGameForUser(TEST_USER_NAME).getMatchsInfo.get(0).getPlayerOneChoose(),"The player one choose must be scissors");
		assertEquals(ROCK_VALUE, gService.getGameForUser(TEST_USER_NAME).getMatchsInfo.get(0).getPlayerTwoChoose(),"The player two choose must be rock");
		assertEquals(WINNER_PLAYER_TWO_ID, gService.getGameForUser(TEST_USER_NAME).getMatchsInfo.get(0).getWinner,"The winner must be player two");		
	}
	
}
