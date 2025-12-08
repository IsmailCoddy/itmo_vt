package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class AgilityMove extends StatusMove {
    public AgilityMove() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.SPEED, +2);
    }

    @Override
    protected String describe() {
        return "повышает скорость";
    }
}
