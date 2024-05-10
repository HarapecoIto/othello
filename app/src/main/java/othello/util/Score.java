package othello.util;

import java.util.Arrays;
import othello.base.Square;

public class Score implements Cloneable {

  private final int[] score;

  public Score() {
    this.score = new int[64];
  }

  public void setScore(Square sq, int score) {
    this.score[sq.getIndex()] = score;
  }

  public int getScore(Square sq) {
    return this.score[sq.getIndex()];
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Score s) {
      return Arrays.stream(Square.values()).allMatch(sq -> s.getScore(sq) == this.getScore(sq));
    }
    return false;
  }

  @Override
  public int hashCode() {
    return String.join(",",
            Arrays.stream(Square.values())
                .map(sq -> String.format("Score[%d]: %d", sq.getIndex(), this.getScore(sq)))
                .toList())
        .hashCode();
  }

  @Override
  protected Score clone() {
    Score s = new Score();
    Arrays.stream(Square.values()).forEach(sq -> s.setScore(sq, this.getScore(sq)));
    return s;
  }

}
