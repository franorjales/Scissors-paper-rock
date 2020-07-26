package lottoland.Repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.oneOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Model.Game;
import lottoland.Model.Match;
import lottoland.Repository.GameRepositoryImpl;

public class GameRepositoryImplTests {
	
    private GameRepositoryImpl gRepository;

	//TODO Constants -> USER_ID_LENGTH
    
    
	@BeforeEach
	public void prepareTests() {
		this.gRepository = new GameRepositoryImpl();
	}

	@Test
	@Order(1)
	public void addNewGameThenTheNewGameShouldExist() {
		int zero = 0;
		Game newGame = this.gRepository.addNewGame();
		
		assertEquals(zero, newGame.getNumberOfmatchesPlayeds(),"There must be zero played matches in a new game");
		assertEquals(newGame.getUser(), this.gRepository.getGame(newGame.getUser()).getUser(),"The game previously created must exist in the BBDD");

	}
	
	@Test
	@Order(2)
	public void updateAGameThenTheGameShouldBeSavedUpdated() {
		Game gameTest = this.gRepository.addNewGame();
		Match newMatch = new Match();
		
		newMatch.setPlayerOneChoose(Constants.SCISSORS_VALUE);
		newMatch.setPlayerTwoChoose(Constants.ROCK_VALUE);
		newMatch.setWinner(Constants.WINNER_PLAYER_TWO_ID);
		
		gameTest.addMatch(newMatch);
		
		Game updatedGame = this.gRepository.updateGame(gameTest);
		
		assertEquals(gameTest.getMatchsInfo.get(0).getPlayerOneChoose(), updatedGame.getMatchsInfo.get(0).getPlayerOneChoose(),"The player one choose must be the same in both games");
		assertEquals(gameTest.getMatchsInfo.get(0).getPlayerTwoChoose(), updatedGame.getMatchsInfo.get(0).getPlayerTwoChoose(),"The player two choose must be the same in both games");
		assertEquals(gameTest.getMatchsInfo.get(0).getWinner, updatedGame.getMatchsInfo.get(0).getWinner,"The winner must be the same in both players");	

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
		Game gameTest = this.gRepository.addNewGame();
		boolean userExist = this.gRepository.checkUser(gameTest.getUser());
		
		assertTrue(userExist, "The user should exist");
	}
	
	@Test
	@Order(4)
	public void CheckIfExistANonInsertedUserOrNullThenItMustReturnFalse() {
		
		boolean userExist = this.gRepository.checkUser("");
		boolean userExistNull = this.gRepository.checkUser(null);
		
		assertFalse(userExist, "The user should not exist");
		assertFalse(userExistNull, "The user should not exist");

	}
	
	@Test
	@Order(4)
	public void CheckIfExistACreatedGameThenItMustReturnTheGame() {
		Game gameTest = this.gRepository.addNewGame();
		Game gameFounded = this.gRepository.getGame(gameTest.getUser());
		
		assertEquals(gameTest.getUser(), gameFounded.getUser(),"Both games should have the same user");	
	}
	
	@Test
	@Order(5)
	public void CheckIfExistANONCreatedGameThenAnExceptionMustBeThrown() {
		
		NoGameFoundException e = assertThrows(NoGameFoundException.class, this.gRepository.getGame(null), "There must be an exception passing a null user to the get game function");
		assertThat(e, "The message must be: "+ Constants.NO_GAME_FOUND_EXCEPTION_MESSAGE).hasMessageThat().contains(Constants.NO_GAME_FOUND_EXCEPTION_MESSAGE);
	}
	  
	
}
