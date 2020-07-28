import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the gameHome state domain
 */

const selectGameHomeDomain = state => state.gameHome || initialState;

/**
 * Other specific selectors
 */

/**
 * Default selector used by GameHome
 */

const makeSelectGameHome = () =>
  createSelector(
    selectGameHomeDomain,
    substate => substate,
  );

export default makeSelectGameHome;
export { selectGameHomeDomain };
