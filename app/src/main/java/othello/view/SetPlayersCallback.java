package othello.view;

import jakarta.validation.constraints.NotNull;
import othello.player.Player;

public interface SetPlayersCallback {

  void determineTurnOrder(@NotNull Player player1, @NotNull Player player2);
}
