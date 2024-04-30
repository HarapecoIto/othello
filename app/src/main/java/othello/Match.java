package othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;
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
  private Stone turn;
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
    this.blackPlayer.init(Stone.BLACK);
    this.whitePlayer.init(Stone.WHITE);
    this.turn = Stone.BLACK;
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

  private Player getPlayer(@NotNull Stone playerStone) {
    return playerStone.equals(Stone.BLACK) ? this.blackPlayer : this.whitePlayer;
  }

  private int countStones(Stone stone) {
    if (stone != null) {
      return (int) Arrays.stream(Square.values()).filter(sq -> {
        Optional<Stone> opt = this.board.getStone(sq);
        return opt.isPresent() && opt.get().equals(stone);
      }).count();
    } else {
      return (int) Arrays.stream(Square.values()).filter(sq ->
          this.board.getStone(sq).isEmpty()
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
    Optional<Square> toMove = getPlayer(this.turn).moveStone(board.clone());
    if (toMove.isEmpty() || !list.contains(toMove.get())) {
      // TODO foul
      return;
    } else {
      Optional<List<Square>> taken = Tools.move(board, toMove.get(), this.turn);
      int blackCount = this.countStones(Stone.BLACK);
      int whiteCount = this.countStones(Stone.WHITE);
      if (blackCount + whiteCount == 64) {
        // TODO game over.
        return;
      }
      // TODO ok
      return;
    }
  }

}

