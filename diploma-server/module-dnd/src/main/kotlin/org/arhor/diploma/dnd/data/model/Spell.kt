package org.arhor.diploma.dnd.data.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL
import org.arhor.diploma.commons.Identifiable

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
data class Spell(
    val name: String,
    val desc: List<String>,
    val higherLevel: List<String>?,
    val range: String,
    val level: Byte,
    val ritual: Boolean,
    val components: Set<Component>,
    val material: String?,
    val duration: String,
    val concentration: Boolean,
    val castingTime: String,
    val school: String,
    val classes: List<String>,
    val subclasses: List<String>,
) : Identifiable<String> {

    enum class Component {
        /** Material */
        M,

        /** Verbal */
        V,

        /** Somatic */
        S;
    }

    @get:JsonIgnore
    override val id: String = name
}