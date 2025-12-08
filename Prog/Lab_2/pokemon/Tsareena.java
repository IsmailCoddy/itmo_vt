package pokemon;

import ru.ifmo.se.pokemon.*;
import move.*;

public final class Tsareena extends Steenee {
    public Tsareena(String name, int level) {
        super(name, level);
        setStats(72, 120, 98, 50, 98, 72);
        this.addMove(new TeeterDanceMove());
    }
}
