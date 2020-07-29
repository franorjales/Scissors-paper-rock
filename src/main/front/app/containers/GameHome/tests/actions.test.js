import {
  putError,
  putGame,
  restartGameAction,
  playMatchAction,
  changeHistoricalScreenOnAction,
  putHistorical,
  getHistoricalAction,
} from '../actions';
import {
  PUT_ERROR,
  PUT_GAME,
  RESTART_GAME,
  PLAY_MATCH,
  GET_HISTORICAL,
  PUT_HISTORICAL,
  CHANGE_HISTORICAL_SCREEN_ON,
} from '../constants';

describe('GameHome actions', () => {
  describe('PUT_ERROR Action', () => {
    it('has a type of PUT_ERROR', () => {
      const expected = {
        type: PUT_ERROR,
        error: {
          errorStatus: 404,
        },
      };

      const input = {
        errorStatus: 404,
      };

      expect(putError(input)).toEqual(expected);
    });
  });

  describe('PUT_GAME Action', () => {
    it('has a type of PUT_GAME', () => {
      const expected = {
        type: PUT_GAME,
        game: {
          user: null,
          matches: [],
          numberOfmatchesPlayeds: 0,
        },
      };

      const input = {
        user: null,
        matches: [],
        numberOfmatchesPlayeds: 0,
      };

      expect(putGame(input)).toEqual(expected);
    });
  });

  describe('RESTART_GAME Action', () => {
    it('has a type of RESTART_GAME', () => {
      const expected = {
        type: RESTART_GAME,
        user: 'username',
      };

      const input = 'username';

      expect(restartGameAction(input)).toEqual(expected);
    });
  });

  describe('PLAY_MATCH Action', () => {
    it('has a type of PLAY_MATCH', () => {
      const expected = {
        type: PLAY_MATCH,
        user: 'username',
      };

      const input = 'username';

      expect(playMatchAction(input)).toEqual(expected);
    });
  });

  describe('PUT_HISTORICAL Action', () => {
    it('has a type of PUT_HISTORICAL', () => {
      const expected = {
        type: PUT_HISTORICAL,
        historicalGames: {
          totalRoundsPlayed: 0,
          totalWinsForPlayerOne: 0,
          totalWinsForPlayerTwo: 0,
          totalDraws: 0,
        },
      };

      const input = {
        totalRoundsPlayed: 0,
        totalWinsForPlayerOne: 0,
        totalWinsForPlayerTwo: 0,
        totalDraws: 0,
      };

      expect(putHistorical(input)).toEqual(expected);
    });
  });

  describe('GET_HISTORICAL Action', () => {
    it('has a type of GET_HISTORICAL', () => {
      const expected = {
        type: GET_HISTORICAL,
      };

      expect(getHistoricalAction()).toEqual(expected);
    });
  });

  describe('CHANGE_HISTORICAL_SCREEN_ON Action', () => {
    it('has a type of CHANGE_HISTORICAL_SCREEN_ON', () => {
      const expected = {
        type: CHANGE_HISTORICAL_SCREEN_ON,
      };

      expect(changeHistoricalScreenOnAction()).toEqual(expected);
    });
  });
});
