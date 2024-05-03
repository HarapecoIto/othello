package othello.view;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;
import othello.player.Player;
import othello.player.RandomPlayer;

public class ConsoleView implements OthelloView {

  private final Player player1;
  private final Player player2;
  private final String blackSymbol;
  private final String whiteSymbol;

  public ConsoleView(@NotNull String blackSymbol, @NotNull String whiteSymbol) {
    this.player1 = new RandomPlayer("Random 1", 1L);
    this.player2 = new RandomPlayer("Random 2", 2L);
    this.blackSymbol = blackSymbol;
    this.whiteSymbol = whiteSymbol;
  }

  @Override
  public void selectPlayers(@NotNull TurnOrderDeterminer match) {
    match.determineTurnOrder(this.player1, this.player2);
  }

  @Override
  public void startTurn(@NotNull Board board, @NotNull Disk turn) {
    Player player =
        turn.equals(this.player1.getMyDisk().orElse(null)) ? this.player1 : this.player2;
    this.updateBoardEngine(board, turn, new ArrayList<>());
    System.out.printf("%s(%s) turn.%n", this.diskCharacter(turn), player.getName());

  }

  @Override
  public void endGameByFoul(Board board, Disk turn) {
    Player player =
        turn.equals(this.player1.getMyDisk().orElse(null)) ? this.player1 : this.player2;
    System.out.printf("Foul! %s(%s) lose.%n", this.diskCharacter(turn), player.getName());
    this.updateBoardEngine(board, turn, new ArrayList<>());
  }


  @Override
  public void endGame(Board board, Disk turn, @NotNull List<Square> taken) {
    Player player =
        turn.equals(this.player1.getMyDisk().orElse(null)) ? this.player1 : this.player2;
    int blackDisks = this.countDisk(board, Disk.BLACK);
    int whiteDisks = this.countDisk(board, Disk.WHITE);
    this.updateBoardEngine(board, turn, taken);
    if (blackDisks != whiteDisks) {
      String symbol = this.diskCharacter(blackDisks > whiteDisks ? Disk.BLACK : Disk.WHITE);
      System.out.printf("Winner: %s(%s)%n", symbol, player.getName());
    } else {
      System.out.println("Draw");
    }
  }

  @Override
  public void updateBoard(
      @NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken) {
  }

  private void updateBoardEngine(
      @NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken) {
    int blackDisks = this.countDisk(board, Disk.BLACK);
    int whiteDisks = this.countDisk(board, Disk.WHITE);
    System.out.printf("%s: %d%n", this.diskCharacter(Disk.BLACK), blackDisks);
    System.out.printf("%s: %d%n", this.diskCharacter(Disk.WHITE), whiteDisks);
    System.out.println("    A  B  C  D  E  F  G  H");
    System.out.println("  ┌──┬──┬──┬──┬──┬──┬──┬──┐");
    for (Row row : Row.values()) {
      StringBuilder builder = new StringBuilder(String.format("%d |", row.getIndex() + 1));
      Arrays.stream(Square.values()).filter(sq -> sq.row().equals(row))
          .map(square -> diskCharacter(board.getDisk(square).orElse(null)))
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

  private int countDisk(@NotNull Board board, @NotNull Disk disk) {
    return (int) Arrays.stream(Square.values()).filter(
        sq -> {
          Optional<Disk> opt = board.getDisk(sq);
          return opt.isPresent() && opt.get().equals(disk);
        }).count();
  }

  private String diskCharacter(Disk disk) {
    if (disk == null) {
      return "　";
    } else {
      if (disk.equals(Disk.BLACK)) {
        return this.blackSymbol;
      } else if (disk.equals(Disk.WHITE)) {
        return this.whiteSymbol;
      }
    }
    return "";
  }

}
