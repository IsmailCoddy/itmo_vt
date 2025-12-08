package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class DoubleTeamMove extends StatusMove {
    public DoubleTeamMove() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.EVASION, +1);
    }

    @Override
    protected String describe() {
        return "увеличивает шанс врага на промах";
    }
}
