package othello.player;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import othello.base.Board;
import othello.base.Disk;
import othello.base.Square;

/**
 * Player of game. Both computer and human players are acceptable.
 */
public interface Player {

  /**
   * Get name of player.
   *
   * @return The name of player. It may be appeared in the view.
   */
  String getName();

  /**
   * Initialize player.
   *
   * @param myDisk The player's disk. {@code Disk.BLACK} or {@code Disk.WHITE}.
   */
  void init(@NotNull Disk myDisk);

  /**
   * Get player's disk.
   *
   * @return Player's disk.
   */
  Optional<Disk> getMyDisk();

  /**
   * Make a move disk.
   *
   * @param board The board to move disk.
   * @param moved The square which the other player move on previous turn.
   * @return {@code Optional} of square which to be moved a new disk. Anything other than the
   * squares allowed by the rules is a foul. {@code Optional.empty()} will foul.
   */
  Optional<Square> moveDisk(@NotNull Board board, Square moved);

  /**
   * Terminate the player. You can release any resource used by the player.
   */
  void shutdown();

}
