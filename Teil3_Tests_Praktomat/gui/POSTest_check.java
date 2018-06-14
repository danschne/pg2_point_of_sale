package gui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import geschaeftsobjekt.Artikel;
import geschaeftsobjekt.Dienstleistung;
import geschaeftsobjekt.Kunde;
import geschaeftsobjekt.Produkt;
import gui.POS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class POSTest {
	static private POS pos;
	static private List<Kunde> kunden;
	static private List<Produkt> produkte;
	
	public static String removeChar(String s, char c) {
		StringBuffer buf = new StringBuffer(s);

		while (s.indexOf(c) >= 0){
			buf.deleteCharAt(s.indexOf(c));
			s = buf.toString();
		}

		return s;
	}

	@Test
	public void test01Vererbung() {
		Class<POS> posClass = POS.class;
		String name1 = JFrame.class.getName();
		String name2 = posClass.getSuperclass().getName();
		assertEquals("POS erbt nicht von JFrame!",
				name1, name2);
	}
	
	private static List<Produkt> initProducts() {
		produkte = new LinkedList<>();
		
		Artikel p1 = new Artikel(12345, "Arbeitsplatte", 89.90);
		p1.einlagern(1);
		Dienstleistung p2 = new Dienstleistung(100, "Kuechenmontage", 75., "h");
		Artikel p3 = new Artikel(98989876, "Akku-Handsauger", 129.90);
		p3.einlagern(3);
		Artikel p4 = new Artikel(5261, "Spax 6x100", 3.99);
		p4.einlagern(4);
		Artikel p5 = new Artikel(4593, "Coca Cola 12x1l", 12.69);
		p5.einlagern(5);
		Artikel p6 = new Artikel(4594, "Capri-Sonne", 8.99);
		p6.einlagern(6);
		Artikel p7 = new Artikel(4595, "Jever Partyfass 5l", 8.99);
		p7.einlagern(7);
		Artikel p8 = new Artikel(12346, "Arbeitsplatte", 99.90);
		p8.einlagern(1);
		Artikel p9 = new Artikel(526, "Laminatbodenpack", 13.99);
		p9.einlagern(5);
		Dienstleistung p10 = new Dienstleistung(123, "Parkettmontage", 75.00, "qm");
		Dienstleistung p11 = new Dienstleistung(128, "Montage Sockelleisten", 5.59, "lfdm");

		produkte.add(p1);
		produkte.add(p2);
		produkte.add(p3);
		produkte.add(p4);
		produkte.add(p5);
		produkte.add(p6);
		produkte.add(p7);
		produkte.add(p8);
		produkte.add(p9);
		produkte.add(p10);
		produkte.add(p11);
		
		return produkte;
	}

	@BeforeClass
	static public void erzeugeDatenUndDialog() {
		List<Produkt> produkte = initProducts();
	
		kunden = new ArrayList<>();
		kunden.add(new Kunde(-1, "<bitte auswaehlen>", "", ""));
		kunden.add(new Kunde(0, "Barverkauf", "", ""));
		kunden.add(new Kunde(1, "Madonna", "Sunset Boulevard 1", "Hollywood"));
		kunden.add(new Kunde(2, "Heidi Klum", "Modelwalk 13", "L.A."));
		pos = new POS(produkte, kunden);
	}
	
	@Test
	public void test02Daten() {
		assertEquals("POS liefert die Kundenliste nicht richtig!", kunden, pos.getKunden());
		assertEquals("POS liefert die Produktliste nicht richtig!", produkte, pos.getProdukte());
	}
	
	@Test
	public void test03KundenauswahlModus() {
		// kundenCombobox
		JComboBox<Kunde> cb = pos.getKundenComboBox();
		assertEquals("Die Kunden-Combobox enthaelt nicht alle Eintraege!", 
				kunden.size(), cb.getItemCount());
		for (int i = 0; i < kunden.size(); ++i) {
			assertEquals("Kunde " + kunden.get(i).getName() + " nicht gefunden!", kunden.get(i), cb.getItemAt(i));
		}
		if (!cb.isEnabled()) {
			fail("Die Kunden-Kombobox sollte enabled sein!");
		}
		
		// alle uebrigen sind disabled
		assertTrue("Der Checkout-Button sollte im Kundenauswahl-Modus disabled sein!", 
				!pos.getCheckoutButton().isEnabled());
		for (int i = 0; i < produkte.size(); ++i) {
			assertEquals("Produktbutton an Position " + i + " falsch!", 
					produkte.get(i), pos.getProduktButtons()[i].getProdukt());
			assertTrue("Der Produktbutton mit Index " + i + " sollte disabled sein!",
					!pos.getProduktButtons()[i].isEnabled());
		}
		
		// Kunden auswaehlen und pruefen, ob Produkte enabled sind und die Rechnung richtig
		// angezeigt wird
		cb.setSelectedIndex(1); // Barverkauf
		for (int i = 0; i < produkte.size(); ++i) {
			assertTrue("Der Produktbutton mit Index " + i + " sollte enabled sein!", 
					pos.getProduktButtons()[i].isEnabled());
		}
		assertTrue("Der Checkout-Button sollte enabled sein!",
				pos.getCheckoutButton().isEnabled());
		String text = pos.getTextFeld().getText(); 
		text = removeChar(text, '\r');
		assertEquals("Die Rechnung ist nicht korrekt im Textfeld dargestellt!",
				"Rechnung: 3\n" +
				"Barverkauf\n\n" +
				"----------------------------------\n" +
				"                              0,00", text);
	}
	
	@Test 
	public void test04ProduktauswahlModus() {
		pos.getProduktButtons()[3].doClick(10);
		pos.getProduktButtons()[4].doClick(10);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		
		String text = pos.getTextFeld().getText();
		text = removeChar(text, '\r');
		assertEquals("Rechnungstext stimmt nicht nach Klick auf Akku-Handsauger und Arbeitsplatte", 
				"Rechnung: 3\n" +
				"Barverkauf\n\n" +
				"Akku-Handsauger\n" +
				"   1     x   129,90 =       129,90\n" +
				"Arbeitsplatte\n" +
				"   1     x    89,90 =        89,90\n" +
				"----------------------------------\n" +
				"                            219,80",
				text);
		assertTrue("Akku-Handsauger Produktbutton sollte weiterhin enabled sein, da genug Lagerbestand!", 
				pos.getProduktButtons()[3].isEnabled());
		assertTrue("Arbeitsplatte Produktbutton sollte disabled sein, da kein Lagerbestand mehr verfuegbar!", 
				!pos.getProduktButtons()[4].isEnabled());
	}
	
	@Test
	public void test05Checkout() {
		pos.getCheckoutButton().doClick(10);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		JComboBox<Kunde> cb = pos.getKundenComboBox();
		if (!cb.isEnabled()) {
			fail("Die Kunden-Combobox sollte nach einem Checkout enabled sein!");
		}
		
		// alle uebrigen sind disabled
		if (pos.getCheckoutButton().isEnabled()) {
			fail("Der Checkout-Button sollte disabled sein!");
		}
		for (int i = 0; i < produkte.size(); ++i) {
			if (pos.getProduktButtons()[i].isEnabled()) {
				fail("Der Produktbutton mit Index " + i + " sollte disabled sein!");
			}
		}
		
		cb.setSelectedIndex(2); // Kunden auswaehlen
		if (!pos.getProduktButtons()[3].isEnabled()) {
			fail("Akku-Handsauger Produktbutton sollte weiterhin enabled sein!");
		}
		if (pos.getProduktButtons()[4].isEnabled()) {
			fail("Arbeitsplatte Produktbutton sollte disabled sein!");
		}
		if (!pos.getCheckoutButton().isEnabled()) {
			fail("Der Checkout-Button sollte nach Kundenauswahl enabled sein!");
		}
		String text = pos.getTextFeld().getText();
		text = removeChar(text, '\r');
		assertEquals("Die Rechnung ist nicht korrekt im Textfeld dargestellt!",
			"Rechnung: 4\n" +
			"Kunde: 1\n" +
			"Madonna\n" +
			"Sunset Boulevard 1\n" +
			"Hollywood\n\n" +
			"----------------------------------\n" +
			"                              0,00",
			text);
	}
}
