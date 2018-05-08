package geschaeftsobjekt;

import static org.junit.Assert.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.junit.Test;


public class TestArtikelWaWi01 {

	private String[] nichtErlaubteMethoden = {"setNr", "setKurzbezeichnung", "setLagerbestand"};
	private String[] geforderteMethoden = {"getNr", "getBezeichnung", "getKurzbezeichnung", "getLagerbestand", "setBezeichnung", "einlagern", "auslagern"};


	@Test
	public void testeReadOnlyAttribute(){
		Class artikel = Artikel.class;
		Method[] methods = artikel.getMethods();

		// Setter trotz read-only?
		for(Method m : methods){
			for(String pfui : nichtErlaubteMethoden)
				if(m.getName().equals(pfui))
					fail("Setter " + pfui + " nicht erlaubt, Attribut ist read-only (Aufgabenstellung exakt beachten!)");
		}

	}

	@Test
	public void testVollstaendigkeitDerMethoden(){
		Class artikel = Artikel.class;
		Method[] methods = artikel.getMethods();

		for(String name : geforderteMethoden){
			boolean vorhanden = false;
			for(Method m : methods){
				if(m.getName().equals(name)){
					vorhanden = true;
					break;
				}
			}
			if(vorhanden==false)
				fail("Methode " + name + " fehlt");
		}
	}

	@Test
	public void testePrivateAttribute(){
		// Default-Regel: Attribute haben so restriktive Sichtbarkeit wie moeglich --> hier: alle private
		for(Field attribut : Artikel.class.getDeclaredFields()){
			if(!Modifier.isPrivate(attribut.getModifiers()))
				fail("Attribut " + attribut.getName() + " ist nicht private (so restriktiv wie moeglich --> hier private!)");
		}
	}

	@Test
	public void testKonstruktor(){
		Constructor[] constuctors = Artikel.class.getConstructors();
		for(Constructor c : constuctors){
			Class[] types = c.getParameterTypes();
			if(types.length == 0)
				fail("Default-Konstruktor ohne Parameter fuer Artikel nicht erlaubt, siehe Aufgabenstellung");
			if(types.length == 1)
				fail("Konstruktor muss mind. Nr und Bezeichnung als Parameter erhalten, weniger nicht erlaubt, siehe Aufgabenstellung");
		}
	}

	@Test
	public void testeKurzbezeichnung() {
		HashMap<Artikel, String> testStrings = new HashMap<>();
		testStrings.put(new Artikel(123, "Torx-Schrauben 6x35", 1.), "TRXSCHRB01238");
		testStrings.put(new Artikel(8, "Spax 8x55", 1.), "SPX8X550008X");
		testStrings.put(new Artikel(62873408, "Schlosschraube", 1.), "SCHLSSCH34087");
		testStrings.put(new Artikel(1, "A", 1.), "00011");
		testStrings.put(new Artikel(1, "a", 1.), "00011");
		testStrings.put(new Artikel(12, "a", 1.), "00123");
		testStrings.put(new Artikel(123, "a", 1.), "01236");
		testStrings.put(new Artikel(1234, "a", 1.), "1234X");
		testStrings.put(new Artikel(12345, "a", 1.), "23453");
		testStrings.put(new Artikel(123456, "a", 1.), "34567");
		testStrings.put(new Artikel(1, "", 1.), "00011");
		testStrings.put(new Artikel(1, "X", 1.), "X00013");
		testStrings.put(new Artikel(1, "x", 1.), "X00013");
		testStrings.put(new Artikel(1, "XAX", 1.), "XX00015");
		testStrings.put(new Artikel(1, "xax", 1.), "XX00015");
		testStrings.put(new Artikel(1, "AXA", 1.), "X00013");
		testStrings.put(new Artikel(1, "axa", 1.), "X00013");
		testStrings.put(new Artikel(1, "AAAAAAAAAABCEFGHJKLMNPQRSTVWXYZ", 1.), "BCFGHJKL00015");
		testStrings.put(new Artikel(1, "aaaaaaaaaabcefghjklmnpqrstvwxyz", 1.), "BCFGHJKL00015");
		testStrings.put(new Artikel(1, "!\"$%.,+-&/()=?BCEFGHJK", 1.), "BCFGHJK00014");
		testStrings.put(new Artikel(2109, "0987654321", 1.), "098765432109X");

		// Methode erzeugeKurzbezeichnung testen
		for(Artikel a : testStrings.keySet()){
			assertEquals("Falsche Kurzbezeichnung zu geg. (Lang-)Bezeichnung bei direktem Test von erzeugeKurzbezeichnung", testStrings.get(a), Artikel.erzeugeKurzbezeichnung(a.getNr(), a.getBezeichnung()));
		}

		// Artikel-Objekte nach Erzeugung testen
		for(Artikel a : testStrings.keySet()){
			assertEquals("Falsche Kurzbezeichnung zu geg. (Lang-)Bezeichnung bei Test von Artikel-Objekt", testStrings.get(a), a.getKurzbezeichnung());
		}

		// setBezeichnung --> auch Kurzbezeichnung geaendert?
		Artikel last = null;
		for(Artikel a : testStrings.keySet()){
			if(last == null){
				last = a;
				continue;
			}
			last.setBezeichnung(a.getBezeichnung());
			assertEquals("setBezeichnung(...) muss auch Kurzbezeichnung veraendern", Artikel.erzeugeKurzbezeichnung(last.getNr(), a.getBezeichnung()), last.getKurzbezeichnung());
			last=a;
		}

	}

	@Test
	public void testeEinlagernAuslagern(){
		Artikel test = new Artikel(1, "Blub", 1.);
		test.einlagern(10);
		assertEquals("Lagerbestand wird beim Einlagern nicht korrekt verbucht (0 vorher --> einlagern(10) --> muss 10 sein", 10, test.getLagerbestand());
		test.einlagern(5);
		assertEquals("Lagerbestand wird beim Einlagern nicht korrekt verbucht (10 vorher --> einlagern(5) --> muss 15 sein", 15, test.getLagerbestand());
		test.auslagern(12);
		assertEquals("Lagerbestand wird beim Auslagern nicht korrekt verbucht (15 vorher --> auslagern(12) --> muss 3 sein", 3, test.getLagerbestand());
		test.auslagern(4);
		assertEquals("Bei nicht ausreichendem Lagerbestand findet beim Auslagern keine Veraenderung statt (3 vorher --> auslagern(4) --> muss 3 bleiben", 3, test.getLagerbestand());
	}
	//Buzb
	@Test
	public void testToString(){
		HashMap<Artikel, String> testStrings = new HashMap<>();
		testStrings.put(new Artikel(123, "Torx-Schrauben 6x35", 1.), "TRXSCHRB01238");
		testStrings.put(new Artikel(8, "Spax 8x55", 1.), "SPX8X550008X");
		testStrings.put(new Artikel(62873408, "Schlosschraube", 1.), "SCHLSSCH34087");

		for(Artikel a : testStrings.keySet()){
			//a.einlagern(((int)Math.random()*1000) + 1);
			a.einlagern(10);
			String korrekt = "" + a.getNr() + ", " + testStrings.get(a) + ", " + a.getBezeichnung()+ ", " + a.getLagerbestand() + " auf Lager";
			assertEquals("toString() erzeugt falschen String, Aufgabenstellung (Beispiel) beachten!", korrekt, a.toString());
		}
	}

}
