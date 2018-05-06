package geschaeftsobjekt;

public abstract class Produkt extends Geschaeftsobjekt implements Comparable<Produkt> {
  /* Attribute */
  private String bezeichnung;
  private float preis;

  /* Konstruktoren */
  public Produkt(int nr, String bezeichnung, float preis) {
    super(nr);
    this.bezeichnung = bezeichnung;
    this.preis = preis;
  }

  /* Methoden */
  @Override
  public int compareTo(Produkt pOther) {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    // Verhalten, wenn kein Produkt übergeben wird? Instanzprüfung?
    Produkt pOther = (Produkt) obj;

    if (this.getClass().getSimpleName().equals(pOther.getClass().getSimpleName()) &&
        this.getNr() == pOther.getNr()) {
      return true;
    }
    return false;
  }

  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
  }

  public void setPreis(float preis) {
    this.preis = preis;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public float getPreis() {
    return preis;
  }
}
