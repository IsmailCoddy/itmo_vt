package chars;

import enums.*;
import exceptions.*;
import interfaces.Singer;
import records.Location;
import sounds.SongSound;
import world.World;

public class Ogre extends Creature implements Singer {
    private int eyes;
    private boolean lookingUp;
    private boolean walkingOnTrees;

    public Ogre(String name, Location location) {
        super(name, location, Mood.CURIOUS, VoiceType.LOUD);
        this.eyes = 2;
        this.lookingUp = false;
        this.walkingOnTrees = true;
    }

    public boolean hasEyes() {
        return eyes > 0;
    }

    public boolean isLookingUp() {
        return lookingUp;
    }

    public void lookUp(World world) throws DeadCreatureException {
        requireAlive("lookUp");
        lookingUp = true;
        setMood(Mood.CURIOUS, world);
        world.println(getName() + " задрал голову и стал вглядываться вверх.");
    }

    public boolean tryLookUpBecauseOfSound(World world) throws DeadCreatureException {
        requireAlive("tryLookUpBecauseOfSound");

        int base = 60;
        int roll = world.random().nextInt(100) + 1;
        boolean result = roll <= base;

        world.println(getName() + " прислушался... (шанс посмотреть вверх: " + base + "%, бросок=" + roll + ")");
        if (result) {
            lookUp(world);
        }
        return result;
    }

    public void ripOutEyes(World world) {
        if (!isAlive()) return;

        if (eyes <= 0) {
            throw new InvalidActionRuntimeException("нельзя вырвать глаза у " + getName() + ": глаз уже нет");
        }
        eyes = 0;
        world.println("Когти вцепились в лицо " + getName() + ". Глаза вырваны.");
        die("без глаз");
        world.println(getName() + " упал мёртвый на " + enums.LocationType.GROUND.getDescription() + ".");
    }

    @Override
    public void sing(World world) {
        try {
            requireAlive("sing");
            setVoice(VoiceType.LOUD, world);
            new SongSound(3.0, 70).play(world);
            world.println(getName() + " шёл по деревьям, как по траве, и распевал песни.");
        } catch (DeadCreatureException e) {
            world.println(e.getMessage());
        }
    }

    @Override
    public void act(World world) throws DeadCreatureException {
        requireAlive("act");
        if (walkingOnTrees) {
            world.println(getName() + " продолжает двигаться по верхушкам деревьев.");
        } else {
            world.println(getName() + " идёт по земле.");
        }
    }

    @Override
    public String toString() {
        return super.toString() + " {eyes=" + eyes + ", lookingUp=" + lookingUp + "}";
    }
}