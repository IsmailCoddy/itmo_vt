package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class RestMove extends StatusMove {
    public RestMove() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        double currentHP = p.getHP();
        double baseHP = p.getStat(Stat.HP);
        p.setMod(Stat.HP, (int)(-(baseHP - currentHP)));
        p.setCondition(new Effect());
        Effect.sleep(p);
    }

    @Override
    protected String describe() {
        return "Восстанавливается";
    }
}
