package protoss.employeemanagementsystem.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import protoss.employeemanagementsystem.entity.DeleteEmployeeResponse
import protoss.employeemanagementsystem.entity.Employee
import protoss.employeemanagementsystem.repository.EmployeeRepository
import protoss.employeemanagementsystem.service.EmployeeService

@Controller
class EMController {

    @Autowired
    lateinit var employeeRepository: EmployeeRepository

    @Autowired
    lateinit var employeeService: EmployeeService

    @PostMapping("/createEmployee")
    fun createEmployee(@RequestBody employee: Employee): ResponseEntity<Employee> {
        return employeeService.createEmployee(employee)
    }

    @GetMapping("/findAllEmployees")
    fun findAllEmployees(): ResponseEntity<List<Employee>> {
        val allEmployees = employeeRepository.findAll()
        return ResponseEntity.ok().body(allEmployees)
    }

    @GetMapping("/findEmployee/{id}")
    fun getEmployeeDetail(@PathVariable id: Int): ResponseEntity<Employee> {
        val employee = employeeService.getEmployeeDetail(id)
        return ResponseEntity.ok(employee)
    }

    @PutMapping("/updateEmployee/{id}")
    fun updateDetail(@PathVariable id: Int, @RequestBody newDetail: Employee): ResponseEntity<Employee> {
        return employeeService.updateDetail(id, newDetail)
    }

    @DeleteMapping("/deleteEmployee/{id}")
    fun deleteEmployee(@PathVariable id: Int): ResponseEntity<DeleteEmployeeResponse> {
        return employeeService.deleteEmployee(id)
    }

    @GetMapping("/search/{field}/{term}")
    fun filterSearch(@PathVariable field: String, @PathVariable term: String): ResponseEntity<List<Employee?>> {
        return employeeService.filterSearch(field, term)
    }

}