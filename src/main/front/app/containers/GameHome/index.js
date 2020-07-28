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
import { playMatchAction, restartGameAction } from './actions';
import makeSelectGameHome from './selectors';
import reducer from './reducer';
import saga from './saga';
import {
  matchValueWithStringForWinner,
  matchValueWithStringForChooses,
} from '../../utils/commonFunctions';

const useStyles = makeStyles(theme => ({
  root: {
    flexGrow: 1,
  },
  buttonGrid: {
    float: 'right;',
  },
  table: {},
  message: {},
}));

export function GameHome({ gameHome, playMatch, restartGame }) {
  useInjectReducer({ key: 'gameHome', reducer });
  useInjectSaga({ key: 'gameHome', saga });
  const classes = useStyles();

  return (
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
                {gameHome.game.matches.map((row, index) => (
                  <TableRow key={index}>
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
        </Grid>
      ) : (
        <Grid item xs={12} align="center">
          <h1>Hello! Do you want to play a game?</h1>
        </Grid>
      )}
      <Grid item xs={6} align="center">
        <Button
          variant="contained"
          onClick={() => {
            playMatch(gameHome.game.user);
          }}
        >
          Play one Match!
        </Button>
      </Grid>
      <Grid item xs={6} align="center">
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
};

const mapStateToProps = createStructuredSelector({
  gameHome: makeSelectGameHome(),
});

function mapDispatchToProps(dispatch) {
  return {
    playMatch: user => dispatch(playMatchAction(user)),
    restartGame: user => dispatch(restartGameAction(user)),
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
