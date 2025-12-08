package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class FocusEnergyMove extends StatusMove {
    public FocusEnergyMove() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected String describe() {
        return "фокусируется";
    }
}
