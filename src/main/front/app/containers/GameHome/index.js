/**
 *
 * GameHome
 *
 */

import React, { memo } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { compose } from 'redux';
import { useInjectSaga } from 'utils/injectSaga';
import { useInjectReducer } from 'utils/injectReducer';
import { makeStyles } from '@material-ui/core/styles';
import Grid from '@material-ui/core/Grid';
import Button from '@material-ui/core/Button';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import {
  playMatchAction,
  restartGameAction,
  getHistoricalAction,
  changeHistoricalScreenOnAction,
} from './actions';
import makeSelectGameHome from './selectors';
import reducer from './reducer';
import saga from './saga';
import {
  matchValueWithStringForWinner,
  matchValueWithStringForChooses,
} from '../../utils/commonFunctions';

const useStyles = makeStyles(() => ({
  root: {
    flexGrow: 1,
  },
  buttonGrid: {
    float: 'right;',
  },
  table: {},
  message: {},
}));

export function GameHome({
  gameHome,
  playMatch,
  restartGame,
  returnToPlayScreen,
  getHistorical,
}) {
  useInjectReducer({ key: 'gameHome', reducer });
  useInjectSaga({ key: 'gameHome', saga });
  const classes = useStyles();

  return gameHome.historicalGamesScreenOn ? (
    <Grid container className={classes.root}>
      <Grid item xs={12} align="center">
        The number of matches already playeds are:{' '}
        {gameHome.historicalGames.totalRoundsPlayed}
      </Grid>
      <Grid item xs={12} align="center">
        The number of matches winned by player one are:{' '}
        {gameHome.historicalGames.totalWinsForPlayerOne}
      </Grid>
      <Grid item xs={12} align="center">
        The number of matches winned by player two are:{' '}
        {gameHome.historicalGames.totalWinsForPlayerTwo}
      </Grid>
      <Grid item xs={12} align="center">
        The number of matches with draw result are:{' '}
        {gameHome.historicalGames.totalDraws}
      </Grid>
      <Grid item xs={12} align="center">
        <Button
          variant="contained"
          onClick={() => {
            returnToPlayScreen();
          }}
        >
          Return to play the game again
        </Button>
      </Grid>
    </Grid>
  ) : (
    <Grid container className={classes.root}>
      {gameHome.game.matches.length > 0 ? (
        <Grid item xs={12}>
          <TableContainer component={Paper}>
            <Table
              className={classes.table}
              size="small"
              aria-label="a dense table"
            >
              <TableHead>
                <TableRow>
                  <TableCell>Player One Choose</TableCell>
                  <TableCell>Player Two Choose</TableCell>
                  <TableCell>Winner</TableCell>
                </TableRow>
              </TableHead>
              <TableBody>
                {gameHome.game.matches.map(row => (
                  <TableRow key={row.playerOneChoose}>
                    <TableCell component="th" scope="row">
                      {matchValueWithStringForChooses(row.playerOneChoose)}
                    </TableCell>
                    <TableCell>
                      {matchValueWithStringForChooses(row.playerTwoChoose)}
                    </TableCell>
                    <TableCell>
                      {matchValueWithStringForWinner(row.winner)}
                    </TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </TableContainer>
          <Grid item xs={12} align="center">
            Number Of rounds played: {gameHome.game.numberOfmatchesPlayeds}
          </Grid>
        </Grid>
      ) : (
        <Grid item xs={12} align="center">
          <h1>Hello! Do you want to play a game?</h1>
        </Grid>
      )}
      <Grid item xs={4} align="center">
        <Button
          variant="contained"
          onClick={() => {
            playMatch(gameHome.game.user);
          }}
        >
          Play one Match!
        </Button>
      </Grid>
      <Grid item xs={4} align="center">
        <Button
          variant="contained"
          onClick={() => {
            getHistorical();
          }}
        >
          Access to game history
        </Button>
      </Grid>
      <Grid item xs={4} align="center">
        {gameHome.game.user === null ? (
          undefined
        ) : (
          <Button
            variant="contained"
            onClick={() => {
              restartGame(gameHome.game.user);
            }}
          >
            Restart the game
          </Button>
        )}
      </Grid>
    </Grid>
  );
}

GameHome.propTypes = {
  playMatch: PropTypes.func,
  restartGame: PropTypes.func,
  gameHome: PropTypes.object,
  getHistorical: PropTypes.func,
  returnToPlayScreen: PropTypes.func,
};

const mapStateToProps = createStructuredSelector({
  gameHome: makeSelectGameHome(),
});

export function mapDispatchToProps(dispatch) {
  return {
    playMatch: user => dispatch(playMatchAction(user)),
    restartGame: user => dispatch(restartGameAction(user)),
    getHistorical: () => dispatch(getHistoricalAction()),
    returnToPlayScreen: () => dispatch(changeHistoricalScreenOnAction()),
  };
}

const withConnect = connect(
  mapStateToProps,
  mapDispatchToProps,
);

export default compose(
  withConnect,
  memo,
)(GameHome);
