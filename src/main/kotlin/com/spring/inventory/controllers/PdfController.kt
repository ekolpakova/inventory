package com.spring.inventory.controllers

import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.spring.inventory.entities.Fix
import com.spring.inventory.services.FixService
import org.springframework.beans.factory.annotation.Autowired
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

    @Autowired
    lateinit var fixService: FixService

    @GetMapping("createTable")
    fun createTable(): Document {
        val document = Document()

        val font_ = "/home/lisa/IdeaProjects/inventory/src/main/kotlin/com/spring/inventory/fonts/timesnewroman.ttf"
        val bf = BaseFont.createFont(font_, BaseFont.IDENTITY_H, BaseFont.EMBEDDED)
        val font = Font(bf, 12.0f, Font.NORMAL)

        //val font:Font = Font(Font.FontFamily.TIMES_ROMAN, 14.0f)
        PdfWriter.getInstance(document, FileOutputStream("Data.pdf"))

        document.open()

        val table = PdfPTable(4)
        val p = Paragraph("Акт передачи оборудования в ремонт", font)
        p.alignment = Paragraph.ALIGN_CENTER
        document.add(p)
        val br = Paragraph(" ")
        document.add(br)

        addTableHeader(table, font)
        addRows(table, font)
        addCustomRows(table, font)

        document.add(table)
        document.close()

        return document
    }
    fun addTableHeader(table: PdfPTable, font: Font) {

        Stream.of("Наименование", "Описание поломки", "Ответственное лицо", "Контактный телефон")
            .forEach { columnTitle ->
                val header = PdfPCell()
                header.backgroundColor = BaseColor.LIGHT_GRAY
                header.borderWidth = 1f
                header.phrase = Phrase(columnTitle, font)
                table.addCell(header)
            }
    }
    fun addRows(table: PdfPTable, font: Font) {
        val fixes: MutableList<Fix> = fixService.getFixes()
        for(fix in fixes) {
            val fio = fix.responsiblePerson?.surname + " " + fix.responsiblePerson?.firstName?.get(0) + "." + fix.responsiblePerson?.patronym?.get(0)
            table.addCell(Phrase(fix.inventoryItem?.name, font))
            table.addCell(Phrase(fix.description, font))
            table.addCell(Phrase(fio, font))
            table.addCell(Phrase(fix.phone, font))
        }
    }
    fun addCustomRows(table: PdfPTable, font: Font) {

    }
}