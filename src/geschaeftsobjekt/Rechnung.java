package geschaeftsobjekt;

import exception.BookingException;
import exception.OutOfStockException;
import java.util.ArrayList;
import java.util.List;

public class Rechnung extends Geschaeftsobjekt {

  /* Attribute */
  private static int zaehlerRechnungen = 1;
  private List<Rechnungsposition> rechnungspositionen = new ArrayList<>();
  private Kunde kunde;
  private Rechnungsstatus rechnungsstatus;

  /* Konstruktoren */
  public Rechnung() {
    super(zaehlerRechnungen);
    zaehlerRechnungen++;
    this.rechnungsstatus = Rechnungsstatus.IN_ANLAGE;
  }

  public Rechnung(Kunde kunde) {
    this();
    this.kunde = kunde;
  }

  /* Methoden */
  public Rechnungsposition addRechnungsposition(int anzahl, Produkt p)
      throws OutOfStockException, BookingException {
    if (rechnungsstatus != Rechnungsstatus.IN_ANLAGE) {
      throw new BookingException("Rechungsstatus nicht " + Rechnungsstatus.IN_ANLAGE);
    }

    Rechnungsposition rp = getRechnungsposition(p);
    boolean rpBereitsVorhanden = (rp != null);
    if (p instanceof Artikel) {
      Artikel a = (Artikel) p;
      int bereitsInRechnung = (rpBereitsVorhanden ? rp.getAnzahl() : 0);
      if (a.getLagerbestand() - bereitsInRechnung - anzahl < 0) {
        throw new OutOfStockException(a, "Lagerbestand nicht ausreichend");
      }
    }

    if (rpBereitsVorhanden) {
      rp.setAnzahl(anzahl + rp.getAnzahl());
    } else {
      rp = new Rechnungsposition(anzahl, p);
      rechnungspositionen.add(rp);
    }
    return rp;
  }

  public void buchen() throws OutOfStockException, BookingException {
    if (rechnungsstatus != Rechnungsstatus.IN_ANLAGE) {
      throw new BookingException("Rechungsstatus nicht " + Rechnungsstatus.IN_ANLAGE);
    }

    for (Rechnungsposition rp : rechnungspositionen) {
      if (rp.getProdukt() instanceof Artikel) {
        Artikel a = (Artikel) rp.getProdukt();
        try {
          a.auslagern(rp.getAnzahl());
        } catch (OutOfStockException e) {
          throw e;
        }
      }
    }

    rechnungsstatus = Rechnungsstatus.GEBUCHT;
  }

  @Override
  public String toString() {
    /* return ; */
    String ausgabe = getClass().getSimpleName() + ": " + getNr() + "\n";
    ausgabe += (kunde != null ?
               (kunde.getClass().getSimpleName() + ": " + kunde.getNr() + "\n" + kunde.getName() + "\n" +
                   kunde.getStrasse() + "\n" + kunde.getOrt()) :
               "Barverkauf") +
               "\n";

    for (Rechnungsposition rp : rechnungspositionen) {
      ausgabe += "\n" + rp;
    }

    ausgabe += "\n----------------------------------";
    ausgabe += String.format("\n%34.2f", getGesamtpreis());

    return ausgabe;
  }

  public double getGesamtpreis() {
    double gesamtpreis = 0;

    for (Rechnungsposition rp : rechnungspositionen) {
      gesamtpreis += rp.getPreis();
    }
    return gesamtpreis;
  }

  public Rechnungsposition getRechnungsposition(Produkt p) {
    for (Rechnungsposition rp : rechnungspositionen) {
      if (rp.getProdukt().equals(p)) {
        return rp;
      }
    }
    return null;
  }

  public List<Rechnungsposition> getRechnungspositionen() {
    return rechnungspositionen;
  }

  public int getAnzahlRechnungspositionen() {
    return rechnungspositionen.size();
  }

  public Kunde getKunde() {
    return kunde;
  }

  public Rechnungsstatus getRechnungsstatus() {
    return rechnungsstatus;
  }

}
