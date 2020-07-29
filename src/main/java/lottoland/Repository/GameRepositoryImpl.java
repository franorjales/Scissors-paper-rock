package lottoland.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lottoland.Commons.Constants;
import lottoland.Exceptions.NoGameFoundException;
import lottoland.Interfaces.IGame;
import lottoland.Interfaces.IGameRepository;
import lottoland.Model.Game;

public class GameRepositoryImpl implements IGameRepository{
	
	private List<IGame> gameDB;
	private List<IGame> historicalGameBD;
	
	public GameRepositoryImpl() {
		this.gameDB =  new ArrayList<IGame>();
		this.historicalGameBD = new ArrayList<IGame>();
	}
	
	@Override
	public IGame addNewGame() {
		boolean isValidUser = false;
		String user = "";
		
		while(!isValidUser) {
			user = this.generateRandomString();
			isValidUser = !this.checkUser(user);
		}
		
		IGame newGame = new Game(user);
		
		this.gameDB.add(newGame);
		this.addHistoricalGame(newGame);
		return newGame;
	};
	
	@Override
	public void updateGame(IGame game) {
		this.gameDB = this.gameDB.stream().map((g) -> g.getUser().equals(game.getUser()) ? game : g).collect(Collectors.toList());
		this.updateHistoricalGame(game);
	};
	
	@Override
	public Boolean checkUser(String user) {
		Boolean userExist = false;
		if(user == null) {
			return userExist;
		}
		
		for (IGame game : this.gameDB) {
			if(game.getUser().equals(user)) {
				userExist = true;
			}; 
		}
		
		return userExist;
	};
	
	@Override
	public IGame getGame(String user) throws NoGameFoundException{
		IGame returnGame = null;
		
		for (IGame game : this.gameDB) {
			if(game.getUser().equals(user)) {
				returnGame = game;
			}; 
		}
		
		if(returnGame==null) {
			throw new NoGameFoundException(Constants.NO_GAME_FOUND_EXCEPTION_MESSAGE);
		}
		
		return returnGame;
	};
	
	@Override
	public String generateRandomString() {
		// chose a Character random from this String 
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(Constants.USER_ID_LENGTH); 
  
        for (int i = 0; i < Constants.USER_ID_LENGTH; i++) { 
  
            // generate a random number between 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 

            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
	}

	@Override
	public List<IGame> getHistoricalGames() {
		return this.historicalGameBD;
	}

	private void addHistoricalGame(IGame game) {
		this.historicalGameBD.add(game);
		
	}

	private void updateHistoricalGame(IGame game) {
		this.historicalGameBD = this.historicalGameBD.stream().map((g) -> g.getUser().equals(game.getUser()) ? game : g).collect(Collectors.toList());		
	};
}
