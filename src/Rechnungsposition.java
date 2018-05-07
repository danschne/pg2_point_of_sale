import geschaeftsobjekt.Produkt;

public class Rechnungsposition {
  /* Attribute */
  private int anzahl;
  private Produkt produkt;

  /* Konstruktoren */
  public Rechnungsposition(int anzahl, Produkt produkt) {
    this.anzahl = anzahl;
    this.produkt = produkt;
  }

  /* Methoden */
  @Override
  public String toString() {
    int zeilenlaenge = 34;
    int laengeBezeichnung = produkt.getBezeichnung().length();
    String zeile1 = produkt.getBezeichnung().substring(0,
        (laengeBezeichnung > zeilenlaenge ? zeilenlaenge : laengeBezeichnung));

    String zeile2 = String.format("%4d ", anzahl);

    return zeile1 + "\n" + zeile2;
  }

  public void setAnzahl(int anzahl) {
    this.anzahl = anzahl;
  }

  public double getPreis() {
    return anzahl * produkt.getPreis();
  }

  public int getAnzahl() {
    return anzahl;
  }

  public Produkt getProdukt() {
    return produkt;
  }
}
