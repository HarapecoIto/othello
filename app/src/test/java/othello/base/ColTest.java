package othello.base;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class ColTest {

  @Test
  void testGetIndex() {
    assertEquals(0, Col.COL_A.getIndex());
    assertEquals(1, Col.COL_B.getIndex());
    assertEquals(2, Col.COL_C.getIndex());
    assertEquals(3, Col.COL_D.getIndex());
    assertEquals(4, Col.COL_E.getIndex());
    assertEquals(5, Col.COL_F.getIndex());
    assertEquals(6, Col.COL_G.getIndex());
    assertEquals(7, Col.COL_H.getIndex());
  }

  @Test
  void testEquals() {
    assertNotEquals(Col.COL_A, Col.COL_B);
    assertNotEquals(Col.COL_A, Col.COL_C);
    assertNotEquals(Col.COL_A, Col.COL_D);
    assertNotEquals(Col.COL_A, Col.COL_E);
    assertNotEquals(Col.COL_A, Col.COL_F);
    assertNotEquals(Col.COL_A, Col.COL_G);
    assertNotEquals(Col.COL_A, Col.COL_H);

    assertNotEquals(Col.COL_B, Col.COL_A);
    assertNotEquals(Col.COL_B, Col.COL_C);
    assertNotEquals(Col.COL_B, Col.COL_D);
    assertNotEquals(Col.COL_B, Col.COL_E);
    assertNotEquals(Col.COL_B, Col.COL_F);
    assertNotEquals(Col.COL_B, Col.COL_G);
    assertNotEquals(Col.COL_B, Col.COL_H);

    assertNotEquals(Col.COL_C, Col.COL_A);
    assertNotEquals(Col.COL_C, Col.COL_B);
    assertNotEquals(Col.COL_C, Col.COL_D);
    assertNotEquals(Col.COL_C, Col.COL_E);
    assertNotEquals(Col.COL_C, Col.COL_F);
    assertNotEquals(Col.COL_C, Col.COL_G);
    assertNotEquals(Col.COL_C, Col.COL_H);

    assertNotEquals(Col.COL_D, Col.COL_A);
    assertNotEquals(Col.COL_D, Col.COL_B);
    assertNotEquals(Col.COL_D, Col.COL_C);
    assertNotEquals(Col.COL_D, Col.COL_E);
    assertNotEquals(Col.COL_D, Col.COL_F);
    assertNotEquals(Col.COL_D, Col.COL_G);
    assertNotEquals(Col.COL_D, Col.COL_H);

    assertNotEquals(Col.COL_E, Col.COL_A);
    assertNotEquals(Col.COL_E, Col.COL_B);
    assertNotEquals(Col.COL_E, Col.COL_C);
    assertNotEquals(Col.COL_E, Col.COL_D);
    assertNotEquals(Col.COL_E, Col.COL_F);
    assertNotEquals(Col.COL_E, Col.COL_G);
    assertNotEquals(Col.COL_E, Col.COL_H);

    assertNotEquals(Col.COL_F, Col.COL_A);
    assertNotEquals(Col.COL_F, Col.COL_B);
    assertNotEquals(Col.COL_F, Col.COL_C);
    assertNotEquals(Col.COL_F, Col.COL_D);
    assertNotEquals(Col.COL_F, Col.COL_E);
    assertNotEquals(Col.COL_F, Col.COL_G);
    assertNotEquals(Col.COL_F, Col.COL_H);

    assertNotEquals(Col.COL_G, Col.COL_A);
    assertNotEquals(Col.COL_G, Col.COL_B);
    assertNotEquals(Col.COL_G, Col.COL_C);
    assertNotEquals(Col.COL_G, Col.COL_D);
    assertNotEquals(Col.COL_G, Col.COL_E);
    assertNotEquals(Col.COL_G, Col.COL_F);
    assertNotEquals(Col.COL_G, Col.COL_H);

    assertNotEquals(Col.COL_H, Col.COL_A);
    assertNotEquals(Col.COL_H, Col.COL_B);
    assertNotEquals(Col.COL_H, Col.COL_C);
    assertNotEquals(Col.COL_H, Col.COL_D);
    assertNotEquals(Col.COL_H, Col.COL_E);
    assertNotEquals(Col.COL_H, Col.COL_F);
    assertNotEquals(Col.COL_H, Col.COL_G);
  }

  @Test
  void testRight() {
    assertEquals(Col.COL_B, Col.COL_A.right().orElse(null));
    assertEquals(Col.COL_C, Col.COL_B.right().orElse(null));
    assertEquals(Col.COL_D, Col.COL_C.right().orElse(null));
    assertEquals(Col.COL_E, Col.COL_D.right().orElse(null));
    assertEquals(Col.COL_F, Col.COL_E.right().orElse(null));
    assertEquals(Col.COL_G, Col.COL_F.right().orElse(null));
    assertEquals(Col.COL_H, Col.COL_G.right().orElse(null));
    assertNull(Col.COL_H.right().orElse(null));
  }

  @Test
  void testLeft() {
    assertNull(Col.COL_A.left().orElse(null));
    assertEquals(Col.COL_A, Col.COL_B.left().orElse(null));
    assertEquals(Col.COL_B, Col.COL_C.left().orElse(null));
    assertEquals(Col.COL_C, Col.COL_D.left().orElse(null));
    assertEquals(Col.COL_D, Col.COL_E.left().orElse(null));
    assertEquals(Col.COL_E, Col.COL_F.left().orElse(null));
    assertEquals(Col.COL_F, Col.COL_G.left().orElse(null));
    assertEquals(Col.COL_G, Col.COL_H.left().orElse(null));
  }

}
