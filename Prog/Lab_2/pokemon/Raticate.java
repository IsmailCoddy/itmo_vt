package pokemon;

import ru.ifmo.se.pokemon.*;
import move.*;

public final class Raticate extends Rattata {
    public Raticate(String name, int level) {
        super(name, level);
        setStats(55, 81, 60, 50, 70, 97);
        this.addMove(new SwordsDanceMove());
    }
}
