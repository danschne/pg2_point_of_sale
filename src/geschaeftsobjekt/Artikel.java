package geschaeftsobjekt;

public class Artikel {
  /* Attribute */
  private int nr;
  private String bezeichnung;
  private String kurzbezeichnung;
  private int lagerbestand;

  /* Konstruktoren */
  public Artikel(int nr, String bezeichnung) {
    this.nr = nr;
    this.bezeichnung = bezeichnung;
    kurzbezeichnung = erzeugeKurzbezeichnung(this.nr, this.bezeichnung);
  }

  public Artikel(int nr, String bezeichnung, int lagerbestand) {
    this(nr, bezeichnung);
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

  public void auslagern(int lagerbestand) {
    if (this.lagerbestand - lagerbestand >= 0) {
      this.lagerbestand -= lagerbestand;
    }
  }

  public String toString() {
    return (
        "" + nr + ", " + kurzbezeichnung + ", " + bezeichnung + ", " + lagerbestand + " auf Lager"
    );
  }

  public int getNr() {
    return nr;
  }

  public void setBezeichnung(String bezeichnung) {
    this.bezeichnung = bezeichnung;
    kurzbezeichnung = erzeugeKurzbezeichnung(this.nr, this.bezeichnung);
  }

  public String getBezeichnung() {
    return bezeichnung;
  }

  public String getKurzbezeichnung() {
    return kurzbezeichnung;
  }

  public int getLagerbestand() {
    return lagerbestand;
  }
}
