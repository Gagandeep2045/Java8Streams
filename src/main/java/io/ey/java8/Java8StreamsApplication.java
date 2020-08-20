package io.ey.java8;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;
import io.ey.java8.domain.Department;
import io.ey.java8.domain.Employee;
import io.ey.java8.repository.DepartmentRepository;
import io.ey.java8.repository.EmployeeRepository;
import static java.util.stream.Collectors.*;

@SpringBootApplication
public class Java8StreamsApplication {
	// https://stackify.com/streams-guide-java-8/
	public static void main(String[] args) {
		ApplicationContext ac = SpringApplication.run(Java8StreamsApplication.class, args);
		EmployeeRepository employeeRepository = ac.getBean(EmployeeRepository.class);
		DepartmentRepository documentRepository = ac.getBean(DepartmentRepository.class);
		List<Employee> empList = employeeRepository.findAll();
		System.out.println("empList: " + empList);
		System.out.println("--------------------------------");
		List<Department> deptList = documentRepository.findAll();
		System.out.println("deptList: " + deptList);
		getDistinctDepartmentsSortByDeptId(empList, deptList);
		getDepartmensWithMaxAvgSalary(empList, deptList);
		streamReduceExample();
		getDepartmentAndLocationWithMinCumulativeSalary(empList, deptList);
		getDepartmentAndLocationWithMinCumulativeSalary_OptimalSoln(empList, deptList);
		increaseSalaryByFivePercentAndPrintInDescendingOrder(empList);
		predictOutput();
	}

	private static void predictOutput() {
		String s = "hackerearth";
		s.chars().distinct().peek(ch -> System.out.println(ch)).sorted();
	}

	// No Output since stream is never executed as peek is intermediate
	// operation not terminal operation
	private static void increaseSalaryByFivePercentAndPrintInDescendingOrder(List<Employee> empList) {

		empList.stream().peek(emp -> {
			emp.setSalary((int) (emp.getSalary() * 105.0 / 100.0));
		}).sorted((e1, e2) -> e2.getSalary() - e1.getSalary()).forEach(emp -> {
			System.out.println("EmpId: " + emp.getEmpid() + " Salary: " + emp.getSalary());
		});
	}

	private static void getDepartmentAndLocationWithMinCumulativeSalary(List<Employee> empList,
			List<Department> deptList) {
		// SQL Query
		// select sum(salary),deptid,city from employee
		// group by deptid,city

		Map<DepartmentAndCity, List<Employee>> empMap = empList.stream()
				.collect(Collectors.<Employee, DepartmentAndCity>groupingBy(
						emp -> new DepartmentAndCity(emp.getDepartment(), emp.getCity())));
		Map<DepartmentAndCity, Integer> salaryMap = new HashMap<>();
		empMap.forEach((deptAndCity, employees) -> {
			int sumSalary = employees.stream().map(e -> e.getSalary()).reduce(0, (x, y) -> x + y);
			salaryMap.put(deptAndCity, sumSalary);
		});
		Optional<Integer> minimumCumulativeSalary = salaryMap.values().stream().min((a, b) -> a - b);
		int minimumSalarySumForDeptAndCity = minimumCumulativeSalary.get();
		List<Entry<DepartmentAndCity, Integer>> mimimumSlarySumDeptAndCity = salaryMap.entrySet().stream()
				.filter(entry -> entry.getValue() == minimumSalarySumForDeptAndCity).collect(Collectors.toList());
		System.out.println(mimimumSlarySumDeptAndCity);
	}
	
