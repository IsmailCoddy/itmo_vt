import chars.*;
import enums.*;
import exceptions.*;
import records.*;
import world.*;

public class Lab3 {
    public static void main(String[] args) {
        World world = World.createDefault();

        Welcom welcom = new Welcom("Вэлком", new Location("дорога к дворцу", LocationType.ROAD));
        Ogre ogre = new Ogre("людоед", new Location("верхушки деревьев", LocationType.TREES));
        Crow crow = new Crow("ворона", new Location("небо", LocationType.SKY));

        world.println("— Не беспокойтесь, хозяин, — сказал " + welcom.getName() + ". — Положитесь на судьбу.");

        try {
            world.getRoad().checkPassable();
            welcom.moveTo(new Location("дорога к дворцу людоеда", LocationType.ROAD), world);
            world.getRoad().describe(world);
            world.getForest().describe(world);

            ogre.sing(world);
            welcom.sing(world);

            boolean ogreLooksUp = ogre.tryLookUpBecauseOfSound(world);
            if (ogreLooksUp) {
                crow.appearNear(ogre, world);
                crow.attack(ogre, world);
            } else {
                world.println("Людоед не стал задирать голову. Он насторожился и продолжил путь.");
            }

            try {
                ogre.act(world);
            } catch (DeadCreatureException e) {
                world.println("Поймали checked-исключение: " + e.getMessage());
            }

        } catch (PathBlockedException e) {
            world.println("Поймали checked-исключение: " + e.getMessage());
        } catch (InvalidActionRuntimeException e) {
            world.println("Поймали unchecked-исключение: " + e.getMessage());
        } catch (Exception e) {
            world.println("Непредвиденная ошибка: " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        world.println("Сцена завершена.");
    }
}