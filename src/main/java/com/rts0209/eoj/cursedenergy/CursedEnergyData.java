package com.rts0209.eoj.cursedenergy;

public class CursedEnergyData {

    private int energy;
    private int maxEnergy;

    public CursedEnergyData() {
        this.maxEnergy = 100;
        this.energy = maxEnergy;
    }

    public int getEnergy() {
        return energy;
    }

    public int getMaxEnergy() {
        return maxEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(0, Math.min(energy, maxEnergy));
    }

    public void addEnergy(int amount) {
        setEnergy(this.energy + amount);
    }

    public void removeEnergy(int amount) {
        setEnergy(this.energy - amount);
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = Math.max(1, maxEnergy);
        this.energy = Math.min(this.energy, this.maxEnergy);
    }

    public boolean isFull() {
        return this.energy >= this.maxEnergy;
    }
}
