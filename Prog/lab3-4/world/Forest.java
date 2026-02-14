package world;

import java.util.ArrayList;
import java.util.Objects;

public class Forest {
    private final ArrayList<Tree> trees;

    public Forest(ArrayList<Tree> trees) {
        this.trees = trees;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public void describe(World world) {
        int skulls = trees.stream().mapToInt(Tree::getHangingSkulls).sum();
        world.println("На деревьях вместо плодов висят человеческие черепа. Всего на деревьях: " + skulls + ".");
    }

    @Override
    public String toString() {
        return "Forest{trees=" + trees + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forest forest)) return false;
        return Objects.equals(trees, forest.trees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trees);
    }
}