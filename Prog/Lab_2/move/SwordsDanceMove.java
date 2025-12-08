package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class SwordsDanceMove extends StatusMove {
    public SwordsDanceMove() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, +2);
    }

    @Override
    protected String describe() {
        return "повышает атаку";
    }
}
