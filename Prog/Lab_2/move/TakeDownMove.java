package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class TakeDownMove extends PhysicalMove {
    public TakeDownMove() {
        super(Type.NORMAL, 90, 85);
    }

    @Override
    protected void applySelfDamage(Pokemon att, double damage) {
        att.setMod(Stat.HP, (int)(damage * 0.25));
    }

    @Override
    protected String describe() {
        return "наносит себе часть урона";
    }
}
