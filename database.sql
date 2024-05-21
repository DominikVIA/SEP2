DROP SCHEMA IF EXISTS exam_master CASCADE;
CREATE SCHEMA exam_master;
SET SCHEMA 'exam_master';

CREATE TABLE students (
    student_id INT PRIMARY KEY,
    name VARCHAR(250),
    password VARCHAR(50)
);

CREATE TABLE teachers (
    teacher_id VARCHAR(4) PRIMARY KEY,
    name VARCHAR(250),
    password VARCHAR(50)
);

CREATE TABLE courses (
    code VARCHAR(12) PRIMARY KEY,
    semester INT,
    title VARCHAR(50),
    description VARCHAR(250)
);

CREATE TABLE course_students (
    course_code VARCHAR(12) REFERENCES courses(code) ON DELETE CASCADE,
    student INT REFERENCES students(student_id),
    PRIMARY KEY (course_code, student)
);

CREATE TABLE course_teachers (
    course_code VARCHAR(12) REFERENCES courses(code) ON DELETE CASCADE,
    teacher VARCHAR(4) REFERENCES teachers(teacher_id),
    PRIMARY KEY (course_code, teacher)
);

CREATE TABLE exams (
    id SERIAL PRIMARY KEY,
    title VARCHAR(150),
    content VARCHAR(300),
    room VARCHAR(7),
    examiners VARCHAR(8),
    date DATE,
    time TIME,
    course_code VARCHAR(12) REFERENCES courses(code) ON DELETE CASCADE,
    written BOOLEAN,
    completed BOOLEAN
);

CREATE TABLE results(
    student_id INT REFERENCES students(student_id),
    exam_id INT REFERENCES exams(id) ON DELETE CASCADE,
    grade VARCHAR(2),
    feedback VARCHAR(250),
    PRIMARY KEY (student_id, exam_id)
);

CREATE TABLE announcements(
    id SERIAL PRIMARY KEY,
    exam_id INT REFERENCES exams(id) ON DELETE CASCADE,
    title VARCHAR(150),
    content VARCHAR(10000),
    date DATE,
    time TIME
); 

INSERT INTO courses VALUES ('IT-SDJ2X-S24', 2, 'Software Development in Java', 'Making multi-user applications in Java.');
INSERT INTO courses VALUES ('IT-SEP2X-S24', 2, 'SemEster Project', 'Creating a project solving a problem chosen by the students.');
INSERT INTO courses VALUES ('IT-DBS1X-S24', 2, 'DataBase Systems', 'Creating, operating and designing databases in postgresSQL.');
INSERT INTO courses VALUES ('IT-SWE1X-S24', 2, 'SoftWare Engineering', 'Learning about theoretical principles behind effective programming and how to work in teams using SCRUM.');
INSERT INTO courses VALUES ('IT-SEP1X-A23', 1, 'SemEster Project', 'Creating a project solving a fictional problem provided by the professors.');
INSERT INTO courses VALUES ('IT-SDJ1X-A23', 1, 'Software Development in Java', 'Learning the basics of Java programming and how to create basic window applications.');
INSERT INTO courses VALUES ('IT-WEB1X-A23', 1, 'WEBsite design', 'Designing and programming functional websites using HTML, CSS and JavaScript.');
INSERT INTO courses VALUES ('IT-DMA1X-A23', 1, 'Dynamic Mathematical Algorithms', 'Learning basic mathematical principles behind modern software products.');

INSERT INTO students VALUES(345784, 'Maria Patricia Yepez Escudero', 'Yepez');
INSERT INTO students VALUES(343873, 'Sebastian Andres Villarroel Marin', 'Villarroel');
INSERT INTO students VALUES(344688, 'Joan Hageneier Caldentey', 'Hageneier');
INSERT INTO students VALUES(344353, 'Dominik Kielbowski', 'Kielbowski');

INSERT INTO teachers VALUES ('OIH', 'Ole Ildsgaard Hougaard', 'Hougaard');
INSERT INTO teachers VALUES ('LBS', 'Lars Bech Sorensen', 'Sorensen');
INSERT INTO teachers VALUES ('ALHE', 'Allan Henriksen', 'Henriksen');
INSERT INTO teachers VALUES ('SKLM', 'Soren Klit Lambaek', 'Lambaek');
INSERT INTO teachers VALUES ('MWA', 'Mona Wendel Andersen', 'Andersen');
INSERT INTO teachers VALUES ('RIB', 'Richard Brooks', 'Brooks');

