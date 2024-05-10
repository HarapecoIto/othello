package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

public class LemonPlayer extends CitrusPlayer {

  private static final class Position {

    private final Board board;
    private final Disk turn;
    private final int step;
    private final List<Integer> myDisks;
    private final List<Integer> yourDisks;

    Position(@NotNull Board board, @NotNull Disk turn, int step) {
      this.board = board;
      this.turn = turn;
      this.step = step;
      this.myDisks = new ArrayList<>();
      this.yourDisks = new ArrayList<>();
      Arrays.stream(Square.values()).forEach(sq -> {
        this.myDisks.add(0);
        this.yourDisks.add(0);
      });
    }

    public Board getBoard() {
      return this.board.clone();
    }

    public Disk getTurn() {
      return this.turn;
    }

    public int getStep() {
      return this.step;
    }

    public void setMyDisks(int idx, int disks) {
      if (this.myDisks.size() > idx) {
        this.myDisks.set(idx, disks);
      }
    }

    public void setYourDisks(int idx, int disks) {
      if (this.yourDisks.size() > idx) {
        this.yourDisks.set(idx, disks);
      }
    }

    public List<Integer> getMyDisks() {
      return Collections.unmodifiableList(this.myDisks);
    }

    public List<Integer> getYourDisks() {
      return Collections.unmodifiableList(this.yourDisks);
    }
  }


  private final int MAX_STEP;

  public LemonPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
  }

  @Override
  List<Square> moveCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      throw new OthelloException();
    }
    Position position = new Position(board, this.myDisk.get(), 0);
    this.explore(position);
    int max = position.getMyDisks().stream().max(Comparator.naturalOrder()).orElse(0);
    List<Square> squares = Arrays.stream(Square.values())
        .filter(sq -> position.getMyDisks().get(sq.getIndex()) == max)
        .toList();
    if (max > 0 && !squares.isEmpty()) {
      return squares;
    }
    return new ArrayList<>();
  }

  private void explore(@NotNull Position position1) {
    if (position1.getStep() < MAX_STEP) {
      Score score = Tools.countReversibleDisks(position1.getBoard(), position1.getTurn());
      List<Square> movable = Arrays.stream(Square.values())
          .filter(
              sq -> score.getScore(sq) > 0
          ).toList();
      if (!movable.isEmpty()) {
        movable.forEach(
            // explore
            sq -> {
              // move
              Board work = position1.getBoard().clone();
              Tools.move(work, sq, position1.getTurn());
              // next position
              Position position2 =
                  new Position(work, position1.getTurn().reverse(), position1.getStep() + 1);
              // exec explore
              this.explore(position2);
              // count max
              int myMax = position2.getMyDisks().stream()
                  .max(Comparator.naturalOrder()).orElse(0);
              int yourMax = position2.getYourDisks().stream()
                  .max(Comparator.naturalOrder()).orElse(0);
              position1.setMyDisks(sq.getIndex(), myMax);
              position1.setYourDisks(sq.getIndex(), yourMax);
            });
        return;
      }
    }
    // max step or pass or end of game
    Arrays.stream(Square.values()).forEach(sq -> {
      position1.setMyDisks(sq.getIndex(),
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
      position1.setYourDisks(sq.getIndex(),
          Tools.countDisks(position1.getBoard(), position1.getTurn()));
    });
  }

}
