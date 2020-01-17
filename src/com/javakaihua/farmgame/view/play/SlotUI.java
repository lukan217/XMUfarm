package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.javakaihua.farmgame.model.Crop;
import com.javakaihua.farmgame.model.Fruit;
import com.javakaihua.farmgame.model.Prop;
import com.javakaihua.farmgame.model.User;
import com.javakaihua.farmgame.view.PlayScreen;

public class SlotUI extends Table{
	//背包，仓库上的小格子

	private Texture texture;//纹理，用于存放图片
	private float width,height;//用于存储记录table的长和宽
	private InputWindow inputWindow;//输入窗口
	private Crop crop=null;
	private Fruit fruit=null;
	private Prop prop=null;
	private Label seedNumberLabel;
	private Label fruitNumberLabel;
	private Label propNumberLabel;
	private Pixmap pm;//用来存储光标图片
    public SlotUI(Crop crop, User user, PackageUI bag, Stage stage, Skin skin, String type){//根据type来判断是仓库种子还是商店种子
    	super();
        
    	this.crop=crop;
        if(type=="storeSeed"){//给商店种子创建小table
        texture=new Texture(Gdx.files.internal(crop.getPic()));
        Image seedImage=new Image(texture);
        seedImage.setSize(80, 80);
     
	    Label seedNameLabel=new Label(crop.getName(),skin);
	    Label seedPriceLabel=new Label("价格: "+crop.getSeedPrice(),skin);
	    Label seedBuyLevelLabel=new Label("等级: "+crop.getBuyLevel(),skin);
	    
        inputWindow=new InputWindow(crop,user,bag,stage,skin);//输入窗口

	    this.add(seedNameLabel);
	    this.row();
	    this.add(seedImage).width(80).height(80).padTop(18);
	    this.row();
	    this.add(seedPriceLabel).padTop(15).width(127.5f);
	    this.row();
	    this.add(seedBuyLevelLabel).width(127.5f);
	    this.addListener(new ClickListener(Buttons.LEFT){//给仓库种子的slot设置监听，当点击时跳出输入窗口
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		inputWindow.setVisible(true);
	       		inputWindow.toFront();
	    	   }
	        });
        }
        else if(type=="bagSeed"){//给背包种子创建小table
        	texture=new Texture(Gdx.files.internal(crop.getPic()));
            Image seedImage=new Image(texture);
           
            seedImage.setSize(80, 80);
    	    Label seedNameLabel=new Label(crop.getName(),skin);
    	    Label seedGrowthCycleLabel=new Label("周期:"+crop.getStageCount()+"x"+crop.getUnitTime()+"时",skin);
    	    pm= new Pixmap(Gdx.files.internal("assets/image/mouse/seed/seed"+crop.getCropId()+".png"));
    	    seedNumberLabel=new Label("库存:"+crop.getCropNumber(),skin);
    	    this.addListener(new ClickListener(Buttons.LEFT){
    	    	
           	     public void clicked(InputEvent event,float x, float y) {
           		 Gdx.input.setCursorImage(pm, 0, 0);
           		 PlayScreen.mouseStatus=crop.getCropId();
           		 bag.setVisible(false);
           	     }
            });

    	    this.add(seedNameLabel);
    	    this.row();
    	    this.add(seedImage).width(80).height(80).padTop(18);
    	    this.row();
    	    this.add(seedGrowthCycleLabel).padTop(15).width(127.5f);
    	    this.row();
    	    this.add(seedNumberLabel).width(127.5f);
        }
     }
    
    
    public SlotUI(Fruit fruit, User user, PackageUI bag, Stage stage, Skin skin) {
    	super();
        
    	this.fruit=fruit;
        texture=new Texture(Gdx.files.internal(fruit.getPic()));
        Image fruitImage=new Image(texture);
        fruitImage.setSize(80, 80);
        
	    Label fruitNameLabel=new Label(fruit.getName(),skin);
	    Label fruitPriceLabel=new Label("售价: "+fruit.getFruitPrice(),skin);
	    fruitNumberLabel=new Label("库存: "+fruit.getFruitNumber(),skin);
	    
        inputWindow=new InputWindow(fruit,user,bag,stage,skin);

	    this.add(fruitNameLabel);
	    this.row();
	    this.add(fruitImage).width(80).height(80).padTop(18);
	    this.row();
	    this.add(fruitPriceLabel).padTop(15).width(127.5f);
	    this.row();
	    this.add(fruitNumberLabel).width(127.5f);
	    this.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		inputWindow.setVisible(true);
	       		inputWindow.toFront();
	    	   }
	        });
        }
    
    public SlotUI(Prop prop, User user, PackageUI bag, Stage stage, Skin skin, String type, Boolean createWindow){//Boolean createWindow判断道具是否需要创建输入
    	super();
        
    	this.prop=prop;
        if(type=="storeProp") {//store里的prop都需要创建输入窗
        	
        texture=new Texture(Gdx.files.internal(prop.getPic()));
        Image propImage=new Image(texture);
       
        propImage.setSize(80, 80);
	    Label propNameLabel=new Label(prop.getName(),skin);
	    Label propPriceLabel=new Label("价格: "+prop.getPropPrice(),skin);
	    Label propBuyLevelLabel=new Label("等级: "+prop.getBuyLevel(),skin);
	    
	 
        inputWindow=new InputWindow("购买数量",prop,user,bag,stage,skin);

	    this.add(propNameLabel);
	    this.row();
	    this.add(propImage).width(80).height(80).padTop(18);
	    this.row();
	    this.add(propPriceLabel).padTop(15).width(127.5f);
	    this.row();
	    this.add(propBuyLevelLabel).width(127.5f);
	    this.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		inputWindow.setVisible(true);
	       		inputWindow.toFront();
	    	   }
	        });
        }
        else if(type=="bagProp"){//背包里的道具部分需要创建输入窗
        	texture=new Texture(Gdx.files.internal(prop.getPic()));
            Image propImage=new Image(texture);
            propImage.setSize(80, 80);
             
     	    Label propNameLabel=new Label(prop.getName(),skin);
     	    Label propPropertyLabel=new Label(prop.getProperty()+":"+prop.getValue(),skin);
     	    propNumberLabel=new Label("库存:"+prop.getPropNumber(),skin);
     	    
     	    if(createWindow){//需要创建输入窗时，构建输入窗并且给table设置监听
                inputWindow=new InputWindow("使用数量",prop,user,bag,stage,skin);
     	        this.addListener(new ClickListener(Buttons.LEFT){
     	        	
   	       	        public void clicked(InputEvent event,float x, float y) {
 
   	       		        inputWindow.setVisible(true);
   	     	            inputWindow.toFront();
   	    	            }
   	                });
             }
     	    else{//不需要创建输入窗时，仅需给table设置监听，当点击时鼠标形态发生变化
     	    	pm= new Pixmap(Gdx.files.internal("assets/image/mouse/prop/prop"+prop.getPropId()+".png"));
     		    this.addListener(new ClickListener(Buttons.LEFT){    	
        	       	public void clicked(InputEvent event,float x, float y) {
        	       		 
        	       		Gdx.input.setCursorImage(pm, 0, 0);
                  		PlayScreen.mouseStatus=-4;
                  		bag.setVisible(false);
        	    	    }
        	        });
 	       	 }

     	    this.add(propNameLabel);
     	    this.row();
     	    this.add(propImage).width(80).height(80).padTop(18);
     	    this.row();
     	    this.add(propPropertyLabel).padTop(15).width(128);
     	    this.row();
     	    this.add(propNumberLabel).width(128);
        }
    }
    
    public InputWindow getInputWindow(){
    	return inputWindow;
    }
    
    @Override
    public void act(float delta){//时时更新slot中库存label值
    	super.act(delta);
    	if(crop!=null&&seedNumberLabel!=null)
    		seedNumberLabel.setText("库存: "+crop.getCropNumber());
    	else if(fruit!=null&&fruitNumberLabel!=null)
    		fruitNumberLabel.setText("库存: "+fruit.getFruitNumber());
    	else if(prop!=null&&propNumberLabel!=null)
    		propNumberLabel.setText("库存: "+prop.getPropNumber());
	}

}