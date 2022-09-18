package net.fabricmc.example.rune;

public enum RuneType {
    FIRE("Fire"), WATER("Water"), GROUND("Ground"), AIR("Air");
    private final String name;
    RuneType(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public RuneType fromString(String name) {
        switch (name) {
            case "Fire" -> { return FIRE; }
            case "Water" -> { return WATER; }
            case "Ground" -> { return GROUND; }
            case "Air" -> { return AIR; }
            default -> { return null; }
        }
    }
}
