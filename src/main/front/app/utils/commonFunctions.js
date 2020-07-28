import {
  SCISSORS_VALUE,
  SCISSORS,
  PAPER_VALUE,
  PAPER,
  ROCK_VALUE,
  ROCK,
  WINNER_PLAYER_ONE_ID,
  WINNER_PLAYER_TWO_ID,
  DRAW_VALUE,
  PLAYER_ONE,
  PLAYER_TWO,
  DRAW,
} from './constants';

export function matchValueWithStringForChooses(value) {
  switch (value) {
    case SCISSORS_VALUE:
      return SCISSORS;
    case PAPER_VALUE:
      return PAPER;
    case ROCK_VALUE:
      return ROCK;
    default:
      return '';
  }
}

export function matchValueWithStringForWinner(value) {
  switch (value) {
    case WINNER_PLAYER_ONE_ID:
      return PLAYER_ONE;
    case WINNER_PLAYER_TWO_ID:
      return PLAYER_TWO;
    case DRAW_VALUE:
      return DRAW;
    default:
      return '';
  }
}
