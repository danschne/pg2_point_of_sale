import geschaeftsobjekt.*;

public class TestRechnungsposition {
  public static void main(String[] args) {
    Artikel a = new Artikel(526, "Laminatbodenpack Buche Klick-Fix SuperEasy", 13.99);
    System.out.println(new Rechnungsposition(4, a));

    Dienstleistung d1 = new Dienstleistung(123, "Parkettmontage", 75.00, "h");
    System.out.println("\n" + new Rechnungsposition(20, d1));

    Dienstleistung d2 = new Dienstleistung(128, "Montage Sockelleisten", 5.59, "lfdm");
    System.out.println("\n" + new Rechnungsposition(331, d2));
  }
}
