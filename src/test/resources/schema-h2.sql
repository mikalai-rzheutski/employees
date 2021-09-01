CREATE
TYPE
gender_type
AS
ENUM
(
'MALE',
'FEMALE'
);
CREATE TABLE employee (
  id INT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100)  NOT NULL,
  department_id INT       NOT NULL,
  job_title VARCHAR(100)  NOT NULL,
  gender gender_type      NOT NULL,
  date_of_birth DATE      NOT NULL
                      );