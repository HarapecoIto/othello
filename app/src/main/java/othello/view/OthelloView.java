package othello.view;

import jakarta.validation.constraints.NotNull;
import java.util.List;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

public interface OthelloView {

  void selectPlayers(@NotNull TurnOrderDeterminer match);

  void startTurn(@NotNull Board board, @NotNull Disk turn);

  void updateBoard(@NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken);

  void endGameByFoul(@NotNull Board board, @NotNull Disk turn);

  void endGame(@NotNull Board board, @NotNull Disk turn, @NotNull List<Square> taken);

}
