package othello.view;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;
import othello.player.Player;

public interface OthelloView {

  Optional<Player> selectBlackPlayer();

  Optional<Player> selectWhitePlayer();

  void startTurn(@NotNull Board board, @NotNull Disk turn);

  void updateBoard(@NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken);

  void endGameByFoul(@NotNull Board board, @NotNull Disk turn);

  void endGame(@NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken);

}
