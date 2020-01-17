package com.javakaihua.farmgame.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.javakaihua.farmgame.main.FarmGame;
import com.javakaihua.farmgame.model.*;
import com.javakaihua.farmgame.utils.*;
import com.javakaihua.farmgame.view.play.*;

import java.sql.SQLException;

public class PlayScreen implements Screen {
    //游戏主界面

    public FarmGame game;
    private Timer worldTime;//计时器
    private Skin skin;

    //数据变量
    private User user;
    private Array<Land> landArray;
    private Array<LandView> landViewArray;
    private Array<Crop> cropArray;
    private Array<Fruit> fruitArray;
    private Array<Prop> propArray;

    //人物移动相关变量
    private Entity player;
    private Sprite currentPlayerSprite;
    private TextureRegion currentPlayerFrame;
    private Dialog bedMenu;
    private PlayerController controller;

    //舞台变量
    private Stage stage;//主舞台
    private Stage landStage;//土地舞台

    //界面UI
    private StatusUI statusUI;
    private ToolUI toolUI;
    private PackageUI bag;
    private StoreUI store;
    private Userwindow userwindow;
    private Changewindow changewindow;
    private Exitwindow exitwindow;
    public SettingUI settingUI;

    //鼠标变量
    private Pixmap pm;//鼠标初始文件
    public static int mouseStatus=-100;//鼠标状态

    //地图相关变量
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private MapObject object;
    private MapLoader loader;
    private int mapWidth;
    private int mapHeight;
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera cam;
    private Viewport gameView;

    boolean showSleep=false;
    public boolean isFirstTalk=true;


    public PlayScreen(FarmGame game,User user) {

        this.game = game;
        this.user=user;
        skin=game.manager.get("assets/skin/skin.json");


        //从数据库读取数据
        try {
            worldTime= DataManage.readTimerDataFromSQL(user.getUserID());
            cropArray=DataManage.readCropDataFromSQL(user.getUserID());
            landArray=DataManage.readLandDataFromSQL(user.getUserID());
            fruitArray=DataManage.readFruitDataFromSQL(user.getUserID());
            propArray=DataManage.readPropDataFromSQL(user.getUserID());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //初始化地图相关变量
        cam = new OrthographicCamera();
        map = new TmxMapLoader().load("assets/map/farm.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        renderer.setView(cam);
        loader = new MapLoader(this);
        gameView = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),cam);
        cam.setToOrtho(false, gameView.getWorldWidth(), gameView.getWorldHeight());
        cam.zoom = 0.8f;
        cam.update();
        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;
        shapeRenderer = new ShapeRenderer();

        //初始化人物移动变量
        player = new Entity();
        player.positionState= Entity.position.inside;
        player.startingPosition(loader.getPlayerSpawn().x,loader.getPlayerSpawn().y);
        currentPlayerSprite = player.getFrameSprite();
        controller = new PlayerController(this, player);

        //初始化鼠标图标
        pm= new Pixmap(Gdx.files.internal("assets/image/mouse/cursor.png"));
        Gdx.input.setCursorImage(pm, 0, 0);

        //初始化舞台变量
        stage=new Stage();
        landStage=new Stage(gameView);



        //加入监听
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(controller);
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(landStage);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //初始化各个窗口、控件，并设置位置
        statusUI=new StatusUI(this);
        toolUI=new ToolUI(skin);
        bag=new PackageUI(cropArray, propArray, fruitArray,user,stage,skin);
        bag.update();
        store=new StoreUI(cropArray,propArray,user,bag,stage,skin);
        userwindow=new Userwindow("",skin,this);
        exitwindow=new Exitwindow("",skin);
        changewindow=new Changewindow("",skin,this);
        settingUI=new SettingUI("", skin,game.gamemusic);
        settingUI.getSlider().setValue(game.gamemusic.getMusicVolume());
        settingUI.getCheckBox().setChecked(game.gamemusic.Musicplaying());
        settingUI.getSoundCheckBox().setChecked(game.gamemusic.isSoundPlaying());
        statusUI.setPosition(20, 700);
        settingUI.setPosition(500,200);
        landViewArray=new Array<LandView>();
        for(int i=0;i<landArray.size;i++){
            landViewArray.add(new LandView(this,landArray.get(i),320+(i/3)*200,200+(i%3)*100));
        }
        userwindow.setVisible(false);
        exitwindow.setVisible(false);
        changewindow.setVisible(false);
        settingUI.setVisible(false);

        //在舞台上加入各个窗口、控件
        stage.addActor(statusUI);
        stage.addActor(statusUI);
        stage.addActor(userwindow);
        stage.addActor(exitwindow);
        stage.addActor(changewindow);
        stage.addActor(settingUI);
        stage.addActor(toolUI);
        stage.addActor(store);
        stage.addActor(bag);

        //在土地舞台上加入土地
        for(int i=0;i<landViewArray.size;i++)
        {
            landStage.addActor(landViewArray.get(i));
        }

        //为各个控件加监听
        this.SetListener();

    }

