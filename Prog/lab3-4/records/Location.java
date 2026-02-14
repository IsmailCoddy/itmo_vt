package records;

import enums.LocationType;

public record Location(String name, LocationType type) {
    @Override
    public String toString() {
        return name + " (" + type.getDescription() + ")";
    }
}