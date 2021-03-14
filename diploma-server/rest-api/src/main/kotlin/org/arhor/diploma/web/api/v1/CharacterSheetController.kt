package org.arhor.diploma.web.api.v1

import org.arhor.diploma.service.export.CharsheetService
import org.slf4j.LoggerFactory
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.invoke.MethodHandles

@RestController
@RequestMapping(path = ["/api/v1/charsheets"])
class CharacterSheetController(private val service: CharsheetService) {

    @GetMapping(produces = [MediaType.APPLICATION_PDF_VALUE])
    fun downloadCharsheet(): ResponseEntity<FileSystemResource> {
        log.debug("downloading empty character sheet")

        val file = service.getEmptyCharsheet()
        val resource = FileSystemResource(file)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=${file.name}")
            .contentLength(file.length())
            .body(resource)
    }

    companion object {
        @JvmStatic
        private val log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass())

        private const val CHAR_SHEET_PAGE_1 = "classpath:sheets/5E_CharacterSheet_Fillable_Page1.pdf"
    }
}