import {
  putError,
  putGame,
  restartGameAction,
  playMatchAction,
} from '../actions';
import { PUT_ERROR, PUT_GAME, RESTART_GAME, PLAY_MATCH } from '../constants';

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
});
