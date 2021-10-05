package com.mastery.java.task.service;

import com.mastery.java.task.dal.EmployeeDao;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


public class JdbcEmployeeServiceTest extends AbstractEmployeeServiceTest {

	@Mock
	private EmployeeDao employeeDao;

	@Override
	@BeforeAll
	public void initMock() {
		MockitoAnnotations.openMocks(this);
		Mockito.when(employeeDao.read(0)).thenReturn(employee);
		Mockito.when(employeeDao.isEmployeeExistent(0)).thenReturn(true);
		employeeService = new JdbcEmployeeService(employeeDao);
	}

}
