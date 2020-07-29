import produce from 'immer';
import gameHomeReducer from '../reducer';
import { putError, putGame } from '../actions';

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

  /**
   * Example state change comparison
   *
   * it('should handle the someAction action correctly', () => {
   *   const expectedResult = produce(state, draft => {
   *     draft.loading = true;
   *     draft.error = false;
   *     draft.userData.nested = false;
   *   });
   *
   *   expect(appReducer(state, someAction())).toEqual(expectedResult);
   * });
   */
});
