package geschaeftsobjekt;

public class TestEquals {

  public static void main(String[] args) {
    Artikel a1 = new Artikel(1, "Schrauben", 4.5);
    Artikel a2 = new Artikel(1, "Schrauben", 4.5);
    Artikel a3 = new Artikel(2, "Schrauben", 4.5);
    Dienstleistung d1 = new Dienstleistung(1, "Software Engineering", 4.5, "Euro");

    System.out.println(a1.equals(a2));
    System.out.println(a2.equals(a3));
    System.out.println(d1.equals(a1));
  }

}