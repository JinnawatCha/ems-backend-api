package protoss.employeemanagementsystem.service

import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import jakarta.servlet.ServletOutputStream
import jakarta.servlet.http.HttpServletResponse
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import protoss.employeemanagementsystem.repository.EmployeeRepository
import java.io.OutputStream

@Service
class ReportService @Autowired constructor(
    private val employeeRepository: EmployeeRepository
) {

    fun generateExcel(response: HttpServletResponse) {
        val listEmployee = employeeRepository.findAll()

        val workbook = HSSFWorkbook()
        val sheet = workbook.createSheet("Employee Report")
        val row = sheet.createRow(0)

        row.createCell(0).setCellValue("ID")
        row.createCell(1).setCellValue("Firstname")
        row.createCell(2).setCellValue("Lastname")
        row.createCell(3).setCellValue("Gender")
        row.createCell(4).setCellValue("Email")
        row.createCell(5).setCellValue("PhoneNumber")
        row.createCell(6).setCellValue("Address")
        row.createCell(7).setCellValue("Department")
        row.createCell(8).setCellValue("Role")
        row.createCell(9).setCellValue("HireDate")
        row.createCell(10).setCellValue("Salary")

        var dataRowIndex = 1

        for (employee in listEmployee) {
            val dataRow = sheet.createRow(dataRowIndex)
            dataRow.createCell(0).setCellValue(employee.id.toDouble())
            dataRow.createCell(1).setCellValue(employee.firstName)
            dataRow.createCell(2).setCellValue(employee.lastName)
            dataRow.createCell(3).setCellValue(employee.gender)
            dataRow.createCell(4).setCellValue(employee.email)
            dataRow.createCell(5).setCellValue(employee.phone)
            dataRow.createCell(6).setCellValue(employee.address)
            dataRow.createCell(7).setCellValue(employee.department)
            dataRow.createCell(8).setCellValue(employee.role)
            dataRow.createCell(9).setCellValue(employee.hireDate)
            dataRow.createCell(10).setCellValue(employee.salary.toDouble())
            dataRowIndex++
        }

        val ops: ServletOutputStream = response.outputStream
        workbook.write(ops)
        workbook.close()
        ops.close()
    }

    fun generatePDF(outputStream: OutputStream) {
        val pdfWriter = PdfWriter(outputStream)
        val pdfDocument = PdfDocument(pdfWriter)
        val pageSize = PageSize.A4.rotate()
        val document = Document(pdfDocument, pageSize)

        document.add(Paragraph("Employee List"))
        val table = Table(floatArrayOf(1f, 3f, 3f, 3f, 1f, 3f, 3f, 3f, 3f, 3f, 3f))
        table.addHeaderCell("ID")
        table.addHeaderCell("Firstname")
        table.addHeaderCell("Lastname")
        table.addHeaderCell("Gender")
        table.addHeaderCell("Email")
        table.addHeaderCell("PhoneNumber")
        table.addHeaderCell("Address")
        table.addHeaderCell("Department")
        table.addHeaderCell("Role")
        table.addHeaderCell("HireDate")
        table.addHeaderCell("Salary")

        val employees = employeeRepository.findAll()
        for (employee in employees) {
            table.addCell(employee.id.toString())
            table.addCell(employee.firstName)
            table.addCell(employee.lastName)
            table.addCell(employee.gender)
            table.addCell(employee.email)
            table.addCell(employee.phone)
            table.addCell(employee.address)
            table.addCell(employee.department)
            table.addCell(employee.role)
            table.addCell(employee.hireDate)
            table.addCell(employee.salary.toString())
        }

        document.add(table)
        document.close()

    }
}
