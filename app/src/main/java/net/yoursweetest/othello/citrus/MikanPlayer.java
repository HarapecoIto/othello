package net.yoursweetest.othello.citrus;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

  static Optional<Square> deserializeSquare(String string) {
    if (string.matches("^([1-8][A-H])$")) {
      char[] chars = string.toCharArray();
      int row = chars[0] - '1';
      int col = chars[1] - 'A';
      return Optional.of(Square.values()[row * 8 + col]);
    }
    return Optional.empty();
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

  static Optional<Board> deserializeBoard(@NotNull String boardString) {
    if (boardString.matches("^[BWE]{64}$")) {
      Board board = new Board();
      char[] chars = boardString.toCharArray();
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

  private static void add(Score s1, Score s2) {
    Arrays.stream(Square.values()).forEach(
        sq -> s1.setScore(sq, s1.getScore(sq) + s2.getScore(sq))
    );
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
    // target queue
    int totalDisks = Tools.countDisks(board, Disk.BLACK) + Tools.countDisks(board, Disk.WHITE);
    String queue = totalDisks > 4 ? this.targetQueue + serializeSquare(moved) : "";
    // initial data
    String boardString = serializeBoard(board);
    this.boardState.put(queue, boardString);
    // release unused cache
    this.boardState.keySet().stream()
        .filter(q -> !q.startsWith(queue))
        .forEach(q -> this.boardState.remove(q));
    // explore
    explore(queue);
    int maxStep = this.boardState.keySet().stream()
        .map(key -> key.length() / 2)
        .max(Comparator.naturalOrder()).orElse(0);
    List<String> leaves = this.boardState.keySet().stream().filter(
        // max length or pass
        key -> key.length() == 2 * maxStep || key.endsWith(serializeSquare(null))
    ).toList();
    // max score
    int max = leaves.stream()
        .map(key -> countMyDisk(this.boardState.get(key)))
        .max(Comparator.naturalOrder()).orElse(-1);
    return leaves.stream()
        .filter(key -> countMyDisk(this.boardState.get(key)) == max)
        .map(key -> key.substring(queue.length(), 2))
        .distinct()
        .map(MikanPlayer::deserializeSquare)
        .filter(Optional::isPresent)
        .map(Optional::get).toList();
  }

  private int countMyDisk(String queue) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    String symbol = this.myDisk.get().equals(Disk.BLACK) ? "B" : "W";
    String board = this.boardState.get(queue);
    if (board == null) {
      return -1;
    }
    return (int) Arrays.stream(this.boardState.get(board).split(""))
        .filter(s -> s.equals(symbol))
        .count();
  }


  private boolean isLeaf(String queue) {
    int leafStep = Math.min(this.targetQueue.length() / 2 + MAX_STEP, 60);
    String pass = serializeSquare(null);
    return queue.endsWith(pass + pass) || queue.length() / 2 < leafStep;
  }

  private void explore(String queue) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    Disk turn = queue.length() % 4 == 0 ? Disk.WHITE : Disk.BLACK;
    int maxLength = this.boardState.keySet().stream()
        .map(String::length)
        .max(Comparator.naturalOrder()).orElse(0);
    this.boardState.keySet().stream()
        .filter(q -> q.length() == maxLength)
        .forEach(
            q -> {
              Optional<Board> opt = deserializeBoard(this.boardState.get(q));
              if (opt.isPresent()) {
                Board board = opt.get();
                Score movable = Tools.countTurnoverableDisks(board, turn);
                Arrays.stream(Square.values())
                    .filter(sq -> movable.getScore(sq) > 0)
                    .forEach(sq -> {
                      Board work = board.clone();
                      Tools.move(work, sq, turn);
                      int mine = Tools.countDisks(work, myDisk.get());
                      String newQueue = q + serializeSquare(sq);
                      String newBoard = serializeBoard(work);
                      this.boardState.put(newQueue, newBoard);
                      if (!isLeaf(newQueue)) {
                        explore(newQueue);
                      }
                    });
              }
            }
        );
  }
}