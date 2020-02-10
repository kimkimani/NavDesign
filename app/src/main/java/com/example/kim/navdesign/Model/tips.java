package com.example.kim.navdesign.Model;

public class tips {
    String time;
    String league ;
    String match ;
    String tip ;
    String odd ;
    String results ;


    public tips(String time , String league , String match , String tip , String odd , String results) {
        this.time=time;
        this.league=league;
        this.match=match;
        this.tip=tip;
        this.odd=odd;
        this.results=results;
    }
    public String getLeague() {
        return league;
    }

    public String getTime() {
        return time;
    }

    public String getMatch() {
        return match;
    }

    public String getTip() {
        return tip;
    }

    public String getOdd() {
        return odd;
    }

    public String getResults() {
        return results;
    }
}


