package world;

import java.util.Objects;

public class Tree {
    private final String name;
    private int hangingSkulls;

    public Tree(String name) {
        this.name = name;
        this.hangingSkulls = 0;
    }

    public void hangSkulls(int n) {
        if (n > 0) {
            hangingSkulls += n;
        }
    }

    public int getHangingSkulls() {
        return hangingSkulls;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Tree{" + "name='" + name + '\'' + ", hangingSkulls=" + hangingSkulls + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tree tree)) return false;
        return hangingSkulls == tree.hangingSkulls && Objects.equals(name, tree.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, hangingSkulls);
    }
}