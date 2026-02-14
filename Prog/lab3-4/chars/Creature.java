package chars;

import enums.*;
import exceptions.DeadCreatureException;
import interfaces.Alive;
import records.Location;
import world.World;

import java.util.Objects;

public abstract class Creature implements Alive {
    private final String name;
    private boolean alive;
    private Location location;
    private Mood mood;
    private VoiceType voice;

    protected Creature(String name, Location location, Mood mood, VoiceType voice) {
        this.name = name;
        this.location = location;
        this.mood = mood;
        this.voice = voice;
        this.alive = true;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public Mood getMood() {
        return mood;
    }

    public VoiceType getVoice() {
        return voice;
    }

    public void setVoice(VoiceType voice, World world) {
        this.voice = voice;
        world.println(name + " меняет голос на: " + voice.getDescription() + ".");
    }

    public void moveTo(Location location, World world) throws DeadCreatureException {
        requireAlive("moveTo");
        this.location = location;
        world.println(name + " отправился в " + location + ".");
    }

    public void setMood(Mood mood, World world) throws DeadCreatureException {
        requireAlive("setMood");
        this.mood = mood;
        world.println(name + " теперь " + mood.getDescription() + ".");
    }

    protected void requireAlive(String action) throws DeadCreatureException {
        if (!alive) {
            throw new DeadCreatureException(name + " не может выполнить действие '" + action + "', потому что он мёртв");
        }
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public void die(String reason) {
        if (!alive) return;
        alive = false;
        mood = Mood.DEAD;
    }

    public abstract void act(World world) throws DeadCreatureException;

    @Override
    public String toString() {
        return name + "@" + location + " [" + (alive ? "жив" : "мертв") + ", " + mood.getDescription() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Creature creature = (Creature) o;
        return alive == creature.alive &&
                Objects.equals(name, creature.name) &&
                Objects.equals(location, creature.location) &&
                mood == creature.mood &&
                voice == creature.voice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, alive, location, mood, voice);
    }
}