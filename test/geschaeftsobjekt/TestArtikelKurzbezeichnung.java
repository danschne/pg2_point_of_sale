package geschaeftsobjekt;

public class TestArtikelKurzbezeichnung {

  public static void main(String[] args) {
    System.out.println(Artikel.erzeugeKurzbezeichnung(123, "Torx-Schrauben 6x35"));
    System.out.println(Artikel.erzeugeKurzbezeichnung(8, "Spax 8x55"));
    System.out.println(Artikel.erzeugeKurzbezeichnung(62873408, "Schloßschraube"));
  }

}
