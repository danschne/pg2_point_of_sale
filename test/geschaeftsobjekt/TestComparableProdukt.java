package geschaeftsobjekt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestComparableProdukt {
  public static void main(String[] args) {
    List<Produkt> l1 = new ArrayList<>();
    Artikel a1 = new Artikel(1, "Schrauben", 4.95);
    Artikel a2 = new Artikel(1, "Schrauben", 5.99);
    Artikel a3 = new Artikel(1, "Nägel", 5.99);
    Dienstleistung d1 = new Dienstleistung(1, "Friseur", 4.5, "Euro");
    Dienstleistung d2 = new Dienstleistung(1, "Zahnarzt", 4.5, "Euro");

    l1.add(d1);
    l1.add(a2);
    l1.add(a3);
    l1.add(a1);
    l1.add(d2);

    System.out.println(l1);
    Collections.sort(l1);
    System.out.println(l1);
  }
}
