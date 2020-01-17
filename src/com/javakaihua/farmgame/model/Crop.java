package com.javakaihua.farmgame.model;

public class Crop {
	//作物类，存储作物信息

	private int cropId;// 农作物编号
	private String name;// 农作物名称
	private String pic;// 农作物果实图片
	private int stageCount;// 农作物生长阶段数
	private int unitTime;// 农作物生长时各阶段时间基数
	private double seedPrice;// 种子购买单价
	private int buyLevel;// 种子购买等级
	private int cropNumber;//种子库存量


	public Crop(int cropId, String name, String pic, int stageCount,
			int unitTime,  double seedPrice, int buyLevel,int cropNumber) {
		this.cropId = cropId;
		this.name = name;
		this.pic = pic;
		this.stageCount = stageCount;
		this.unitTime = unitTime;
		this.seedPrice = seedPrice;
		this.buyLevel = buyLevel;
		this.cropNumber=cropNumber;
	}
     public Crop() {
    	 super();
     }

	// 按农作物id获取果实图片的完整路径
	public String getCropFruitPic() {
		return "assets/image/crops/crop" + cropId + "/seed.png";
	}



	// 按农作物id获取农作物枯萎图片的完整路径
	public String getCropEndPic() {
		return "assets/image/crops/crop" + cropId + "/cron_end.png";
	}

	// 按农作物id和现在的生长阶段获取农作物当前阶段图片的完整路径
	public String getNowStagePic( int stageNow) {
		return "assets/image/crops/crop" + cropId + "/" + stageNow + ".png";
	}

	public int getCropId() {
		return cropId;
	}

	public void setCropId(int cropId) {
		this.cropId = cropId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getStageCount() {
		return stageCount;
	}

	public void setStageCount(int stageCount) {
		this.stageCount = stageCount;
	}

	public int getUnitTime() {
		return unitTime;
	}

	public void setUnitTime(int unitTime) {
		this.unitTime = unitTime;
	}


	public double getSeedPrice() {
		return seedPrice;
	}

	public void setSeedPrice(double seedPrice) {
		this.seedPrice = seedPrice;
	}

	public int getBuyLevel() {
		return buyLevel;
	}

	public void setBuyLevel(int buyLevel) {
		this.buyLevel = buyLevel;
	}
	public int getCropNumber() {
		return cropNumber;
	}

	public void setCropNumber(int cropNumber) {
		this.cropNumber = cropNumber;
	}
}
