package com.example.givemefive.app;

/**
 * Created by ljj on 14-3-26.
 */
public class StateInfo {

    public StateInfo(){

    }

    private int timeId;
    public int getTimeId(){
        return timeId;
    }
    public void setTimeId(int ti){
        timeId = ti;
    }

    private int roomId;
    public int getRoomId(){
        return roomId;
    }
    public void setRoomId(int ri){
        roomId = ri;
    }

    private String timeName;
    public String getTimeName(){
        return timeName;
    }
    public void setTimeName(String tn){
        timeName = tn;
    }

    private int stateId;
    public int getState(){
        return stateId;
    }
    public void setStateId(int si){
        stateId = si;
    }

    private String stateName;
    public String getStateName(){
        return stateName;
    }
    public void setStateName(String sn){
        stateName = sn;
    }
}
