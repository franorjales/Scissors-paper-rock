/*
 *
 * GameHome reducer
 *
 */
import produce from 'immer';
import { PUT_GAME, PUT_ERROR } from './constants';

export const initialState = {
  game: {
    user: null,
    matches: [],
    numberOfmatchesPlayeds: 0,
  },
  error: {
    errorStatus: null,
  },
};

/* eslint-disable default-case, no-param-reassign */
const gameHomeReducer = (state = initialState, action) =>
  produce(state, draft => {
    switch (action.type) {
      case PUT_GAME:
        draft.game = action.game;
        break;
      case PUT_ERROR:
        draft.error = action.error;
        break;
    }
  });

export default gameHomeReducer;
