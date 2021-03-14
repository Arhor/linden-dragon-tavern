package org.arhor.diploma.service.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.arhor.diploma.commons.Identifiable

@JsonIgnoreProperties(ignoreUnknown = true)
data class Ability(
    var name: String?,
    var fullName: String?,
    var description: List<String>,
    var skills: List<String>
) : Identifiable<String> {

    override val id: String?
        get() = name
}