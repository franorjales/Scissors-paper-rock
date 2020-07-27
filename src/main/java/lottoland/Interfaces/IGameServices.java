package lottoland.Interfaces;

import lottoland.Exceptions.NoGameFoundException;
import lottoland.Exceptions.NoUserException;

public interface IGameServices {
	public IGame playMatch(String user, int playerOneChoose) throws NoGameFoundException;
	public IGame playMatch(String user) throws NoGameFoundException;
	public IGame restartUserGame(String user) throws NoUserException;
	public IMatch calculateMatch(int playerOneChoose, int playerTwoChoose);

}