INSERT INTO course_teachers VALUES ('IT-SDJ2X-S24', 'OIH');
INSERT INTO course_teachers VALUES ('IT-SWE1X-S24', 'LBS');
INSERT INTO course_teachers VALUES ('IT-SEP2X-S24', 'LBS');
INSERT INTO course_teachers VALUES ('IT-SEP2X-S24', 'OIH');
INSERT INTO course_teachers VALUES ('IT-DBS1X-S24', 'ALHE');
INSERT INTO course_teachers VALUES ('IT-SDJ1X-A23', 'ALHE');
INSERT INTO course_teachers VALUES ('IT-SEP1X-A23', 'ALHE');
INSERT INTO course_teachers VALUES ('IT-SEP1X-A23', 'MWA');
INSERT INTO course_teachers VALUES ('IT-WEB1X-A23', 'SKLM');
INSERT INTO course_teachers VALUES ('IT-DMA1X-A23', 'RIB');

-- adding maria --
INSERT INTO course_students VALUES ('IT-SDJ2X-S24', 345784);
INSERT INTO course_students VALUES ('IT-SEP2X-S24', 345784);
INSERT INTO course_students VALUES ('IT-DBS1X-S24', 345784);
INSERT INTO course_students VALUES ('IT-SWE1X-S24', 345784);
INSERT INTO course_students VALUES ('IT-SDJ1X-A23', 345784);
INSERT INTO course_students VALUES ('IT-SEP1X-A23', 345784);
INSERT INTO course_students VALUES ('IT-DMA1X-A23', 345784);
INSERT INTO course_students VALUES ('IT-WEB1X-A23', 345784);

-- adding seba --
INSERT INTO course_students VALUES ('IT-SDJ2X-S24', 343873);
INSERT INTO course_students VALUES ('IT-SEP2X-S24', 343873);
INSERT INTO course_students VALUES ('IT-SWE1X-S24', 343873);
INSERT INTO course_students VALUES ('IT-DBS1X-S24', 343873);
INSERT INTO course_students VALUES ('IT-SDJ1X-A23', 343873);
INSERT INTO course_students VALUES ('IT-SEP1X-A23', 343873);
INSERT INTO course_students VALUES ('IT-DMA1X-A23', 343873);
INSERT INTO course_students VALUES ('IT-WEB1X-A23', 343873);

-- adding joan --
INSERT INTO course_students VALUES ('IT-SDJ2X-S24', 344688);
INSERT INTO course_students VALUES ('IT-SEP2X-S24', 344688);
INSERT INTO course_students VALUES ('IT-SWE1X-S24', 344688);
INSERT INTO course_students VALUES ('IT-DBS1X-S24', 344688);
INSERT INTO course_students VALUES ('IT-SDJ1X-A23', 344688);
INSERT INTO course_students VALUES ('IT-SEP1X-A23', 344688);
INSERT INTO course_students VALUES ('IT-DMA1X-A23', 344688);
INSERT INTO course_students VALUES ('IT-WEB1X-A23', 344688);

-- adding myself --
INSERT INTO course_students VALUES ('IT-SDJ2X-S24', 344353);
INSERT INTO course_students VALUES ('IT-SEP2X-S24', 344353);
INSERT INTO course_students VALUES ('IT-SWE1X-S24', 344353);
INSERT INTO course_students VALUES ('IT-DBS1X-S24', 344353);
INSERT INTO course_students VALUES ('IT-SDJ1X-A23', 344353);
INSERT INTO course_students VALUES ('IT-SEP1X-A23', 344353);
INSERT INTO course_students VALUES ('IT-DMA1X-A23', 344353);
INSERT INTO course_students VALUES ('IT-WEB1X-A23', 344353);

INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ2 exam', 'Oral exam using IT-SDJ2X-S24 assignments', 'C05.15a',  'Both' ,'20-MAY-2024', '10:00', 'IT-SDJ2X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ2 exam', 'Oral exam using IT-SDJ2X-S24 assignments', 'C05.15a',  'Both' ,'20-MAY-2024', '10:30', 'IT-SDJ2X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ2 exam', 'Oral exam using IT-SDJ2X-S24 assignments', 'C05.15a',  'Both' ,'20-MAY-2024', '11:00', 'IT-SDJ2X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ2 exam', 'Oral exam using IT-SDJ2X-S24 assignments', 'C05.15a',  'Both' ,'20-MAY-2024', '11:30', 'IT-SDJ2X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SWE1 exam', 'Individual presentations about IT-SWE1X-S24 topics.', 'C05.15a',  'Internal' ,'19-MAY-2024', '12:00', 'IT-SWE1X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SWE1 exam', 'Individual presentations about IT-SWE1X-S24 topics.', 'C05.15a',  'Internal' ,'19-MAY-2024', '13:00', 'IT-SWE1X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SWE1 exam', 'Individual presentations about IT-SWE1X-S24 topics.', 'C05.15a',  'Internal' ,'19-MAY-2024', '14:00', 'IT-SWE1X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SWE1 exam', 'Individual presentations about IT-SWE1X-S24 topics.', 'C05.15a',  'Internal' ,'19-MAY-2024', '15:00', 'IT-SWE1X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SEP2 exam', 'Presentation made by entire sep group about the project.', 'C03.12', 'Internal', '21-MAY-2024', '11:40', 'IT-SEP2X-S24', false, false);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('DBS1 exam', 'Written exam about IT-DBS1X-S24 topics.', 'C01.01' , 'External', '22-MAY-2024', '9:00', 'IT-DBS1X-S24', true, false);

