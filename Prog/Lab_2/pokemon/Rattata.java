package pokemon;

import ru.ifmo.se.pokemon.*;
import move.*;

public class Rattata extends Pokemon {
    public Rattata(String name, int level) {
        super(name, level);
        setType(Type.NORMAL);
        setStats(30, 56, 35, 25, 35, 72);
        this.addMove(new ThunderboltMove());
        this.addMove(new FocusEnergyMove());
        this.addMove(new DoubleTeamMove());
    }
}
