package geschaeftsobjekt;

public abstract class Geschaeftsobjekt {
  /* Attribute */
  private int nr;

  /* Konstruktoren */
  public Geschaeftsobjekt(int nr) {
    this.nr = nr;
  }

  /* Methoden */
  public int getNr() {
    return nr;
  }
}
