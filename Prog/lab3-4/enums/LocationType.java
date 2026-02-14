package enums;

public enum LocationType {
    ROAD("дорога"),
    TREES("деревья"),
    SKY("небо"),
    PALACE("дворец"),
    GROUND("земля");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}