package com.ris.trgovina;


public class Artikel {
    private String id;
    private String name;
    private String price;
    private String kolicina;

    public Artikel(String id, String name, String price, String kolicina) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.kolicina = kolicina;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getKolicina() {
        return kolicina;
    }

    public void setKolicina(String kolicina) {
        this.kolicina = kolicina;
    }
}
