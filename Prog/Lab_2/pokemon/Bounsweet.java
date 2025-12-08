package pokemon;

import ru.ifmo.se.pokemon.*;
import move.*;

public class Bounsweet extends Pokemon {
    public Bounsweet(String name, int level) {
        super(name, level);
        setType(Type.GRASS);
        setStats(42, 30, 38, 30, 38, 32);
        this.addMove(new RazorLeafMove());
        this.addMove(new RestMove());
    }
}
