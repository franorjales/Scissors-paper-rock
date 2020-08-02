/**
 * Test sagas
 */

/* eslint-disable redux-saga/yield-effects */
import { put, takeLatest } from 'redux-saga/effects';
import gameHomeSaga, { playMatch, restartGame, getHistorical } from '../saga';
import { RESTART_GAME, PLAY_MATCH } from '../constants';
import { putError, putGame, putHistorical } from '../actions';

const action = { user: 'testUserName' };

describe('playMatch Saga', () => {
  let getPlayMatchGenerator;

  beforeEach(() => {
    getPlayMatchGenerator = playMatch(action);

    const callDescriptor = getPlayMatchGenerator.next(action).value;
    expect(callDescriptor).toMatchSnapshot();
  });

  it('should dispatch the putGame action if it requests the data successfully', () => {
    const response = {
      user: 'testUserName',
      matches: [],
      numberOfmatchesPlayeds: 0,
    };
    const putDescriptor = getPlayMatchGenerator.next(response).value;
    expect(putDescriptor).toEqual(put(putGame(response)));
  });

  it('should call the putError action if the response errors', () => {
    const response = new Error('Some error');
    const putDescriptor = getPlayMatchGenerator.throw(response).value;
    expect(putDescriptor).toEqual(put(putError(response)));
  });
});

describe('restartGame Saga', () => {
  let getRestartGameGenerator;

  beforeEach(() => {
    getRestartGameGenerator = restartGame(action);

    const callDescriptor = getRestartGameGenerator.next(action).value;
    expect(callDescriptor).toMatchSnapshot();
  });

  it('should dispatch the putGame action if it requests the data successfully', () => {
    const response = {
      user: 'testUserName',
      matches: [],
      numberOfmatchesPlayeds: 0,
    };
    const putDescriptor = getRestartGameGenerator.next(response).value;
    expect(putDescriptor).toEqual(put(putGame(response)));
  });

  it('should call the putError action if the response errors', () => {
    const response = new Error('Some error');
    const putDescriptor = getRestartGameGenerator.throw(response).value;
    expect(putDescriptor).toEqual(put(putError(response)));
  });
});

describe('getHistorical Saga', () => {
  let getHistoricalGenerator;

  beforeEach(() => {
    getHistoricalGenerator = getHistorical(action);

    const callDescriptor = getHistoricalGenerator.next(action).value;
    expect(callDescriptor).toMatchSnapshot();
  });

  it('should dispatch the putGame action if it requests the data successfully', () => {
    const response = {
      totalRoundsPlayed: 1,
      totalWinsForPlayerOne: 1,
      totalWinsForPlayerTwo: 1,
      totalDraws: 1,
    };
    const putDescriptor = getHistoricalGenerator.next(response).value;
    expect(putDescriptor).toEqual(put(putHistorical(response)));
  });

  it('should call the putError action if the response errors', () => {
    const response = new Error('Some error');
    const putDescriptor = getHistoricalGenerator.throw(response).value;
    expect(putDescriptor).toEqual(put(putError(response)));
  });
});

describe('gameHomeSaga Saga', () => {
  const gameHomeSagaTest = gameHomeSaga();

  it('should start task to watch for actions', () => {
    let takeLatestDescriptor = gameHomeSagaTest.next().value;
    expect(takeLatestDescriptor).toEqual(takeLatest(PLAY_MATCH, playMatch));
    takeLatestDescriptor = gameHomeSagaTest.next().value;
    expect(takeLatestDescriptor).toEqual(takeLatest(RESTART_GAME, restartGame));
  });
});
