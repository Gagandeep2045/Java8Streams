package io.ey.java8.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity()
public class Department {

	@Id
	@Column(name = "deptid")
	int deptId;

	@Column(name = "departmentname")
	String departmentName;

	@OneToMany(mappedBy = "department", fetch = FetchType.EAGER)
	List<Employee> employees;

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override()
	public String toString() {
		return "deptid: " + deptId + " departmentName: " + departmentName + " employees: " + employees;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Department)) {
			return false;
		}
		Department d = (Department) obj;
		return d.deptId == this.deptId && d.departmentName.equals(this.departmentName);
	}

}
