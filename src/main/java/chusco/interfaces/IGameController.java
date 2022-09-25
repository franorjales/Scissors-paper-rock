package chusco.interfaces;

import chusco.exceptions.NoGameFoundException;
import chusco.exceptions.NoUserException;
import chusco.model.GameDTO;
import chusco.model.HistoricalGamesDTO;

public interface IGameController {

	public GameDTO playMatch(String user) throws NoGameFoundException;
	public GameDTO restartUserGame(String user) throws NoUserException;
	public HistoricalGamesDTO getHistoricalGames();
}
