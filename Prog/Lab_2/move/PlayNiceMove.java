package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class PlayNiceMove extends StatusMove {
    public PlayNiceMove() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, -1);
    }

    @Override
    protected String describe() {
        return "снижает атаку соперника";
    }
}
