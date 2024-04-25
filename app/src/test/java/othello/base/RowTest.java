package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class RowTest {

  @Test void getIndex() {
    assertEquals(0, Row.ROW_1.getIndex());
    assertEquals(1, Row.ROW_2.getIndex());
    assertEquals(2, Row.ROW_3.getIndex());
    assertEquals(3, Row.ROW_4.getIndex());
    assertEquals(4, Row.ROW_5.getIndex());
    assertEquals(5, Row.ROW_6.getIndex());
    assertEquals(6, Row.ROW_7.getIndex());
    assertEquals(7, Row.ROW_8.getIndex());
  }

  @Test void equals() {
    assertNotEquals(Row.ROW_1, Row.ROW_2);
    assertNotEquals(Row.ROW_1, Row.ROW_3);
    assertNotEquals(Row.ROW_1, Row.ROW_4);
    assertNotEquals(Row.ROW_1, Row.ROW_5);
    assertNotEquals(Row.ROW_1, Row.ROW_6);
    assertNotEquals(Row.ROW_1, Row.ROW_7);
    assertNotEquals(Row.ROW_1, Row.ROW_8);

    assertNotEquals(Row.ROW_2, Row.ROW_1);
    assertNotEquals(Row.ROW_2, Row.ROW_3);
    assertNotEquals(Row.ROW_2, Row.ROW_4);
    assertNotEquals(Row.ROW_2, Row.ROW_5);
    assertNotEquals(Row.ROW_2, Row.ROW_6);
    assertNotEquals(Row.ROW_2, Row.ROW_7);
    assertNotEquals(Row.ROW_2, Row.ROW_8);

    assertNotEquals(Row.ROW_3, Row.ROW_1);
    assertNotEquals(Row.ROW_3, Row.ROW_2);
    assertNotEquals(Row.ROW_3, Row.ROW_4);
    assertNotEquals(Row.ROW_3, Row.ROW_5);
    assertNotEquals(Row.ROW_3, Row.ROW_6);
    assertNotEquals(Row.ROW_3, Row.ROW_7);
    assertNotEquals(Row.ROW_3, Row.ROW_8);

    assertNotEquals(Row.ROW_4, Row.ROW_1);
    assertNotEquals(Row.ROW_4, Row.ROW_2);
    assertNotEquals(Row.ROW_4, Row.ROW_3);
    assertNotEquals(Row.ROW_4, Row.ROW_5);
    assertNotEquals(Row.ROW_4, Row.ROW_6);
    assertNotEquals(Row.ROW_4, Row.ROW_7);
    assertNotEquals(Row.ROW_4, Row.ROW_8);

    assertNotEquals(Row.ROW_5, Row.ROW_1);
    assertNotEquals(Row.ROW_5, Row.ROW_2);
    assertNotEquals(Row.ROW_5, Row.ROW_3);
    assertNotEquals(Row.ROW_5, Row.ROW_4);
    assertNotEquals(Row.ROW_5, Row.ROW_6);
    assertNotEquals(Row.ROW_5, Row.ROW_7);
    assertNotEquals(Row.ROW_5, Row.ROW_8);

    assertNotEquals(Row.ROW_6, Row.ROW_1);
    assertNotEquals(Row.ROW_6, Row.ROW_2);
    assertNotEquals(Row.ROW_6, Row.ROW_3);
    assertNotEquals(Row.ROW_6, Row.ROW_4);
    assertNotEquals(Row.ROW_6, Row.ROW_5);
    assertNotEquals(Row.ROW_6, Row.ROW_7);
    assertNotEquals(Row.ROW_6, Row.ROW_8);

    assertNotEquals(Row.ROW_7, Row.ROW_1);
    assertNotEquals(Row.ROW_7, Row.ROW_2);
    assertNotEquals(Row.ROW_7, Row.ROW_3);
    assertNotEquals(Row.ROW_7, Row.ROW_4);
    assertNotEquals(Row.ROW_7, Row.ROW_5);
    assertNotEquals(Row.ROW_7, Row.ROW_6);
    assertNotEquals(Row.ROW_7, Row.ROW_8);

    assertNotEquals(Row.ROW_8, Row.ROW_1);
    assertNotEquals(Row.ROW_8, Row.ROW_2);
    assertNotEquals(Row.ROW_8, Row.ROW_3);
    assertNotEquals(Row.ROW_8, Row.ROW_4);
    assertNotEquals(Row.ROW_8, Row.ROW_5);
    assertNotEquals(Row.ROW_8, Row.ROW_6);
    assertNotEquals(Row.ROW_8, Row.ROW_7);
  }

  @Test
  void up() {
    assertEquals(Row.ROW_2, Row.ROW_1.up().orElse(null));
    assertEquals(Row.ROW_3, Row.ROW_2.up().orElse(null));
    assertEquals(Row.ROW_4, Row.ROW_3.up().orElse(null));
    assertEquals(Row.ROW_5, Row.ROW_4.up().orElse(null));
    assertEquals(Row.ROW_6, Row.ROW_5.up().orElse(null));
    assertEquals(Row.ROW_7, Row.ROW_6.up().orElse(null));
    assertEquals(Row.ROW_8, Row.ROW_7.up().orElse(null));
    assertNull(Row.ROW_8.up().orElse(null));
  }

  @Test
  void down() {
    assertNull(Row.ROW_1.down().orElse(null));
    assertEquals(Row.ROW_1, Row.ROW_2.down().orElse(null));
    assertEquals(Row.ROW_2, Row.ROW_3.down().orElse(null));
    assertEquals(Row.ROW_3, Row.ROW_4.down().orElse(null));
    assertEquals(Row.ROW_4, Row.ROW_5.down().orElse(null));
    assertEquals(Row.ROW_5, Row.ROW_6.down().orElse(null));
    assertEquals(Row.ROW_6, Row.ROW_7.down().orElse(null));
    assertEquals(Row.ROW_7, Row.ROW_8.down().orElse(null));
  }

}
