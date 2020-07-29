/**
 *
 * Tests for GameHome
 *
 * @see https://github.com/react-boilerplate/react-boilerplate/tree/master/docs/testing
 *
 */

import React from 'react';
import { render } from 'react-testing-library';
import { IntlProvider } from 'react-intl';
import { Provider } from 'react-redux';
import { browserHistory } from 'react-router-dom';
import configureStore from '../../../configureStore';
import { playMatchAction, restartGameAction } from '../actions';
import { initialState } from '../reducer';
// import 'jest-dom/extend-expect'; // add some helpful assertions

import { GameHome } from '../index';

describe('<GameHome />', () => {
  let store;

  beforeAll(() => {
    store = configureStore({}, browserHistory);
  });

  it('should render and match the snapshot', () => {
    const {
      container: { firstChild },
    } = render(
      <Provider store={store}>
        <IntlProvider locale="en">
          <GameHome
            gameHome={initialState}
            playMatch={playMatchAction}
            restartGame={restartGameAction}
          />
        </IntlProvider>
      </Provider>,
    );
    expect(firstChild).toMatchSnapshot();
  });
});
