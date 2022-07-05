package com.example.tabtest2;

public class Word {
    private String Eng;
    private String Kor;

    public Word(){}

    public Word(String e, String k){
        Eng = e;
        Kor = k;
    }

    public String getEng() {
        return Eng;
    }

    public String getKor() {
        return Kor;
    }

    public void setEng(String eng) {
        this.Eng = eng;
    }

    public void setKor(String kor) {
        this.Kor = kor;
    }

}
