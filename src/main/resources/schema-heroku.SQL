DO
' BEGIN
CREATE TYPE gender_type AS ENUM (''MALE'', ''FEMALE'');
EXCEPTION
WHEN duplicate_object THEN null;
END ';
CREATE TABLE IF NOT EXISTS employee (
  id  serial  PRIMARY  KEY,
  first_name  VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  department_id INT NOT NULL,
  job_title VARCHAR(100) NOT NULL,
  gender gender_type NOT NULL,
  date_of_birth DATE NOT NULL );