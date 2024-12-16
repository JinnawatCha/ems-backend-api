package protoss.employeemanagementsystem.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import protoss.employeemanagementsystem.entity.Employee

@Repository
interface EmployeeRepository : JpaRepository<Employee, Int> {
    fun findByIdIs(id: Int): Employee?

    fun findByIdEquals(id: Int): List<Employee?>
    fun findByFirstNameContainsIgnoreCase(name: String): List<Employee?>
    fun findByLastNameContainsIgnoreCase(name: String): List<Employee?>
    fun findByEmailContainsIgnoreCase(email: String): List<Employee?>
    fun findByGenderEqualsIgnoreCase(gender: String): List<Employee?>
    fun findByPhoneContains(phone: String): List<Employee?>
    fun findByAddressContainsIgnoreCase(address: String): List<Employee?>
    fun findByDepartmentContainsIgnoreCase(department: String): List<Employee?>
    fun findByRoleContainsIgnoreCase(role: String): List<Employee?>
    fun findByHireDateIs(hireDate: String): List<Employee?>
    fun findBySalaryEquals(salary: Int): List<Employee?>

}