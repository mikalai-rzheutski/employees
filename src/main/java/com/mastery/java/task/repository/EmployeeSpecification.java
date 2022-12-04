package com.mastery.java.task.repository;

import com.mastery.java.task.model.entities.employee.Employee;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class EmployeeSpecification implements Specification<Employee> {

  private Map<String, String> searchParams;

  @Override
  public Predicate toPredicate(
          Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
    List<Predicate> predicates = new ArrayList();
    String nonStrictValue = searchParams.remove("nonStrict");
    boolean nonStrictFilter = nonStrictValue != null && nonStrictValue.toLowerCase().equals("yes");
    for (Map.Entry<String, String> searchParameter : searchParams.entrySet()) {
      if (searchParameter.getValue() == null) {
        continue;
      }
      String value = searchParameter.getValue().toLowerCase();
      Expression<String> exp = root.get(searchParameter.getKey()).as(String.class);
      if (nonStrictFilter) {
        predicates.add(cb.like(cb.lower(exp), "%" + value + "%"));
      } else {
        predicates.add(cb.equal(cb.lower(exp), value));
      }
    }
    criteriaQuery.orderBy(cb.asc(root.get("lastName")));
    return cb.and(predicates.toArray(new Predicate[0]));
  }
}
