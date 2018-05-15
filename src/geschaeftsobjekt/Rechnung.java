package geschaeftsobjekt;

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
  public Rechnungsposition addRechnungsposition(int anzahl, Produkt p) {
    if (rechnungsstatus != Rechnungsstatus.IN_ANLAGE) {
      return null;
    }

    Rechnungsposition rp = getRechnungsposition(p);
    boolean rpBereitsVorhanden = (rp != null ? true : false);
    boolean lagerbestandIstAusreichend = true;
    if (p instanceof Artikel) {
      Artikel a = (Artikel) p;
      int bereitsInRechnung = (rpBereitsVorhanden ? rp.getAnzahl() : 0);
      if (a.getLagerbestand() - bereitsInRechnung - anzahl < 0) {
        lagerbestandIstAusreichend = false;
      }
    }

    if (lagerbestandIstAusreichend) {
      if (rpBereitsVorhanden) {
        rp.setAnzahl(anzahl + rp.getAnzahl());
      } else {
        rp = new Rechnungsposition(anzahl, p);
        rechnungspositionen.add(rp);
      }
      return rp;
    }
    return (rpBereitsVorhanden ? rp : null);
  }

  public void buchen() {
    if (rechnungsstatus != Rechnungsstatus.IN_ANLAGE) {
      return;
    }

    for (Rechnungsposition rp : rechnungspositionen) {
      if (rp.getProdukt() instanceof Artikel) {
        Artikel a = (Artikel) rp.getProdukt();
        a.auslagern(rp.getAnzahl());
      }
    }

    rechnungsstatus = Rechnungsstatus.GEBUCHT;
  }

  @Override
  public String toString() {
    String ausgabe = getClass().getSimpleName() + ": " + getNr() + "\n";
    ausgabe += (kunde != null ? kunde : "Barverkauf") + "\n";

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
