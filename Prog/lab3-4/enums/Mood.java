package enums;

public enum Mood {
    CALM("спокоен"),
    CURIOUS("любопытен"),
    CONFUSED("смущён"),
    ANGRY("зол"),
    DEAD("мертв");

    private final String description;

    Mood(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}