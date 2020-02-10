package com.example.kim.navdesign.Model;

public class jacport {

    String no ;
    String time;

    String match ;
    String tip ;

    String results ;



    public jacport(String time  ,  String no , String match , String tip , String results) {
        this.time=time;
        this.no=no;
        this.match=match;
        this.tip=tip;

        this.results=results;
    }



    public String getno() {
        return no;
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


   public String getResults() {
        return results;
    }


}


