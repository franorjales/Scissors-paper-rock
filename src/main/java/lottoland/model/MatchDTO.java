package lottoland.Model;

import lottoland.Interfaces.IMatch;

public class MatchDTO {

	private int playerOneChoose;
	private int playerTwoChoose;
	private int winner;
	
	public MatchDTO(int playerOneChoose, int playerTwoChoose, int winner) {
		this.playerOneChoose = playerOneChoose;
		this.playerTwoChoose = playerTwoChoose;
		this.winner = winner;
	}
	
	public MatchDTO(IMatch match) {
		this.playerOneChoose = match.getPlayerOneChoose();
		this.playerTwoChoose = match.getPlayerTwoChoose();
		this.winner = match.getWinner();
	}

	public int getPlayerOneChoose() {
		return this.playerOneChoose;
	}

	public int getPlayerTwoChoose() {
		return this.playerTwoChoose;

	}

	public int getWinner() {
		return this.winner;

	}

}
