package chusco.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chusco.exceptions.NoGameFoundException;
import chusco.exceptions.NoUserException;
import chusco.interfaces.IGame;
import chusco.interfaces.IGameController;
import chusco.interfaces.IGameServices;
import chusco.model.GameDTO;
import chusco.model.HistoricalGamesDTO;

@RestController
public class GameControllerImpl implements IGameController{
	
    private IGameServices gService; 
	
    @Autowired
	public GameControllerImpl(IGameServices gService) {
		this.gService = gService;
	}
		
	@Override
	@GetMapping("/api/playMatch")
	public GameDTO playMatch(@RequestParam(required = false)String user) throws NoGameFoundException {
		
		IGame game = this.gService.playMatch(user);
		
		GameDTO gameDto = new GameDTO(game);

		return gameDto;
	}

	@Override
	@GetMapping("/api/restartGame")
	public GameDTO restartUserGame(@RequestParam(required = true)String user) throws NoUserException {
		IGame game = this.gService.restartUserGame(user);
		
		GameDTO gameDto = new GameDTO(game);

		return gameDto;
	}
	
	@Override
	@GetMapping("/api/getHistoricalGames")
	public HistoricalGamesDTO getHistoricalGames() {
		List<IGame> historicalGames = this.gService.getHistoricalGames();
		
		HistoricalGamesDTO historicalGamesDto = new HistoricalGamesDTO(historicalGames);

		return historicalGamesDto;
	}

}
