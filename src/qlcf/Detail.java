
package qlcf;

public class Detail {
    private String user;
    private String name;
    
    public Detail(){
        user="User";
        name="Nguyễn Hoàng Việt";
    }
    
    public Detail(String us, String na){
        this.user=us;
        this.name=na;
    }

    public Detail(Detail detail){
        this.user=detail.user;
        this.name=detail.name;
    }
    
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
