package com.spring.inventory.controllers

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.FileOutputStream
import java.util.stream.Stream

@RestController
@Transactional
@RequestMapping("/api/v1/moderator")
class PdfController {

    @GetMapping("createTable")
    fun createTable(): Document {
        val document = Document()
        PdfWriter.getInstance(document, FileOutputStream("Data.pdf"))

        document.open()

        val table = PdfPTable(3)
        addTableHeader(table)
        addRows(table)
        addCustomRows(table)

        document.add(table)
        document.close()

        return document
    }
    fun addTableHeader(table: PdfPTable) {
        Stream.of("column header 1", "column header 2", "column header 3")
            .forEach { columnTitle ->
                val header = PdfPCell()
                header.backgroundColor = BaseColor.LIGHT_GRAY
                header.borderWidth = 2f
                header.phrase = Phrase(columnTitle)
                table.addCell(header)
            }
    }
    fun addRows(table: PdfPTable) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }
    fun addCustomRows(table: PdfPTable) {

    }
}