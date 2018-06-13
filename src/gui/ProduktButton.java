package gui;

import geschaeftsobjekt.Artikel;
import geschaeftsobjekt.Produkt;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.File;

public class ProduktButton extends JButton {

  private Produkt p;

  public ProduktButton(Produkt p){
    super(p.getBezeichnung());
    this.p = p;
    ImageIcon icon;

    if(p instanceof Artikel){
      String filename = "images/" + ((Artikel) p).getKurzbezeichnung() + ".jpg";
      File file = new File(filename);
      if(file.exists()) {
        icon = new ImageIcon(filename);
      }
      else {
        icon = new ImageIcon("images/generic.jpg");
      }
    } else {
      icon = new ImageIcon("images/generic_service.jpg");
    }
    this.setIcon(icon);
    setHorizontalTextPosition(JButton.CENTER);
    setVerticalTextPosition(JButton.BOTTOM);
  }

  public Produkt getProdukt(){
    return p;
  }

}
