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

  private Optional<Player> blackPlayer;
  private Optional<Player> whitePlayer;

  public ConsoleView() {
    this.blackPlayer = Optional.empty();
    this.whitePlayer = Optional.empty();
  }

  @Override
  public Optional<Player> selectBlackPlayer() {
    this.blackPlayer = Optional.of(new RandomPlayer(Disk.BLACK, 1L, "Random 1"));
    return this.blackPlayer;
  }

  @Override
  public Optional<Player> selectWhitePlayer() {
    this.whitePlayer = Optional.of(new RandomPlayer(Disk.WHITE, 2L, "Random 2"));
    return this.whitePlayer;
  }

  @Override
  public void startTurn(@NotNull Board board, @NotNull Disk turn) {
    if (this.blackPlayer.isPresent() && this.whitePlayer.isPresent()) {
      Optional<Player> player = turn.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
      this.updateBoardEngine(board, turn, new ArrayList<>());
      System.out.printf("%s(%s) turn.%n", this.diskCharacter(turn), player.get().getName());
    }
  }

  @Override
  public void endGameByFoul(Board board, Disk turn) {
    if (this.blackPlayer.isPresent() && this.whitePlayer.isPresent()) {
      Optional<Player> player = turn.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
      System.out.printf("Foul! %s(%s) lose.%n", this.diskCharacter(turn), player.get().getName());
      this.updateBoardEngine(board, turn, new ArrayList<>());
    }
  }

  @Override
  public void endGame(Board board, Disk turn, @NotNull List<Square> taken) {
    if (this.blackPlayer.isPresent() && this.whitePlayer.isPresent()) {
      Optional<Player> player = turn.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
      System.out.printf("%s(%s) win.%n", this.diskCharacter(turn), player.get().getName());
      this.updateBoardEngine(board, turn, taken);
    }
  }

  @Override
  public void updateBoard(@NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken) {
  }

  private void updateBoardEngine(@NotNull Board board, @NotNull Disk turn,
      @NotNull List<Square> taken) {
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
        return "㊚";
      } else if (disk.equals(Disk.WHITE)) {
        return "㊛";
      }
    }
    return "";
  }

}
