package chusco.interfaces;

import java.util.List;

public interface IGame {

	public int getNumberOfmatchesPlayeds();
	public String getUser();
	public List<IMatch> getMatches();
	public void addMatch(IMatch newMatch);
	
}
