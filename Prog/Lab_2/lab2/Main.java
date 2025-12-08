package lab2;

import ru.ifmo.se.pokemon.*;
import pokemon.*;
import move.*;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Basculin p1 = new Basculin("Баск", 1);
        Rattata p2 = new Rattata("Рат", 1);
        Raticate p3 = new Raticate("Баск", 1);
        Bounsweet p4 = new Bounsweet("Баун", 1);
        Steenee p5 = new Steenee("Сти", 1);
        Tsareena p6 = new Tsareena("Царина", 1);
        b.addAlly(p1);
        b.addFoe(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addAlly(p5);
        b.addFoe(p6);
        b.go();
    }
}
