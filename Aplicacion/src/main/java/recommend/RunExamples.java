package recommend;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import es.model.employee.Employee;
import es.model.employeeservice.EmployeeService;

public class RunExamples {
	
    public static void main(String[] args) throws Exception {
    	
    	ApplicationContext context = 
    			new ClassPathXmlApplicationContext("spring-module.xml");
    	
    	EmployeeService employeeService =
    			(EmployeeService) context.getBean("employeeServiceBean");
    	
    	Employee e = employeeService.findEmployeeById(7369);
    	System.out.println("asdas");
    	System.out.println(e);
    
    }
}
