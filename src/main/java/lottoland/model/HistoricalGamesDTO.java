package lottoland.Model;

import java.util.List;
import java.util.stream.Collectors;

import lottoland.Interfaces.IGame;

public class HistoricalGamesDTO {
	private List<GameDTO> historicalGames;
	
	public HistoricalGamesDTO() {}
	
	public HistoricalGamesDTO(List<IGame> historicalGames) {
		this.historicalGames = historicalGames.stream().map((g) -> new GameDTO(g)).collect(Collectors.toList());
	}

	public List<GameDTO> getHistoricalGames() {
		return historicalGames;
	}

	public void setHistoricalGames(List<GameDTO> historicalGames) {
		this.historicalGames = historicalGames;
	} 
	
}
