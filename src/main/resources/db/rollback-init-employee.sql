DELETE
FROM employee
WHERE (first_name = 'Иван' AND last_name = 'Петров')
   OR
  (first_name = 'Петр' AND last_name = 'Сидоров')
   OR
  (first_name = 'Анна' AND last_name = 'Ковалева')