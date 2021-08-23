-- This script inserts data only when the table is empty
INSERT INTO employee (first_name, last_name, department_id, job_title, gender, date_of_birth)
SELECT x.first_name, x.last_name, x.department_id, x.job_title, x.gender, x.date_of_birth
FROM (
       SELECT
         '����'                      as first_name,
         '������'                    as last_name,
         1                           as department_id,
         '��������'                  as job_title,
         CAST('MALE' AS gender_type) as gender,
         CAST('1982-12-17' AS DATE)  as date_of_birth
       UNION
       SELECT
         '�����',
         '��������',
         1,
         '����������� ���������',
         CAST('FEMALE' AS gender_type),
         CAST('1985-07-16' AS DATE)
       UNION
       SELECT
         '����',
         '�������',
         2,
         '�����������',
         CAST('MALE' AS gender_type),
         CAST('1990-03-10' AS DATE)
       UNION
       SELECT
         '����',
         '�������',
         3,
         '����������',
         CAST('FEMALE' AS gender_type),
         CAST('1994-10-18' AS DATE)
     ) as x
WHERE
    (SELECT COUNT(*) FROM employee) <= 0;