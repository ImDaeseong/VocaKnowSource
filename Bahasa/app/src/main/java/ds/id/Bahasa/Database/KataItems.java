package ds.id.Bahasa.Database;

public class KataItems {
    private int rIndex;
    private String Part1;
    private String Part2;
    private String KataKor;
    private String KataIndo;
    private String lembutlidah;

    public KataItems(int rIndex, String Part1, String Part2, String KataKor, String KataIndo, String lembutlidah){
        this.rIndex = rIndex;
        this.Part1 = Part1;
        this.Part2 = Part2;
        this.KataKor = KataKor;
        this.KataIndo = KataIndo;
        this.lembutlidah = lembutlidah;
    }

    public int getrIndex() {
        return this.rIndex;
    }

    public void setrIndex(int rIndex) {
        this.rIndex = rIndex;
    }

    public String getPart1() {
        return this.Part1;
    }

    public void setPart1(String part1) {
        this.Part1 = part1;
    }

    public String getPart2() {
        return this.Part2;
    }

    public void setPart2(String part2) {
        this.Part2 = part2;
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

    public String getLembutlidah() {
        return this.lembutlidah;
    }

    public void setLembutlidah(String lembutlidah) {
        this.lembutlidah = lembutlidah;
    }
}
