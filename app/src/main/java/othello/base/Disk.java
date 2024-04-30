package othello.base;

public enum Disk {
  BLACK, WHITE;

  public Disk reverse() {
    return this.equals(Disk.BLACK) ? Disk.WHITE : Disk.BLACK;
  }

}
