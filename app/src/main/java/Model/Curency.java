package Model;

public class Curency {
    private String description;

    public Curency(String description) {
        this.description = description;
    }

    public Curency() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Curency{" +
                "description='" + description + '\'' +
                '}';
    }
}
