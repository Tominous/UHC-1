package net.saikatsune.aurityuhc.manager;

public class ConfigManager {

    private int borderSize = 1500, finalHeal = 10, graceTime = 20,
                starterFood = 16, borderTime = 45;

    private boolean shears = true, nether = false, goldenHeads = true,
            speed1 = false, speed2 = false, strength1 = false, strength2 = false,
            enderpearlDamage = true;

    private double appleRate = 2, flintRate = 50;


    public void setAppleRate(double appleRate) {
        this.appleRate = appleRate;
    }

    public void setEnderpearlDamage(boolean enderpearlDamage) {
        this.enderpearlDamage = enderpearlDamage;
    }

    public void setStarterFood(int starterFood) {
        this.starterFood = starterFood;
    }

    public void setFlintRate(double flintRate) {
        this.flintRate = flintRate;
    }

    public void setFinalHeal(int finalHeal) {
        this.finalHeal = finalHeal;
    }

    public void setGraceTime(int graceTime) {
        this.graceTime = graceTime;
    }

    public void setBorderSize(int borderSize) {
        this.borderSize = borderSize;
    }

    public void setGoldenHeads(boolean goldenHeads) {
        this.goldenHeads = goldenHeads;
    }

    public void setNether(boolean nether) {
        this.nether = nether;
    }

    public void setBorderTime(int borderTime) {
        this.borderTime = borderTime;
    }

    public void setShears(boolean shears) {
        this.shears = shears;
    }

    public void setSpeed1(boolean speed1) {
        this.speed1 = speed1;
    }

    public void setSpeed2(boolean speed2) {
        this.speed2 = speed2;
    }

    public void setStrength1(boolean strength1) {
        this.strength1 = strength1;
    }

    public void setStrength2(boolean strength2) {
        this.strength2 = strength2;
    }

    public double getAppleRate() {
        return appleRate;
    }

    public int getBorderSize() {
        return borderSize;
    }

    public boolean isShears() {
        return shears;
    }

    public boolean isGoldenHeads() {
        return goldenHeads;
    }

    public int getStarterFood() {
        return starterFood;
    }

    public boolean isNether() {
        return nether;
    }

    public boolean isSpeed1() {
        return speed1;
    }

    public boolean isSpeed2() {
        return speed2;
    }

    public boolean isStrength1() {
        return strength1;
    }

    public boolean isStrength2() {
        return strength2;
    }

    public boolean isEnderpearlDamage() {
        return enderpearlDamage;
    }

    public int getFinalHeal() {
        return finalHeal;
    }

    public double getFlintRate() {
        return flintRate;
    }

    public int getGraceTime() {
        return graceTime;
    }

    public int getBorderTime() {
        return borderTime;
    }
}
