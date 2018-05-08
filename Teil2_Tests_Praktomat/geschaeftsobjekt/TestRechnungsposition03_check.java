package geschaeftsobjekt;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;


import org.junit.Test;

import static geschaeftsobjekt.Reflections.*;

public class TestRechnungsposition03{

	/*
	 * Rechnungsposition:
	 */
	@Test
	public void testeRechnungspos(){
		// Vererbungsbeziehung?
		assertFalse("Eine Rechnungsposition ist KEIN Geschaeftsobjekt! Auf korrekte Vererbung achten!", Rechnungsposition.class.getSuperclass().equals(Geschaeftsobjekt.class));

		// Default-Konstruktor?
		Constructor<?>[] ctors = Rechnungsposition.class.getConstructors();
		for(Constructor<?> c : ctors){
			Class<?>[] parameters = c.getParameterTypes();
			assertTrue("Rechnungspositionen koennen nur dann angelegt werden, wenn beide Attribute gegeben sind! Achten Sie auf die Anzahl der Parameter Ihres Konstruktors", parameters.length >= 2);
		}

		if(containsMethodName(Rechnungsposition.class, "setProdukt") || containsMethodName(Rechnungsposition.class, "setProduct"))
			fail("Ein nachtraegliches Aendern des Produkts zu einer Rechnungsposition darf nicht moeglich sein!");


		Artikel a = new Artikel(1, "A", 1.);
		Rechnungsposition p = new Rechnungsposition(3, a);

		p.getProdukt();
		p.getAnzahl();
		p.getPreis();

		assertTrue("Fehlerhafte Implementierung von getProdukt() der Klasse Rechnungsposition, es wird nicht das im Konstruktor gesetzte Objekt zurueckgegeben (nach Erzeugung einer neuen Rechnungsposition)", p.getProdukt()==a);
		assertEquals("Fehlerhafte Implementierung von getAnzahl() der Klasse Rechnungsposition (nach Erzeugung einer neuen Rechnungsposition mit Anzahl 3)", 3, p.getAnzahl());
		assertTrue("Fehlerhafte Implementierung von getPreis() der Klasse Rechnungsposition (nach Erzeugung einer neuen Rechnungsposition mit Anzahl 3 x 1.00 muss Preis == 3.00 sein)", new Double(3.).equals(p.getPreis()));

		p.setAnzahl(5);
		assertEquals("Fehlerhafte Implementierung von setAnzahl() der Klasse Rechnungsposition (nach Setzen auf Anzahl == 5)", 5, p.getAnzahl());
		assertTrue("Fehlerhafte Implementierung von getPreis() der Klasse Rechnungsposition (nach Veraendern der Anzahl)", new Double(5.).equals(p.getPreis()));

	}

	/*
	 * toString()
	 *
	 */
	@Test
	public void testeToString(){
		
		Rechnungsposition r = new Rechnungsposition(1, new Artikel(1, "Artikelbez", 1.99));

		String tostring = r.toString();
		tostring = removeChar(tostring, '\r');
		assertEquals("Fehlerhafte toString()-Implementierung einer Rechnungsposition fuer einen Artikel", "Artikelbez\n   1     x     1,99 =         1,99", tostring);

		r = new Rechnungsposition(1, new Dienstleistung(2, "Dienstleistungsbez", 1.99, "EH"));
		tostring = r.toString();
		tostring = removeChar(tostring, '\r');
		assertEquals("Fehlerhafte toString()-Implementierung einer Rechnungsposition fuer eine Dienstleistung", "Dienstleistungsbez\n   1 EH  x     1,99 =         1,99", tostring);

		r = new Rechnungsposition(1000, new Dienstleistung(2, "Dienstleistungsbezeichnungdiesuperlangeistfastschonzulange", 12345.12, "lfdm"));
		tostring = r.toString();
		tostring = removeChar(tostring, '\r');
		assertEquals("Fehlerhafte toString()-Implementierung einer Rechnungsposition fuer eine Dienstleistung", "Dienstleistungsbezeichnungdiesuper\n1000 lfd x 12345,12 =  12345120,00", tostring);


	}
	
	public static String removeChar(String s, char c){
		StringBuffer buf = new StringBuffer(s);
		while(s.indexOf(c) >= 0){
			buf.deleteCharAt(s.indexOf(c));
			s=buf.toString();
		}
		return s;
	}
}
