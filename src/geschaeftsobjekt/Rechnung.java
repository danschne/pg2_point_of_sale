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
    this.rechnungsstatus = Rechnungsstatus.IN_ERSTELLUNG;
  }

  public Rechnung(Kunde kunde) {
    this();
    this.kunde = kunde;
  }

  /* Methoden */
  public Rechnungsposition addRechnungsposition(int anzahl, Produkt p) {
    boolean rpBereitsVorhanden = false;
    Rechnungsposition rp = getRechnungsposition(p);
    if (rp != null) {
      rpBereitsVorhanden = true;
    }

    if (!rpBereitsVorhanden && rechnungsstatus != Rechnungsstatus.IN_ERSTELLUNG) {
      return null;
    }

    boolean lagerbestandIstAusreichend = true;
    if (p instanceof Artikel) {
      Artikel a = (Artikel) p;
      if (a.getLagerbestand() - anzahl < 0) {
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
    return null;
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

  public Kunde getKunde() {
    return kunde;
  }

  public Rechnungsstatus getRechnungsstatus() {
    return rechnungsstatus;
  }
}
