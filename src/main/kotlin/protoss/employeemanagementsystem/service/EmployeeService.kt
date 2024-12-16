package protoss.employeemanagementsystem.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import protoss.employeemanagementsystem.entity.DeleteEmployeeResponse
import protoss.employeemanagementsystem.entity.Employee
import protoss.employeemanagementsystem.repository.EmployeeRepository

@Service
class EmployeeService {

    private val logger: Logger = LoggerFactory.getLogger(EmployeeService::class.java)

    @Autowired
    lateinit var employeeRepository: EmployeeRepository




    fun createEmployee(employee: Employee): ResponseEntity<Employee> {
        val employee = Employee(
            firstName = employee.firstName,
            lastName = employee.lastName,
            gender = employee.gender,
            email = employee.email,
            phone = employee.phone,
            address = employee.address,
            department = employee.department,
            role = employee.role,
            hireDate = employee.hireDate,
            salary = employee.salary
        )
        employeeRepository.save(employee)
        return ResponseEntity.ok().body(employee)
    }

    fun getEmployeeDetail(id: Int): Employee? {
        return employeeRepository.findByIdIs(id)
    }

    fun updateDetail(id: Int, updateEmployee: Employee): ResponseEntity<Employee> {
        val employee = employeeRepository.findByIdIs(id)
        if (employee !== null) {
            val existingEmployee = employee.copy()

            existingEmployee.firstName = updateEmployee.firstName
            existingEmployee.lastName = updateEmployee.lastName
            existingEmployee.gender = updateEmployee.gender
            existingEmployee.email = updateEmployee.email
            existingEmployee.phone = updateEmployee.phone
            existingEmployee.address = updateEmployee.address
            existingEmployee.department = updateEmployee.department
            existingEmployee.role = updateEmployee.role
            existingEmployee.hireDate = updateEmployee.hireDate
            existingEmployee.salary = updateEmployee.salary

            val saveUpdate = employeeRepository.save(existingEmployee)
            return ResponseEntity.ok(saveUpdate)
        } else {
            return ResponseEntity.notFound().build()
        }
    }

    fun deleteEmployee(id: Int): ResponseEntity<DeleteEmployeeResponse> {
        return try {
            val employee = employeeRepository.findByIdIs(id)
            if (employee == null) {
                logger.warn("Employee number $id not found")
                return ResponseEntity.notFound().build()
            } else {
                employeeRepository.delete(employee)
                val status = !employeeRepository.existsById(employee.id)

                val response = DeleteEmployeeResponse(
                    status = status,
                    id = id
                )
                ResponseEntity.ok(response)
            }
        } catch (e: Exception) {
            logger.error(e.message)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    fun filterSearch(field: String, term: String): ResponseEntity<List<Employee?>> {
        return when (field) {
            "id" -> {
                val output = employeeRepository.findByIdEquals(term.toInt())
                ResponseEntity.ok(output)
            }

            "firstName" -> {
                val output = employeeRepository.findByFirstNameContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "lastName" -> {
                val output = employeeRepository.findByLastNameContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "email" -> {
                val output = employeeRepository.findByEmailContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "gender" -> {
                val output = employeeRepository.findByGenderEqualsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "phone" -> {
                val output = employeeRepository.findByPhoneContains(term)
                ResponseEntity.ok(output)
            }

            "address" -> {
                val output = employeeRepository.findByAddressContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "department" -> {
                val output = employeeRepository.findByDepartmentContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "role" -> {
                val output = employeeRepository.findByRoleContainsIgnoreCase(term)
                ResponseEntity.ok(output)
            }

            "hireDate" -> {
                val output = employeeRepository.findByHireDateIs(term)
                ResponseEntity.ok(output)
            }

            "salary" -> {
                val output = employeeRepository.findBySalaryEquals(term.toInt())
                ResponseEntity.ok(output)
            }

            else -> {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
            }
        }
    }

}