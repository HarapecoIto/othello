package othello.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import othello.base.Square;

public class ScoreTest {

  @Test
  void setterTest() {
    Score s = new Score();
    Arrays.stream(Square.values()).forEach(
        sq -> assertEquals(0, s.getScore(sq))
    );

    Arrays.stream(Square.values()).forEach(
        sq -> s.setScore(sq, 100 + sq.getIndex())
    );
    Arrays.stream(Square.values()).forEach(
        sq -> assertEquals(100 + sq.getIndex(), s.getScore(sq))
    );
  }

  @Test
  void equalsTest() {
    Function<Boolean, Score> f = (b) -> {
      Score s = new Score();
      Arrays.stream(Square.values()).forEach(
          sq -> s.setScore(sq, 100 + sq.getIndex()));
      return s;
    };
    Score score = f.apply(true);
    Score[] scores = new Score[64];
    for (int i = 0; i < 64; i++) {
      scores[i] = f.apply(true);
      assertEquals(score, scores[i]);
    }

    Arrays.stream(Square.values()).forEach(
        sq -> scores[sq.getIndex()].setScore(sq, 1000));
    for (int i = 0; i < 63; i++) {
      assertNotEquals(score, scores[i]);
    }
  }

  @Test
  void hashCodeTest() {
    Function<Boolean, Score> f = (b) -> {
      Score s = new Score();
      Arrays.stream(Square.values()).forEach(
          sq -> s.setScore(sq, 100 + sq.getIndex()));
      return s;
    };
    Score score = f.apply(true);
    Score[] scores = new Score[64];
    for (int i = 0; i < 64; i++) {
      scores[i] = f.apply(true);
      assertEquals(score.hashCode(), scores[i].hashCode());
    }
  }

  @Test
  void cloneTest() {
    Function<Boolean, Score> f = (b) -> {
      Score s = new Score();
      Arrays.stream(Square.values()).forEach(
          sq -> s.setScore(sq, 100 + sq.getIndex()));
      return s;
    };
    Score score = f.apply(true);
    Score clone = score.clone();
    assertEquals(score, clone);
    clone.setScore(Square.SQUARE_1_A, 1000);
    assertNotEquals(score, clone);
  }
}
