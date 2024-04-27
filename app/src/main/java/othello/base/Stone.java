package othello.base;

public enum Stone {
  BLACK, WHITE;

  public Stone reverse() {
    return this.equals(Stone.BLACK) ? Stone.WHITE : Stone.BLACK;
  }

}
