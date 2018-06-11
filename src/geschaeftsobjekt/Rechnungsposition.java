package geschaeftsobjekt;

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

    String einheit = "";
    boolean produktIstDienstleistung = produkt instanceof Dienstleistung;
    if (produktIstDienstleistung) {
      Dienstleistung d = (Dienstleistung) produkt;
      einheit = d.getEinheit();
    }
    String zeile2 = String.format("%4d %-3.3s x %8.2f = %12.2f", anzahl, einheit, produkt.getPreis(),
        getPreis());

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
