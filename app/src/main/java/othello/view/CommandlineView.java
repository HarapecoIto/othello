package othello.view;

import java.util.Arrays;
import java.util.List;
import othello.base.Board;
import othello.base.Row;
import othello.base.Square;
import othello.base.Stone;

public class CommandlineView {

  public static void update(Board board) {
    List<Square> squares = Arrays.asList(Square.values());
    System.out.println("┌─┬─┬─┬─┬─┬─┬─┬─┐");
    for (Row row : Row.values()) {
      System.out.print("│");
      squares.stream().filter(sq -> sq.row().equals(row)).toList().stream()
          .map(square -> stoneString(board.getStone(square).orElse(null)))
          .forEach(string ->
              System.out.print(string + "│")
          );
      System.out.println();
      if (!row.equals(Row.ROW_8)) {
        System.out.println("├─┼─┼─┼─┼─┼─┼─┼─┤");
      } else {
        System.out.println("└─┴─┴─┴─┴─┴─┴─┴─┘");
      }
    }
  }

  static private String stoneString(Stone stone) {
    if (stone == null) {
      return "　";
    } else {
      if (stone.equals(Stone.BLACK)) {
        return "●";
      } else if (stone.equals(Stone.WHITE)) {
        return "○";
      }
    }
    return "☆";
  }

}
