package geschaeftsobjekt;

import exception.OutOfStockException;

public class Artikel extends Produkt {

  /* Attribute */
  private String kurzbezeichnung;
  private int lagerbestand;

  /* Konstruktoren */
  public Artikel(int nr, String bezeichnung, double preis) {
    super(nr, bezeichnung, preis);
    kurzbezeichnung = erzeugeKurzbezeichnung(getNr(), getBezeichnung());
  }

  public Artikel(int nr, String bezeichnung, double preis, int lagerbestand) {
    this(nr, bezeichnung, preis);
    this.lagerbestand = lagerbestand;
  }

  /* Methoden */
  public static String erzeugeKurzbezeichnung(int nr, String bezeichnung) {
    String konsonanten = erzeugeKonsonanten(bezeichnung);
    String zahlen = String.format("%04d", nr % 10000);
    String pruefziffer = erzeugePruefziffer(konsonanten + zahlen);

    return konsonanten + zahlen + pruefziffer;
  }

  private static String erzeugeKonsonanten(String bezeichnung) {
    String konsonanten = "BCDFGHJKLMNPQRSTVWXYZ";
    String ziffern = "0123456789";
    String ergebnis = "";
    int zaehler = 0;
    char[] modifizierteBezeichnung = bezeichnung.replaceAll("ß", "s")
                                                .toUpperCase()
                                                .toCharArray();

    for (char c : modifizierteBezeichnung) {
      String s = Character.toString(c);
      if (konsonanten.contains(s) || ziffern.contains(s)) {
        ergebnis += s;
        zaehler++;
      }
      if (zaehler == 8) {
        break;
      }
    }
    return ergebnis;
  }

  private static String erzeugePruefziffer(String zwischenbezeichnung) {
    char[] zwischenbezeichnungCharArray = zwischenbezeichnung.toCharArray();
    int summeChars = 0;

    for (char c : zwischenbezeichnungCharArray) {
      if (Character.isUpperCase(c)) {
        summeChars += c - '@';
      } else {
        summeChars += c - '0';
      }
    }
    int pruefziffer = summeChars % 11;
    if (pruefziffer == 10) {
      return "X";
    }
    return "" + pruefziffer;
  }

  public void einlagern(int lagerbestand) {
    this.lagerbestand += lagerbestand;
  }

  public void auslagern(int lagerbestand) throws OutOfStockException {
    if (this.lagerbestand - lagerbestand >= 0) {
      this.lagerbestand -= lagerbestand;
    } else {
      throw new OutOfStockException("Lagerbestand nicht ausreichend", this);
    }
  }

  public String toString() {
    return (
       "" + getNr() + ", " + kurzbezeichnung + ", " + getBezeichnung() + ", " + lagerbestand + " auf Lager"
    );
  }

  public void setBezeichnung(String bezeichnung) {
    super.setBezeichnung(bezeichnung);
    kurzbezeichnung = erzeugeKurzbezeichnung(getNr(), getBezeichnung());
  }

  public String getKurzbezeichnung() {
    return kurzbezeichnung;
  }

  public int getLagerbestand() {
    return lagerbestand;
  }

}
