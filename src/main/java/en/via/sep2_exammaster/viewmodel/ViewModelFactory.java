package en.via.sep2_exammaster.viewmodel;

import en.via.sep2_exammaster.model.Model;

import java.rmi.RemoteException;

public class ViewModelFactory {
  private final LoginViewModel loginViewModel;

  public ViewModelFactory(Model model) {
    this.loginViewModel = new LoginViewModel(model);
  }

  public LoginViewModel getLoginViewModel() {
    return loginViewModel;
  }
}