	/*The {@code mapping()} collectors are most useful when used in a
    * multi-level reduction, such as downstream of a {@code groupingBy} or
    * {@code partitioningBy}.  For example, given a stream of
    * {@code Person}, to accumulate the set of last names in each city:
    * <pre>{@code
    * Map<City, Set<String>> lastNamesByCity
    *   = people.stream().collect(
    *     groupingBy(Person::getCity,
    *                mapping(Person::getLastName,
    *                        toSet())));
    * }
    * 
     public static <T, K, A, D>
    Collector<T, ?, Map<K, D>> groupingBy(Function<? super T, ? extends K> classifier,
                                          Collector<? super T, A, D> downstream) {
        return groupingBy(classifier, HashMap::new, downstream);
    }
    
    * */
	//--------Usage of mapping() in groupingBy()
	private static void getDepartmentAndLocationWithMinCumulativeSalary_OptimalSoln(List<Employee> empList,
			List<Department> deptList) {

		Map<DepartmentAndCity, List<Integer>> salaryMap = empList.stream()
				.collect(groupingBy(emp -> new DepartmentAndCity(emp.getDepartment(), emp.getCity()),
						Collectors.mapping(emp -> emp.getSalary(), toList())));
		Map<DepartmentAndCity, Integer> totalSalaryMap = new HashMap<>();
		salaryMap.forEach((deptAndCity, listSalary) -> {
			int total = listSalary.stream().reduce(0, (x, y) -> x + y);
			totalSalaryMap.put(deptAndCity, total);
		});
		int mimimumSalary = totalSalaryMap.values().stream().min((x, y) -> x - y).get();
		List<Entry<DepartmentAndCity, Integer>> mimimumSlarySumDeptAndCity = totalSalaryMap.entrySet().stream()
				.filter(e -> e.getValue() == mimimumSalary).collect(Collectors.toList());
		System.out.println(mimimumSlarySumDeptAndCity);
	}

	static class DepartmentAndCity {
		DepartmentAndCity(Department dept, String city) {
			this.dept = dept;
			this.city = city;
		}

		Department dept;
		String city;

		@Override
		public boolean equals(Object obj) {
			DepartmentAndCity d = (DepartmentAndCity) obj;
			return d.city.equals(this.city) && d.dept.equals(this.dept);
		}

		@Override
		public int hashCode() {
			return dept.hashCode() + city.hashCode();
		}

		public String toString() {
			return "deptId: " + dept.getDeptId() + " city: " + city;
		}
	}

	private static void streamReduceExample() {
		// TODO Auto-generated method stub
		List<Integer> numbers = Arrays.asList(23, 42, 33, 2);
		Integer result = numbers.stream().reduce(10, (x, y) -> (x + y) % 2 == 0 ? x * y : (x + y) / 2);
		System.out.println("Reduction Result" + result);
	}

	private static void getDepartmensWithMaxAvgSalary(List<Employee> empList, List<Department> deptList) {
		// SQL Query->
		/*
		 * select deptid from employee group by deptid having avg(salary) = (
		 * select max(avg(salary)) from employee group by deptid );
		 */
		Map<Department, List<Employee>> m = empList.stream()
				.collect(Collectors.<Employee, Department>groupingBy(emp -> emp.getDepartment()));

		Map<Department, Double> avgSalaryMap = new HashMap<>();
		m.forEach((dept, employees) -> {
			double sumofSalaries = employees.stream().map(e -> e.getSalary() + 0.0).reduce(0.0, (a, b) -> a + b);
			avgSalaryMap.put(dept, sumofSalaries / employees.size());

		});

		Optional<Double> maxAverageSalaryOptional = avgSalaryMap.values().stream()
				.max((sal1, sal2) -> (int) (sal1 - sal2));
		final double maxAverageSalary = maxAverageSalaryOptional.get();
		avgSalaryMap.forEach((dept, avgSalary) -> {
			if (avgSalary == maxAverageSalary) {
				System.out.println("Dept: " + dept);
			}
		});
	}

	public static void getDistinctDepartmentsSortByDeptId(List<Employee> empList, List<Department> deptList) {
		// SQL Query->select deptid from department order by deptid asc
		List<Integer> expectedDeptIdList = deptList.stream().map(dept -> dept.getDeptId()).sorted()
				.collect(Collectors.toList());

		// SQL Query -> select distinct deptid from employee order by deptid asc
		List<Integer> actualDeptIdList = empList.stream().map(emp -> emp.getDepartment().getDeptId()).distinct()
				.sorted().collect(Collectors.toList());

		boolean isEqual = expectedDeptIdList.equals(actualDeptIdList);
		Assert.isTrue(isEqual);
	}

}
