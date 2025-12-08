package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class TeeterDanceMove extends StatusMove {
    public TeeterDanceMove() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.confuse();
    }

    @Override
    protected String describe() {
        return "накладывает растерянность";
    }
}
