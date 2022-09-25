package chusco.model;

import chusco.interfaces.IMatch;

public class MatchDTO {

	private int playerOneChoose;
	private int playerTwoChoose;
	private int winner;
	
	public MatchDTO() {}
	
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

	/**
	 * @param playerOneChoose the playerOneChoose to set
	 */
	public void setPlayerOneChoose(int playerOneChoose) {
		this.playerOneChoose = playerOneChoose;
	}

	/**
	 * @param playerTwoChoose the playerTwoChoose to set
	 */
	public void setPlayerTwoChoose(int playerTwoChoose) {
		this.playerTwoChoose = playerTwoChoose;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}

}
