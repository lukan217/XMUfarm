package com.javakaihua.farmgame.view.play;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.javakaihua.farmgame.model.Crop;
import com.javakaihua.farmgame.model.Land;
import com.javakaihua.farmgame.view.PlayScreen;



public class LandView extends Actor{
    //土地可视化的类

    private Land land;//土地数据
    private PlayScreen playScreen;
    private Crop crop;//土地上的作物数据
    private Texture landTexture;//土地背景图
    private Texture cropTexture;//作物图，根据土地上种植的情况进行变化
    private double noWateredTime=0;

    public LandView(PlayScreen playScreen,Land land,float x,float y) {
        super();
        this.land=land;
        this.playScreen=playScreen;
        this.setPosition(x,y);
        landTexture=new Texture(Gdx.files.internal("assets/image/background/landbackground.png"));
        this.setSize(landTexture.getWidth(), landTexture.getHeight());//土地的大小设置为背景图大小
        loadLandData();//载入当前土地
        this.addListener(new ClickListener(){public void clicked(InputEvent event, float x, float y)
        {

                if(playScreen.getMouseStatus()==-100){

                }
                else if(playScreen.getMouseStatus()==-1)
                    shovel();
                else if(playScreen.getMouseStatus()==-2)
                    water();
                else if(playScreen.getMouseStatus()==-3)
                    pick();
                else if(playScreen.getMouseStatus()==-4)
                    fertilize();
                else
                    seed(playScreen.getMouseStatus());
            }
            });

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(landTexture!=null){//画出背景图
            if(land.isWatered())//如果浇水了，将画笔颜色改成深灰色以实现浇水效果
                batch.setColor(Color.GRAY);
            batch.draw(landTexture, getX(), getY());
            batch.setColor(Color.WHITE);
        }

        if(cropTexture!=null)//画出作物图
            batch.draw(cropTexture,getX(),getY());
}

    @Override
    public void act(float delta) {
        super.act(delta);
        if(land.getCropID()!=-1){
            if(playScreen.getWorldTime().getTotalPassedSecond()-land.getWaterTime()>86400) {
                land.setWatered(false);
            }
        }
        if(land.getCropID()!=-1&&!land.isPickable()&&!land.isPerished())//有种植、不枯萎、没成熟才能够生长
            grow();
    }

    public void pick(){//采摘函数
        if(land.getCropID()==-1)
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("该土地上没有种植作物哦！").button("确认", true).show(playScreen.getStage());
        }
        else if(!playScreen.getUser().isActable(3))
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("体力不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }
        else if(land.isPickable()) {

            playScreen.game.gamemusic.setharvestSoundPlaying();
            playScreen.getUser().setExp(playScreen.getUser().getExp()+5);
            playScreen.getUser().setHp(playScreen.getUser().getHp()-3);
            playScreen.getFruitArray().get(land.getCropID()).setFruitNumber(land.getFruitNum()+land.getFruitNum());
            new Dialog("",playScreen.getSkin(),"dialog").text("采摘成功，获得"+land.getFruitNum()+"个"+playScreen.getFruitArray().get(land.getCropID()).getName()+"!").button("确认", true).show(playScreen.getStage());
            land.setFruitNum(0);
            cropTexture=new Texture(crop.getCropEndPic());
            land.setPerished(true);
            land.setPickable(false);
            land.setFertilized(false);
            playScreen.getBag().update();

        }
        else if(land.isPerished())
            new Dialog("",playScreen.getSkin(),"dialog").text("作物已经死亡！").button("确认", true).show(playScreen.getStage());
        else
            new Dialog("",playScreen.getSkin(),"dialog").text("果实还未成熟！请耐心等待！").button("确认", true).show(playScreen.getStage());

    }

