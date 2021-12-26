package com.mastery.java.task.model.entities.employee;

import com.mastery.java.task.model.Gender;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class EnumTypeSql extends org.hibernate.type.EnumType {

  @Override
  public void nullSafeSet(
      PreparedStatement st, Object value, int index, SharedSessionContractImplementor session)
      throws HibernateException, SQLException {
    if (value == null) {
      st.setNull(index, Types.OTHER);
    } else {
      st.setObject(index, value.toString(), Types.OTHER);
    }
  }

  @Override
  public Object nullSafeGet(
      ResultSet resultSet, String[] names, SharedSessionContractImplementor session, Object owner)
      throws HibernateException, SQLException {
    String name = resultSet.getString(names[0]);
    Object result = null;
    if (!resultSet.wasNull()) {
      result = Enum.valueOf(Gender.class, name);
    }
    return result;
  }
}
