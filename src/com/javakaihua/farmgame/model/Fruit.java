package com.javakaihua.farmgame.model;

public class Fruit {
	//果实类，存储果实信息
	private int fruitId;//农作物编号
	private String name;//农作物名字
	private String pic;//农作物图片
	private double fruitPrice;//农作物售价
	private int fruitNumber;// 农作物数量

	public Fruit() {
			super();
		}

		public Fruit(int fruitId, String name,String pic,double fruitPrice,int fruitNumber) {

			this.fruitId = fruitId;
			this.name = name;
			this.pic=pic;
			this.fruitPrice=fruitPrice;
			this.fruitNumber=fruitNumber;
		}

	

		public int getFruitId() {
			return fruitId;
		}

		public void setFruitId(int fruitId) {
			this.fruitId = fruitId;
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
		public double getFruitPrice() {
			return fruitPrice;
		}

		public void setFruitPrice(double fruitPrice) {
			this.fruitPrice = fruitPrice;
		}
		public int getFruitNumber() {
			return fruitNumber;
		}

		public void setFruitNumber(int fruitNumber) {
			this.fruitNumber = fruitNumber;
		}

}
