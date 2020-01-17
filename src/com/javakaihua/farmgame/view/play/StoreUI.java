package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.javakaihua.farmgame.model.Crop;
import com.javakaihua.farmgame.model.Prop;
import com.javakaihua.farmgame.model.User;

public class StoreUI extends Window{
	//商店界面

     private Texture texture;//纹理，用来处理图片
     private TextureRegion region;//切割纹理
     private TextureRegionDrawable windowdrable;//用来设置窗口背景图
     private TextButton seedButton;//导航栏种子类
     private TextButton propButton;//导航栏道具类
     private ImageButton leftButton;//向左换页符
     private ImageButton rightButton;//向右换页符
     private ImageButton closeButton;//关闭键
     private Label titleLabel;//商店名称标志
     private Table seedTable1;//商店第一页种子
     private Table seedTable2;//商店第二页种子
     private Table propTable;//道具页
     private SlotUI[] storeSeedTable;//各种种子
     private SlotUI[] storePropTable;//各种道具
     private float x,y;//记录商店窗口的长宽
    
	 public StoreUI(Array<Crop> cropArray, Array<Prop> propArray, User user , PackageUI bag, Stage stage, Skin skin) {
		super("",skin);
		
        seedTable1=new Table();
        seedTable2=new Table();
		propTable=new Table();
		
		 storeSeedTable=new SlotUI[cropArray.size];
		
	     for(int j=0;j<storeSeedTable.length;j++){//构建商店每种种子的slot并且将其窗口对象传入stage
	        	storeSeedTable[j]=new SlotUI(cropArray.get(j),user,bag,stage,skin,"storeSeed");
	        	stage.addActor(storeSeedTable[j].getInputWindow());
	        }
	     
	     storePropTable=new SlotUI[propArray.size];
	     for(int i=0;i<propArray.size;i++){//构建商店每种道具的slot并且将其窗口对象传入stage
	        	storePropTable[i]=new SlotUI(propArray.get(i),user,bag,stage,skin,"storeProp",true);
	        	stage.addActor(storePropTable[i].getInputWindow());
	        }
	     
	     for(int i=0;i<storeSeedTable.length;i++){//实现各种种子slot在商店每一页上的排列
	        	if(i<=7) {
	        	seedTable1.add(storeSeedTable[i]).padLeft(40).height(245);
	        	if(i==3)
	        	seedTable1.row();
	        	}
	        	if(i>7) {
	        	seedTable2.add(storeSeedTable[i]).padLeft(40).height(245);
	            	if(i==11)
	            seedTable2.row();
	        	}
	        }
	     for(int i=0;i<storePropTable.length;i++){//实现各种道具slot在商店每一页上的排列
	        
	        	propTable.add(storePropTable[i]).padLeft(40).height(245);
	   
	        
	        }
	   
		texture=new Texture(Gdx.files.internal("assets/image/background/store.png"));//传入图片，设置窗口背景
		region=new TextureRegion(texture,0,0,748,645);
		windowdrable=new TextureRegionDrawable(region);
		this.setBackground(windowdrable);
		

	    titleLabel=new Label("商店",skin,"title");//设置窗口标题
	    titleLabel.setScale(0.5f);
	    this.addActor(titleLabel);
	    titleLabel.setColor(Color.BLACK);
	 
		seedButton=new TextButton("种子",skin);
		seedButton.setTransform(true);
        seedButton.setScale(1);
        seedButton.addListener(new ClickListener(Buttons.LEFT){//设置监听，点击时跳转到种子页
       	 public void clicked(InputEvent event,float x, float y) {
       		 
       		propTable.setVisible(false);
       		seedTable2.setVisible(false);
     		seedTable1.setVisible(true);
    	   }
        });
        this.addActor(seedButton);
        
        propButton=new TextButton("道具",skin);
        propButton.setTransform(true);
        propButton.setScale(1);
        propButton.addListener(new ClickListener(Buttons.LEFT){//设置监听，点击时跳转到道具页
       	 public void clicked(InputEvent event,float x, float y) {
       		 
              propTable.setVisible(true);
              seedTable1.setVisible(false);
              seedTable2.setVisible(false);
    	   }
        });
		this.addActor(propButton);
		
		leftButton=new ImageButton(skin,"down");
		leftButton.addListener(new ClickListener(Buttons.LEFT){//跳转到左边一页
		public void clicked(InputEvent event,float x, float y) {
			if(seedTable2.isVisible())
				seedTable1.setVisible(true);
			    seedTable2.setVisible(false);
		 }
	    });
		this.addActor(leftButton);
		
		rightButton=new ImageButton(skin,"up");
		rightButton.addListener(new ClickListener(Buttons.LEFT){//跳转到右边一页
		public void clicked(InputEvent event,float x, float y) {
			if(seedTable1.isVisible())
				seedTable2.setVisible(true);
			    seedTable1.setVisible(false);
		 }
	    });
		this.addActor(rightButton);
		
		StoreUI thisstore=this;
        closeButton=new ImageButton(skin,"win2");
		closeButton.setTransform(true);
        closeButton.addListener(new ClickListener(Buttons.LEFT){//关闭窗口
       	 public void clicked(InputEvent event,float x, float y) {
       		 
       		 seedTable1.setVisible(true);
       		 seedTable2.setVisible(false);
       		 propTable.setVisible(false);
       		 thisstore.setVisible(false);
    	   }
        });
		this.addActor(closeButton);//把所有对象加入商店这个窗口，并设置位置
		
		this.addActor(seedTable1);
		this.addActor(seedTable2);
		this.addActor(propTable);
		seedTable1.setPosition(457, 375);
		seedTable2.setPosition(457, 375);
		propTable.setPosition(457, 497.5f);
        this.setModal(true);
        this.setMovable(true);
        this.setSize(748*1.3f, 645*1.3f);
        x=this.getWidth();
        y=this.getHeight();
        seedButton.setPosition(x/10*2.9f, y/10*7.5f);
        propButton.setPosition(x/10*5.95f, y/10*7.5f);
        closeButton.setPosition(x/10*8.8f, y/10*8.2f);
        leftButton.setPosition(x/10*1, y/10*4.3f);
        rightButton.setPosition(x/10*8.5f, y/10*4.3f);
        titleLabel.setPosition(x/2-titleLabel.getWidth()/2, y/10*8.8f);
        this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
        this.setVisible(false);
        seedTable1.setVisible(true);
        seedTable2.setVisible(false);
        propTable.setVisible(false);

	}



	public Table getseedTable1() {
		return seedTable1;
	}
	public Table getseedTable2() {
		return seedTable2;
	}
	public Table getpropTable() {
	    return propTable;
	}
	public SlotUI[] getStoreSeedTable() {
		return storeSeedTable;
	}
    public SlotUI[] getStorePropTable() {
    	return storePropTable;
    }
    
    
}
