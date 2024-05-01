package othello;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;
import othello.view.OthelloView;

public class Match extends Thread {

  public enum TurnStatus {
    START_OF_GAME, MOVED, PASS, FOUL, END_OF_GAME
  }

  private final OthelloView view;
  private final Board board;
  private Disk turn;
  private boolean passFlag;
  private Optional<Player> blackPlayer;
  private Optional<Player> whitePlayer;

  Match(@NotNull OthelloView view) {
    this.view = view;
    this.board = new Board();
    this.board.init();
    this.turn = Disk.BLACK;
    this.passFlag = false;
    this.blackPlayer = Optional.empty();
    this.whitePlayer = Optional.empty();
  }

  @Override
  public void run() {
    this.blackPlayer = this.view.selectBlackPlayer();
    this.whitePlayer = this.view.selectWhitePlayer();
    if (this.blackPlayer.isEmpty() || this.whitePlayer.isEmpty()) {
      System.err.println("Could not select players.");
      return;
    }
    TurnStatus status = null;
    do {
      Optional<Player> player = this.turn.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
      // assert
      if (player.isEmpty()) {
        System.err.println("Error: no player.");
        return;
      }
      // exec turn
      this.view.startTurn(this.board.clone(), this.turn);
      status = this.turn(player.get());
      // foul -> end
      if (status.equals(TurnStatus.FOUL)) {
        this.view.endGameByFoul(this.board.clone(), this.turn);
        return;
      }
      // moved or pass -> next turn
      if (status.equals(TurnStatus.MOVED) || status.equals(TurnStatus.PASS)) {
        this.view.updateBoard(this.board.clone(), this.turn);
      }
      // switch turn
      this.turn = this.turn.equals(Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
    } while (!status.equals(TurnStatus.END_OF_GAME));
    // end of game
    this.view.endGame(this.board.clone(), this.turn);
  }

  private List<Square> getMoveableSquares() {
    return Arrays.stream(Square.values())
        .filter(sq -> Tools.countReversibleDisks(this.board.clone(), sq, this.turn) > 0)
        .toList();
  }

  public TurnStatus turn(Player player) {
    List<Square> list = getMoveableSquares();
    if (list.isEmpty()) {
      int blackCount = Tools.countDisks(this.board, Disk.BLACK);
      int whiteCount = Tools.countDisks(this.board, Disk.WHITE);
      if (blackCount + whiteCount == 64) {
        return TurnStatus.END_OF_GAME;
      }
      if (this.passFlag) {
        return TurnStatus.END_OF_GAME;
      }
      this.passFlag = true;
      return TurnStatus.PASS;
    }
    this.passFlag = false;
    Optional<Square> toMove = player.moveDisk(this.board.clone());
    if (toMove.isEmpty() || !list.contains(toMove.get())) {
      return TurnStatus.FOUL;
    } else {
      Optional<List<Square>> taken = Tools.move(this.board, toMove.get(), this.turn);
      return TurnStatus.MOVED;
    }
  }

}

