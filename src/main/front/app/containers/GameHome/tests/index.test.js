/**
 *
 * Tests for GameHome
 *
 * @see https://github.com/react-boilerplate/react-boilerplate/tree/master/docs/testing
 *
 */

import React from 'react';
import Adapter from 'enzyme-adapter-react-16';
import { shallow, configure } from 'enzyme';
import { render } from 'react-testing-library';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import { browserHistory } from 'react-router-dom';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import configureStore from '../../../configureStore';
import {
  playMatchAction,
  restartGameAction,
  getHistoricalAction,
  changeHistoricalScreenOnAction,
} from '../actions';
import {
  matchValueWithStringForWinner,
  matchValueWithStringForChooses,
} from '../../../utils/commonFunctions';
import { initialState } from '../reducer';
import { GameHome, mapDispatchToProps, generateRows } from '../index';

configure({ adapter: new Adapter() });

describe('<GameHome />', () => {
  let store;

  beforeAll(() => {
    store = configureStore({}, browserHistory);
  });

  it('should render and match the snapshot', () => {
    const dispatch = jest.fn();
    const mDTProps = mapDispatchToProps(dispatch);
    const {
      container: { firstChild },
    } = render(
      <Provider store={store}>
        <IntlProvider locale="en">
          <GameHome
            gameHome={initialState}
            playMatch={mDTProps.playMatch}
            restartGame={mDTProps.restartGame}
            returnToPlayScreen={mDTProps.returnToPlayScreen}
            getHistorical={mDTProps.getHistorical}
          />
        </IntlProvider>
      </Provider>,
    );
    expect(firstChild).toMatchSnapshot();
  });

  it('should render and match the snapshot', () => {
    const dispatch = jest.fn();
    const mDTProps = mapDispatchToProps(dispatch);
    const user = 'xasdf';
    const state = {
      game: {
        user,
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

    const {
      container: { firstChild },
    } = render(
      <Provider store={store}>
        <IntlProvider locale="en">
          <GameHome
            gameHome={state}
            playMatch={mDTProps.playMatch}
            restartGame={mDTProps.restartGame}
            returnToPlayScreen={mDTProps.returnToPlayScreen}
            getHistorical={mDTProps.getHistorical}
          />
        </IntlProvider>
      </Provider>,
    );
    expect(firstChild).toMatchSnapshot();
  });

  it('buttons should shoot the properly functions', () => {
    const dispatch = jest.fn();
    const mDTProps = mapDispatchToProps(dispatch);
    const user = 'xasdf';
    const state = {
      game: {
        user,
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

    let passedUserPlay = '';
    let passedUserRestart = '';
    const handleOnClickPlay = param => {
      passedUserPlay = param;
    };

    const handleOnClickRestart = param => {
      passedUserRestart = param;
    };

    const wrapper = shallow(
      <GameHome
        gameHome={state}
        playMatch={handleOnClickPlay}
        restartGame={handleOnClickRestart}
        returnToPlayScreen={mDTProps.returnToPlayScreen}
        getHistorical={mDTProps.getHistorical}
      />,
    );

    const buttonPlay = wrapper.find('#play-button');
    buttonPlay.simulate('click');
    const buttonRestart = wrapper.find('#restart-button');
    buttonRestart.simulate('click');
    expect(passedUserPlay).toBe(user);
    expect(passedUserRestart).toBe(user);
  });
});

describe('mapDispatchToProps', () => {
  describe('shoot functions', () => {
    it('should dispatch restartGameAction when called', () => {
      const user = 'xasdf';
      const dispatch = jest.fn();
      const result = mapDispatchToProps(dispatch);
      result.restartGame(user);
      expect(dispatch).toHaveBeenCalledWith(restartGameAction(user));
    });

    it('should dispatch playMatchAction when called', () => {
      const user = 'xasdf';
      const dispatch = jest.fn();
      const result = mapDispatchToProps(dispatch);
      result.playMatch(user);
      expect(dispatch).toHaveBeenCalledWith(playMatchAction(user));
    });

    it('should dispatch getHistoricalAction when called', () => {
      const dispatch = jest.fn();
      const result = mapDispatchToProps(dispatch);
      result.getHistorical();
      expect(dispatch).toHaveBeenCalledWith(getHistoricalAction());
    });

    it('should dispatch changeHistoricalScreenOnAction when called', () => {
      const dispatch = jest.fn();
      const result = mapDispatchToProps(dispatch);
      result.returnToPlayScreen();
      expect(dispatch).toHaveBeenCalledWith(changeHistoricalScreenOnAction());
    });
  });
});

describe('generateRows', () => {
  it('should return a table body', () => {
    const testMatches = [
      {
        playerOneChoose: 1,
        playerTwoChoose: 2,
        winner: 3,
      },
    ];
    const result = generateRows(testMatches);

    const expected = [
      <TableRow key={1}>
        <TableCell component="th" scope="row">
          {matchValueWithStringForChooses(1)}
        </TableCell>
        <TableCell>{matchValueWithStringForChooses(2)}</TableCell>
        <TableCell>{matchValueWithStringForWinner(3)}</TableCell>
      </TableRow>,
    ];

    expect(result).toEqual(expected);
  });
});