    public void shovel(){//铲除函数
        if(land.getCropID()==-1)
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("土地无需铲除！").button("确认", true).show(playScreen.getStage());
        }
        else if(!playScreen.getUser().isActable(4))
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("体力不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }

        else if(land.isPerished())
        {
            playScreen.game.gamemusic.setremoveSoundPlaying();
            playScreen.getUser().setExp(playScreen.getUser().getExp()+2);
            int random=MathUtils.random(1, 100);
            playScreen.getUser().setHp(playScreen.getUser().getHp()-4);
            if(random>80){
                Crop luckyCrop=playScreen.getCropArray().random();
                int luckyNumber=MathUtils.random(1, 5);
                playScreen.getCropArray().get(luckyCrop.getCropId()).setCropNumber(luckyCrop.getCropNumber()+luckyNumber);
                playScreen.getBag().update();
                new Dialog("",playScreen.getSkin(),"dialog").text("恭喜您，在翻土时意外获得"+luckyCrop.getName()+"x"+luckyNumber).button("确认", true).show(playScreen.getStage());
            }
            resetLand();
            cropTexture=null;
        }
        else if(land.isPickable()){
            new Dialog("",playScreen.getSkin(),"dialog") {
                protected void result(Object object) {
                    if (object.equals(true)) {
                        playScreen.getUser().setHp(playScreen.getUser().getHp()-4);
                        resetLand();
                        cropTexture=null;
                    }

                }
            }.text("作物已经成熟，确认铲除吗？").button("确认", true).button("取消",false).show(playScreen.getStage());

        }
        else{
            new Dialog("",playScreen.getSkin(),"dialog") {
                protected void result(Object object) {
                    if (object.equals(true)) {
                        playScreen.game.gamemusic.setremoveSoundPlaying();
                        playScreen.getUser().setHp(playScreen.getUser().getHp()-4);
                        resetLand();
                        cropTexture=null;
                    }

                }
            }.text("作物还在生长，确认铲除吗？").button("确认", true).button("取消",false).show(playScreen.getStage());

        }

    }


