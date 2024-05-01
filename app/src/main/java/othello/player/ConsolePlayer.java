package othello.player;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class ConsolePlayer implements Player {

  private final Disk myDisk;
  private final String name;

  public ConsolePlayer(Disk myDisk, String name) {
    this.myDisk = myDisk;
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Optional<Square> moveDisk(Board board) {
    List<Square> squares = Arrays.stream(Square.values()).filter(
        sq -> Tools.countReversibleDisks(board.clone(), sq, this.myDisk) > 0
    ).toList();
    System.out.println("Select square.");
    System.out.flush();
    while (true) {
      Optional<Square> square = this.getSquareByConsole();
      if (square.isPresent()
          && squares.stream().filter(sq -> square.get().equals(sq)).count() == 1) {
        return square;
      }
      System.out.println("Bad square.");
    }
  }

  private Optional<Square> getSquareByConsole() {
    String line = new Scanner(System.in).nextLine();
    List<Integer> rows = Arrays.stream("13245678".split(""))
        .filter(line::contains)
        .map(c -> (int) (c.toCharArray()[0] - '1')).toList();
    List<Integer> cols1 = Arrays.stream("abcdefgh".split(""))
        .filter(line::contains)
        .map(c -> (int) (c.toCharArray()[0] - 'a')).toList();
    List<Integer> cols2 = Arrays.stream("ABCDEFGH".split(""))
        .filter(line::contains)
        .map(c -> (int) (c.toCharArray()[0] - 'A')).toList();
    if (rows.size() == 1 && cols1.size() + cols2.size() == 1) {
      int row = rows.get(0);
      int col = !cols1.isEmpty() ? cols1.get(0) : cols2.get(0);
      System.out.printf("row: %d, col: %d%n", row, col);
      return Optional.of(Square.values()[row * 8 + col]);
    }
    return Optional.empty();
  }
}
