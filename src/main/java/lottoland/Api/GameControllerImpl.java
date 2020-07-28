package lottoland.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Exceptions.NoUserException;
import lottoland.Interfaces.IGame;
import lottoland.Interfaces.IGameController;
import lottoland.Interfaces.IGameServices;
import lottoland.Model.GameDTO;

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

}