    public void water(){//浇水函数
        if(land.getCropID()==-1)
            new Dialog("",playScreen.getSkin(),"dialog").text("没有种植作物的土地不需要浇水哦").button("确认", true).show(playScreen.getStage());

        else if(land.isWatered())
            new Dialog("",playScreen.getSkin(),"dialog").text("这块土地已经浇过水了！").button("确认", true).show(playScreen.getStage());
        else if(land.isPerished())
            new Dialog("",playScreen.getSkin(),"dialog").text("作物已经死亡,不用浇水啦！").button("确认", true).show(playScreen.getStage());
        else if(land.isPickable())
            new Dialog("",playScreen.getSkin(),"dialog").text("作物已经成熟，不用浇水啦！").button("确认", true).show(playScreen.getStage());
        else if(!playScreen.getUser().isActable(1))
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("体力不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }
        else {
            playScreen.game.gamemusic.setwaterSoundPlaying();
            playScreen.getUser().setHp(playScreen.getUser().getHp()-1);
            playScreen.getUser().setExp(playScreen.getUser().getExp()+1);
            land.setWatered(true);
            land.setWaterTime(playScreen.getWorldTime().getTotalPassedSecond());
        }
    }

    public void fertilize(){//施肥函数
        if(land.getCropID()==-1)
            new Dialog("",playScreen.getSkin(),"dialog").text("没有种植作物的土地不需要施肥哦").button("确认", true).show(playScreen.getStage());

        else if(land.isFertilized())
            new Dialog("",playScreen.getSkin(),"dialog").text("这块土地已经施肥过了！").button("确认", true).show(playScreen.getStage());
        else if(land.isPerished())
            new Dialog("",playScreen.getSkin(),"dialog").text("作物已经死亡,不用施肥啦！").button("确认", true).show(playScreen.getStage());
        else if(land.isPickable())
            new Dialog("",playScreen.getSkin(),"dialog").text("作物已经成熟，不用施肥啦！").button("确认", true).show(playScreen.getStage());
        else if(!playScreen.getUser().isActable(2))
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("体力不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }
        else if(playScreen.getPropArray().get(3).getPropNumber()==0)
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("库存肥料不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }
        else {
            playScreen.game.gamemusic.setwaterSoundPlaying();
            new Dialog("",playScreen.getSkin(),"dialog").text("施肥成功！作物产量将翻倍！").button("确认", true).show(playScreen.getStage());
            playScreen.getPropArray().get(3).setPropNumber(playScreen.getPropArray().get(3).getPropNumber()-1);
            playScreen.getBag().update();
            playScreen.getUser().setHp(playScreen.getUser().getHp()-2);
            playScreen.getUser().setExp(playScreen.getUser().getExp()+3);
            land.setFertilized(true);

        }
    }

    public void seed(int cropID){//播种函数
        if(!land.isSeedable()){
            new Dialog("",playScreen.getSkin(),"dialog").text("这块土地已经播种过啦！").button("确认", true).show(playScreen.getStage());
        }
        else if(!playScreen.getUser().isActable(3))
        {
            new Dialog("",playScreen.getSkin(),"dialog").text("体力不足，无法进行操作！").button("确认", true).show(playScreen.getStage());

        }


        else {
            if(playScreen.getCropArray().get(cropID).getCropNumber()>0)
            {
                playScreen.game.gamemusic.setSeedSoundPlaying();
                playScreen.getUser().setExp(playScreen.getUser().getExp()+4);
                playScreen.getUser().setHp(playScreen.getUser().getHp()-3);
                land.setCropID(cropID);
                land.setStartTime(playScreen.getWorldTime().getTotalPassedSecond());
                land.setWaterTime(land.getStartTime());
                crop=playScreen.getCropArray().get(cropID);
                crop.setCropNumber(crop.getCropNumber()-1);
                cropTexture=new Texture(Gdx.files.internal(crop.getNowStagePic(0)));
                land.setSeedable(false);
                playScreen.getBag().update();
            }
            else
                new Dialog("",playScreen.getSkin(),"dialog").text(playScreen.getCropArray().get(cropID).getName()+"库存不足啦!").button("确认", true).show(playScreen.getStage());
        }

    }

    public void loadLandData(){
        //加载土地数据
        if(land.getCropID()!=-1){
            this.crop=playScreen.getCropArray().get(land.getCropID());
            if(land.isPerished()){
                cropTexture=new Texture(Gdx.files.internal(crop.getCropEndPic()));
            }
            else
                cropTexture=new Texture(Gdx.files.internal(crop.getNowStagePic(land.getNowStage())));
        }
        else {
            cropTexture=null;
        }
    }

    public void resetLand(){
        //重置土地上的数据
        land.setNowStage(0);
        land.setWatered(false);
        land.setStartTime(0);
        land.setFruitNum(0);
        land.setCropID(-1);
        land.setPerished(false);
        land.setNoWaterCount(0);
        land.setPerished(false);
        land.setPickable(false);
        land.setSeedable(true);
        land.setGrowTime(0);
        noWateredTime=0;
        land.setWaterTime(0);

    }
    public void checkWater(){

    }

    public void grow(){

        if(land.isWatered())
            noWateredTime=0;
        else
            noWateredTime=playScreen.getWorldTime().getTotalPassedSecond()-land.getWaterTime()-86400;

        if(noWateredTime>=86400&&!land.isPerished()){
            //没浇水次数大于三作物死亡
            land.setPerished(true);
            cropTexture=new Texture(crop.getCropEndPic());
        }

        if(land.getNowStage()==crop.getStageCount())//生长阶段等于作物最大阶段则可采摘
        {
            land.setPickable(true);
            land.setFruitNum(MathUtils.random(10, 20));
            if(land.isFertilized())//施肥产量翻倍
            {
                land.setFruitNum(land.getFruitNum()*2);
            }
        }

        if(!land.isPickable()&&!land.isPerished()){//未成熟且未枯萎的情况下生长
            if(land.isWatered())//浇水了则增加生长时间
                land.setGrowTime(land.getGrowTime()+Gdx.graphics.getDeltaTime() * playScreen.getWorldTime().getTimeRatio());
            if(land.getGrowTime()>(land.getNowStage()+1)*crop.getUnitTime()*1800)
            {
                land.setNowStage(land.getNowStage()+1);
                cropTexture=new Texture(crop.getNowStagePic(land.getNowStage()));
            }

        }
    }

    public Crop getCrop() {
        return crop;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public Texture getCropTexture() {
        return cropTexture;
    }

    public void setCropTexture(Texture cropTexture) {
        this.cropTexture = cropTexture;
    }


}
