package lottoland.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lottoland.Interfaces.IGame;

public class GameDTO {
	private List<MatchDTO> matches;
	private String user;

	public GameDTO(String user) {
		this.user = user;
		this.matches = new ArrayList<MatchDTO>();
	}
	
	public GameDTO(IGame game) {
		this.user = game.getUser();
		this.matches = game.getMatches().stream().map((match) -> new MatchDTO(match)).collect(Collectors.toList());
		
	}
	
	public int getNumberOfmatchesPlayeds() {
		return this.matches.size();
	}

	public String getUser() {
		return this.user;
	}

	public List<MatchDTO> getMatches() {
		return this.matches;
	}

	public void addMatch(MatchDTO newMatch) {
		this.matches.add(newMatch);		
	}
}
