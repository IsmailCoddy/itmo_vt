package pokemon;

import ru.ifmo.se.pokemon.*;
import move.*;

public final class Basculin extends Pokemon {
    public Basculin(String name, int level) {
        super(name, level);
        setType(Type.WATER);
        setStats(70, 92, 65, 80, 55, 98);
        this.addMove(new CrunchMove());
        this.addMove(new MudShotMove());
        this.addMove(new TakeDownMove());
        this.addMove(new AgilityMove());
    }
}
