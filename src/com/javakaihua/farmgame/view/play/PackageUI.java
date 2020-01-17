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
import com.javakaihua.farmgame.model.Fruit;
import com.javakaihua.farmgame.model.Prop;
import com.javakaihua.farmgame.model.User;


public class PackageUI extends Window{
	//背包界面

       private Texture texture;//纹理
       private TextureRegion region;//截取纹理一部分
       private TextureRegionDrawable windowdrable;//背包窗口背景
       private TextButton seedButton;
       private TextButton propButton;
       private TextButton fruitButton;
       private ImageButton leftButton;
       private ImageButton rightButton;
       private ImageButton closeButton;
       private Label titleLabel;
       private Table seedTable1;//背包种子第一页
       private Table seedTable2;//背包种子第二页
       private Table propTable;//背包道具页
       private Table fruitTable1;//背包果实第一页
       private Table fruitTable2;//背包果实第二页
       private SlotUI[] bagSeedTable;//各个种子
       private SlotUI[] bagPropTable;//各个道具
       private SlotUI[] bagFruitTable;//各个果实
       private Array<Crop> cropArray;
       private Array<Prop> propArray;
       private Array<Fruit> fruitArray;
	   private float x,y;
	   
		 public PackageUI(Array<Crop> cropArray, Array<Prop> propArray, Array<Fruit> fruitArray, User user, Stage stage, Skin skin) {
			super("", skin);
			
			PackageUI thispackage=this;
			this.cropArray=cropArray;
			this.propArray=propArray;
			this.fruitArray=fruitArray;
			
			texture=new Texture(Gdx.files.internal("assets/image/background/package.png"));
			region=new TextureRegion(texture,0,0,748,645);
			windowdrable=new TextureRegionDrawable(region);
			this.setBackground(windowdrable);
			
			titleLabel=new Label("背包",skin,"title");
		    this.addActor(titleLabel);
		    titleLabel.setColor(Color.BLACK);
		    
	        seedTable1=new Table();
	        seedTable2=new Table();
			propTable=new Table();
			fruitTable1=new Table();
			fruitTable2=new Table();
			
			bagSeedTable=new SlotUI[cropArray.size];
			bagFruitTable=new SlotUI[fruitArray.size];
			bagPropTable=new SlotUI[propArray.size];
			for(int i=0;i<cropArray.size;i++) {
	        	bagSeedTable[i]=new SlotUI(cropArray.get(i),user,thispackage,stage,skin,"bagSeed");
	        	
	        }
			for(int i=0;i<fruitArray.size;i++) {
	        	bagFruitTable[i]=new SlotUI(fruitArray.get(i),user,thispackage,stage,skin);
	        	stage.addActor(bagFruitTable[i].getInputWindow());
	        }
	     
	        for(int i=0;i<3;i++) {
	        	bagPropTable[i]=new SlotUI(propArray.get(i),user,thispackage,stage,skin,"bagProp",true);
	        	stage.addActor(bagPropTable[i].getInputWindow());
	        	
	        }
		    bagPropTable[3]=new SlotUI(propArray.get(3),user,thispackage,stage,skin,"bagProp",false);
		     
		        
		    seedButton=new TextButton("种子",skin);
	        seedButton.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		propTable.setVisible(false);
	       		fruitTable1.setVisible(false);
	       		fruitTable2.setVisible(false);
	     		seedTable1.setVisible(true);
	     		seedTable2.setVisible(false);
	    	   }
	        });
	        this.addActor(seedButton);
	        
	        fruitButton=new TextButton("果实",skin);
	        fruitButton.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		  fruitTable1.setVisible(true);
	              propTable.setVisible(false);
	              seedTable1.setVisible(false);
	              seedTable2.setVisible(false);
	              fruitTable2.setVisible(false);
	    	   }
	        });
			this.addActor(fruitButton);
			
	        propButton=new TextButton("道具",skin);
	        propButton.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	              propTable.setVisible(true);
	              seedTable1.setVisible(false);
	              seedTable2.setVisible(false);
	              fruitTable1.setVisible(false);
	              fruitTable2.setVisible(false);
	    	   }
	        });
			this.addActor(propButton);
			
			leftButton=new ImageButton(skin,"down");
			leftButton.addListener(new ClickListener(Buttons.LEFT) {
			public void clicked(InputEvent event,float x, float y) {
				if(seedTable2.isVisible()) {
					seedTable1.setVisible(true);
				    seedTable2.setVisible(false);
				}
				else if(fruitTable2.isVisible()) {
					fruitTable1.setVisible(true);
					fruitTable2.setVisible(false);
				}
			 }
		    });
			this.addActor(leftButton);
			
			rightButton=new ImageButton(skin,"up");
			rightButton.addListener(new ClickListener(Buttons.LEFT) {
			public void clicked(InputEvent event,float x, float y) {
				if(seedTable1.isVisible()) {
					seedTable2.setVisible(true);
				    seedTable1.setVisible(false);
				}
				else if(fruitTable1.isVisible()) {
					fruitTable2.setVisible(true);
					fruitTable1.setVisible(false);
				}
			 }
		    });
			this.addActor(rightButton);
			
			;
	        closeButton=new ImageButton(skin,"win2");
	        closeButton.addListener(new ClickListener(Buttons.LEFT){
	       	 public void clicked(InputEvent event,float x, float y) {
	       		 
	       		 seedTable1.setVisible(true);
	       		 fruitTable1.setVisible(false);
	       		 fruitTable2.setVisible(false);
	       		 seedTable2.setVisible(false);
	       		 propTable.setVisible(false);
	       		 thispackage.setVisible(false);
	    	   }
	        });
			this.addActor(closeButton);
			
			this.addActor(seedTable1);
			this.addActor(seedTable2);
			this.addActor(propTable);
			this.addActor(fruitTable1);
			this.addActor(fruitTable2);
			seedTable1.setPosition(457, 375);
	        this.setModal(true);
	        this.setMovable(true);
	        this.setSize(748*1.3f, 645*1.3f);
	        x=this.getWidth();
	        y=this.getHeight();
	        seedButton.setPosition(x/10*2.9f, y/10*7.5f);
	        propButton.setPosition(x/10*5.95f, y/10*7.5f);
	        fruitButton.setPosition(x/10*4.4f, y/10*7.5f);
	        closeButton.setPosition(x/10*8.8f, y/10*8.2f);
	        leftButton.setPosition(x/10*1, y/10*4.3f);
	        rightButton.setPosition(x/10*8.5f, y/10*4.3f);
	        titleLabel.setPosition(x/2-titleLabel.getWidth()/2, y/10*8.8f);
	        this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
	        this.setVisible(false);
	        seedTable1.setVisible(true);
	        seedTable2.setVisible(false);
	        fruitTable1.setVisible(false);
	        fruitTable2.setVisible(false);
	        propTable.setVisible(false);

			 //TODO Auto-generated constructor stub
		}
		
		
		
		public void update() {
			
	        
		 
	        seedTable1.clear();
		    seedTable2.clear();
		    propTable.clear();
		    fruitTable1.clear();
		    fruitTable2.clear();
		 
		    int cropCount=0,fruitCount=0,propCount=0;
		    int i=0,j=0,k=0;
		    for(;i<cropArray.size;i++) {
		    	
		    	if(cropCount<=7&&cropArray.get(i).getCropNumber()>0) {
		    		seedTable1.add(bagSeedTable[i]).padLeft(40).height(245);
		    		cropCount++;
		    		if(cropCount==1)
		    		seedTable1.setPosition(206, 497.5f);
		    		else if(cropCount==2)
		    	    seedTable1.setPosition(289.5f, 497.5f);
		    		else if(cropCount==3)
		    	    seedTable1.setPosition(373, 497.5f);
		    		else if(cropCount==4) {
		    			seedTable1.setPosition(457, 497.5f);
			    		seedTable1.row();
		    		}
		    		else 
		    		seedTable1.setPosition(457, 375);
		    	}
		        else if(cropCount>7&&cropArray.get(i).getCropNumber()>0){
		        	seedTable2.add(bagSeedTable[i]).padLeft(40).height(245);
		        	cropCount++;
		    		if(cropCount==9)
		    		seedTable2.setPosition(206, 497.5f);
		    		else if(cropCount==10)
		    	    seedTable2.setPosition(289.5f, 497.5f);
		    		else if(cropCount==11)
		    	    seedTable2.setPosition(373, 497.5f);
		    		else if(cropCount==12) {
		    			seedTable2.setPosition(457, 497.5f);
			    		seedTable2.row();
		    		}
		    		else 
		    		seedTable2.setPosition(457, 375);
		    	}
		    		
		    }
		    
		    
            for(;j<fruitArray.size;j++) {
		    	
		    	if(fruitCount<=7&&fruitArray.get(j).getFruitNumber()>0) {
		    		fruitTable1.add(bagFruitTable[j]).padLeft(40).height(245);
		    		fruitCount++;
		    		if(fruitCount==1)
		    		fruitTable1.setPosition(206, 497.5f);
		    		else if(fruitCount==2)
		    	    fruitTable1.setPosition(289.5f, 497.5f);
		    		else if(fruitCount==3)
		    	    fruitTable1.setPosition(373, 497.5f);
		    		else if(fruitCount==4) {
		    			fruitTable1.setPosition(457, 497.5f);
			    		fruitTable1.row();
		    		}
		    		else 
		    		fruitTable1.setPosition(457, 375);
		    	}
		        else if(fruitCount>7&&fruitArray.get(j).getFruitNumber()>0){
		        	fruitTable2.add(bagFruitTable[j]).padLeft(40).height(245);
		        	fruitCount++;
		    		if(fruitCount==9)
		    		fruitTable2.setPosition(206, 497.5f);
		    		else if(fruitCount==10)
		    	    fruitTable2.setPosition(289.5f, 497.5f);
		    		else if(fruitCount==11)
		    	    fruitTable2.setPosition(373, 497.5f);
		    		else if(fruitCount==12) {
		    			fruitTable2.setPosition(457, 497.5f);
			    		fruitTable2.row();
		    		}
		    		else 
		    		fruitTable2.setPosition(457, 375);
		    	}
		    		
		    }
            
            for(;k<propArray.size;k++) {
		    	
		    	if(propArray.get(k).getPropNumber()>0) {
		    		propTable.add(bagPropTable[k]).padLeft(40).height(245);
		    		propCount++;
		    		if(propCount==1)
		    		propTable.setPosition(206, 497.5f);
		    		else if(propCount==2)
		    	    propTable.setPosition(289.5f, 497.5f);
		    		else if(propCount==3)
		    		propTable.setPosition(373, 497.5f);
		    		else if(propCount==4) {
		    			propTable.setPosition(457, 497.5f);
		    			propTable.row();
		            }
		        }
             }
         
		    
		}
		public SlotUI[] getBagSeedTable() {
			return bagSeedTable;
		}
		public SlotUI[] getBagFruitTable() {
			return bagFruitTable;
		}
		public SlotUI[] getBagPropTable() {
		    return bagPropTable;
		}
		
}
