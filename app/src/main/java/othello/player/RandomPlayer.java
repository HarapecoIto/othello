package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.Tools;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public class RandomPlayer implements Player {

  private static final String PLAYER_NAME = "Random Player";

  private Stone myStone;
  private Random rand;

  public RandomPlayer(long seed) {
    this.rand = new Random(seed);
  }

  @Override
  public String getPlayerName() {
    return PLAYER_NAME;
  }

  @Override
  public void init(@NotNull Stone myStone) {
    this.myStone = myStone;
  }

  @Override
  public Optional<Square> moveStone(@NotNull Board board) {
    List<Square> list = new ArrayList<>();
    for (Square sq : Square.values()) {
      if (Tools.countToTake(board, sq, this.myStone) > 0) {
        list.add(sq);
      }
    }
    return !list.isEmpty() ? Optional.of(list.get(this.rand.nextInt(list.size())))
        : Optional.empty();
  }
}
