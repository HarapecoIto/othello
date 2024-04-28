package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import othello.base.Board;
import othello.base.Square;
import othello.base.Stone;

public interface Player {

  String getPlayerName();

  Optional<Square> moveStone(@NotNull Board board, @NotNull Stone player);

}
