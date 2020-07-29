import { selectGameHomeDomain } from '../selectors';

describe('selectGameHomeDomain', () => {
  it('should select the home state', () => {
    const gameHomeState = {
      game: {
        user: null,
        matches: [],
        numberOfmatchesPlayeds: 0,
      },
      error: {
        errorStatus: null,
      },
      historicalGames: {
        totalRoundsPlayed: 0,
        totalWinsForPlayerOne: 0,
        totalWinsForPlayerTwo: 0,
        totalDraws: 0,
      },
      historicalGamesScreenOn: false,
    };
    const mockedState = {
      home: gameHomeState,
    };
    expect(selectGameHomeDomain(mockedState)).toEqual(gameHomeState);
  });
});
