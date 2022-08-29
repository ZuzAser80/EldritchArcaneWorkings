package net.fabricmc.example.spell;

public enum SpellRank {
    novice(0),
    apprentice(1),
    average(2),
    master(3),
    god(4);
    private int id;
    SpellRank(int index) {
        id = index;
    }
    public int getId() {
        return id;
    }
}
