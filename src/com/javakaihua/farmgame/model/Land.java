package com.javakaihua.farmgame.model;

public class Land {
    private int landID;// 每一块的土地编号
    private int cropID = -1;// 此土地上种植的农作物编号(-1代表没有种植农作物)
    private double growTime;// 净生长时间，只有浇水了才会变化
    private double startTime;//开始种植的时间，播种时记录
    private double waterTime;//浇水的时间
    private int nowStage=0;// 当前的生长阶段
    private boolean isPerished;// 是否是枯萎状态
    private boolean isBugged;//是否有虫害
    private boolean isWeeded;//是否长杂草了
    private boolean isWatered;//是否浇水了
    private int noWaterCount;//连续没有浇水次数
    private int fruitNum;// 成熟时果实数字
    private boolean isFertilized;//是否施肥了

    private boolean pickable=false;
    private boolean seedable=true;


    public Land() {
        super();
    }
    public Land(int landID)
    {
        this.landID=landID;
    }

    public Land(int landID, int cropID, double growTime, double startTime,double waterTime, int nowStage, boolean isPerished, boolean isBugged, boolean isWeeded, boolean isWatered, int noWaterCount, int fruitNum) {
        this.landID = landID;
        this.cropID = cropID;
        this.growTime = growTime;
        this.startTime = startTime;
        this.waterTime=waterTime;
        this.nowStage = nowStage;
        this.isPerished = isPerished;
        this.isBugged = isBugged;
        this.isWeeded = isWeeded;
        this.isWatered = isWatered;
        this.noWaterCount = noWaterCount;
        this.fruitNum = fruitNum;
    }

    public int getLandID() {
        return landID;
    }

    public void setLandID(int landID) {
        this.landID = landID;
    }

    public int getCropID() {
        return cropID;
    }

    public void setCropID(int cropID) {
        this.cropID = cropID;
    }

    public double getGrowTime() {
        return growTime;
    }

    public void setGrowTime(double growTime) {
        this.growTime = growTime;
    }

    public int getNowStage() {
        return nowStage;
    }

    public void setNowStage(int nowStage) {
        this.nowStage = nowStage;
    }


    public boolean isBugged() {
        return isBugged;
    }

    public void setBugged(boolean bugged) {
        isBugged = bugged;
    }

    public boolean isWeeded() {
        return isWeeded;
    }

    public void setWeeded(boolean weeded) {
        isWeeded = weeded;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public void setWatered(boolean watered) {
        isWatered = watered;
    }

    public int getNoWaterCount() {
        return noWaterCount;
    }

    public void setNoWaterCount(int noWaterCount) {
        this.noWaterCount = noWaterCount;
    }

    public int getFruitNum() {
        return fruitNum;
    }

    public void setFruitNum(int fruitNum) {
        this.fruitNum = fruitNum;
    }

    public boolean isPerished() {
        return isPerished;
    }

    public void setPerished(boolean perished) {
        isPerished = perished;
    }
    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }
    public boolean isPickable() {
        return pickable;
    }

    public void setPickable(boolean pickable) {
        this.pickable = pickable;
    }

    public boolean isSeedable() {
        return seedable;
    }

    public void setSeedable(boolean seedable) {
        this.seedable = seedable;
    }

    public double getWaterTime() {
        return waterTime;
    }

    public void setWaterTime(double waterTime) {
        this.waterTime = waterTime;
    }

    public boolean isFertilized() {
        return isFertilized;
    }

    public void setFertilized(boolean fertilized) {
        isFertilized = fertilized;
    }
}
