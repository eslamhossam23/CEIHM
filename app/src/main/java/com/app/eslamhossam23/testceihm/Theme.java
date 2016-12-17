package com.app.eslamhossam23.testceihm;

/**
 * Created by Havoc on 0017-17-12-2016.
 */

public class Theme {
    private int idImage;
    private String name;

    public Theme(int idImage, String name) {
        this.idImage = idImage;
        this.name = name;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
