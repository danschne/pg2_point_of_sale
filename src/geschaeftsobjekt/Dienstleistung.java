package geschaeftsobjekt;

public class Dienstleistung extends Produkt {

  /* Attribute */
  private String einheit;

  /* Konstruktoren */
  public Dienstleistung(int nr, String bezeichnung, double preis, String einheit) {
    super(nr, bezeichnung, preis);
    this.einheit = einheit;
  }

  /* Methoden */
  public void setEinheit(String einheit) {
    this.einheit = einheit;
  }

  public String getEinheit() {
    return einheit;
  }

}
