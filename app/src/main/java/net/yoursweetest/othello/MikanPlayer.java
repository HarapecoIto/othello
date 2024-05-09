package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import othello.OthelloException;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class MikanPlayer extends CitrusPlayer {

  private static final class Position {

    private final Board board;
    private Optional<Square> moved;
    private final Disk turn;
    private int step;
    private int myDiskCount;
    private int yourDiskCount;
    private Optional<List<Position>> children;

    Position(@NotNull Board board, Optional<Square> moved, @NotNull Disk turn, int step) {
      this.board = board;
      this.moved = moved;
      this.turn = turn;
      this.step = step;
      this.myDiskCount = 0;
      this.yourDiskCount = 0;
      this.children = Optional.empty();
    }

    public Board getBoard() {
      return this.board.clone();
    }

    public Disk getTurn() {
      return this.turn;
    }

    public void setStep(int step) {
      this.step = step;
    }

    public int getStep() {
      return this.step;
    }

    public void setMoved(Optional<Square> square) {
      this.moved = square;
    }

    public Optional<Square> getMoved() {
      return this.moved;
    }

    public void setMyDiskCount(int count) {
      this.myDiskCount = count;
    }

    public int getMyDiskCount() {
      return this.myDiskCount;
    }

    public void setYourDiskCount(int count) {
      this.yourDiskCount = count;
    }

    public int getYourDiskCount() {
      return this.yourDiskCount;
    }

    public void setChildren(@NotNull List<Position> children) {
      this.children = Optional.of(children);
    }

    public Optional<List<Position>> getChildren() {
      return this.children;
    }

  }

  private final int MAX_STEP;
  private Optional<Position> root;

  public MikanPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.root = Optional.empty();
  }

  private Position searchNewRoot(@NotNull Board board, Optional<Square> moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // first move
    if (this.root.isEmpty()) {
      System.err.println("The first move.");
      return new Position(board, Optional.empty(), this.myDisk.get().reverse(), 0);
    }
    // you passed
    if (this.root.get().getChildren().isEmpty()) {
      return new Position(board, moved, this.myDisk.get().reverse(), 0);
    }
    // root is already explored -> search children
    for (Position p1 : this.root.get().getChildren().get()) {
      for (Position p2 : p1.getChildren().orElse(new ArrayList<>())) {
        if (p2.getBoard().equals(board)) {
          p2.setStep(0);
          return p2;
        }
      }
    }
    // assert
    System.err.println("No root found.");
    throw new OthelloException();
  }

  @Override
  public Optional<Square> moveDisk(
      @NotNull Board board, Optional<Square> moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    if (moved.isPresent()) {
      System.err.printf("You moved, Row: %d, Col: %d%n", moved.get().row().getIndex(),
          moved.get().col().getIndex());
    } else {
      System.err.println("The first move or You passed");
    }
    // new root
    boolean youPassed = this.root.isPresent() && this.root.get().getChildren().isEmpty();
    Position newRoot = this.searchNewRoot(board, moved);
    this.root = Optional.of(newRoot);
    this.explore(newRoot, youPassed);
    // best move
    if (newRoot.getChildren().isPresent()) {
      int max = newRoot.getChildren().get().stream()
          .map(Position::getMyDiskCount)
          .max(Comparator.naturalOrder()).orElse(0);
      Optional<Square> square = newRoot.getChildren().get().stream()
          .filter(p -> p.getMyDiskCount() == max)
          .filter(p -> p.getMoved().isPresent())
          .map(p -> p.getMoved().get()).findFirst();
      if (max > 0 && square.isPresent()) {
        System.err.printf("I move, Row: %d, Col: %d%n", square.get().row().getIndex(),
            square.get().col().getIndex());
        // update
        this.root = Optional.of(newRoot);
        return square;
      }
    }
    System.err.println("pass or failed");
    return Optional.empty();
  }

  private void explore(@NotNull Position position1, boolean passed) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    position1.setMyDiskCount(0);
    position1.setYourDiskCount(0);
    int myDisks = Tools.countDisks(position1.getBoard(), this.myDisk.get());
    int yourDisks = Tools.countDisks(position1.getBoard(), this.myDisk.get().reverse());
    if (position1.getStep() < MAX_STEP && myDisks + yourDisks < 64) {
      // there exists data of previous turn
      if (position1.getChildren().isPresent()) {
        System.err.println("position1 had already explored");
        // just update step
        position1.getChildren().get().forEach(
            p -> {
              p.setStep(position1.getStep() + 1);
              this.explore(p, false);
            }
        );
      } else {
        System.err.println("position1 had not explored");
        // unexplored -> (normal move)
        List<Position> children = Arrays.stream(Square.values())
            .filter(
                sq -> Tools.countReversibleDisks(position1.getBoard(), sq,
                    position1.getTurn().reverse())
                    > 0
            ).map(
                sq -> {
                  // move
                  Board work = position1.getBoard().clone();
                  Tools.move(work, sq, position1.getTurn().reverse());
                  // next position
                  Position position2 =
                      new Position(
                          work, Optional.of(sq), position1.getTurn().reverse(),
                          position1.getStep() + 1);
                  // exec explore
                  this.explore(position2, passed);
                  return position2;
                }).toList();

        // debug print
        children.forEach(
            p -> {
              if (p.getMoved().isPresent()) {
                System.err.printf("Step: %d, Row: %d, Col: %d Max: %d, %s%n", p.getStep(),
                    p.getMoved().get().row().getIndex(),
                    p.getMoved().get().col().getIndex(),
                    p.getMyDiskCount(),
                    p.getTurn().equals(Disk.BLACK) ? "BLACK" : "WHITE");
              }

            });

        position1.setChildren(children);
      }
      // not (pass -> pass)
      if (!(passed && position1.getChildren().orElse(new ArrayList<>()).isEmpty())) {
        // count max
        int myMax = position1.getChildren().get().stream().map(Position::getMyDiskCount)
            .max(Comparator.naturalOrder()).orElse(0);
        int yourMax = position1.getChildren().get().stream().map(Position::getYourDiskCount)
            .max(Comparator.naturalOrder()).orElse(0);
        position1.setMyDiskCount(myMax);
        position1.setYourDiskCount(yourMax);
        return;
      }
    }
    // (max step) or (pass -> pass) or (end of game)
    System.err.println("Leaf");
    position1.setMyDiskCount(
        Tools.countDisks(position1.getBoard(), position1.getTurn()));
    position1.setYourDiskCount(
        Tools.countDisks(position1.getBoard(), position1.getTurn().reverse()));
  }

}
