package com.rts0209.eoj.cursedenergy;

public class SorcererProgressData {

    private int level;
    private int experience;

    public SorcererProgressData() {
        this.level = 1;
        this.experience = 0;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public void setLevel(int level) {
        this.level = Math.max(1, level);
        this.experience = 0;
    }

    public void addLevels(int amount) {
        setLevel(this.level + amount);
    }

    public void addExperience(int amount) {
        this.experience = Math.max(0, this.experience + amount);
        while (this.experience >= getExperienceForNextLevel()) {
            this.experience -= getExperienceForNextLevel();
            this.level++;
        }
    }

    public int getExperienceForNextLevel() {
        return 100 + (this.level - 1) * 50;
    }
}
