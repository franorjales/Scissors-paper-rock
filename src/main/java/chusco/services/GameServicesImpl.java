package chusco.services;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;

import chusco.commons.Constants;
import chusco.exceptions.NoGameFoundException;
import chusco.exceptions.NoUserException;
import chusco.interfaces.IGame;
import chusco.interfaces.IGameRepository;
import chusco.interfaces.IGameServices;
import chusco.interfaces.IMatch;
import chusco.model.Game;
import chusco.model.Match;

public class GameServicesImpl implements IGameServices{

	private IGameRepository gRepository;
	
	@Autowired
	public GameServicesImpl(IGameRepository gRepository) {
		this.gRepository = gRepository;
	}

	@Override
	public IGame playMatch(String user, int playerOneChoose) throws NoGameFoundException {
		Boolean userExist = this.gRepository.checkUser(user);
		IGame game = null;
		if(!userExist) {
			game = this.gRepository.addNewGame();
		} else {
			game = this.gRepository.getGame(user);
		}
		
		IMatch newMatch = this.calculateMatch(playerOneChoose, Constants.ROCK_VALUE);
		game.addMatch(newMatch);
		this.gRepository.updateGame(game);
		
		return game;

	}

	@Override
	public IGame playMatch(String user) throws NoGameFoundException{
		int randomNum = ThreadLocalRandom.current().nextInt(Constants.SCISSORS_VALUE, Constants.ROCK_VALUE + 1);
		return this.playMatch(user, randomNum);
	}

	@Override
	public IGame restartUserGame(String user) throws NoUserException{
		Boolean userExist = this.gRepository.checkUser(user);
		if(!userExist) {
			throw new NoUserException(Constants.NO_USER_EXCEPTION_MESSAGE);
		}
		IGame newGame = new Game(user);
		
		this.gRepository.updateGame(newGame);
		
		return newGame;
	}

	@Override
	public IMatch calculateMatch(int playerOneChoose, int playerTwoChoose) {
		int winner = 0;
		
		
		if(playerOneChoose == Constants.SCISSORS_VALUE && playerTwoChoose == Constants.ROCK_VALUE ||
				playerOneChoose == Constants.PAPER_VALUE && playerTwoChoose == Constants.SCISSORS_VALUE ||
				playerOneChoose == Constants.ROCK_VALUE && playerTwoChoose == Constants.PAPER_VALUE ) {
			winner = Constants.WINNER_PLAYER_TWO_ID;
		} else if(playerOneChoose == playerTwoChoose) {
			winner = Constants.DRAW_VALUE;
		} else {
			winner = Constants.WINNER_PLAYER_ONE_ID;
		}
		
		return new Match(playerOneChoose, playerTwoChoose, winner);

	}

	@Override
	public List<IGame> getHistoricalGames() {
		return this.gRepository.getHistoricalGames();
	}
	
	

}
