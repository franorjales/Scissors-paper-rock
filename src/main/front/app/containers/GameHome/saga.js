import { takeLatest, call, put } from 'redux-saga/effects';
import request from '../../utils/request';
import { PLAY_MATCH, RESTART_GAME } from './constants';
import { putGame, putError } from './actions';

export function* playMatch(action) {
  const { user } = action;
  try {
    const game = yield call(request, `/api/playMatch?user=${user}`);
    yield put(putGame(game));
  } catch (error) {
    yield put(putError(error));
  }
}

export function* restartGame(action) {
  const { user } = action;
  try {
    const game = yield call(request, `/api/restartGame?user=${user}`);
    yield put(putGame(game));
  } catch (error) {
    yield put(putError(error));
  }
}

// Individual exports for testing
export default function* gameHomeSaga() {
  yield takeLatest(PLAY_MATCH, playMatch);
  yield takeLatest(RESTART_GAME, restartGame);
}
