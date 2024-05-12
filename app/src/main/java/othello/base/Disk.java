package othello.base;

/**
 * A "Disk" is a black and white disk that can be placed in any square on the board.The disk can be
 * turned over.
 */
public enum Disk {

  /**
   * Indicates the black side of the disc is up.
   */
  BLACK,

  /**
   * Indicates the white side of the disc is up.
   */
  WHITE;

  /**
   * Turn the disc over from black to white or white to black.
   *
   * @return {@code WHITE} for {@code BLACK} and {@code BLACK} for {@code WHITE}.
   */
  public Disk reverse() {
    return this.equals(Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
  }

}
