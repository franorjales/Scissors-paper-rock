/*
 *
 * GameHome actions
 *
 */

import {
  PLAY_MATCH,
  RESTART_GAME,
  PUT_GAME,
  PUT_ERROR,
  PUT_HISTORICAL,
  GET_HISTORICAL,
  CHANGE_HISTORICAL_SCREEN_ON,
} from './constants';

export function playMatchAction(user) {
  return {
    type: PLAY_MATCH,
    user,
  };
}

export function restartGameAction(user) {
  return {
    type: RESTART_GAME,
    user,
  };
}

export function putGame(game) {
  return {
    type: PUT_GAME,
    game,
  };
}

export function putError(error) {
  return {
    type: PUT_ERROR,
    error,
  };
}

export function getHistoricalAction() {
  return {
    type: GET_HISTORICAL,
  };
}

export function putHistorical(historicalGames) {
  return {
    type: PUT_HISTORICAL,
    historicalGames,
  };
}

export function changeHistoricalScreenOnAction() {
  return {
    type: CHANGE_HISTORICAL_SCREEN_ON,
  };
}
