package geschaeftsobjekt;

public abstract class Produkt extends Geschaeftsobjekt implements Comparable<Produkt> {
  /* Attribute */
  private String bezeichnung;
  private Double preis;

  /* Konstruktoren */
  public Produkt(int nr, String bezeichnung, double preis) {
    super(nr);
    this.bezeichnung = bezeichnung;
    this.preis = preis;
  }

  /* Methoden */
  @Override
  public int compareTo(Produkt pOther) {
    boolean thisIstArtikel = this instanceof Artikel;
    boolean pOtherIstArtikel = pOther instanceof Artikel;

    if (thisIstArtikel && !pOtherIstArtikel) {
      return 1;
    } else if (!thisIstArtikel && pOtherIstArtikel) {
      return -1;
    } else {
      if (this.bezeichnung == null) {
        return -1;
      } else if (pOther.getBezeichnung() == null) {
        return 1;
      } else {
        int vergleichBezeichnungen = this.bezeichnung.compareTo(pOther.getBezeichnung());

        if (vergleichBezeichnungen != 0) {
          return vergleichBezeichnungen;
        }
        return this.preis.compareTo(pOther.getPreis());
      }
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Produkt)) {
      return false;
    }

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

  public void setPreis(double preis) {
    this.preis = preis;
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public double getPreis() {
    return preis;
  }
}
