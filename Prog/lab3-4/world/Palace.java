package world;

import records.Location;

import java.util.Objects;

public class Palace {
    private final Location location;

    public Palace(Location location) {
        this.location = location;
    }

    public void describe(World world) {
        world.println("Вдали виднеется " + location + ".");
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "Palace{location=" + location + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Palace palace)) return false;
        return Objects.equals(location, palace.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }
}