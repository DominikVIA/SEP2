package en.via.sep2_exammaster.shared;

import java.io.Serializable;

public abstract class User implements Serializable
{
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
    return "(" + name + ")";
  }

  public boolean equals(Object obj){
    if(obj == null || obj.getClass() != getClass()) return false;
    User temp = (User) obj;
    return temp.getPassword().equals(getPassword());
  }
}
