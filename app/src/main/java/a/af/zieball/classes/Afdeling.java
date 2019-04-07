package a.af.zieball.classes;

public class Afdeling {

    private String name;
    private int totalScore;

    public Afdeling(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    @Override
    public String toString() {
        return "Afdeling{" +
                "name='" + name + '\'' +
                ", totalScore=" + totalScore +
                '}';
    }
}
