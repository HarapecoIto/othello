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

public class Match extends Thread {

  public enum TurnStatus {
    START_OF_GAME, MOVED, PASS, FOUL, END_OF_GAME
  }

  private final OthelloView view;
  private final Board board;
  private Disk turn;
  private boolean passFlag;
  private boolean endOfGame;
  private Optional<Player> blackPlayer;
  private Optional<Player> whitePlayer;

  Match(@NotNull OthelloView view) {
    this.view = view;
    this.board = new Board();
    this.board.init();
    this.turn = Disk.WHITE;
    this.passFlag = false;
    this.endOfGame = false;
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
    this.blackPlayer.get().init(Disk.BLACK);
    this.whitePlayer.get().init(Disk.WHITE);
    this.view.updateBoard(this.board.clone(), this.turn, new ArrayList<>());
    do {
      // assert
      Optional<Player> player = this.turn.equals(Disk.BLACK) ? this.blackPlayer : this.whitePlayer;
      if (player.isEmpty()) {
        System.err.println("Error: no player.");
        this.endOfGame = true;
        return;
      }
      // exec turn
      this.view.startTurn(this.board.clone(), this.turn);
      Optional<List<Square>> taken = this.turn(player.get());
      // foul -> end
      if (taken.isEmpty()) {
        this.view.endGameByFoul(this.board.clone(), this.turn);
        this.endOfGame = true;
        return;
      }
      // pass
      if (taken.get().isEmpty()) {
        // both player's pass -> end of game
        if (this.passFlag) {
          this.view.endGame(this.board.clone(), this.turn, taken.get());
          this.endOfGame = true;
          return;
        }
        // first player's pass
        this.passFlag = true;
      } else {
        // clear pass
        this.passFlag = false;
      }
      this.view.updateBoard(this.board.clone(), this.turn, taken.get());
      this.turn = this.turn.equals(Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
      // end of game
      int blackDisks = Tools.countDisks(this.board.clone(), Disk.BLACK);
      int whiteDisks = Tools.countDisks(this.board.clone(), Disk.WHITE);
      if (blackDisks + whiteDisks == 64) {
        this.view.endGame(this.board.clone(), this.turn, taken.get());
        this.endOfGame = true;
        return;
      }
    } while (true);
  }

  private List<Square> getMoveableSquares() {
    return Arrays.stream(Square.values())
        .filter(sq -> Tools.countReversibleDisks(this.board.clone(), sq, this.turn) > 0)
        .toList();
  }

  public Optional<List<Square>> turn(Player player) {
    // assert
    if (this.endOfGame) {
      return Optional.empty();
    }
    List<Square> list = getMoveableSquares();
    if (list.isEmpty()) {
      if (this.passFlag) {
        return Optional.of(new ArrayList<>());
      }
      return Optional.of(new ArrayList<>());
    }
    Optional<Square> toMove = player.moveDisk(this.board.clone());
    // foul
    if (toMove.isEmpty()) {
      return Optional.empty();
    }
    // moved
    return Tools.move(this.board, toMove.get(), this.turn);
  }

}

