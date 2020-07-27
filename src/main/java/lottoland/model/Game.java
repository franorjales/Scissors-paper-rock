package lottoland.Model;

import java.util.ArrayList;
import java.util.List;

import lottoland.Interfaces.IGame;
import lottoland.Interfaces.IMatch;

public class Game implements IGame{
	
	private List<IMatch> matches;
	private String user;

	public Game(String user) {
		this.user = user;
		this.matches = new ArrayList<IMatch>();
	}
	
	@Override
	public int getNumberOfmatchesPlayeds() {
		return this.matches.size();
	}

	@Override
	public String getUser() {
		return this.user;
	}

	@Override
	public List<IMatch> getMatches() {
		return this.matches;
	}

	@Override
	public void addMatch(IMatch newMatch) {
		this.matches.add(newMatch);		
	}

}
