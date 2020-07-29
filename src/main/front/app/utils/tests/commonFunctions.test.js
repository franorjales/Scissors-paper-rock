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
} from '../constants';
import {
  matchValueWithStringForChooses,
  matchValueWithStringForWinner,
} from '../commonFunctions';

describe('request', () => {
  describe('matchValueWithStringForChooses test', () => {
    it('should match value with string', () => {
      expect(matchValueWithStringForChooses(SCISSORS_VALUE)).toBe(SCISSORS);
      expect(matchValueWithStringForChooses(PAPER_VALUE)).toBe(PAPER);
      expect(matchValueWithStringForChooses(ROCK_VALUE)).toBe(ROCK);
      expect(matchValueWithStringForChooses('')).toBe('');
    });
  });

  describe('matchValueWithStringForWinner test', () => {
    it('should match value with string', () => {
      expect(matchValueWithStringForWinner(WINNER_PLAYER_ONE_ID)).toBe(
        PLAYER_ONE,
      );
      expect(matchValueWithStringForWinner(WINNER_PLAYER_TWO_ID)).toBe(
        PLAYER_TWO,
      );
      expect(matchValueWithStringForWinner(DRAW_VALUE)).toBe(DRAW);
      expect(matchValueWithStringForWinner('')).toBe('');
    });
  });
});
