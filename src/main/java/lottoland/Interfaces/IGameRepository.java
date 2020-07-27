package lottoland.Interfaces;

import lottoland.Exceptions.NoGameFoundException;

public interface IGameRepository {
	public IGame addNewGame();
	public void updateGame(IGame game);
	public Boolean checkUser(String user);
	public IGame getGame (String user) throws NoGameFoundException;
	public String generateRandomString();
}
