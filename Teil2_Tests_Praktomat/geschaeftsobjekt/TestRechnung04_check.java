package geschaeftsobjekt;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import static geschaeftsobjekt.Reflections.*;
import static geschaeftsobjekt.TestRechnungsposition03.removeChar;

public class TestRechnung04{

	/*
	 * Rechnung
	 */
	@Test
	public void testeRechnung(){
		// Vererbungsbeziehung?
		assertTrue("Eine Rechnung IST ein Geschaeftsobjekt! Auf korrekte Vererbung achten!", Rechnung.class.getSuperclass().equals(Geschaeftsobjekt.class));

		assertFalse("Das Setzen des Rechnungsstatus ueber einen Setter ist nicht erlaubt!", hasSetterForType(Rechnung.class, Rechnungsstatus.class));
		assertFalse("Das Setzen eines neuen Kunden ueber einen Setter ist nicht erlaubt!", hasSetterForType(Rechnung.class, Kunde.class));

		int nr = new Rechnung().getNr();

		for(int i=nr+1; i<nr+100; i++){
			assertEquals("Nr einer erstellten Rechnung muss automatisch um 1 hochgezaehlt werden (Tipp: static-Attribut nutzen)!", i, new Rechnung().getNr());
		}

		Rechnung r = new Rechnung();
		assertEquals("Der Status einer Rechnung muss bis zur Verbuchung IN_ERSTELLUNG sein!", Rechnungsstatus.IN_ANLAGE /* müsste IN_ERSTELLUNG sein, gibt kein IN_ANLAGE */, r.getRechnungsstatus());
		r.buchen();
		assertEquals("Der Status einer Rechnung muss nach Verbuchung GEBUCHT sein!", Rechnungsstatus.GEBUCHT, r.getRechnungsstatus());

		Kunde k = new Kunde(1, "Name", "Strasse", "Ort");
		r = new Rechnung(k);
		assertEquals("Kunde wird von Rechnung nicht korrekt ueber Getter zurueckgegeben!", k, r.getKunde());

	}


	/*
	 * Methoden-Tests:
	 *  - public Rechnungsposition addRechnungsposition(int anzahl, Produkt p)
	 *  - public Rechnungsposition getRechnungsposition(Produkt p)
	 *  - public int getAnzahlRechnungspositionen()
	 *  - public double getGesamtpreis()
	 *  - public List<Rechnungsposition> getRechnungspositionen()
	 *  - public void buchen() --> auslagern()!!!
	 */
	@Test
	public void testeAddRechnungsposition(){
		Artikel a = new Artikel(1, "Artikelbez", 1.99);
		Rechnung r = new Rechnung();
		Rechnungsposition pos = null;
		List<Rechnungsposition> liste;

		liste = r.getRechnungspositionen();
		assertNotNull("Bei einer Rechnung noch ohne Rechnungspositionen muss von getRechnungspositionen() eine leere Liste zurueckgegeben werden (nicht null)!", liste);
		assertEquals("Bei einer Rechnung noch ohne Rechnungspostionen muss von getRechnungspositionen() eine LEERE Liste (OHNE Eintraege) zurueckgegeben werden!", 0, liste.size());

		a.einlagern(3);
		pos = r.addRechnungsposition(1, a); // 1.
		assertNotNull("Rechnung gibt keine Objektreferenz auf neue Rechnungsposition zurueck!", pos);

		liste = r.getRechnungspositionen(); 
		assertEquals("Anzahl der Rechnungspositionen in Rechnung nicht korrekt (nach Hinzufuegen der ersten Rechnungsposition)!", 1, liste.size());
		assertEquals("Erste erzeugte Rechnungsposition und Rechnungsposition in Liste stimmen nicht ueberein!", pos, liste.get(0));

		r.addRechnungsposition(1, a);  // 2.
		liste = r.getRechnungspositionen(); 

		assertTrue("Hinzufuegen einer Rechnungsposition fuer einen bereits enthaltenen Artikel darf die Anzahl der Rechnungspositionen nicht veraendern!", liste.size()==1);
		Artikel neu = new Artikel(1, "Andere_Artikelbez_aber_gleiche_Nr", 1.99);
		neu.einlagern(3);
		r.addRechnungsposition(1, neu); // 3.

		liste = r.getRechnungspositionen();
		assertTrue("Hinzufuegen einer Rechnungsposition nutzt NICHT die equals()-Methode der Klasse Produkt", liste.size()==1);

		pos = r.addRechnungsposition(2, new Artikel(3, "Soviel_hab_ich_gar_nicht_auf_Lager", 1.));
		liste = r.getRechnungspositionen();
		assertNull("Eine Rechnung darf keine Rechnungsposition anlegen fuer einen Artikel, der nicht einen vollstaendig ausreichenden Lagerbestand hat (es wurde kein null zurueckgegeben)!", pos);
		assertTrue("Eine Rechnung darf keine Rechnungsposition anlegen fuer einen Artikel, der nicht einen vollstaendig ausreichenden Lagerbestand hat (neue Rechnungsposition wurde Liste hinzugefuegt)!", liste.size()==1);

		a = new Artikel(4, "", 1.);
		a.einlagern(1);
		pos = r.addRechnungsposition(1, a);
		Rechnungsposition alt = pos;
		pos = r.addRechnungsposition(1, a);
		assertNotNull("Ist der Lagerbestand eines Artikels nicht ausreichend UND besteht fuer diesen Artikel schon eine Rechnungsposition DANN muss als Referenz die unveraenderte Rechnungsposition zurueckgegeben werden!", pos);
		assertTrue("Eine Rechnung darf die Anzahl einer Rechnungsposition nicht aendern, falls kein vollstaendig ausreichender Lagerbestand mehr vorhanden ist! Wichtig: Hierzu ist auch die in der Rechnungsposition gespeicherte Anzahl zu beruecksichtigen!", pos.getAnzahl()==1);

		assertTrue("Der Lagerbestand eines Artikels darf durch Hinzufuegen einer Rechnungsposition nicht veraendert werden! Erst nach Verbuchung durch buchen()!", a.getLagerbestand()==1);
		r.buchen();
		a = new Artikel(5, "", 1.);
		a.einlagern(1);
		pos = r.addRechnungsposition(1, a);
		assertNull("Eine Rechnungsposition kann nur im Rechnungsstatus IN_ERSTELLUNG hinzugefuegt werden (nach buchen() einer Rechnung wird bei erneutem Aufruf von addRechnungsposition null zurueckgegeben)", pos);

		assertEquals("getAnzahlRechnungsposition() liefert falschen Wert fuer Rechnung mit zwei Rechnungspositionen!", 2, r.getAnzahlRechnungspositionen());

		assertTrue("Gesamtpreis der Rechnung wird falsch berechnet!", new Double(6.97).equals(r.getGesamtpreis()));

	}

