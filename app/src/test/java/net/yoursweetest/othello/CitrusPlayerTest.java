package net.yoursweetest.othello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.Match;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Row;
import othello.base.Square;
import othello.player.Player;
import othello.player.RandomPlayer;
import othello.view.OthelloView;
import othello.view.SetPlayersCallback;

public class CitrusPlayerTest {

  static class ViewStab implements OthelloView {

    private final CitrusPlayer player1;
    private final CitrusPlayer player2;
    private final Player anotherPlayer;

    ViewStab(CitrusPlayer player1, CitrusPlayer player2) {
      this.player1 = player1;
      this.player2 = player2;
      this.anotherPlayer = new RandomPlayer("Random 0", 0);
    }

    @Override
    public void selectPlayers(SetPlayersCallback callback) {
      callback.determineTurnOrder(this.player1, this.anotherPlayer);
    }

    @Override
    public void startTurn(Board board, Disk turn) {

    }

    @Override
    public void updateBoard(Board board, Disk turn, List<Square> taken) {
      updateBoardEngine(board, turn, taken);
    }

    @Override
    public void endGameByFoul(Board board, Disk turn) {

    }

    @Override
    public void endGame(Board board, Disk turn, List<Square> taken) {

    }

    private void updateBoardEngine(
        @NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken) {
      int blackDisks = this.countDisk(board, Disk.BLACK);
      int whiteDisks = this.countDisk(board, Disk.WHITE);
      System.err.printf("%s: %d%n", this.diskCharacter(Disk.BLACK), blackDisks);
      System.err.printf("%s: %d%n", this.diskCharacter(Disk.WHITE), whiteDisks);
      System.err.println("    A  B  C  D  E  F  G  H");
      System.err.println("  ┌──┬──┬──┬──┬──┬──┬──┬──┐");
      for (Row row : Row.values()) {
        StringBuilder builder = new StringBuilder(String.format("%d |", row.getIndex() + 1));
        Arrays.stream(Square.values()).filter(sq -> sq.row().equals(row))
            .map(square -> diskCharacter(board.getDisk(square).orElse(null)))
            .forEach(string -> {
              builder.append(string);
              builder.append("|");
            });
        System.err.println(builder);
        if (!row.equals(Row.ROW_8)) {
          System.err.println("  ├──┼──┼──┼──┼──┼──┼──┼──┤");
        } else {
          System.err.println("  └──┴──┴──┴──┴──┴──┴──┴──┘");
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

  static private class PlayerWrapper implements Player {

    CitrusPlayer player1;
    CitrusPlayer player2;
    Disk myDisk;

    public PlayerWrapper(CitrusPlayer player1, CitrusPlayer player2) {
      this.player1 = player1;
      this.player2 = player2;
      this.myDisk = null;
    }

    @Override
    public String getName() {
      return "Test Target Player";
    }

    @Override
    public void init(Disk myDisk) {
      this.player1.init(myDisk);
      this.player2.init(myDisk);
    }

    @Override
    public Optional<Disk> getMyDisk() {
      return Optional.ofNullable(this.myDisk);
    }

    @Override
    public Optional<Square> moveDisk(Board board, Square moved) {
      Board board1 = board.clone();
      Board board2 = board.clone();
      List<Square> candidates1 = player1.allCandidates(board1, moved);
      List<Square> candidates2 = player2.allCandidates(board2, moved);
      assertEquals(candidates1.size(), candidates2.size());
      assertNotEquals(0, candidates1.size());
      System.err.printf("Candidates: %d %d%n", candidates1.size(), candidates2.size());
      for (int k = 0; k < candidates1.size(); k++) {
        assertEquals(candidates1.get(k), candidates2.get(k));
      }
      Square moved1 = player1.moveDisk(board1, moved).orElse(null);
      Square moved2 = player2.moveDisk(board2, moved).orElse(null);
      assertEquals(moved1, moved2);
      return Optional.ofNullable(moved1);
    }

    @Override
    public void shutdown() {
      this.player1.shutdown();
      this.player2.shutdown();
    }
  }

  static void compareMovedWith(CitrusPlayer player1, CitrusPlayer player2) {
    OthelloView view = new ViewStab(player1, player2);
    Match match = new Match(view, 1L);
    Thread thread = new Thread(match);
    thread.start();
    try {
      thread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
