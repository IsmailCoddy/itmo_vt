package world;

import exceptions.PathBlockedException;
import records.Remains;

import java.util.ArrayList;
import java.util.Objects;

public class Road {
    private final ArrayList<Remains> remains;
    private final int dangerLevel;

    public Road(ArrayList<Remains> remains, int dangerLevel) {
        this.remains = remains;
        this.dangerLevel = dangerLevel;
    }

    public ArrayList<Remains> getRemains() {
        return remains;
    }

    public void describe(World world) {
        world.println("Вся дорога туда была усеяна человеческими костями.");
        for (Remains r : remains) {
            world.println("На дороге: " + r);
        }
    }

    public void checkPassable() throws PathBlockedException {
        if (dangerLevel >= 95) {
            throw new PathBlockedException("дорога слишком опасна, пройти невозможно (dangerLevel=" + dangerLevel + ")");
        }
    }

    @Override
    public String toString() {
        return "Road{remains=" + remains + ", dangerLevel=" + dangerLevel + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Road road)) return false;
        return dangerLevel == road.dangerLevel && Objects.equals(remains, road.remains);
    }

    @Override
    public int hashCode() {
        return Objects.hash(remains, dangerLevel);
    }
}