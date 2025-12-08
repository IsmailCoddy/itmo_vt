package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class MudShotMove extends SpecialMove {
    public MudShotMove() {
        super(Type.GROUND, 55, 95);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPEED, -1);
    }

    @Override
    protected String describe() {
        return "снижает скорость на 1 ступень";
    }
}
