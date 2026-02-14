package sounds;

import java.util.Objects;
import world.World;

public abstract class SoundEffect {
    private String type;
    private double durationSec;
    private int volume;

    protected SoundEffect(String type, double durationSec, int volume) {
        this.type = type;
        this.durationSec = durationSec;
        this.volume = volume;
    }

    public String getType() {
        return type;
    }

    public double getDurationSec() {
        return durationSec;
    }

    public int getVolume() {
        return volume;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDurationSec(double durationSec) {
        this.durationSec = durationSec;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public abstract void play(World world);

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundEffect that = (SoundEffect) o;
        return Double.compare(durationSec, that.durationSec) == 0 &&
                volume == that.volume &&
                Objects.equals(type, that.type);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(type, durationSec, volume);
    }

    @Override
    public String toString() {
        return "SoundEffect{" +
                "type='" + type + '\'' +
                ", durationSec=" + durationSec +
                ", volume=" + volume +
                '}';
    }
}