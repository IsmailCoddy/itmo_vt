package sounds;

import world.World;

public class CroakSound extends SoundEffect {
    public CroakSound(double durationSec, int volume) {
        super("карканье", durationSec, volume);
    }

    @Override
    public void play(World world) {
        world.println("Слышится " + getType() + " (" + getDurationSec() + " сек, громкость " + getVolume() + ").");
    }
}