INSERT INTO results VALUES (345784, 1, null, null);
INSERT INTO results VALUES (345784, 5, null, null);
INSERT INTO results VALUES (345784, 9, null, null);
INSERT INTO results VALUES (345784, 10, null, null);
INSERT INTO results VALUES (343873, 2, null, null);
INSERT INTO results VALUES (343873, 6, null, null);
INSERT INTO results VALUES (343873, 9, null, null);
INSERT INTO results VALUES (343873, 10, null, null);
INSERT INTO results VALUES (344688, 3, null, null);
INSERT INTO results VALUES (344688, 7, null, null);
INSERT INTO results VALUES (344688, 9, null, null);
INSERT INTO results VALUES (344688, 10, null, null);
INSERT INTO results VALUES (344353, 4, null, null);
INSERT INTO results VALUES (344353, 8, null, null);
INSERT INTO results VALUES (344353, 9, null, null);
INSERT INTO results VALUES (344353, 10, null, null);

INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ1 exam', 'Oral exam about implementing a randomly selected class diagram.', 'C05.16',  'Both' ,'3-FEB-2024', '10:00', 'IT-SDJ1X-A23', false, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ1 exam', 'Oral exam about implementing a randomly selected class diagram.', 'C05.16',  'Both' ,'3-FEB-2024', '10:20', 'IT-SDJ1X-A23', false, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ1 exam', 'Oral exam about implementing a randomly selected class diagram.', 'C05.16',  'Both' ,'3-FEB-2024', '10:40', 'IT-SDJ1X-A23', false, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SDJ1 exam', 'Oral exam about implementing a randomly selected class diagram.', 'C05.16',  'Both' ,'3-FEB-2024', '11:00', 'IT-SDJ1X-A23', false, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('WEB1 exam', 'Written exam in which students answer HTML, CSS or JavaScript related questions.', 'C01.02',  'External' ,'7-FEB-2024', '12:00', 'IT-WEB1X-A23', true, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('SEP1 exam', 'Presentation made by entire sep group about their project.', 'C04.12', 'Internal', '10-FEB-2024', '11:00', 'IT-SEP1X-A23', false, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('DMA1 exam', 'Written exam about IT-DMA1X-A23 topics.', 'C02.01' , 'External', '12-FEB-2024', '8:30', 'IT-DMA1X-A23', true, true);
INSERT INTO exams(title, content, room, examiners, date, time, course_code, written, completed)
VALUES ('WEB1 re-exam 1', 'Written exam in which students answer HTML, CSS or JavaScript related questions.', 'C01.02',  'External' ,'1-MAR-2024', '12:00', 'IT-WEB1X-A23', true, true);

INSERT INTO results VALUES (345784, 11, 12, 'so chill during the exam');
INSERT INTO results VALUES (345784, 15, 7, 'made a great website');
INSERT INTO results VALUES (345784, 16, 12, 'group 7 on top');
INSERT INTO results VALUES (345784, 17, 10, 'can add 2+2');
INSERT INTO results VALUES (343873, 12, 12, 'played r6 instead of doing the exam but did it well');
INSERT INTO results VALUES (343873, 15, 7, 'made a cool website');
INSERT INTO results VALUES (343873, 16, 12, 'group 7 on top');
INSERT INTO results VALUES (343873, 17, 10, 'didnt come still did pretty ok');
INSERT INTO results VALUES (344688, 13, 12, 'designed a very nice gui for the class diagram');
INSERT INTO results VALUES (344688, 15, 7, 'made a fun website');
INSERT INTO results VALUES (344688, 16, 12, 'group 7 on top');
INSERT INTO results VALUES (344688, 17, 10, 'multiplied 2x2 impressively quick');
INSERT INTO results VALUES (344353, 14, 12, 'talked about personal stuff');
INSERT INTO results VALUES (344353, 15, -3, 'suckaaaa');
INSERT INTO results VALUES (344353, 16, 12, 'group 7 on top');
INSERT INTO results VALUES (344353, 17, 10, 'calculated a poop to butt ratio');
INSERT INTO results VALUES (344353, 18, 7, 'nice try');

SELECT * FROM exams;
