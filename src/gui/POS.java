package gui;

import geschaeftsobjekt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;

public class POS extends JFrame implements ActionListener {

  /* Attribute */
	static final String SERIALIZATION_PATH = "data/Produkt.ser";
  private List<Kunde> kunden;
	private List<Produkt> produkte;
  private Rechnung rechnung;
  private JComboBox<Kunde> kundenComboBox;
  private JTextArea textFeld;
	private JButton checkoutButton;
	private ProduktButton[] produktButtons;

	/* Konstruktoren */
	public POS(List<Produkt> produkte, List<Kunde> kunden) {
	  super("Point of Sale");
	  this.produkte = produkte;
	  this.kunden = kunden;
	  setLayout(new BorderLayout());
	  setExtendedState(JFrame.MAXIMIZED_BOTH);
	  initComponents();
	  pack();
	  setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  aktiviereKundenauswahl();
	  setVisible(true);
  }

  /* Methoden */
  private static List<Produkt> initProducts() {
    List<Produkt> produkte = new LinkedList<>();
    Artikel p1 = new Artikel(12345, "Arbeitsplatte", 89.90);
    p1.einlagern(1);
    Dienstleistung p2 = new Dienstleistung(100, "Küchenmontage", 75., "h");
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
    produkte.add(p1); produkte.add(p2);
    produkte.add(p3); produkte.add(p4);
    produkte.add(p5); produkte.add(p6);
    produkte.add(p7); produkte.add(p8);
    produkte.add(p9); produkte.add(p10);
    produkte.add(p11);
//  Produkt.saveProducts(produkte, SERIALIZATION_PATH);
    return produkte;
	}

	public static void main(String args[]) {
//	List<Produkt> produkte = Produkt.loadProducts(SERIALIZATION_PATH);
//	try {
//		produkte = Produkt.loadProducts(SERIALIZATION_PATH);
//	} catch (Exception e) {
//		initProducts();
//		produkte = Produkt.loadProducts(SERIALIZATION_PATH);
//	}

		List<Kunde> kunden = new ArrayList<>();
		kunden.add(new Kunde(-1, "<bitte auswählen>", "", ""));
		kunden.add(new Kunde(0, "Barverkauf", "", ""));
		kunden.add(new Kunde(1, "Madonna", "Sunset Boulevard 1", "Hollywood"));
		kunden.add(new Kunde(2, "Heidi Klum", "Modelwalk 13", "L.A."));
		new POS(initProducts(), kunden);
	}

	private void initComponents() {
    JPanel panelWest = new JPanel(new BorderLayout());
	  add(panelWest, BorderLayout.WEST);

    JPanel panelCenter = new JPanel();
	  add(panelCenter, BorderLayout.CENTER);

	  kundenComboBox = new JComboBox<>();
	  for (Kunde k : kunden) {
	    kundenComboBox.addItem(k);
    }
    kundenComboBox.addActionListener(this);
	  panelWest.add(kundenComboBox, BorderLayout.NORTH);

	  textFeld = new JTextArea();
	  textFeld.setPreferredSize(new Dimension(300, 600));
	  textFeld.setFont(new Font("Courier New", Font.BOLD, 14));
	  panelWest.add(textFeld, BorderLayout.CENTER);

	  checkoutButton = new JButton("CHECKOUT");
	  panelWest.add(checkoutButton, BorderLayout.SOUTH);

	  produktButtons = new ProduktButton[produkte.size()];
	  for (int i = 0; i < produkte.size(); i++) {
	    ProduktButton pB = new ProduktButton(produkte.get(i));
	    produktButtons[i] = pB;
	    panelCenter.add(pB);
    }
  }

  private void aktiviereKundenauswahl() {
    kundenComboBox.setEnabled(true);
    kundenComboBox.setSelectedIndex(0);
    textFeld.setText("");
    checkoutButton.setEnabled(false);
    for (ProduktButton pB : produktButtons) {
      pB.setEnabled(false);
    }
  }

  private void aktiviereProduktauswahl() {
    kundenComboBox.setEnabled(false);
    textFeld.setText(rechnung.toString());
    checkoutButton.setEnabled(true);
    for (ProduktButton pB : produktButtons) {
      if (pB.getProdukt() instanceof Artikel) {
        Artikel a = (Artikel) pB.getProdukt();
        if (a.getLagerbestand() <= 0) {
          continue;
        }
      }
      pB.setEnabled(true);
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();

    /* kundenComboBox */
    if (source == kundenComboBox) {
      Kunde selectedItem = (Kunde) kundenComboBox.getSelectedItem();
      for (int i = 1; i < kunden.size(); i++) {
        Kunde k = kunden.get(i);
        if (selectedItem == k) {
          if (i != 1) {
            rechnung = new Rechnung(k);
          } else {
            rechnung = new Rechnung();
          }
          aktiviereProduktauswahl();
          break;
        }
      }
    }

    /* checkoutButton */
    else if (source == checkoutButton) {

    }

    /* produktButtons */
    else {

    }
  }

  public List<Kunde> getKunden() {
    return kunden;
  }

  public List<Produkt> getProdukte() {
    return produkte;
  }

  public Rechnung getRechnung() {
    return rechnung;
  }

  public JComboBox<Kunde> getKundenComboBox() {
    return kundenComboBox;
  }

  public JTextArea getTextFeld() {
    return textFeld;
  }

  public JButton getCheckoutButton() {
    return checkoutButton;
  }

  public ProduktButton[] getProduktButtons() {
    return produktButtons;
  }

}
