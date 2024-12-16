package protoss.employeemanagementsystem.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import protoss.employeemanagementsystem.service.ReportService

@RestController
class ReportRestController @Autowired constructor(
    private val reportService: ReportService
) {

    @GetMapping("/excel")
    fun generateExcelReport(response: HttpServletResponse) {
        response.contentType = "application/octet-stream"
        val headerKey = "Content-Disposition"
        val headerValue = "attachment; filename=employees.xlsx"
        response.setHeader(headerKey, headerValue)
        reportService.generateExcel(response)
        response.flushBuffer()
    }

    @GetMapping("/pdf")
    fun exportPdf(response: HttpServletResponse) {
        response.contentType = "application/pdf"
        response.setHeader("Content-Disposition", "attachment; filename=employee.pdf\"")

        reportService.generatePDF(response.outputStream)
    }
}
