package lottoland.Model;

import java.util.List;
import lottoland.Commons.Constants;
import lottoland.Interfaces.IGame;
import lottoland.Interfaces.IMatch;

public class HistoricalGamesDTO {
	
	private int totalRoundsPlayed;
	private int totalWinsForPlayerOne;
	private int totalWinsForPlayerTwo;
	private int totalDraws;
	
	public HistoricalGamesDTO() {}
	
	public HistoricalGamesDTO(List<IGame> historicalGames) {
		
		this.totalRoundsPlayed = 0;
		this.totalWinsForPlayerOne = 0;
		this.totalWinsForPlayerTwo = 0;
		this.totalDraws = 0;
		
		for(IGame game : historicalGames) {
			this.totalRoundsPlayed += game.getNumberOfmatchesPlayeds();
			
			for(IMatch match : game.getMatches()) {
				
				switch(match.getWinner())
				{
				   case Constants.WINNER_PLAYER_ONE_ID :
					  this.totalWinsForPlayerOne ++;
				      break; 
				   
				   case Constants.WINNER_PLAYER_TWO_ID :
					  this.totalWinsForPlayerTwo ++;
				      break; 

				   case Constants.DRAW_VALUE :
					  this.totalDraws ++;
					  break; 
				   default : 
					   break; 
				}
			}

		}
		
	}

	/**
	 * @return the totalRoundsPlayed
	 */
	public int getTotalRoundsPlayed() {
		return totalRoundsPlayed;
	}

	/**
	 * @param totalRoundsPlayed the totalRoundsPlayed to set
	 */
	public void setTotalRoundsPlayed(int totalRoundsPlayed) {
		this.totalRoundsPlayed = totalRoundsPlayed;
	}

	/**
	 * @return the totalWinsForPlayerOne
	 */
	public int getTotalWinsForPlayerOne() {
		return totalWinsForPlayerOne;
	}

	public void setTotalWinsForPlayerOne(int totalWinsForPlayerOne) {
		this.totalWinsForPlayerOne = totalWinsForPlayerOne;
	}

	public int getTotalWinsForPlayerTwo() {
		return totalWinsForPlayerTwo;
	}

	public void setTotalWinsForPlayerTwo(int totalWinsForPlayerTwo) {
		this.totalWinsForPlayerTwo = totalWinsForPlayerTwo;
	}

	public int getTotalDraws() {
		return totalDraws;
	}

	public void setTotalDraws(int totalDraws) {
		this.totalDraws = totalDraws;
	}
 
	
}
