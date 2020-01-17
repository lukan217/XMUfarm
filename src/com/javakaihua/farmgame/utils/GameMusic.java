package com.javakaihua.farmgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GameMusic { // 背景音乐
    private Music music;
    // 音效
    private Sound waterSound, harvestSound, removeSound,moveSound,seedSound;
    
    private Boolean isSoundPlaying;

    public GameMusic(Music music, Sound waterSound, Sound harvestSound, Sound removeSound,Sound moveSound){
        this.music = music;
        this.waterSound = waterSound;
        this.harvestSound = harvestSound;
        this.removeSound = removeSound;
        this.moveSound = moveSound;
    }
    public GameMusic(){
        music= Gdx.audio.newMusic(Gdx.files.internal("assets/music/1.mp3"));//背景音乐
        music.setLooping(true);
        isSoundPlaying=false;
        waterSound=Gdx.audio.newSound(Gdx.files.internal("assets/music/water.mp3"));//浇水音效
        harvestSound=Gdx.audio.newSound(Gdx.files.internal("assets/music/get.mp3"));//收获音效
        moveSound=Gdx.audio.newSound(Gdx.files.internal("assets/music/walk.mp3"));//人物走动音效
        removeSound=Gdx.audio.newSound(Gdx.files.internal("assets/music/remove.mp3"));//铲除音效
        seedSound=Gdx.audio.newSound(Gdx.files.internal("assets/music/dirt.mp3"));
    }
    //设置音乐
    public void setMusicVolume(float volume) {
        music.setVolume(volume);
    }
    public float getMusicVolume() {
        return music.getVolume();
    }
    public void setMusicPlaying() {
        music.play();
    }
    public void setMusicPause() {
        music.pause();
    }
    public void setMusicStop(){
        music.stop();
    }
    public boolean Musicplaying(){
        return music.isPlaying();
    }
    public void setMusicLooping(){
        music.setLooping(true);
    }

    //设置游戏音效
    public void setSoundVolume(long a,float volume) {
        waterSound.setVolume(a,volume);
        harvestSound.setVolume(a,volume);
        removeSound.setVolume(a,volume);
        moveSound.setVolume(a,volume);
        seedSound.setVolume(a, volume);
    }
    public void setwaterSoundPlaying(){
        waterSound.play();
    }
    public void setharvestSoundPlaying(){
        harvestSound.play();
    }
    public void setremoveSoundPlaying(){
        removeSound.play();
    }
    public void setSeedSoundPlaying(){seedSound.play();}

    public Boolean isSoundPlaying() {
    	return isSoundPlaying;
    }
    public void setIsSoundPlaying(Boolean isSoundPlaying) {
    	this.isSoundPlaying=isSoundPlaying;
    }
    public Sound getHarvestSound() {
    	return harvestSound;
    }
    public Sound getWaterSound() {
    	return waterSound;
    }
    public Sound getRemoveSound() {
    	return removeSound;
    }
}