    @Override
    public void show() {

    }



    @Override
    public void render(float delta) {
        //刷新界面
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (currentPlayerSprite.getX() + (cam.viewportWidth / 2 * cam.zoom) < mapWidth &&
                currentPlayerSprite.getX() - (cam.viewportWidth / 2 * cam.zoom) > 0)
            cam.position.x = player.getPlayerX();

        if (currentPlayerSprite.getY() + cam.viewportHeight / 2 * cam.zoom< mapHeight &&
                currentPlayerSprite.getY() - cam.viewportHeight / 2* cam.zoom > 0)
            cam.position.y = player.getPlayerY();

        if (!isCollision(player.getBoundingBox()))
            player.setCurrentToNext();

        if ((object = isTeleport(player.getBoundingBox())) != null) {
            if(object.getName().equals("farmhouse"))
            {
                for(int i=0;i<landViewArray.size;i++)
                    landViewArray.get(i).setVisible(false);
                toolUI.setVisible(false);

            }
            else if(object.getName().equals("farm")){
                for(int i=0;i<landViewArray.size;i++)
                    landViewArray.get(i).setVisible(true);
                toolUI.setVisible(true);
            }
            map.dispose();
            map = new TmxMapLoader().load("assets/map/"+object.getName()+".tmx");
            renderer.setMap(map);
            mapWidth = map.getProperties().get("width", Integer.class) * 32;
            mapHeight = map.getProperties().get("height", Integer.class) * 32;
            for (MapObject spawn : map.getLayers().get("Player_Spawn").getObjects()) {
                Rectangle rectangle = ((RectangleMapObject) spawn).getRectangle();
                if (spawn.getName().equals(object.getName())) {
                    player.setCurrentPosition(rectangle.x, rectangle.y);
                    cam.position.x =440;
                    cam.position.y = 360;
                }
            }
        }


        cam.update();
        player.update(delta);
        renderer.render();
        controller.update(delta);
        game.batch.setProjectionMatrix(cam.combined);
        currentPlayerFrame = player.getCurrentFrame();
        renderer.setView(cam);


        if(worldTime.getElapsedInHours()==7){

            if(user.isFaint())
            {
                int lost= MathUtils.random(0, (int)(user.getMoney()*0.2));
                int hpLost=MathUtils.random(0, (int)(user.getHp()*0.8));
                user.setMoney(user.getMoney()-lost);
                user.setHp(user.getHp()-hpLost);
                new Dialog("",skin,"dialog").text("很不幸，您昨晚晕倒损失了"+lost+"金币和"+hpLost+"体力").button("确认", true).show(stage);
                user.setSleep(false);
                user.setFaint(false);
                worldTime.setRealToTimerRatio(300);
                toolUI.setVisible(true);

            }
            else if(user.isSleep())
            {
                user.setSleep(false);
                user.setHp(100);
                player.setCurrentPosition(700, 600);
                worldTime.setRealToTimerRatio(300);
                new Dialog("",skin,"dialog").text("经过一晚上的休息，您的体力已经回满！").button("确认", true).show(stage);

            }
            showSleep=false;
            isFirstTalk=true;

        }

        //十一点的时候弹出一个对话框，选择是否回房间睡觉
        if(worldTime.getElapsedInHours()==23&&!user.isSleep()&&!showSleep){
            showSleep=true;
            worldTime.setPause(true);
            new Dialog("",skin,"dialog") {
                protected void result(Object object) {
                    if (object.equals(true)) {
                        user.setSleep(true);
                        goTOBed();
                        worldTime.setRealToTimerRatio(3600);
                        worldTime.setPause(false);
                    }
                    else {
                        worldTime.setPause(false);
                    }

                }
            }.text("夜深了。是否回房休息？").button("洗洗睡咯！", true).button("不！我还能肝！",false).show(stage);

        }

        //两点了还不睡觉
        if (worldTime.getElapsedInHours()==2&&!user.isSleep())
        {
            worldTime.setPause(true);
            new Dialog("",skin,"dialog"){
                protected void result(Object object) {
                    if (object.equals(true)) {
                        worldTime.setPause(false);
                    }
                    else {
                        worldTime.setPause(false);
                    }

                }
            }.text("由于过度劳累，您已晕倒！").button("确认", true).show(stage);
            user.setFaint(true);
            user.setSleep(true);
            player.faint();
            toolUI.setVisible(false);
            worldTime.setRealToTimerRatio(3600);
        }



        //根据时间变化整个地图的对比度
        shapeRenderer.setProjectionMatrix(cam.combined);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(statusUI.getAmbientLighting());
        Matrix4 mat = cam.combined.cpy();
        shapeRenderer.setProjectionMatrix(mat);
        mat.setToOrtho2D(0, 0, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        shapeRenderer.rect(cam.position.x - gameView.getWorldWidth()/2, cam.position.y - gameView.getWorldHeight()/2, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        shapeRenderer.end();

        //计时
        worldTime.tick();


        game.batch.begin();
        game.batch.draw(currentPlayerFrame, currentPlayerSprite.getX(), currentPlayerSprite.getY());
        game.batch.end();

        landStage.act();
        landStage.draw();
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        //退出游戏前保存游戏数据
        try {

            DataManage.saveTimerDataToSQL(worldTime,user.getUserID());
            DataManage.saveUserDataToSQL(user);
            DataManage.saveCropDataToSQL(cropArray, user.getUserID());
            DataManage.saveFruitDataToSQL(fruitArray, user.getUserID());
            DataManage.saveLandDataToSQL(landArray, user.getUserID());
            DataManage.savePropDataToSQL(propArray, user.getUserID());
            System.out.println("用户数据保存成功!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public PackageUI getBag() {
        return bag;
    }

    public Timer getWorldTime(){
        return worldTime;
    }

    public User getUser() {
        return user;
    }

    public Array<Crop> getCropArray() {
        return cropArray;
    }

    public Stage getStage() {
        return stage;
    }

    public Skin getSkin() {
        return skin;
    }

    public StatusUI getStatusUI() {
        return statusUI;
    }


    public TiledMap getMap() {
        return map;
    }

    public Array<Land> getLandArray() {
        return landArray;
    }

    public Array<LandView> getLandViewArray() {
        return landViewArray;
    }

    public Array<Fruit> getFruitArray() {
        return fruitArray;
    }

    public Array<Prop> getPropArray() {
        return propArray;
    }
    public int getMouseStatus() {
        return mouseStatus;
    }
    public Stage getLandStage() {
        return landStage;
    }

    public OrthographicCamera getCam() {
        return cam;
    }




    //地图传送
    public MapObject isTeleport(Rectangle boundingBox) {
        MapLayer objectLayer = map.getLayers().get("Teleport");
        for(MapObject object: objectLayer.getObjects()){
            if(object instanceof RectangleMapObject){
                Rectangle rectangle = ((RectangleMapObject) object). getRectangle();
                if(boundingBox.overlaps(rectangle))
                    return object;
            }
        }
        return null;
    }

    public void goTOBed(){
        for(int i=0;i<landViewArray.size;i++)
            landViewArray.get(i).setVisible(false);
        toolUI.setVisible(false);

        player.sleep();
        map.dispose();
        map = new TmxMapLoader().load("assets/map/farmhouse.tmx");
        renderer.setMap(map);
        mapWidth = map.getProperties().get("width", Integer.class) * 32;
        mapHeight = map.getProperties().get("height", Integer.class) * 32;
        for (MapObject spawn : map.getLayers().get("Player_Spawn").getObjects()) {
            Rectangle rectangle = ((RectangleMapObject) spawn).getRectangle();
            if (spawn.getName().equals("farmhouse")) {
                player.setCurrentPosition(770, 590);
                cam.position.x =440;
                cam.position.y = 360;

            }
        }

    }

    //碰撞检测
    public boolean isCollision(Rectangle boundingBox){
        MapLayer objectLayer = map.getLayers().get("Collision");
        return checkCollision(boundingBox, objectLayer);
    }


    public boolean checkCollision(Rectangle boundingBox, MapLayer objectLayer) {
        for (MapObject object : objectLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                if (boundingBox.overlaps(rectangle))
                    return true;
            }
        }
        return false;
    }

    public boolean isShowSleep() {
        return showSleep;
    }

    public void setShowSleep(boolean showSleep) {
        this.showSleep = showSleep;
    }

    //监听函数，各个控件的监听都在这里加入
    private void SetListener() {
        statusUI.getHeadPicContainer().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                userwindow.setVisible(true);

            }
        });
        userwindow.Getchange().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                userwindow.setVisible(false);
                changewindow.setVisible(true);
            }
        });
        userwindow.Getexit().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                exitwindow.setVisible(true);
                exitwindow.toFront();
            }
        });
        exitwindow.Getno().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                exitwindow.setVisible(false);
            }
        });
        exitwindow.Getyes().addListener(new ClickListener() {//切换账号
            public void clicked(InputEvent event, float x, float y) {
                dispose();

                game.setScreen(game.loginScreen=new LoginScreen(game));


            }
        });
        changewindow.Getpasswordfield().setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                boolean isnull;
                boolean middle;
                boolean best;
                isnull=Check.checkPass(changewindow.Getpasswordfield().getText());
                middle=Check.checkPassmiddle(changewindow.Getpasswordfield().getText());
                best=Check.checkPassbest(changewindow.Getpasswordfield().getText());
                if(isnull==true){

                    changewindow.getPass2().setVisible(false);
                    changewindow.getPass3().setVisible(false);
                    changewindow.getPass4().setVisible(false);
                    changewindow.getPass1().setVisible(true);
                }

                else if(best==true){//强度为高
                    changewindow.getPass3().setVisible(false);
                    changewindow.getPass2().setVisible(false);
                    changewindow.getPass1().setVisible(false);
                    changewindow.getPass4().setVisible(true);
                }
                else if(middle==true){//强度为中
                    changewindow.getPass4().setVisible(false);
                    changewindow.getPass2().setVisible(false);
                    changewindow.getPass1().setVisible(false);
                    changewindow.getPass3().setVisible(true);
                }
                else{//强度为低
                    changewindow.getPass3().setVisible(false);
                    changewindow.getPass2().setVisible(false);
                    changewindow.getPass1().setVisible(false);
                    changewindow.getPass2().setVisible(true);
                }

            }
        });

        changewindow.Getyes().addListener(new ClickListener() {//切换账号
            public void clicked(InputEvent event, float x, float y) {
                if (Check.checkusername(changewindow.Getusernamefield().getText()))
                    new Dialog("", skin, "dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                changewindow.Getusernamefield().setText("");
                                changewindow.Getpasswordfield().setText("");
                                changewindow.Getverifypassfield().setText("");
                            }
                        }
                    }.text("请输入您想修改的昵称和密码").button("确认", true).show(stage);
                else if (!Check.checkVerifyPass(changewindow.Getpasswordfield().getText(),
                        changewindow.Getverifypassfield().getText())
                ) {//两次输入密码不一致
                    new Dialog("", skin, "dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                changewindow.Getpasswordfield().setText("");
                                changewindow.Getverifypassfield().setText("");
                            }

                        }
                    }.text("前后密码不一致，请重新输入！").button("确认", true).show(stage);
                }
                else if(!Check.checkPassworst(changewindow.Getpasswordfield().getText()))
                {
                    new Dialog("",skin,"dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                changewindow.Getpasswordfield().setText("");
                                changewindow.Getverifypassfield().setText("");
                            }
                        }
                    }.text("密码强度过低，请重新输入！").button("确认", true).show(stage);
                }
                else {//读入数据库

                    new Dialog("", skin, "dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                if (changewindow.getImageFlag() != 0)
                                    user.setPic("assets/image/icon/" + changewindow.getImageFlag() + ".png");
                                user.setPass(changewindow.Getpasswordfield().getText());
                                user.setUserName(changewindow.Getusernamefield().getText());
                                statusUI.update();
                                userwindow.update();
                                changewindow.initial();
                            }
                        }
                    }.text("信息修改成功！").button("确认", true).show(stage);
                    changewindow.setVisible(false);
                }
            }
        });

        changewindow.Getno().addListener(new ClickListener() {//切换账号
            public void clicked(InputEvent event, float x, float y) {
                changewindow.setVisible(false);
                userwindow.setVisible(true);
                changewindow.initial();
            }
        });
        changewindow.GetX().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                changewindow.setVisible(false);
                userwindow.setVisible(true);
                changewindow.initial();
            }
        });


        userwindow.GetX().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                userwindow.setVisible(false);
            }
        });
        statusUI.getExit().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                try {
                    DataManage.saveUserDataToSQL(user);//保存用户信息
                    new Dialog("", skin, "dialog") {
                        protected void result(Object object) {
                            if (object.equals(true)) {
                                dispose();
                                System.exit(-1);
                            }
                        }
                    }.text("是否确认退出").button("是", true).button("否", false).show(stage);
                } catch (SQLException e) {
                    e.printStackTrace();
                }


            }
        });
        statusUI.getMusic().addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                settingUI.setVisible(true);
            }
        });

        toolUI.getStoreButton().addListener(new ClickListener(Input.Buttons.LEFT){
            public void clicked(InputEvent event,float x, float y) {

                store.setVisible(true);
                Gdx.input.setCursorImage(pm, 0, 0);
                mouseStatus=-100;
            }
        });
        toolUI.getPackageButton().addListener(new ClickListener(Input.Buttons.LEFT){
            public void clicked(InputEvent event,float x, float y) {
                bag.setVisible(true);
                Gdx.input.setCursorImage(pm, 0, 0);
                mouseStatus=-100;
            }
        });
        stage.addListener(new ClickListener(Input.Buttons.RIGHT){
            public void clicked(InputEvent event,float x, float y) {
                Gdx.input.setCursorImage(pm, 0, 0);
                mouseStatus=-100;
            }
        });

    }

    }



