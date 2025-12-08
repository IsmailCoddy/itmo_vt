package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class ThunderboltMove extends SpecialMove {
    public ThunderboltMove() {
        super(Type.ELECTRIC, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Math.random() < 0.1) {
            Effect.paralyze(p);
        }
    }

    @Override
    protected String describe() {
        return "накладывает паралич";
    }
}
