package com.javakaihua.farmgame.model;

public class Prop {
	//道具类，存储道具信息

	private int propId;// 道具编号
	private String name;// 道具名称
	private String pic;// 道具图片
	private double propPrice;// 道具购买单价
	private int buyLevel;// 道具购买等级

	public int getPropId() {
		return propId;
	}

	public void setPropId(int propId) {
		this.propId = propId;
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

	public double getPropPrice() {
		return propPrice;
	}

	public void setPropPrice(double propPrice) {
		this.propPrice = propPrice;
	}

	public int getBuyLevel() {
		return buyLevel;
	}

	public void setBuyLevel(int buyLevel) {
		this.buyLevel = buyLevel;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getPropNumber() {
		return propNumber;
	}

	public void setPropNumber(int propNumber) {
		this.propNumber = propNumber;
	}

	private String property;// 道具属性
	private int value;//道具属性值
	private int propNumber;//道具库存量



	public Prop(int propId, String name, String pic, double propPrice, int buyLevel,String property,int value,int propNumber) {
		this.propId = propId;
		this.name = name;
		this.pic = pic;
		this.propPrice = propPrice;
		this.buyLevel = buyLevel;
		this.property=property;
		this.value=value;
		this.propNumber=propNumber;
	}
     public Prop() {
    	 super();
     }



	
}
