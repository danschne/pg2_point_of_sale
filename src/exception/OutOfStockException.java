package exception;

import geschaeftsobjekt.Produkt;

public class OutOfStockException extends Exception {

  /* Attribute */
  private Produkt produkt;

  /* Konstruktoren */
  public OutOfStockException(String fehlermeldung, Produkt produkt) {
    super(fehlermeldung);
    this.produkt = produkt;
  }

  /* Methoden */
  public Produkt getProdukt() {
    return produkt;
  }

}
