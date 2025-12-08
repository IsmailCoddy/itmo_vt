package move;

import ru.ifmo.se.pokemon.*;
import pokemon.*;

public final class RazorLeafMove extends PhysicalMove {
    public RazorLeafMove() {
        super(Type.GRASS, 55, 95);
    }

    @Override
    protected String describe() {
        return "готовится";
    }
}
