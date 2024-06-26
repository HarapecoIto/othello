package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

/**
 * Console player is a manual player, which can be input the square to move the disc to be input
 * from the console.
 */
public class ConsolePlayer implements Player {

  private Optional<Disk> myDisk;
  private final String name;

  /**
   * Constructor of console player.
   *
   * @param name Player's name.
   */
  public ConsolePlayer(@NotNull String name) {
    this.myDisk = Optional.empty();
    this.name = name;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = Optional.of(myDisk);
  }

  @Override
  public Optional<Disk> getMyDisk() {
    return this.myDisk;
  }

  @Override
  public Optional<Square> move(@NotNull Board board, @NotNull Square moved) {
    //assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Board clone = board.clone();
    Score score = Tools.countTurnoverableDisks(clone, this.myDisk.get());
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) > 0).toList();
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

  @Override
  public void shutdown() {
  }

}
