package org.arhor.diploma.service

import org.arhor.diploma.commons.Identifiable
import java.io.Serializable

interface Deleter<T : Identifiable<K>, K : Serializable> {

    fun delete(id: K)

    fun delete(item: T)
}