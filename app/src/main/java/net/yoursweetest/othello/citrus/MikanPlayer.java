package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import othello.OthelloException;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.util.Score;
import othello.util.Tools;

/**
 * This player is a making efficient version of {@code LemonPlayer}. This player saves the search
 * results of the previous turn and reuses them to reduce amount of calculation. All public methods
 * return the same result as {@code LemonPlayer}. In particular, given the same random seed as
 * {@code LemonPlayer}, they return the same result for each seed.
 */
public class MikanPlayer extends CitrusPlayer {

  static String serializeSquare(Square square) {
    if (square == null) {
      return "@@";
    }
    char row = (char) ((int) '1' + square.row().index());
    char col = (char) ((int) 'A' + square.col().index());
    return new String(new char[]{row, col});
  }

  static String serializeBoard(@NotNull Board board) {
    StringBuilder builder = new StringBuilder();
    Arrays.stream(Square.values()).forEach(sq -> {
      Optional<Disk> opt = board.getDisk(sq);
      if (opt.isPresent()) {
        builder.append(opt.get().equals(Disk.BLACK) ? 'B' : 'W');
      } else {
        builder.append('E');
      }
    });
    return builder.toString();
  }

  private static final Pattern statusPattern = Pattern.compile("^[BWE]{64}$");

  static Optional<Board> deserializeBoard(@NotNull String status) {
    if (statusPattern.matcher(status).matches()) {
      Board board = new Board();
      char[] chars = status.toCharArray();
      Arrays.stream(Square.values()).forEach(
          sq -> {
            char c = chars[sq.index()];
            board.setDisk(sq, c == 'B' ? Disk.BLACK : c == 'W' ? Disk.WHITE : null);
          }
      );
      return Optional.of(board);
    }
    return Optional.empty();
  }

  private static Score plus(Score s1, Score s2) {
    Score result = new Score();
    Arrays.stream(Square.values()).forEach(
        sq -> result.setScore(sq, s1.getScore(sq) + s2.getScore(sq))
    );
    return result;
  }

  private final int MAX_STEP;
  private String targetQueue;

  /**
   * key: moving queue. value: board status.
   */
  private Map<String, String> boardState;

  /**
   * Constructor of Mikan player.
   *
   * @param name    Player's name.
   * @param seed    Random seed.
   * @param maxStep Maximum step what this player explores to move.
   */
  public MikanPlayer(@NotNull String name, long seed, int maxStep) {
    super(name, seed);
    this.MAX_STEP = maxStep;
    this.targetQueue = "";
    this.boardState = new HashMap<>();
  }

  @Override
  List<Square> calculateCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // new root
    int totalDisks = 64 - Tools.countDisks(board, null);
    String prefix = totalDisks > 4 ? this.targetQueue + serializeSquare(moved) : "";
    this.boardState.keySet().stream()
        .filter(queue -> !queue.startsWith(prefix))
        .forEach(queue -> this.boardState.remove(queue));
    Optional<Score> score = explore(prefix);
    int max = Arrays.stream(Square.values())
        .map(sq -> score.orElse(new Score()).getScore(sq))
        .max(Comparator.naturalOrder())
        .orElse(0);
    return Arrays.stream(Square.values())
        .filter(sq -> score.orElse(new Score()).getScore(sq) == max)
        .toList();
  }

  private Optional<Score> explore(String queue) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    int step = queue.length() / 2;
    int targetStep = Math.max(this.targetQueue.length() / 2 + MAX_STEP, 60);
    Optional<Board> board = deserializeBoard(this.boardState.get(queue));
    Disk turn = queue.length() % 4 == 0 ? Disk.WHITE : Disk.BLACK;
    if (board.isEmpty()) {
      return Optional.empty();
    }
    // leaf: max step or (pass-> pass)
    String pass = serializeSquare(null);
    if (step >= targetStep || queue.endsWith(pass + pass)) {
      Score score = Tools.countTurnoverableDisks(board.get(), turn);
      Score result = new Score();
      Arrays.stream(Square.values())
          .filter(sq -> score.getScore(sq) > 0)
          .forEach(sq -> {
            Board work = board.get().clone();
            Tools.move(work, sq, turn.turnOver());
            result.setScore(sq, Tools.countDisks(work, this.myDisk.get()));
            String queueString = queue + serializeSquare(sq);
            String boardString = serializeBoard(work);
            this.boardState.put(queueString, boardString);
          });
      return Optional.of(result);
    }

    // branch
    Score score = Tools.countTurnoverableDisks(board.get(), turn);
    // pass
    if (Arrays.stream(Square.values()).allMatch(sq -> score.getScore(sq) < 1)) {
      String queueString = queue + serializeSquare(null);
      String boardString = this.boardState.get(queue);
      this.boardState.put(queueString, boardString);
      return Optional.of(new Score());
    }
    // explore
    List<Optional<Score>> list = Arrays.stream(Square.values())
        .filter(sq -> score.getScore(sq) > 0)
        .map(sq -> {
          Board work = board.get().clone();
          Tools.move(work, sq, turn.turnOver());
          String newQueue = queue + serializeSquare(sq);
          this.boardState.put(newQueue, serializeBoard(work));
          return explore(newQueue);
        }).toList();
    if (list.stream().anyMatch(Optional::isEmpty)) {
      return Optional.empty();
    }
    Score result = new Score();
    for (Optional<Score> s : list) {
      if (s.isPresent()) {
        result = plus(result, s.get());
      }
    }
    return Optional.of(result);
  }
}