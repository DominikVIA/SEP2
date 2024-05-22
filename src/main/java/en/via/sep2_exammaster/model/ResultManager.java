package en.via.sep2_exammaster.model;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import en.via.sep2_exammaster.shared.*;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ResultManager extends UnicastRemoteObject implements ResultManagerInterface, RemotePropertyChangeListener<Serializable> {
  private User loggedIn;
  private final ServerConnector server;
  private final PropertyChangeSupport support;

  public ResultManager(ServerConnector server) throws IOException{
    this.server = server;
    this.support = new PropertyChangeSupport(this);
  }

  @Override
  public void setLoggedIn(User user){
    loggedIn = user;
  }

  @Override
  public List<Result> getResults() throws IOException
  {
    return server.getResultsByStudentId(((Student)loggedIn).getStudentNo());
  }

  @Override
  public Result getStudentExamResult(Exam exam, Student student)
      throws IOException
  {
    return server.getStudentExamResult(exam, student);
  }

  @Override
  public List<Result> getResultsByExam(Exam exam)
      throws IOException
  {
    return server.getResultsByExam(exam);
  }

  @Override
  public void viewResult(Result result) {
    support.firePropertyChange("view result", null, result);
  }

  @Override
  public void editResult(Student student, Exam exam, Grade grade, String feedback) throws IOException{
    server.editResult(loggedIn, student, exam, grade, feedback);
  }

  @Override public void addListener(PropertyChangeListener listener) {
    support.addPropertyChangeListener(listener);
  }

  @Override public void removeListener(PropertyChangeListener listener){
    support.removePropertyChangeListener(listener);
  }

  @Override public void propertyChange(
      RemotePropertyChangeEvent<Serializable> evt)
      throws RemoteException {
    if(evt.getOldValue().equals(loggedIn)) support.firePropertyChange(evt.getPropertyName(), null, evt.getNewValue());
  }
}
