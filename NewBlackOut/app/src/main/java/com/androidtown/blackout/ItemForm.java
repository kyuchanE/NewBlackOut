package com.androidtown.blackout;

public class ItemForm {

    private String date,duration,name,number;//보안을 위해서 private로
    private int imageNumber;


    public ItemForm(String date, int imageNumber1, String name, String number, String duration){//new생성자를 통해서 생성자가 만들어진다.
        this.date = date;
        this.imageNumber = imageNumber1;
        this.name = name;
        this.number = number;
        this.duration = duration;

    }
    public String getDate(){//외부로 id값을 리턴해서 내보내준다.

        return date;
    }
    public int getImageNumber(){//외부로 이미지 값을 리턴해서 보내준다.

        return imageNumber;
    }
    public String getName(){

        return name;
    }
    public String getNumber(){

        return number;
    }
    public String getDuration(){

        return duration;
    }
    public void setDate(String date){//외부에서 받은 id를 내부로 넣어준다.

        this.date=date;
    }
    public void setImageNumber(int imageNumber1){//외부에서 받은 imagenumber를 내부로 넣어준다.

        this.imageNumber = imageNumber1;
    }
    public void setName(String name){
        this.name = name;

    }
    public void setNumber(String number){
        this.number = number;

    }
    public void setDuration(String duration){
        this.duration = duration;

    }
}
