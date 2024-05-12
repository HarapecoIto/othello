package othello.base;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RowTest {

  @Test
  @DisplayName("Test row.getIndex();")
  void testGetIndex() {
    assertThat(Row.ROW_1.getIndex()).isEqualTo(0);
    assertThat(Row.ROW_2.getIndex()).isEqualTo(1);
    assertThat(Row.ROW_3.getIndex()).isEqualTo(2);
    assertThat(Row.ROW_4.getIndex()).isEqualTo(3);
    assertThat(Row.ROW_5.getIndex()).isEqualTo(4);
    assertThat(Row.ROW_6.getIndex()).isEqualTo(5);
    assertThat(Row.ROW_7.getIndex()).isEqualTo(6);
    assertThat(Row.ROW_8.getIndex()).isEqualTo(7);
  }

  @Test
  @DisplayName("Test row.equals();")
  void testEquals() {
    Row[] rows = new Row[]{
        Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4,
        Row.ROW_5, Row.ROW_6, Row.ROW_7, Row.ROW_8};
    for (int i = 0; i < rows.length; i++) {
      for (int j = 0; j < rows.length; j++) {
        if (i == j) {
          assertThat(rows[i]).isEqualTo(rows[j]);
        } else {
          assertThat(rows[i]).isNotEqualTo(rows[j]);
        }
      }
    }
  }

  @Test
  @DisplayName("Test row.down();")
  void testDown() {
    Row[] rows = new Row[]{
        Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4,
        Row.ROW_5, Row.ROW_6, Row.ROW_7, Row.ROW_8};
    for (int i = 0; i < rows.length - 1; i++) {
      assertThat(rows[i].down().orElse(null)).isEqualTo(rows[i + 1]);
    }
    assertThat(Row.ROW_8.down().orElse(null)).isEqualTo(null);
  }

  @Test
  @DisplayName("Test row.up();")
  void testUp() {
    Row[] rows = new Row[]{
        Row.ROW_1, Row.ROW_2, Row.ROW_3, Row.ROW_4,
        Row.ROW_5, Row.ROW_6, Row.ROW_7, Row.ROW_8};
    for (int i = 1; i < rows.length; i++) {
      assertThat(rows[i].up().orElse(null)).isEqualTo(rows[i - 1]);
    }
    assertThat(Row.ROW_1.up().orElse(null)).isEqualTo(null);
  }

}
