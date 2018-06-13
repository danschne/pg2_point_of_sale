package exception;

import geschaeftsobjekt.Produkt;

public class OutOfStockException extends Exception {

  /* Attribute */
  private Produkt produkt;

  /* Konstruktoren */
  public OutOfStockException(Produkt produkt, String fehlermeldung) {
    super(fehlermeldung);
    this.produkt = produkt;
  }

  /* Methoden */
  public Produkt getProdukt() {
    return produkt;
  }

}
