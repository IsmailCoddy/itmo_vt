package chars;

import enums.Mood;
import enums.VoiceType;
import exceptions.DeadCreatureException;
import interfaces.Singer;
import records.Location;
import sounds.ThinSongSound;
import world.World;

public class Welcom extends Creature implements Singer {
    public Welcom(String name, Location location) {
        super(name, location, Mood.CALM, VoiceType.THIN);
    }

    @Override
    public void sing(World world) {
        try {
            requireAlive("sing");
            setVoice(VoiceType.THIN, world);
            new ThinSongSound(2.0, 15).play(world);
            world.println(getName() + " тихонечко запел " + getVoice().getDescription() + ".");
        } catch (DeadCreatureException e) {
            world.println(e.getMessage());
        }
    }

    @Override
    public void act(World world) throws DeadCreatureException {
        requireAlive("act");
        world.println(getName() + " продолжает путь и надеется на судьбу.");
    }
}