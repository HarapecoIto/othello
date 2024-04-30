package othello.view;

import java.util.Arrays;
import java.util.List;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;

public class ConsoleView implements OthelloView {

  public ConsoleView() {
  }

  @Override
  public void update(Board board) {
    List<Square> squares = Arrays.asList(Square.values());
    System.out.println("    A  B  C  D  E  F  G  H");
    System.out.println("  ┌──┬──┬──┬──┬──┬──┬──┬──┐");
    for (Row row : Row.values()) {
      StringBuilder builder = new StringBuilder(String.format("%d |", row.getIndex() + 1));
      squares.stream().filter(sq -> sq.row().equals(row))
          .map(square -> stoneString(board.getDisk(square).orElse(null)))
          .forEach(string -> {
            builder.append(string);
            builder.append("|");
          });
      System.out.println(builder);
      if (!row.equals(Row.ROW_8)) {
        System.out.println("  ├──┼──┼──┼──┼──┼──┼──┼──┤");
      } else {
        System.out.println("  └──┴──┴──┴──┴──┴──┴──┴──┘");
      }
    }
  }

  private String stoneString(Disk disk) {
    if (disk == null) {
      return "　";
    } else {
      if (disk.equals(Disk.BLACK)) {
        return "㊚";
      } else if (disk.equals(Disk.WHITE)) {
        return "㊛";
      }
    }
    return "";
  }

}
