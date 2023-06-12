DROP TABLE IF EXISTS lesson;

CREATE TABLE lesson (
   id   BIGINT NOT NULL AUTO_INCREMENT UNIQUE PRIMARY KEY,
   student_id  BIGINT NOT NULL,
   teacher_id BIGINT NOT NULL,
   description  VARCHAR(255),
   start_date  DATE,
   end_date  DATE,
   lesson_form VARCHAR(255),
   address VARCHAR(255),
   status VARCHAR(255) NOT NULL,
   event_id BIGINT
);