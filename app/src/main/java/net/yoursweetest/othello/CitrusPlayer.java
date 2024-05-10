package net.yoursweetest.othello;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import java.util.Random;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;

public abstract class CitrusPlayer implements Player {

  protected final String name;
  protected final long seed;
  protected final Random rand;
  protected Optional<Disk> myDisk;

  protected CitrusPlayer(@NotNull String name, long seed) {
    this.name = name;
    this.seed = seed;
    this.rand = new Random(this.seed);
    this.myDisk = Optional.of(Disk.BLACK);
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void init(@NotNull Disk myDisk) {
    this.myDisk = Optional.of(myDisk);
  }

  @Override
  public Optional<Disk> getMyDisk() {
    return this.myDisk;
  }

  @Override
  public abstract Optional<Square> moveDisk(@NotNull Board board, Square moved);

  @Override
  public void shutdown() {
  }
}
