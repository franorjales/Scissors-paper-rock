/*
 *
 * GameHome reducer
 *
 */
import produce from 'immer';
import {
  PUT_GAME,
  PUT_ERROR,
  PUT_HISTORICAL,
  CHANGE_HISTORICAL_SCREEN_ON,
} from './constants';

export const initialState = {
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
      case PUT_HISTORICAL:
        draft.historicalGames = action.historicalGames;
        draft.historicalGamesScreenOn = true;
        break;
      case CHANGE_HISTORICAL_SCREEN_ON:
        draft.historicalGamesScreenOn = false;
        break;
    }
  });

export default gameHomeReducer;
