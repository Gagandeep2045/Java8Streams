1. distinct() in getDistinctDepartmentsSortByDeptId

// SQL Query -> select distinct deptid from employee order by deptid asc
		List<Integer> actualDeptIdList = empList.stream().map(emp -> emp.getDepartment().getDeptId()).distinct()
				.sorted().collect(Collectors.toList());
				
Stream<T> distinct();
Distinct uses the equals() method of the elements to decide whether two elements are equal or not.
			
2.Collectors.groupBy() in getDepartmensWithMaxAvgSalary

Map<Department, List<Employee>> m = empList.stream()
				.collect(Collectors.<Employee, Department>groupingBy(emp -> emp.getDepartment()));
getDepartmentAndLocationWithMinCumulativeSalary() -> Multiple columns in group by clause 
Mind equals and hashCode in DepartmentAndCity class as well				
				
3. reduce -> streamReduceExample() , getDepartmensWithMaxAvgSalary
The most common form of reduce() is:

T reduce(T identity(startingValue), BinaryOperator<T> accumulator)
where identity is the starting value and accumulator is the binary operation we repeated apply.

		List<Integer> numbers=Arrays.asList(23,42,33,2);
		Integer result=numbers.stream().reduce(10, (x,y)->(x+y)%2==0?x*y:(x+y)/2);
		System.out.println("Reduction Result"+result);

 a) (10,23) => (10+23)%2==0 ? 10*23 : (10+23)/2
 				false ? 10*23 : (10+23)/2
 				(10+23)/2
 				33/2
 				16
 b) (16,42) => (16+42)%2==0 ? 16*42 : (16+42)/2
 				true ? 16*42 : (16+42)/2
 				16*42
 				672
 c)(672,33) => (672+33)%2==0 ? 672*33 : (672+33)/2
 				false ? 672*33 : (672+33)/2
 				(672+33)/2
 				705/2
 				352
 d)(352,2) => (352+2) % 2 == 0 ? 352*2 : (352+2)/2
 			  true ? 352*2 : (352+2)/2
 			  352 * 2
 			  704
 
 4. Optional <T> max(Comparator<T> c) -> getDepartmensWithMaxAvgSalary
 
 Optional<Double> maxAverageSalaryOptional = avgSalaryMap.values().stream()
				.max((sal1, sal2) -> (int) (sal1 - sal2));
				
5. Peek vs forEach() -> increaseSalaryByFivePercentAndPrintInDescendingOrder

 Stream<T> peek(Consumer<? super T> action);
 void forEach(Consumer<? super T> action);
 
 Peek is an intermediate operation while forEach is terminal operation.
 Chaining possible for peek but not possible for forEach since its return type is void
 
 6. Optional<T> min(Comparator<? super T> comparator);
 			VS
  Optional<T> max(Comparator<? super T> comparator);
  			VS
  Stream<T> sorted(Comparator<? super T> comparator);
  
 6. Short Circuit Operations - Do later
 skip()
limit()
 https://www.logicbig.com/tutorials/core-java-tutorial/java-util-stream/short-circuiting.html
  