	@Test
	public void testeRechnungspositionListe() throws NoSuchMethodException, SecurityException{

//		Method m = Rechnung.class.getMethod("getRechnungspositionen", null);
//		String genType = "";
//		Type returnType = m.getGenericReturnType();
//		if(returnType instanceof ParameterizedType){
//		    ParameterizedType type = (ParameterizedType) returnType;
//		    genType = type.getActualTypeArguments()[0].toString();
//		}
//
//		assertTrue("Methode getRechnungspositionen liefert keine Liste ueber den Typ Rechnungsposition! Generics beachten!", genType.equals("class geschaeftsobjekt.Rechnungsposition"));

		Rechnung r = new Rechnung();
		Artikel p1 = new Artikel(1, "A", 1.);
		p1.einlagern(10);
		Dienstleistung p2 = new Dienstleistung(2, "B", 1., "E");
		LinkedList<Rechnungsposition> erwartet = new LinkedList<>();
		erwartet.add(r.addRechnungsposition(1, p1));
		erwartet.add(r.addRechnungsposition(1, p2));

		List<Rechnungsposition> liste = r.getRechnungspositionen();
		assertTrue("Liste von getRechnungspositionen weicht von tatsaechlich mit addRechnungsposition hinzugefuegten Rechnungspositionen ab!", liste.containsAll(erwartet));
	}

	@Test
	public void testeBuchen(){
		Artikel a = new Artikel(1, "", 1.);
		a.einlagern(10);
		Rechnung r = new Rechnung();
		r.addRechnungsposition(3, a);
		r.buchen();
		assertTrue("Lagerbestand eines Artikels wird bei Verbuchen einer Rechnung (Methode buchen()) nicht korrekt reduziert!", a.getLagerbestand()==7);
		r.buchen();
		assertTrue("Eine bereits verbuchte Rechnung darf nicht erneut verbucht werden (wird die Methode erneut aufgerufen, darf das den Lagerbestand von Artikeln NICHT MEHR veraendern)!", a.getLagerbestand()==7);
	}

	/*
	 * toString()
	 *  - Kunde
	 *  - Barverkauf
	 */
	@Test
	public void testeToString(){
		Kunde k = new Kunde(4711, "Name", "Str", "Ort");
		Rechnung re = new Rechnung(k);

		Artikel a = new Artikel(1, "Klickfix-Parkett", 43.95);
		a.einlagern(10);
		Dienstleistung d1 = new Dienstleistung(2, "Bodenverlegen", 55.00, "Std");
		Dienstleistung d2 = new Dienstleistung(3, "Sockelleisten montieren", 2.99, "m");

		Rechnungsposition pos1 = re.addRechnungsposition(6, a);
		Rechnungsposition pos2 = re.addRechnungsposition(2, d1);
		Rechnungsposition pos3 = re.addRechnungsposition(10, d2);

		String tostring = re.toString();
		tostring = removeChar(tostring, '\r');
		String pos1tostring = removeChar(pos1.toString(), '\r');
		String pos2tostring = removeChar(pos2.toString(), '\r');
		String pos3tostring = removeChar(pos3.toString(), '\r');
		assertEquals("Fehler bei toString() einer Rechnung (Rechnung mit Kunde)", "Rechnung: " + re.getNr() + "\nKunde: 4711\nName\nStr\nOrt\n\n" + pos1tostring + "\n" + pos2tostring + "\n" + pos3tostring + "\n----------------------------------\n                            403,60", tostring);

		// Barverkauf
		re = new Rechnung();
		pos1 = re.addRechnungsposition(6, a);
		pos2 = re.addRechnungsposition(2, d1);
		pos3 = re.addRechnungsposition(10, d2);

		tostring = re.toString();
		tostring = removeChar(tostring, '\r');
		pos1tostring = removeChar(pos1.toString(), '\r');
		pos2tostring = removeChar(pos2.toString(), '\r');
		pos3tostring = removeChar(pos3.toString(), '\r');

		assertEquals("Fehler bei toString() einer Rechnung (Barverkauf, Rechnung ohne Kunde)", "Rechnung: " + re.getNr() + "\nBarverkauf\n\n" + pos1tostring + "\n" + pos2tostring + "\n" + pos3tostring + "\n----------------------------------\n                            403,60", tostring);

	}
}
