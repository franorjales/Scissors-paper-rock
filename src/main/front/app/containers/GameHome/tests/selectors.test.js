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
    };
    const mockedState = {
      home: gameHomeState,
    };
    expect(selectGameHomeDomain(mockedState)).toEqual(gameHomeState);
  });
});
