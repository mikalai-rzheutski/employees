INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth)
VALUES
('Иван', 'Петров', 1, 'директор', CAST('MALE' AS gender_type), CAST('1982-12-17' AS DATE)),
('Петр', 'Сидоров', 2, 'главный инженер', CAST('MALE' AS gender_type), CAST('1980-10-01' AS DATE)),
('Анна', 'Ковалева', 2, 'главный бухгалтер', CAST('FEMALE' AS gender_type), CAST('1985-01-02' AS DATE))