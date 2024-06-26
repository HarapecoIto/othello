package othello.util;

import java.util.Arrays;
import othello.base.Square;

/**
 * Score is integer list which correspond to squares on the board. We call this integer "score". You
 * can use this class for any purpose.
 */
public class Score implements Cloneable {

  private final int[] score;

  public Score() {
    this.score = new int[64];
  }

  /**
   * Set score to the Score object.
   *
   * @param square Target squares for which score are to be set
   * @param score  Score to be set
   */
  public void setScore(Square square, int score) {
    this.score[square.index()] = score;
  }

  /**
   * Get score of the square.
   *
   * @param square Target square to get score.
   * @return Score of the target square.
   */
  public int getScore(Square square) {
    return this.score[square.index()];
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Score s) {
      return Arrays.stream(Square.values())
          .allMatch(sq -> s.getScore(sq) == this.getScore(sq));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return String.join(",",
            Arrays.stream(Square.values())
                .map(sq -> String.format("Score[%d]: %d", sq.index(), this.getScore(sq)))
                .toList())
        .hashCode();
  }

  @Override
  public Score clone() {
    Score s = new Score();
    Arrays.stream(Square.values()).forEach(sq -> s.setScore(sq, this.getScore(sq)));
    return s;
  }

}
