package geschaeftsobjekt;

import java.io.Serializable;

public abstract class Geschaeftsobjekt implements Serializable {

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
