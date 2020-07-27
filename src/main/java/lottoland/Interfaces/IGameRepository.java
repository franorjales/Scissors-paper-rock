package lottoland.Interfaces;

import lottoland.Model.Game;

public interface IGameRepository {
	public Game addNewGame();
	public Game updateGame(Game game);
	public boolean checkUser(String user);
	public Game getGame(String user);
	public String generateRandomString();
}
