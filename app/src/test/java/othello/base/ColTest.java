package othello.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ColTest {

  @Test
  @DisplayName("Test col.getIndex();")
  void testIndex() {
    assertThat(Col.COL_A.index()).isEqualTo(0);
    assertThat(Col.COL_B.index()).isEqualTo(1);
    assertThat(Col.COL_C.index()).isEqualTo(2);
    assertThat(Col.COL_D.index()).isEqualTo(3);
    assertThat(Col.COL_E.index()).isEqualTo(4);
    assertThat(Col.COL_F.index()).isEqualTo(5);
    assertThat(Col.COL_G.index()).isEqualTo(6);
    assertThat(Col.COL_H.index()).isEqualTo(7);
  }

  @Test
  @DisplayName("Test col.equals();")
  void testEquals() {
    Col[] cols = new Col[]{
        Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D,
        Col.COL_E, Col.COL_F, Col.COL_G, Col.COL_H};
    for (int i = 0; i < cols.length; i++) {
      for (int j = 0; j < cols.length; j++) {
        if (i == j) {
          assertThat(cols[i]).isEqualTo(cols[j]);
        } else {
          assertThat(cols[i]).isNotEqualTo(cols[j]);
        }
      }
    }
  }

  @Test
  @DisplayName("Test col.right();")
  void testRight() {
    Col[] cols = new Col[]{
        Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D,
        Col.COL_E, Col.COL_F, Col.COL_G, Col.COL_H};
    for (int i = 0; i < cols.length - 1; i++) {
      assertThat(cols[i].right().orElse(null)).isEqualTo(cols[i + 1]);
    }
    assertThat(Col.COL_H.right().orElse(null)).isEqualTo(null);
  }

  @Test
  @DisplayName("Test col.left();")
  void testLeft() {
    Col[] cols = new Col[]{
        Col.COL_A, Col.COL_B, Col.COL_C, Col.COL_D,
        Col.COL_E, Col.COL_F, Col.COL_G, Col.COL_H};
    for (int i = 1; i < cols.length; i++) {
      assertThat(cols[i].left().orElse(null)).isEqualTo(cols[i - 1]);
    }
    assertThat(Col.COL_A.left().orElse(null)).isEqualTo(null);
  }

}
