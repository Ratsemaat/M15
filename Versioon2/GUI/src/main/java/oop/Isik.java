package oop;

public class Isik {
    private String nimi;
    private String aeg;

    public String getaeg() {
        return aeg;
    }

    public String getNimi() {
        return nimi;
    }

    public Isik( String nimi,String aeg) {
        this.aeg = aeg;
        this.nimi = nimi;
    }
}
