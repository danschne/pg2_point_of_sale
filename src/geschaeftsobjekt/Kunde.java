package geschaeftsobjekt;

public class Kunde extends Geschaeftsobjekt {
  /* Attribute */
  private String name;
  private String strasse;
  private String ort;

  /* Konstruktoren */
  public Kunde(int nr, String name, String strasse, String ort) {
    super(nr);
    this.name = name;
    this.strasse = strasse;
    this.ort = ort;
  }

  /* Methoden */
  public void setName(String name) {
    this.name = name;
  }

  public void setStrasse(String strasse) {
    this.strasse = strasse;
  }

  public void setOrt(String ort) {
    this.ort = ort;
  }

  public String getName() {
    return name;
  }

  public String getStrasse() {
    return strasse;
  }

  public String getOrt() {
    return ort;
  }
}
