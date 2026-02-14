package records;

import enums.RemainsType;

public record Remains(RemainsType type, int amount) {
    @Override
    public String toString() {
        return type.getDescription() + " ×" + amount;
    }
}