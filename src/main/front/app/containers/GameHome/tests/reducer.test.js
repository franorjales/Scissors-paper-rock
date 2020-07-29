import produce from 'immer';
import gameHomeReducer from '../reducer';
import {
  putError,
  putGame,
  putHistorical,
  changeHistoricalScreenOnAction,
} from '../actions';

/* eslint-disable default-case, no-param-reassign */
describe('gameHomeReducer', () => {
  let state;
  beforeEach(() => {
    state = {
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
  });

  it('returns the initial state', () => {
    const expectedResult = state;
    expect(gameHomeReducer(undefined, {})).toEqual(expectedResult);
  });

  it('should handle PUT_ERROR correctly', () => {
    const expectedResult = produce(state, draft => {
      draft.error = {
        errorStatus: 404,
      };
    });

    const input = {
      errorStatus: 404,
    };

    expect(gameHomeReducer(state, putError(input))).toEqual(expectedResult);
  });

  it('should handle PUT_GAME correctly', () => {
    const expectedResult = produce(state, draft => {
      draft.game = {
        user: 'testGame',
        matches: [],
        numberOfmatchesPlayeds: 0,
      };
    });

    const input = {
      user: 'testGame',
      matches: [],
      numberOfmatchesPlayeds: 0,
    };

    expect(gameHomeReducer(state, putGame(input))).toEqual(expectedResult);
  });

  it('should handle PUT_HISTORICAL correctly', () => {
    const expectedResult = produce(state, draft => {
      draft.historicalGames = {
        totalRoundsPlayed: 1,
        totalWinsForPlayerOne: 1,
        totalWinsForPlayerTwo: 1,
        totalDraws: 1,
      };
      draft.historicalGamesScreenOn = true;
    });

    const input = {
      totalRoundsPlayed: 1,
      totalWinsForPlayerOne: 1,
      totalWinsForPlayerTwo: 1,
      totalDraws: 1,
    };

    expect(gameHomeReducer(state, putHistorical(input))).toEqual(
      expectedResult,
    );
  });

  it('should handle CHANGE_HISTORICAL_SCREEN_ON correctly', () => {
    const expectedResult = produce(state, draft => {
      draft.historicalGamesScreenOn = false;
    });

    expect(gameHomeReducer(state, changeHistoricalScreenOnAction())).toEqual(
      expectedResult,
    );
  });
});
