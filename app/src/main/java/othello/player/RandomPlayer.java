package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class RandomPlayer implements Player {

  private static final String PLAYER_NAME = "Random Player";

  private Disk myDisk;
  private Random rand;

  public RandomPlayer(long seed) {
    this.rand = new Random(seed);
  }

  @Override
  public String getPlayerName() {
    return PLAYER_NAME;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = myDisk;
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board) {
    List<Square> list = new ArrayList<>();
    for (Square sq : Square.values()) {
      if (Tools.countToTake(board, sq, this.myDisk) > 0) {
        list.add(sq);
      }
    }
    return !list.isEmpty() ? Optional.of(list.get(this.rand.nextInt(list.size())))
        : Optional.empty();
  }
}
