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
  private String prefix;

  /**
   * key: moving queue. value: board status.
   */
  private Map<String, String> data;

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
    this.prefix = "";
    this.data = new HashMap<>();
    this.data.put("", serializeBoard(new Board()));
  }

  @Override
  public Optional<Square> move(Board board, Square moved) {
    Optional<Square> opt = super.move(board, moved);
    opt.ifPresent(square -> this.prefix += serializeSquare(square));
    return opt;
  }

  @Override
  List<Square> calculateCandidates(@NotNull Board board, Square moved) {
    // assert
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    // previous queue
    int totalDisks = Tools.countDisks(board, Disk.BLACK) + Tools.countDisks(board, Disk.WHITE);
    if (totalDisks == 4) {
      this.prefix = "";
      this.firstExplore();
    } else if (totalDisks == 5) {
      this.prefix = serializeSquare(moved);
      this.secondExplore();
    } else {
      this.prefix += serializeSquare(moved);
      explore();
    }
    // max score
    int max = this.data.values().stream()
        .map(this::countMyDisk)
        .max(Comparator.naturalOrder()).orElse(-1);
    return this.data.keySet().stream()
        .filter(key -> countMyDisk(this.data.get(key)) == max)
        .map(key -> key.substring(this.prefix.length(), this.prefix.length() + 2))
        .distinct()
        .map(MikanPlayer::deserializeSquare)
        .filter(Optional::isPresent)
        .map(Optional::get).toList();
  }

  private int countMyDisk(String boardString) {
    if (this.myDisk.isEmpty()) {
      throw new OthelloException();
    }
    String symbol = this.myDisk.get().equals(Disk.BLACK) ? "B" : "W";
    return (int) Arrays.stream(boardString.split(""))
        .filter(s -> s.equals(symbol))
        .count();
  }

  private void firstExplore() {
    Map<String, String> work = new HashMap<>();
    work.put("", serializeBoard(new Board()));
    for (int step = 0; step < MAX_STEP; step++) {
      Disk turn = step % 2 == 0 ? Disk.WHITE : Disk.BLACK;
      Map<String, String> temp = new HashMap<>();
      work.keySet().forEach(
          key -> {
            Board board = deserializeBoard(work.get(key)).orElse(new Board());
            Score score = Tools.countTurnoverableDisks(board, turn);
            Arrays.stream(Square.values())
                .filter(sq -> score.getScore(sq) > 0)
                .forEach(sq -> {
                  Board clone = board.clone();
                  Tools.move(clone, sq, turn);
                  String boardString = serializeBoard(clone);
                  String queue = key + serializeSquare(sq);
                  temp.put(queue, boardString);
                });
          }
      );
      work.clear();
      temp.keySet().forEach(key -> work.put(key, temp.get(key)));
    }
    this.data.clear();
    work.keySet().forEach(key -> this.data.put(key, work.get(key)));
  }

  private void secondExplore() {
    Map<String, String> work = new HashMap<>();
    Board board0 = new Board();
    Optional<Square> opt = deserializeSquare(this.prefix);
    if (opt.isEmpty()) {
      return;
    }
    Tools.move(board0, opt.get(), Disk.WHITE);
    work.put(this.prefix, serializeBoard(board0));
    for (int step = 1; step < MAX_STEP + 1; step++) {
      Disk turn = step % 2 == 0 ? Disk.WHITE : Disk.BLACK;
      Map<String, String> temp = new HashMap<>();
      work.keySet().forEach(
          key -> {
            Board board = deserializeBoard(work.get(key)).orElse(new Board());
            Score score = Tools.countTurnoverableDisks(board, turn);
            Arrays.stream(Square.values())
                .filter(sq -> score.getScore(sq) > 0)
                .forEach(sq -> {
                  Board clone = board.clone();
                  Tools.move(clone, sq, turn);
                  String boardString = serializeBoard(clone);
                  String queue = key + serializeSquare(sq);
                  temp.put(queue, boardString);
                });
          }
      );
      work.clear();
      temp.keySet().forEach(key -> work.put(key, temp.get(key)));
    }
    this.data.clear();
    work.keySet().forEach(key -> this.data.put(key, work.get(key)));
  }

  private void explore() {
    Map<String, String> work = new HashMap<>();
    this.data.keySet().stream()
        .filter(key -> key.startsWith(this.prefix))
        .forEach(key -> work.put(key, this.data.get(key)));
    int firstStep = work.keySet().stream()
        .map(key -> key.length() / 2)
        .distinct()
        .toList().get(0);
    int lastStep = Math.min(this.prefix.length() / 2 + MAX_STEP, 60);
    for (int step = firstStep; step < lastStep; step++) {
      Disk turn = step % 2 == 0 ? Disk.WHITE : Disk.BLACK;
      Map<String, String> temp = new HashMap<>();
      work.keySet().forEach(
          key -> {
            Board board = deserializeBoard(work.get(key)).orElse(new Board());
            Score score = Tools.countTurnoverableDisks(board, turn);
            Arrays.stream(Square.values())
                .filter(sq -> score.getScore(sq) > 0)
                .forEach(sq -> {
                  Board clone = board.clone();
                  Tools.move(clone, sq, turn);
                  String boardString = serializeBoard(clone);
                  String queue = key + serializeSquare(sq);
                  temp.put(queue, boardString);
                });
          }
      );
      work.clear();
      temp.keySet().forEach(key -> work.put(key, temp.get(key)));
    }
    this.data.clear();
    work.keySet().forEach(key -> this.data.put(key, work.get(key)));
  }

}