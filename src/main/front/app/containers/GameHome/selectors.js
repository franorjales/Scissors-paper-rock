import { createSelector } from 'reselect';
import { initialState } from './reducer';

/**
 * Direct selector to the gameHome state domain
 */
const selectGameHomeDomain = state => state.gameHome || initialState;

/**
 * Default selector used by GameHome
 */

const makeSelectGameHome = () =>
  createSelector(
    selectGameHomeDomain,
    state => state,
  );

export default makeSelectGameHome;
export { selectGameHomeDomain };
