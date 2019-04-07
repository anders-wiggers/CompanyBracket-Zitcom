package a.af.zieball.classes;

public class User {
    private String afdeling;
    private String name;
    private int score;

    public User() {

    }

    public String getAfdeling() {
        return afdeling;
    }

    public void setAfdeling(String afdeling) {
        this.afdeling = afdeling;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "User{" +
                "afdeling='" + afdeling + '\'' +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }

    public void incScore() {
        score ++;
    }
}
