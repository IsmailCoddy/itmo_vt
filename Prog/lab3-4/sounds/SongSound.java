package sounds;

import world.World;

public class SongSound extends SoundEffect {
    public SongSound(double durationSec, int volume) {
        super("песня", durationSec, volume);
    }

    @Override
    public void play(World world) {
        world.println("Звучит " + getType() + " (" + getDurationSec() + " сек, громкость " + getVolume() + ").");
    }
}