package com.fy.TransferAnalysis.PlayerData;

/**
 * Created by fy on 17-9-4.
 */
public class PlayerData {
    private String playername;
    private String team;
    private String league;
    private String position;
    private long marketValue;

    public void setPlayername(String playername){
        this.playername = playername;
    }

    public String getPlayername(){
        return this.playername;
    }

    public void setTeam(String team){
        this.team = team;
    }

    public String getTeam(){
        return this.team;
    }

    public void setLeague(String league){
        this.league = league;
    }

    public String getLeague(){
        return this.league;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String getPosition(){
        return this.position;
    }

    public void setMarketValue(long value){
        this.marketValue = value;
    }

    public long getMarketValue(){
        return this.marketValue;
    }
}
