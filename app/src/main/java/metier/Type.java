package metier;

/**
 * Created by Valoo221 on 22/03/2017.
 */

public class Type {
    private int id;
    private Double prix;
    private int profondeur;
    private String situation;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getProfondeur() {
        return profondeur;
    }

    public void setProfondeur(int profondeur) {
        this.profondeur = profondeur;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public Type(int id, Double prix, int profondeur, String situation) {

        this.id = id;
        this.prix = prix;
        this.profondeur = profondeur;
        this.situation = situation;
    }

    public Type() {}
}
