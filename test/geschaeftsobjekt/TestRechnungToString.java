package geschaeftsobjekt;

public class TestRechnungToString {
  public static void main(String[] args) {
    Kunde k = new Kunde(42, "Max Muster", "Galgenbergstr. 32", "93053 Regensburg");
    Rechnung re = new Rechnung(k);

    Artikel a = new Artikel(526, "Laminatbodenpack Buche Klick-Fix SuperEasy", 13.99);
    a.einlagern(10);

    Dienstleistung d1 = new Dienstleistung(123, "Parkettmontage", 75.00, "h");
    Dienstleistung d2 = new Dienstleistung(128, "Montage Sockelleisten", 5.59, "lfdm");

    re.addRechnungsposition(4, a);
    re.addRechnungsposition(20, d1);
    re.addRechnungsposition(131, d2);
    System.out.println(re);


    Rechnung re1 = new Rechnung();
    Rechnung re2 = new Rechnung();
    a.auslagern(7);

    re1.addRechnungsposition(4, a);     // Lagerbestand nicht ausreichend, wird ignorier
    re1.addRechnungsposition(20, d1);
    re1.addRechnungsposition(1, d1);
    re2.addRechnungsposition(3, a);

    System.out.println(re1);
    System.out.println("\n" + re2);
  }
}
