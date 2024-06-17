import en.via.sep2_exammaster.model.Model;
import en.via.sep2_exammaster.shared.Course;
import en.via.sep2_exammaster.shared.Examiners;
import en.via.sep2_exammaster.shared.Student;
import en.via.sep2_exammaster.shared.Teacher;
import en.via.sep2_exammaster.viewmodel.teacher.CreateExamViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class CreateExamViewModelTest {
  private Model model;
  private CreateExamViewModel viewModel;
  private StringProperty room, title, content, time, student;
  private ObjectProperty<String> type, examiner;
  private ObjectProperty<LocalDate> date;
  private ObjectProperty<ObservableList<Student>> studentsList;

  @BeforeEach
  void setUp() throws IOException
  {
    model = Mockito.mock(Model.class);
    viewModel = new CreateExamViewModel(model);

    Teacher teacher = new Teacher("AA", "AA", "AA");
    Course course = new Course("AA", 2, "SS", "ABC", teacher);
    model.viewCourseRelated(course, "create exam");
    room = new SimpleStringProperty("");
    title = new SimpleStringProperty("");
    content = new SimpleStringProperty("");
    time = new SimpleStringProperty("");
    student = new SimpleStringProperty("");
    examiner = new SimpleObjectProperty<>("");
    type = new SimpleObjectProperty("");

    date = new SimpleObjectProperty<>();
    studentsList = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    viewModel.bindRoom(room);
    viewModel.bindTitle(title);
    viewModel.bindContent(content);
    viewModel.bindTime(time);
    viewModel.bindStudent(student);
    viewModel.bindExaminer(examiner);
    viewModel.bindType(type);
    viewModel.bindDate(date);
    viewModel.bindStudents(studentsList);
  }

  @Test
  void createExam_withMissingInformation_setsError() throws IOException {
    viewModel.onCreate();

    verify(model, never()).createExam(anyString(), anyString(), anyString(), any(Course.class), any(LocalDate.class),
        any(LocalTime.class), anyBoolean(), any(Examiners.class), anyList());
  }

  @Test
  void reset_clearsAllFields() {
    title.set("SDJ2");
    room.set("202");
    content.set("Organic Chemistry");
    time.set("10:00");
    student.set("2");
    studentsList.get().add(new Student(2, "Bob", "Smith"));
    viewModel.reset();

    assertEquals("", title.get());
    assertEquals("", room.get());
    assertEquals("", content.get());
    assertEquals("", time.get());
    assertEquals("", student.get());
    assertTrue(studentsList.get().isEmpty());
    assertEquals(LocalDate.now(), date.get());
  }
}