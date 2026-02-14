package sounds;

import world.World;

public class ThinSongSound extends SoundEffect {
    public ThinSongSound(double durationSec, int volume) {
        super("тонкая песня", durationSec, volume);
    }

    @Override
    public void play(World world) {
        world.println("Тихо звучит " + getType() + " (" + getDurationSec() + " сек, громкость " + getVolume() + ").");
    }
}