package en.via.sep2_exammaster.shared;

public abstract class User {
  private String name, password;

  public User(String name, String password){
    this.name = name;
    this.password = password;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getPassword(){
    return password;
  }

  public void setPassword(String password){
    this.password = password;
  }

  public String toString(){
    return password + " (" + name + ")";
  }
}
