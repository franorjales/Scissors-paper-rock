import { takeLatest, call, put } from 'redux-saga/effects';
import request from '../../utils/request';
import { PLAY_MATCH, RESTART_GAME, GET_HISTORICAL } from './constants';
import { putGame, putError, putHistorical } from './actions';

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

export function* getHistorical() {
  try {
    const historicalGames = yield call(request, `/api/getHistoricalGames`);
    yield put(putHistorical(historicalGames));
  } catch (error) {
    yield put(putError(error));
  }
}

// Individual exports for testing
export default function* gameHomeSaga() {
  yield takeLatest(PLAY_MATCH, playMatch);
  yield takeLatest(RESTART_GAME, restartGame);
  yield takeLatest(GET_HISTORICAL, getHistorical);
}
