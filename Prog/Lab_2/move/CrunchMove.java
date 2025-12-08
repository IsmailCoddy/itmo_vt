package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class CrunchMove extends PhysicalMove {
    public CrunchMove() {
        super(Type.DARK, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.2) {
            p.setMod(Stat.DEFENSE, -1);
        }
    }

    @Override
    protected String describe() {
        return "уменьшает защиту на 1 ступень";
    }
}
