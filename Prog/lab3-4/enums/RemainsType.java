package enums;

public enum RemainsType {
    BONES("кости"),
    SKULLS("черепа");

    private final String description;

    RemainsType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}