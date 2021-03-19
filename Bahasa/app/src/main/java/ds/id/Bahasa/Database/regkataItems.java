package ds.id.Bahasa.Database;

public class regkataItems {

    private int rIndex;
    private String KataKor;
    private String KataIndo;
    private String KataIndoTambah;

    public regkataItems(int rIndex, String KataKor, String KataIndo, String KataIndoTambah){
        this.rIndex = rIndex;
        this.KataKor = KataKor;
        this.KataIndo = KataIndo;
        this.KataIndoTambah = KataIndoTambah;
    }

    public int getrIndex() {
        return this.rIndex;
    }

    public void setrIndex(int rIndex) {
        this.rIndex = rIndex;
    }

    public String getKataKor() {
        return this.KataKor;
    }

    public void setKataKor(String kataKor) {
        this.KataKor = kataKor;
    }

    public String getKataIndo() {
        return this.KataIndo;
    }

    public void setKataIndo(String kataIndo) {
        this.KataIndo = kataIndo;
    }

    public String getKataIndoTambah() {
        return this.KataIndoTambah;
    }

    public void setKataIndoTambah(String kataIndoTambah) {
        this.KataIndoTambah = kataIndoTambah;
    }

    @Override
    public String toString() {
        return "regkataItems " +
                "rIndex=" + rIndex +
                ", KataKor='" + KataKor + '\'' +
                ", KataIndo='" + KataIndo + '\'' +
                ", KataIndoTambah='" + KataIndoTambah + '\'' ;
    }
}
