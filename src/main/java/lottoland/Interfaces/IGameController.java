package lottoland.Interfaces;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Exceptions.NoUserException;
import lottoland.Model.GameDTO;
import lottoland.Model.HistoricalGamesDTO;

public interface IGameController {

	public GameDTO playMatch(String user) throws NoGameFoundException;
	public GameDTO restartUserGame(String user) throws NoUserException;
	public HistoricalGamesDTO getHistoricalGames();
}
