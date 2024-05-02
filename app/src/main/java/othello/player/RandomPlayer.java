package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import othello.Tools;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public class RandomPlayer implements Player {

  private final String name;
  private Optional<Disk> myDisk;
  private final Random rand;

  public RandomPlayer(@NotNull String name, long seed) {
    this.myDisk = Optional.empty();
    this.rand = new Random(seed);
    this.name = name;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = Optional.of(myDisk);
  }


  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Optional<Square> moveDisk(@NotNull Board board) {
    // assert
    if (this.myDisk.isEmpty()) {
      // not initialized
      return Optional.empty();
    }
    List<Square> list = Arrays.stream(Square.values())
        .filter(sq -> Tools.countReversibleDisks(board.clone(), sq, this.myDisk.get()) > 0)
        .toList();
    return !list.isEmpty()
        ? Optional.of(list.get(this.rand.nextInt(list.size())))
        : Optional.empty();
  }
}
