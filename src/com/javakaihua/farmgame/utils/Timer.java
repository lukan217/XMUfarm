package com.javakaihua.farmgame.utils;

import com.badlogic.gdx.Gdx;

public class Timer {
    //时间管理器

    // 定义时间流速
    public final static float REALTIME = 1;//真实时间1s
    public final static float DEMOTIME = 3600; // 测试时间1s=3600s
    public final static float GAMETIME = 300; // 游戏时间1s=600s

    //自从运行开始已过去的时间
    private double secondsSinceStart=28800;
    private float realToTimerRatio=Timer.GAMETIME; // 时间流速

    private int daysPassed=0;//已过去天数

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    private boolean pause;

    public Timer() {
        super();
    }

    // 计时器，放在render()
    public void tick() {
        if(!pause)
        {
            secondsSinceStart += (Gdx.graphics.getDeltaTime() * realToTimerRatio);
            if (secondsSinceStart >= 86400) {
                secondsSinceStart -= 86400;
                daysPassed++;
            }
        }

    }


    // 将时间以标准格式输出
    public String getFormattedTimeofDay() {

        int hours = (int) Math.floor(secondsSinceStart / 3600);
        String sub = " ";
        if (hours == 0) {
            hours = 12;
            sub = sub.concat("AM");
        } else if (hours >= 12) {
            if (hours > 12)
                hours -= 12;
            sub = sub.concat("PM");
        } else if ((hours < 12) && (hours > 0)) {
            sub = sub.concat("AM");
        }

        int minutes = (int) Math.floor((secondsSinceStart % 3600) / 60);

        String res = String.format("%1$d:%2$02d", hours, minutes);
        return res.concat(sub);
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }

    public float getTimeRatio() {
        return realToTimerRatio;
    }

    public double getSecondsSinceStart() {
        return secondsSinceStart;
    }

    public void setSecondsSinceStart(double secondsSinceStart) {
        this.secondsSinceStart = secondsSinceStart;
    }


    public float getRealToTimerRatio() {
        return realToTimerRatio;
    }

    public void setRealToTimerRatio(float realToTimerRatio) {
        this.realToTimerRatio = realToTimerRatio;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public void setTimeRatio(float ratio) {
        realToTimerRatio = ratio;
    }

    public double getTotalPassedSecond(){
        return daysPassed*86400+secondsSinceStart;
    }
    public int getElapsedInHours() {
        return (int)Math.floor(secondsSinceStart / 3600);
    }

}
