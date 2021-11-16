package com.example.fpt_app.Models;

public class TB {
    private int idTB;
    private String nameTB;
    private String ndTB;

    public TB(int idTB, String nameTB, String ndTB) {
        this.idTB = idTB;
        this.nameTB = nameTB;
        this.ndTB = ndTB;
    }

    public int getIdTB() {
        return idTB;
    }

    public void setIdTB(int idTB) {
        this.idTB = idTB;
    }

    public String getNameTB() {
        return ndTB;
    }

    public void setTitel(String nameTB) {
        nameTB = nameTB;
    }

    public String getNoiDung() {
        return ndTB;
    }

    public void setNoiDung(String ndTB) {
        this.ndTB = ndTB;
    }
}
