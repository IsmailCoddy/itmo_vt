package interfaces;

import world.World;
import chars.Creature;

public interface Attacker {
    void attack(Creature target, World world);
}