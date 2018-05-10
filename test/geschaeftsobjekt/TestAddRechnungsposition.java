package geschaeftsobjekt;

public class TestAddRechnungsposition {
  public static void main(String[] args) {
    Rechnung r = new Rechnung();
    System.out.println(r.getRechnungspositionen());

    r.addRechnungsposition(2,
        new Artikel(1, "Schrauben", 3.99, 5));
    System.out.println(r.getRechnungspositionen());

    r.addRechnungsposition(2,
        new Dienstleistung(2, "Fliesen legen", 3.99, "h"));
    System.out.println(r.getRechnungspositionen());

    r.addRechnungsposition(6,
        new Artikel(2, "Dübel", 3.99, 5));
    System.out.println(r.getRechnungspositionen());
  }
}
