package lottoland.Repository;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import lottoland.Commons.Constants;
import lottoland.Exceptions.NoGameFoundException;
import lottoland.Interfaces.IGame;
import lottoland.Interfaces.IGameRepository;
import lottoland.Interfaces.IMatch;
import lottoland.Model.Game;
import lottoland.Model.Match;
import lottoland.Portal.PortalApplication;
import lottoland.Repository.GameRepositoryImpl;

@SpringBootTest(classes = PortalApplication.class)
public class GameRepositoryImplTests {
	
    private IGameRepository gRepository;
    
	private static final String TEST_USER_NAME = "TEST_USER_NAME";
    
	@BeforeEach
	public void prepareTests() {
		this.gRepository = new GameRepositoryImpl();
	}

	@Test
	@Order(1)
	public void addNewGameThenTheNewGameShouldExist() {
		int zero = 0;
		IGame newGame = this.gRepository.addNewGame();
		
		assertEquals(zero, newGame.getNumberOfmatchesPlayeds(),"There must be zero played matches in a new game");
		try {
			assertEquals(newGame.getUser(), this.gRepository.getGame(newGame.getUser()).getUser(),"The game previously created must exist in the BBDD");
		} catch (NoGameFoundException e) {
			fail("An exception has occurred retrieving the inserted game");
		}

	}
	
	@Test
	@Order(2)
	public void updateAGameThenTheGameShouldBeSavedUpdated() {
		IGame gameTest = this.gRepository.addNewGame();
		IMatch newMatch = new Match(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE , Constants.WINNER_PLAYER_TWO_ID);
		
		gameTest.addMatch(newMatch);
		
		this.gRepository.updateGame(gameTest);
		
		IGame updatedGame = null;
		try {
			updatedGame = this.gRepository.getGame(gameTest.getUser());
		} catch (NoGameFoundException e) {
			// TODO Auto-generated catch block
			fail("An exception has occurred retrieving the inserted game");
		}
		
		if(updatedGame == null) {
			fail("The inserted game cannot be null");

		}
		
		assertEquals(gameTest.getMatches().get(0).getPlayerOneChoose(), updatedGame.getMatches().get(0).getPlayerOneChoose(),"The player one choose must be the same in both games");
		assertEquals(gameTest.getMatches().get(0).getPlayerTwoChoose(), updatedGame.getMatches().get(0).getPlayerTwoChoose(),"The player two choose must be the same in both games");
		assertEquals(gameTest.getMatches().get(0).getWinner(), updatedGame.getMatches().get(0).getWinner(),"The winner must be the same in both players");	

	}
	
	@Test
	@Order(3)
	public void generateRandomStringThenTheNewRandomStringMustHaveFixedLength() {
		String randomString = this.gRepository.generateRandomString();
		
		assertEquals(Constants.USER_ID_LENGTH, randomString.length(),"The generated random string length should be" + Integer.toString(Constants.USER_ID_LENGTH));

	}
	
	@Test
	@Order(4)
	public void createANewUserAndCheckIfExistThenItMustReturnTrue() {
		IGame gameTest = this.gRepository.addNewGame();
		boolean userExist = this.gRepository.checkUser(gameTest.getUser());
		
		assertTrue(userExist, "The user should exist");
	}
	
	@Test
	@Order(4)
	public void checkIfExistANonInsertedUserOrNullThenItMustReturnFalse() {
		
		boolean userExist = this.gRepository.checkUser("");
		boolean userExistNull = this.gRepository.checkUser(null);
		
		assertFalse(userExist, "The user should not exist");
		assertFalse(userExistNull, "The user should not exist");

	}
	
	@Test
	@Order(4)
	public void checkIfExistACreatedGameThenItMustReturnTheGame() {
		IGame gameTest = this.gRepository.addNewGame();
		IGame gameFounded;
		try {
			gameFounded = this.gRepository.getGame(gameTest.getUser());
			assertEquals(gameTest.getUser(), gameFounded.getUser(),"Both games should have the same user");	
		} catch (NoGameFoundException e) {
			fail("Exception thrown when retrieve an existing game");
		}
		
		
	}
	
	@Test
	@Order(5)
	public void checkIfExistANonCreatedGameThenAnExceptionMustBeThrown() {
		
		NoGameFoundException e = assertThrows(NoGameFoundException.class,() -> this.gRepository.getGame(null), "There must be an exception passing a null user to the get game function");
		assertEquals(Constants.NO_GAME_FOUND_EXCEPTION_MESSAGE, e.getMessage(), "The message must be: "+ Constants.NO_GAME_FOUND_EXCEPTION_MESSAGE);

	}
	  
	@Test
	@Order(6)
	public void addANewGameThenCheckIfItExistAsHistoricalRecord() {
		
		IGame newGame = this.gRepository.addNewGame();
		
		List<IGame> historicalGameDB = this.gRepository.getHistoricalGames();
		
		IGame insertedGame = historicalGameDB.stream()
				  .filter(game -> newGame.getUser().equals(game.getUser()))
				  .findAny()
				  .orElse(null);
		
		if(insertedGame == null) {
			fail("The new historical game must be saved");
		}
	}
	
	@Test
	@Order(6)
	public void addANewHistoricalRecordThenUpdateItThenCheckIfItIsUpdated() {
		
		IGame newGame = this.gRepository.addNewGame();
		
		newGame.addMatch(new Match(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE, Constants.WINNER_PLAYER_TWO_ID));
		
		this.gRepository.updateGame(newGame);
		
		List<IGame> historicalGameDB = this.gRepository.getHistoricalGames();
		
		IGame insertedGame = historicalGameDB.stream()
				  .filter(game -> newGame.getUser().equals(game.getUser()))
				  .findAny()
				  .orElse(null);
		
		if(insertedGame == null) {
			fail("The new historical game must be saved");
		}
		
		assertEquals(insertedGame.getMatches().get(0).getPlayerOneChoose(), Constants.SCISSORS_VALUE, "The player one choose must be: "+ Constants.SCISSORS_VALUE);
		assertEquals(insertedGame.getMatches().get(0).getPlayerTwoChoose(), Constants.ROCK_VALUE, "The player Two choose must be: "+ Constants.ROCK_VALUE);
		assertEquals(insertedGame.getMatches().get(0).getWinner(), Constants.WINNER_PLAYER_TWO_ID, "The winner one choose must be: "+ Constants.WINNER_PLAYER_TWO_ID);
		
	}
	
}
