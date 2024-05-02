package othello.view;

import jakarta.validation.constraints.NotNull;
import othello.player.Player;

public interface TurnOrderDeterminer {

  void determineTurnOrder(@NotNull Player player1, @NotNull Player player2);
}
