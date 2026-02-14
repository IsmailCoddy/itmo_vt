package chars;

import enums.*;
import exceptions.DeadCreatureException;
import interfaces.Attacker;
import records.Location;
import sounds.CroakSound;
import world.World;

public class Crow extends Creature implements Attacker {
    public Crow(String name, Location location) {
        super(name, location, Mood.CALM, VoiceType.CROAK);
    }

    public void appearNear(Creature target, World world) {
        if (!isAlive()) return;
        world.println("Откуда ни возьмись появилась " + getName() + " рядом с " + target.getName() + ".");
        new CroakSound(0.7, 35).play(world);
    }

    @Override
    public void attack(Creature target, World world) {
        if (!isAlive()) return;

        try {
            requireAlive("attack");
            if (!(target instanceof Ogre ogre)) {
                world.println(getName() + " не понимает, как атаковать " + target.getName() + ".");
                return;
            }

            if (!ogre.isLookingUp()) {
                world.println(getName() + " пытается атаковать, но " + ogre.getName() + " не поднял голову — слишком сложно подлезть к глазам.");
                return;
            }

            int chance = 75;
            int roll = world.random().nextInt(100) + 1;
            world.println(getName() + " бросается в атаку! (шанс успеха: " + chance + "%, бросок=" + roll + ")");

            if (roll <= chance) {
                ogre.ripOutEyes(world);
            } else {
                world.println(getName() + " промахнулась и отлетела в сторону.");
            }

        } catch (DeadCreatureException e) {
            world.println(e.getMessage());
        }
    }

    @Override
    public void act(World world) throws DeadCreatureException {
        requireAlive("act");
        world.println(getName() + " кружит в небе и наблюдает.");
    }
}