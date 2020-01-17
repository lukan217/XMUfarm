package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.javakaihua.farmgame.model.Crop;
import com.javakaihua.farmgame.model.Fruit;
import com.javakaihua.farmgame.model.Prop;
import com.javakaihua.farmgame.model.User;

public class InputWindow extends Window{
	//输入窗，共三大种构造函数

	 private Texture texture;//纹理
     private TextButton confirmButton;//确认按钮
     private TextButton cancelButton;//取消按钮
     private ImageButton closeButton;//关闭按钮
     private TextField numberInputField;//输入框
     private Label fieldTypeLabel;//输入内容类型,例如是背包里的"使用数量"还是商店里的"购买数量"
     private Crop crop;
 	 private Fruit fruit;
 	 private Prop prop;
 	 private Label fruitNumberLabel;//果实库存量（如果构造的是果实）
 	 private Label propNumberLabel;//道具库存量
     private float x,y;
     
     public InputWindow(Crop crop, User user, PackageUI bag, Stage stage, Skin skin){//共三大类输入窗口的构造函数：种子、果实、道具
    	 
    	 super("", skin,"window1");
    	 
    	 this.crop=crop;
    	 texture=new Texture(Gdx.files.internal(crop.getPic()));//得到种子图片，赋值给纹理
    	 Image seedImage=new Image(texture);//纹理传入图片对象中
         seedImage.setSize(80, 80);
      
 	     Label seedNameLabel=new Label(crop.getName(),skin);
 	     Label seedPriceLabel=new Label("价格: "+crop.getSeedPrice(),skin);
 	     Label seedBuyLevelLabel=new Label("等级: "+crop.getBuyLevel(),skin);
    	 numberInputField=new TextField("",skin);
    	 numberInputField.setAlignment(Align.center);//输入框里的值居中
 		 numberInputField.setSize(120, 50);
 		 fieldTypeLabel=new Label("购买数量"+"(1-99)",skin);
 		 
 		 InputWindow thisInputWindow=this;//取此窗口
 		 confirmButton=new TextButton("确认",skin);
         confirmButton.addListener(new ClickListener(Buttons.LEFT){
        	 
        	 public void clicked(InputEvent event,float x, float y) {
  
        	    String regex="^[1-9]\\d*$";
        		if(!numberInputField.getText().matches(regex)) {
           		new Dialog("",skin,"dialog").text("听不懂您在说什么,再说一遍吧!").button("确认", false).show(stage);
           		numberInputField.setText("");
        		}
           		else if(numberInputField.getText().length()>2) {
           		new Dialog("",skin,"dialog").text("太多了,超出购买限制范围了!").button("确认", false).show(stage);
              		numberInputField.setText("");
           		}
           		else{
               		int number = Integer.parseInt(numberInputField.getText());
               		if(number*crop.getSeedPrice()>user.getMoney()) {
               			new Dialog("",skin,"dialog").text("不好意思,您的钱没有这么多!").button("确认", false).show(stage);
               			numberInputField.setText("");
               		}
               		else if(user.getLevel()<crop.getBuyLevel()) {
               			new Dialog("",skin,"dialog").text("不好意思,您的等级不够！").button("确认", false).show(stage);
               			numberInputField.setText("");
               		}
               		else {
               			new Dialog("",skin,"dialog"){
        					protected void result(Object object) {
        						if (object.equals(true)) {
        								
        							thisInputWindow.setVisible(false);;//取此窗口
        						}
        					}
        				}.text("成功购买"+crop.getName()+"x"+numberInputField.getText()).button("确认", true).show(stage);
               			numberInputField.setText("");
               			crop.setCropNumber(crop.getCropNumber()+number);
               			user.setMoney(user.getMoney()-number*crop.getSeedPrice());
               		}
           	    }
                 
   
           		bag.update();//时刻更新背包中的物品

     	     }
         });
         
 		 cancelButton=new TextButton("取消",skin);
         cancelButton.addListener(new ClickListener(Buttons.LEFT){//取消操作，关闭本窗口
        	 
        	 public void clicked(InputEvent event,float x, float y) {
        		
        		thisInputWindow.setVisible(false);
        		numberInputField.setText("");
     	     }
         });

         
         closeButton=new ImageButton(skin,"win1");//关闭本窗口
 		 closeButton.setTransform(true);
 		 closeButton.setScale(0.7f);
         closeButton.addListener(new ClickListener(Buttons.LEFT){
        	 public void clicked(InputEvent event,float x, float y) {
        
        		 thisInputWindow.setVisible(false);
        		 numberInputField.setText("");
     	     }
         });
 		this.addActor(seedNameLabel);
 		this.addActor(seedPriceLabel);
 		this.addActor(seedBuyLevelLabel);
 		this.addActor(seedImage);
 		this.addActor(fieldTypeLabel);
		this.addActor(confirmButton);
		this.addActor(cancelButton);
		this.addActor(closeButton);
		this.addActor(numberInputField);
		
        this.setModal(true);//悬浮在最上层
        this.setMovable(true);//窗口可移动
        this.setWidth(Gdx.graphics.getWidth()/3.5f);//设置窗口大小
        this.setHeight(Gdx.graphics.getHeight()/2.5f);
        x=this.getWidth();
        y=this.getHeight();
        
        seedNameLabel.setPosition(x/2-seedNameLabel.getWidth()/2, y/10*7);//设置窗口内图片、标签等位置
        seedImage.setPosition(x/5*1,y/10*4.3f);
        seedPriceLabel.setPosition(x/5*2.7f, y/10*5.5f);
        seedBuyLevelLabel.setPosition(x/5*2.7f, y/10*4.5f);
        fieldTypeLabel.setPosition(x/5*0.55f, y/10*2.7f);
		numberInputField.setPosition(x/5*2.7f, y/10*2.5f);
		closeButton.setPosition(x/10*8.5f, y/10*8.2f);
        confirmButton.setPosition(x/4*1.2f-confirmButton.getWidth()/2, 0);
        cancelButton.setPosition(x/4*2.8f-confirmButton.getWidth()/2, 0);
        this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
        this.setVisible(false);

     }
     
     
	 public InputWindow(Fruit fruit, User user, PackageUI bag, Stage stage, Skin skin) {
		
		 super("", skin,"window1");
   	     this.fruit=fruit;
   	     
		 texture=new Texture(Gdx.files.internal(fruit.getPic()));
	     Image fruitImage=new Image(texture);
	     fruitImage.setSize(80, 80);
 	     Label fruitNameLabel=new Label(fruit.getName(),skin);
 	     Label fruitPriceLabel=new Label("售价: "+fruit.getFruitPrice(),skin);
 	     fruitNumberLabel=new Label("库存: "+fruit.getFruitNumber(),skin);
   	     numberInputField=new TextField("",skin);
		 numberInputField.setSize(120, 50);
		 numberInputField.setAlignment(Align.center);
		 fieldTypeLabel=new Label("出售数量"+"(1-999)",skin);
		 
		 InputWindow thisInputWindow=this;
		 confirmButton=new TextButton("确认",skin);
         confirmButton.addListener(new ClickListener(Buttons.LEFT){
       	     public void clicked(InputEvent event,float x, float y) {

   			     String regex="^[1-9]\\d*$";
   			     if(!numberInputField.getText().matches(regex)) {
   			  
      			     new Dialog("",skin,"dialog").text("听不懂您在说什么，再说一遍吧!").button("确认", true).show(stage);
      			     numberInputField.setText("");
   			     }
      			 else if(numberInputField.getText().length()>3) {
      			     new Dialog("",skin,"dialog").text("太多了,超出出售限制范围了!").button("确认", true).show(stage);
         			 numberInputField.setText("");
      			 }
      			 else{
      			    	 
          			 int number = Integer.parseInt(numberInputField.getText());
          			 if(number>fruit.getFruitNumber()) {
          				 
          			    	  
          			     new Dialog("",skin,"dialog").text("不好意思,您没有这么多果实啊!").button("确认", true).show(stage);
          				 numberInputField.setText("");
          			 }
          			 else {
          			    	  
          			     new Dialog("",skin,"dialog"){
              			     protected void result(Object object) {
              			    	 
            					if (object.equals(true)) {
            								
            						thisInputWindow.setVisible(false);;//取此窗口
            					}
            				}
            			 }.text("出售成功!金币+"+Integer.parseInt(numberInputField.getText())*fruit.getFruitPrice()).button("确认", true).show(stage);
          				 numberInputField.setText("");
          				 fruit.setFruitNumber(fruit.getFruitNumber()-number);
          				 user.setMoney(user.getMoney()+number*fruit.getFruitPrice());
          			 }
      		      }

      		      bag.update();
    	   }
        });
        
		 cancelButton=new TextButton("取消",skin);
         cancelButton.addListener(new ClickListener(Buttons.LEFT){
       	 public void clicked(InputEvent event,float x, float y) {
       		
       		thisInputWindow.setVisible(false);
       		numberInputField.setText("");
    	   }
        });

        
         closeButton=new ImageButton(skin,"win1");
		 closeButton.setTransform(true);
		 closeButton.setScale(0.7f);
         closeButton.addListener(new ClickListener(Buttons.LEFT){
       	 public void clicked(InputEvent event,float x, float y) {
       		 
       		thisInputWindow.setVisible(false);
       		numberInputField.setText("");
    	   }
        });
		 this.addActor(fruitNameLabel);
		 this.addActor(fruitPriceLabel);
		 this.addActor(fruitNumberLabel);
		 this.addActor(fruitImage);
		 this.addActor(fieldTypeLabel);
		 this.addActor(confirmButton);
		 this.addActor(cancelButton);
		 this.addActor(closeButton);
		 this.addActor(numberInputField);
         this.setModal(true);
         this.setMovable(true);
         this.setWidth(Gdx.graphics.getWidth()/3.5f);
         this.setHeight(Gdx.graphics.getHeight()/2.5f);
         x=this.getWidth();
         y=this.getHeight();
         fruitNameLabel.setPosition(x/2-fruitNameLabel.getWidth()/2, y/10*7);
         fruitImage.setPosition(x/5*1,y/10*4.3f);
         fruitPriceLabel.setPosition(x/5*2.7f, y/10*5.5f);
         fruitNumberLabel.setPosition(x/5*2.7f, y/10*4.5f);
         fieldTypeLabel.setPosition(x/5*0.55f, y/10*2.7f);
		 numberInputField.setPosition(x/5*2.7f, y/10*2.5f);
		 closeButton.setPosition(x/10*8.5f, y/10*8.2f);
         confirmButton.setPosition(x/4*1.2f-confirmButton.getWidth()/2, 0);
         cancelButton.setPosition(x/4*2.8f-confirmButton.getWidth()/2, 0);
         this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
         this.setVisible(false);


	}
	 
	 
	 public InputWindow(String fieldType, Prop prop, User user, PackageUI bag, Stage stage, Skin skin) {
			
		 super("", skin,"window1");
   	     this.prop=prop;
   	     
		 texture=new Texture(Gdx.files.internal(prop.getPic()));
	     Image propImage=new Image(texture);
	     propImage.setSize(80, 80);
   	     numberInputField=new TextField("",skin);
		 numberInputField.setSize(120, 50);
		 numberInputField.setAlignment(Align.center);
		 fieldTypeLabel=new Label(fieldType+"(1-99)",skin);
		 confirmButton=new TextButton("确认",skin);
        
        
         InputWindow thisInputWindow=this;
		 cancelButton=new TextButton("取消",skin);
         cancelButton.addListener(new ClickListener(Buttons.LEFT){
       	 public void clicked(InputEvent event,float x, float y) {
       		
       		thisInputWindow.setVisible(false);
       		numberInputField.setText("");
    	   }
        });

        
         closeButton=new ImageButton(skin,"win1");
		 closeButton.setTransform(true);
		 closeButton.setScale(0.7f);
         closeButton.addListener(new ClickListener(Buttons.LEFT){
       	 public void clicked(InputEvent event,float x, float y) {
       		 
       		thisInputWindow.setVisible(false);
       		numberInputField.setText("");
    	   }
        });

		 this.addActor(propImage);
		 this.addActor(fieldTypeLabel);
		 this.addActor(confirmButton);
		 this.addActor(cancelButton);
		 this.addActor(closeButton);
		 this.addActor(numberInputField);
         this.setModal(true);
         this.setMovable(true);
         this.setWidth(Gdx.graphics.getWidth()/3.5f);
         this.setHeight(Gdx.graphics.getHeight()/2.5f);
         x=this.getWidth();
         y=this.getHeight();
         propImage.setPosition(x/5*1,y/10*4.3f);
         fieldTypeLabel.setPosition(x/5*0.55f, y/10*2.7f);
		 numberInputField.setPosition(x/5*2.7f, y/10*2.5f);
		 closeButton.setPosition(x/10*8.5f, y/10*8.2f);
         confirmButton.setPosition(x/4*1.2f-confirmButton.getWidth()/2, 0);
         cancelButton.setPosition(x/4*2.8f-confirmButton.getWidth()/2, 0);
         this.setPosition(Gdx.graphics.getWidth()/2-this.getWidth()/2, Gdx.graphics.getHeight()/2-this.getHeight()/2);
         this.setVisible(false);
         if(fieldType=="购买数量") {
        	 
            Label propNameLabel=new Label(prop.getName(),skin);
     	    Label propPriceLabel=new Label("价格: "+prop.getPropPrice(),skin);
     	    Label propBuyLevelLabel=new Label("等级: "+prop.getBuyLevel(),skin);
        	 
    		 this.addActor(propNameLabel);
    		 this.addActor(propPriceLabel);
    		 this.addActor(propBuyLevelLabel);
    		 
    		 propNameLabel.setPosition(x/2-propNameLabel.getWidth()/2, y/10*7);
    		 propPriceLabel.setPosition(x/5*2.7f, y/10*5.5f);
    		 propBuyLevelLabel.setPosition(x/5*2.7f, y/10*4.5f);
    		 
    		 confirmButton.addListener(new ClickListener(Buttons.LEFT){
    	       	 public void clicked(InputEvent event,float x, float y) {

    	   			 String regex="^[1-9]\\d*$";
    	   			 if(!numberInputField.getText().matches(regex)) {
    	      			 new Dialog("",skin,"dialog").text("听不懂您在说什么,再说一遍吧!").button("确认", true).show(stage);
    	      			 numberInputField.setText("");
    	   			 }
    	      		 else if(numberInputField.getText().length()>3) {
    	      			 new Dialog("",skin,"dialog").text("太多了,超出购买限制范围了!").button("确认", true).show(stage);
    	         		 numberInputField.setText("");
    	      		 }
    	      		 else{
    	          		 int number = Integer.parseInt(numberInputField.getText());
    	          		 if(number*prop.getPropPrice()>user.getMoney()) {
    	          			 
    	          			 new Dialog("",skin,"dialog").text("不好意思,您的钱没有这么多！!").button("确认", true).show(stage);
    	          			 numberInputField.setText("");
    	          		 }
    	          		else if(user.getLevel()<prop.getBuyLevel()) {
                   			new Dialog("",skin,"dialog").text("不好意思,您的等级不够！").button("确认", false).show(stage);
                   			numberInputField.setText("");
                   		}
    	          		else {
    	          			 new Dialog("",skin,"dialog"){
                  			     protected void result(Object object) {
                  			    	 
                 					if (object.equals(true)) {
                 								
                 						thisInputWindow.setVisible(false);;//取此窗口
                 					}
                 				}
                 			 }.text("成功购买"+prop.getName()+"x"+numberInputField.getText()).button("确认", true).show(stage);
    	          		     numberInputField.setText("");
    	          			 prop.setPropNumber(prop.getPropNumber()+number);
    	          			 user.setMoney(user.getMoney()-number*prop.getPropPrice());
    	          		 }
    	      		 }
    	         
    	      		
    	      		
    	      	     bag.update();

    	    	  }
    	       });
         }
         else if(fieldType=="使用数量") {
        	 
        	 Label propNameLabel=new Label(prop.getName(),skin);
        	 Label propPropertyLabel=new Label(prop.getProperty()+": "+prop.getValue(),skin);
        	 propNumberLabel=new Label("库存: "+prop.getPropNumber(),skin);
        	 
        	 this.addActor(propNameLabel);
    		 this.addActor(propPropertyLabel);
    		 this.addActor(propNumberLabel);
        	 
    		 propNameLabel.setPosition(x/2-propNameLabel.getWidth()/2, y/10*7);
    		 propPropertyLabel.setPosition(x/5*2.7f, y/10*5.5f);
    		 propNumberLabel.setPosition(x/5*2.7f, y/10*4.5f);
    		 confirmButton.addListener(new ClickListener(Buttons.LEFT){
    	       	 public void clicked(InputEvent event,float x, float y) {
    	   			 String regex="^[1-9]\\d*$";
    	   			 if(!numberInputField.getText().matches(regex)) {
    	      			 new Dialog("",skin,"dialog").text("听不懂您在说什么,再说一遍吧!").button("确认", false).show(stage);
    	      			 numberInputField.setText("");
    	   			 }
    	      		 else if(numberInputField.getText().length()>3) {
    	      			 new Dialog("",skin,"dialog").text("太多了,超出使用限制范围了!").button("确认", false).show(stage);
    	         	     numberInputField.setText("");
    	      		 }
    	      		 else{
    	          		 int number = Integer.parseInt(numberInputField.getText());
    	          		 if(number>prop.getPropNumber()) {
    	          			 new Dialog("",skin,"dialog").text("不好意思,您没有这么多食物哦!").button("确认", false).show(stage);
    	          			 numberInputField.setText("");
    	          		 }
    	          		 else {
    	          			 new Dialog("",skin,"dialog"){
                  			     protected void result(Object object) {
                  			    	 
                 					if (object.equals(true)) {
                 								
                 						thisInputWindow.setVisible(false);
                 					}
                 				}
                 			 }.text("成功食用"+prop.getName()+"x"+numberInputField.getText()+"!"+"体力恢复"+prop.getValue()*Integer.parseInt(numberInputField.getText())).button("确认", true).show(stage);
    	          			 numberInputField.setText("");
    	          			 prop.setPropNumber(prop.getPropNumber()-number);
    	          			 if(user.getHp()+number*prop.getValue()<=100)
    	          				 user.setHp(user.getHp()+number*prop.getValue());
    	          			 else
    	          				 user.setHp(100);
    	          		 }
    	      		 }
    	         
    	      		
    	      		
    	      		 bag.update();

    	    	 }
    	     });
         
         }
	}
	 @Override
	    public void act(float delta) {
	    	super.act(delta);
	    	if(fruit!=null)
	    		fruitNumberLabel.setText("库存: "+fruit.getFruitNumber());
	    	else if(prop!=null&&propNumberLabel!=null)
	    		propNumberLabel.setText("库存: "+prop.getPropNumber());

	    		
		}
	 public TextButton getConfirmButton() {
		 return confirmButton;
	 }
	 public TextField getNumberInputField() {
		 return numberInputField;
	 }
}
