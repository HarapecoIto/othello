package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public interface Player {

  String getName();

  void init(@NotNull Disk myDisk);

  Optional<Square> moveDisk(@NotNull Board board);

}
