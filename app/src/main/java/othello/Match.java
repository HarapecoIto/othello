package othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;
import othello.view.OthelloView;

public class Match {

  public enum TurnStatus {
    OK, PASS, FOUL, GAME_OVER
  }

  private final OthelloView view;
  private final Player blackPlayer;
  private final Player whitePlayer;
  private final Board board;
  private Disk turn;
  private boolean passFlag;

  Match(@NotNull OthelloView view, @NotNull Player blackPlayer, @NotNull Player whitePlayer) {
    this.view = view;
    this.blackPlayer = blackPlayer;
    this.whitePlayer = whitePlayer;
    this.board = new Board();
    this.init();
  }

  private void init() {
    this.board.init();
    this.blackPlayer.init(Disk.BLACK);
    this.whitePlayer.init(Disk.WHITE);
    this.turn = Disk.BLACK;
    this.passFlag = false;
  }

  private List<Square> getMoveableSquares() {
    List<Square> list = new ArrayList<>();
    for (Square sq : Square.values()) {
      if (Tools.countToTake(this.board.clone(), sq, this.turn) > 0) {
        list.add(sq);
      }
    }
    return list;
  }

  private Player getPlayer(@NotNull Disk playerDisk) {
    return playerDisk.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
  }

  private int countStones(Disk disk) {
    if (disk != null) {
      return (int) Arrays.stream(Square.values()).filter(sq -> {
        Optional<Disk> opt = this.board.getDisk(sq);
        return opt.isPresent() && opt.get().equals(disk);
      }).count();
    } else {
      return (int) Arrays.stream(Square.values()).filter(sq ->
          this.board.getDisk(sq).isEmpty()
      ).count();
    }
  }

  public void turn() {
    List<Square> list = getMoveableSquares();
    if (list.isEmpty()) {
      if (this.passFlag) {
        // TODO game over.
        return;
      }
      // TODO pass
      this.passFlag = true;
      return;
    }
    this.passFlag = false;
    Optional<Square> toMove = getPlayer(this.turn).moveDisk(board.clone());
    if (toMove.isEmpty() || !list.contains(toMove.get())) {
      // TODO foul
      return;
    } else {
      Optional<List<Square>> taken = Tools.move(board, toMove.get(), this.turn);
      int blackCount = this.countStones(Disk.BLACK);
      int whiteCount = this.countStones(Disk.WHITE);
      if (blackCount + whiteCount == 64) {
        // TODO game over.
        return;
      }
      // TODO ok
      return;
    }
  }

}

