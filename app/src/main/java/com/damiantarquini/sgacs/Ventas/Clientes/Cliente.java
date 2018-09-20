package com.damiantarquini.sgacs.Ventas.Clientes;

public class Cliente {

    private String dniC;
    private String nomC;
    private String telC;
    private String domiC;
    private String aliasC;
    private String refLug;

    public Cliente(String dniC, String nomC, String telC, String domiC, String aliasC, String refLug) {
        this.dniC = dniC;
        this.nomC = nomC;
        this.telC = telC;
        this.domiC = domiC;
        this.aliasC = aliasC;
        this.refLug = refLug;
    }

    public String getDniC() {
        return dniC;
    }

    public void setDniC(String dniC) {
        this.dniC = dniC;
    }

    public String getNomC() {
        return nomC;
    }

    public void setNomC(String nomC) {
        this.nomC = nomC;
    }

    public String getTelC() {
        return telC;
    }

    public void setTelC(String telC) {
        this.telC = telC;
    }

    public String getDomiC() {
        return domiC;
    }

    public void setDomiC(String domiC) {
        this.domiC = domiC;
    }

    public String getAliasC() {
        return aliasC;
    }

    public void setAliasC(String aliasC) {
        this.aliasC = aliasC;
    }

    public String getRefLug() {
        return refLug;
    }

    public void setRefLug(String refLug) {
        this.refLug = refLug;
    }
}
