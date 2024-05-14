package othello.util;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

/**
 * You can use these tools to check the board status. Built-in classes are also use these tools.
 */
public class Tools {

  private static final List<Function<Square, Optional<Square>>> nextFunctions;

  static {
    nextFunctions = new ArrayList<>();
    nextFunctions.add(Square::up);
    nextFunctions.add(Square::down);
    nextFunctions.add(Square::left);
    nextFunctions.add(Square::right);
    nextFunctions.add(Square::upLeft);
    nextFunctions.add(Square::upRight);
    nextFunctions.add(Square::downLeft);
    nextFunctions.add(Square::downRight);
  }

  private static ExecutorService service;

  public static void init() {
    service = Executors.newFixedThreadPool(8);
  }

  public static void shutdown() {
    service.shutdownNow();
  }

  /**
   * Count disks on the board.
   *
   * @param board Target board to count disks.
   * @param disk  The disk color to be counted. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return Number of disks specified by disk parameter.
   */
  public static int countDisks(@NotNull Board board, @NotNull Disk disk) {
    return (int) Arrays.stream(Square.values())
        .filter(sq -> disk.equals(board.getDisk(sq).orElse(null))).count();
  }

  /**
   * Move new disk to the board.
   *
   * @param board  The board to move the disk.
   * @param square Target square to move the disk.
   * @param mine   Disk color to be moved. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return {@code Optional} of List containing turned over disks.
   * @throws OthelloException This square cannot be placed due to the rules.
   */
  public static synchronized Optional<List<Square>> move(
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    Optional<List<Square>> opt = countTurnoverableDisks(board, square, mine);
    if (opt.isEmpty()) {
      throw new OthelloException();
    }
    opt.get().forEach(sq -> board.setDisk(sq, mine));
    board.setDisk(square, mine);
    return opt;
  }

  /**
   * Count how many another player's disk to be turned over, if you move the disk on these squares
   * correspond to each square. Note: No actual moves are made.
   *
   * @param board Target board which to be moved the disk.
   * @param mine  Disk color to be moved. {@code Disk.BLACK} or {@code Disk.WHITE}.
   * @return Score object containing the number of disks to be turned over corresponding to each
   * square.
   */
  public static synchronized Score countTurnoverableDisks(
      @NotNull Board board, @NotNull Disk mine) {
    Score score = new Score();
    for (Square sq : Square.values()) {
      Optional<List<Square>> list = countTurnoverableDisks(board, sq, mine);
      score.setScore(sq, list.map(List::size).orElse(-1));
    }
    return score;
  }

  private static class TurnoverableCounter implements Callable<Optional<List<Square>>> {

    private final Function<Square, Optional<Square>> next;
    private final Board board;
    private final Square square;
    private final Disk mine;

    TurnoverableCounter(
        @NotNull Function<Square, Optional<Square>> next,
        @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
      this.next = next;
      this.board = board;
      this.square = square;
      this.mine = mine;
    }

    @Override
    public Optional<List<Square>> call() throws Exception {
      return countTurnoverableDisksOfLineEngine(next, board, square, mine);
    }
  }

  static Optional<List<Square>> countTurnoverableDisks(
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    List<List<Square>> result =
        nextFunctions.stream()
            .map(
                next -> new TurnoverableCounter(next, board, square, mine))
            .map(
                task -> service.submit(task))
            .map(
                future -> {
                  Optional<List<Square>> opt = Optional.empty();
                  try {
                    opt = future.get();
                  } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                  }
                  return opt;
                })
            .filter(opt -> opt.isPresent())
            .map(opt -> opt.orElse(new ArrayList<>()))
            .toList();
    if (result.size() < 8) {
      return Optional.empty();
    }
    return Optional.of(result.stream().flatMap(Collection::stream).collect(Collectors.toList()));
  }

  static Optional<List<Square>> countTurnoverableDisksOfLineEngine(
      @NotNull Function<Square, Optional<Square>> next,
      @NotNull Board board, @NotNull Square square, @NotNull Disk mine) {
    // make sure that the square is empty.
    if (board.getDisk(square).isPresent()) {
      return Optional.empty();
    }
    final Disk yours = mine.turnOver();
    List<Square> result = new ArrayList<>();
    Optional<Square> opt = next.apply(square);
    do {
      // out of board.
      if (opt.isEmpty()) {
        return Optional.of(new ArrayList<>());
      }
      // next disk.
      Optional<Disk> disk = board.getDisk(opt.get());
      // if empty, ng.
      if (disk.isEmpty()) {
        return Optional.of(new ArrayList<>());
      }
      // if mine, ok.
      if (mine.equals(disk.get())) {
        return Optional.of(result);
      }
      // turn over yours.
      if (yours.equals(disk.get())) {
        result.add(opt.get());
      }
      opt = next.apply(opt.orElse(null));
    } while (true);
  }

}
