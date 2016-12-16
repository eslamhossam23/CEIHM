package com.app.eslamhossam23.testceihm;

/**
 * Created by Eslam on 0015 15/12/2016.
 */
public class Profil {
    private String nom;
    private String prenom;
    private String sex;
    private int idImage;

    public Profil(String nom, String prenom, int idImage) {
        this.nom = nom;
        this.prenom = prenom;
        this.idImage = idImage;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
