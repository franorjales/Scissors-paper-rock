package lottoland.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lottoland.Interfaces.IGame;

public class GameDTO {


	private List<MatchDTO> matches;
	private String user;
	private int numberOfmatchesPlayeds;

	public GameDTO() {}
	
	public GameDTO(String user) {
		this.user = user;
		this.matches = new ArrayList<MatchDTO>();
	}
	
	public GameDTO(IGame game) {
		this.user = game.getUser();
		this.matches = game.getMatches().stream().map((match) -> new MatchDTO(match)).collect(Collectors.toList());
		this.numberOfmatchesPlayeds = game.getMatches().size();
		
	}
	
	public int getNumberOfmatchesPlayeds() {
		return this.numberOfmatchesPlayeds;
	}

	public String getUser() {
		return this.user;
	}

	public List<MatchDTO> getMatches() {
		return this.matches;
	}

	public void addMatch(MatchDTO newMatch) {
		this.matches.add(newMatch);	
		this.numberOfmatchesPlayeds += 1;
	}
	
	/**
	 * @param matches the matches to set
	 */
	public void setMatches(List<MatchDTO> matches) {
		this.matches = matches;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * @param numberOfmatchesPlayeds the numberOfmatchesPlayeds to set
	 */
	public void setNumberOfmatchesPlayeds(int numberOfmatchesPlayeds) {
		this.numberOfmatchesPlayeds = numberOfmatchesPlayeds;
	}
}
