package protoss.employeemanagementsystem.entity

import jakarta.persistence.*

@Entity
@Table(name = "employee")
data class Employee(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    val id: Int = 0,

    @Column(name = "first_name")
    var firstName: String,

    @Column(name = "last_name")
    var lastName: String,

    @Column(name = "gender")
    var gender: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "phone_number")
    var phone: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "department")
    var department: String,

    @Column(name = "role")
    var role: String,

    @Column(name = "hire_date")
    var hireDate: String,

    @Column(name = "salary")
    var salary: Int
)
