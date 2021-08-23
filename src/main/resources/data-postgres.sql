-- This script inserts data only when the table is empty
INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth)
SELECT x.first_name, x.last_name, x.department_id, x.job_title, x.gender, x.date_of_birth
FROM (
       SELECT
         'Иван'                      as first_name,
         'Петров'                    as last_name,
         1                           as department_id,
         'директор'                  as job_title,
         CAST('MALE' AS gender_type) as gender,
         CAST('1982-12-17' AS DATE)  as date_of_birth
       UNION
       SELECT
         'Ирина',
         'Ковалева',
         1,
         'заместитель директора',
         CAST('FEMALE' AS gender_type),
         CAST('1985-07-16' AS DATE)
       UNION
       SELECT
         'Петр',
         'Сидоров',
         2,
         'разработчик',
         CAST('MALE' AS gender_type),
         CAST('1990-03-10' AS DATE)
       UNION
       SELECT
         'Анна',
         'Иванова',
         3,
         'специалист',
         CAST('FEMALE' AS gender_type),
         CAST('1994-10-18' AS DATE)
     ) as x
WHERE
    (SELECT COUNT(*) FROM employee) <= 0;