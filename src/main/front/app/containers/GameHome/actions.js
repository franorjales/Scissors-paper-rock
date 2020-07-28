/*
 *
 * GameHome actions
 *
 */

import { PLAY_MATCH, RESTART_GAME, PUT_GAME, PUT_ERROR } from './constants';

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
