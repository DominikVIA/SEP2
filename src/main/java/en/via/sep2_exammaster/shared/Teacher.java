package en.via.sep2_exammaster.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class Teacher extends User
{
  private String initials;
  private ArrayList<Course> courses;

  public Teacher(String initials, String name, String password){
    super(name, password);
    this.initials = initials;
    this.courses = new ArrayList<>();
  }

  public String getInitials(){
    return initials;
  }

  public String toString(){
    return initials + " " + super.toString();
  }
}
