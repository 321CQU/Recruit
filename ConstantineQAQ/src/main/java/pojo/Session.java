package pojo;

public class Session
{
    private int id;

    private int year;

    private boolean is_autumn;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setYear(int year){
        this.year = year;
    }
    public int getYear(){
        return this.year;
    }
    public void setIs_autumn(boolean is_autumn){
        this.is_autumn = is_autumn;
    }
    public boolean getIs_autumn(){
        return this.is_autumn;
    }
}

