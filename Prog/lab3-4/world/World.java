package world;

import enums.*;
import records.*;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private final Random rnd;
    private final Road road;
    private final Forest forest;
    private final Palace palace;

    private World(Random rnd, Road road, Forest forest, Palace palace) {
        this.rnd = rnd;
        this.road = road;
        this.forest = forest;
        this.palace = palace;
    }

    public static World createDefault() {
        Random rnd = new Random();

        ArrayList<Remains> remains = new ArrayList<>();
        remains.add(new Remains(RemainsType.BONES, rnd.nextInt(30) + 10));
        remains.add(new Remains(RemainsType.SKULLS, rnd.nextInt(10) + 5));

        int dangerLevel = rnd.nextInt(60) + 20;
        Road road = new Road(remains, dangerLevel);

        ArrayList<Tree> trees = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Tree t = new Tree("дерево " + i);
            t.hangSkulls(rnd.nextInt(4) + 1);
            trees.add(t);
        }
        Forest forest = new Forest(trees);

        Palace palace = new Palace(new Location("дворец людоеда", LocationType.PALACE));

        return new World(rnd, road, forest, palace);
    }

    public Random random() {
        return rnd;
    }

    public Road getRoad() {
        return road;
    }

    public Forest getForest() {
        return forest;
    }

    public Palace getPalace() {
        return palace;
    }

    public void println(String text) {
        System.out.println(text);
    }
}