package lottoland.Model;

import lottoland.Interfaces.IMatch;

public class Match implements IMatch{
	
	private int playerOneChoose;
	private int playerTwoChoose;
	private int winner;
	
	public Match(int playerOneChoose, int playerTwoChoose, int winner) {
		this.playerOneChoose = playerOneChoose;
		this.playerTwoChoose = playerTwoChoose;
		this.winner = winner;
	}

	@Override
	public int getPlayerOneChoose() {
		return this.playerOneChoose;
	}

	@Override
	public int getPlayerTwoChoose() {
		return this.playerTwoChoose;

	}

	@Override
	public int getWinner() {
		return this.winner;

	}

}
