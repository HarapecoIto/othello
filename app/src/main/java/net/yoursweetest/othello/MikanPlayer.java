package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private final Optional<Square> moved;
    private final Disk turn;
    private int step;
    private int myDiskCount;
    private int yourDiskCount;
    private List<Position> children;

    Position(@NotNull Board board, @NotNull Optional<Square> moved, @NotNull Disk turn, int step) {
      this.board = board;
      this.moved = moved;
      this.turn = turn;
      this.step = step;
      this.myDiskCount = 0;
      this.yourDiskCount = 0;
      this.children = null;
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
      this.children = children;
    }

    public List<Position> getChildren() {
      List<Position> list = this.isExplored() ? this.children : new ArrayList<>();
      return Collections.unmodifiableList(list);
    }

    public boolean isExplored() {
      return this.children != null;
    }

    public boolean isLeaf() {
      return this.children != null && this.children.isEmpty();
    }

    public boolean isBranch() {
      return this.children != null && !this.children.isEmpty();
    }

  }

  private final int MAX_STEP;
  private Optional<Position> root;

  public MikanPlayer(String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.root = Optional.empty();
  }

  private Position searchNewRoot(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // root is already explored -> search children
    if (this.root.isPresent()) {
      for (Position p1 : this.root.get().getChildren()) {
        for (Position p2 : p1.getChildren()) {
          if (p2.getBoard().equals(board)) {
            p2.setStep(0);
            return p2;
          }
        }
      }
    }
    return new Position(board, Optional.empty(), this.myDisk.get().reverse(), 0);
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // Debug print
    if (moved != null) {
      System.err.printf("You moved, Row: %d, Col: %d%n", moved.row().getIndex(),
          moved.col().getIndex());
    } else {
      System.err.println("The first move or You passed");
    }
    // new root
    Position newRoot = this.searchNewRoot(board);
    this.explore(newRoot, this.root.isPresent() && this.root.get().isLeaf());
    this.root = Optional.of(newRoot);
    // best move
    if (newRoot.isExplored()) {
      int max = newRoot.getChildren().stream()
          .map(Position::getMyDiskCount)
          .max(Comparator.naturalOrder()).orElse(0);
      List<Square> squares = newRoot.getChildren().stream()
          .filter(p -> p.getMyDiskCount() == max)
          .filter(p -> p.getMoved().isPresent())
          .map(p -> p.getMoved().get()).toList();
      if (max > 0 && !squares.isEmpty()) {
        Square square = squares.get(rand.nextInt(squares.size()));
        System.err.printf("I move, Row: %d, Col: %d%n", square.row().getIndex(),
            square.col().getIndex());
        return Optional.of(square);
      }
    }
    System.err.println("pass or failed");
    return Optional.empty();
  }

  private void explore(@NotNull Position position1, boolean passed) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // debug print
    if (position1.getMoved().isPresent()) {
      System.err.printf("Step: %d, Row: %d, Col: %d Max: %d, %s%n", position1.getStep(),
          position1.getMoved().get().row().getIndex(),
          position1.getMoved().get().col().getIndex(),
          position1.getMyDiskCount(),
          position1.getTurn().equals(Disk.BLACK) ? "BLACK" : "WHITE");
    }

    position1.setMyDiskCount(0);
    position1.setYourDiskCount(0);
    if (!stopExploration(position1)) {
      // there exists data of previous turn
      if (position1.isExplored()) {
        System.err.println("position1 had already explored");
        // just update step
        position1.getChildren().forEach(
            p -> {
              p.setStep(position1.getStep() + 1);
              this.explore(p, position1.isLeaf());
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
                  this.explore(position2, position1.getChildren().isEmpty());
                  return position2;
                }).toList();
        position1.setChildren(children);
      }
      // not (pass -> pass)
      if (!passed || !position1.isLeaf()) {
        System.err.println("Branch");
        mergeMaxDisksOfChildren(position1);
        return;
      }
    }
    // (max step) or (pass -> pass) or (end of game)
    System.err.println("Leaf");
    countDisksOfLeaf(position1);
  }

  private boolean stopExploration(@NotNull Position position) {
    // asset
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    int blackDisks = Tools.countDisks(position.getBoard(), Disk.BLACK);
    int whiteDisks = Tools.countDisks(position.getBoard(), Disk.WHITE);
    return position.getStep() > this.MAX_STEP || blackDisks + whiteDisks >= 64;
  }

  private void mergeMaxDisksOfChildren(Position position) {
    int myMax = position.getChildren().stream().map(Position::getMyDiskCount)
        .max(Comparator.naturalOrder()).orElse(0);
    int yourMax = position.getChildren().stream().map(Position::getYourDiskCount)
        .max(Comparator.naturalOrder()).orElse(0);
    position.setMyDiskCount(myMax);
    position.setYourDiskCount(yourMax);
  }

  private void countDisksOfLeaf(Position position) {
    position.setMyDiskCount(
        Tools.countDisks(position.getBoard(), position.getTurn()));
    position.setYourDiskCount(
        Tools.countDisks(position.getBoard(), position.getTurn().reverse()));
  }

}
