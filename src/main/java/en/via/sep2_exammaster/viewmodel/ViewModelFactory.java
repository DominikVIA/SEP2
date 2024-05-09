package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;

import java.rmi.RemoteException;

public class ViewModelFactory {
  private final LoginViewModel loginViewModel;
  private final MyCoursesViewModel myCoursesViewModel;

  public ViewModelFactory(Model model) {
    this.loginViewModel = new LoginViewModel(model);
    this.myCoursesViewModel = new MyCoursesViewModel(model);
  }

  public LoginViewModel getLoginViewModel() {
    return loginViewModel;
  }

  public MyCoursesViewModel getMyCoursesViewModel(){
    return myCoursesViewModel;
  }
}
