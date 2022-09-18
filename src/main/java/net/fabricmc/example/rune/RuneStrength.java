package net.fabricmc.example.rune;

public enum RuneStrength {
    WEAK(1), NORMAL(2), STRONG(3);
    private int strength;
    RuneStrength(int strength) {
        this.strength = strength;
    }
    public int getStrength() {
        return strength;
    }
    public RuneStrength runeStrength(int i) {
        switch (i) {
            case 1 -> { return WEAK; }
            case 2 -> { return NORMAL; }
            case 3 -> { return STRONG; }
            default -> { return null; }
        }
    }
}
