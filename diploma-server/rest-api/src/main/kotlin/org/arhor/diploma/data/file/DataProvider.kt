package org.arhor.diploma.data.file

import org.arhor.diploma.commons.ReloadableResource
import java.util.function.Predicate

/**
 * @param T basic data type which provides only few required fields
 * @param D detailed data type which contains all fields
 * @param K identity type
 */
interface DataProvider<T, D, K> : ReloadableResource {

    fun getOne(id: K): T

    fun getDetails(id: K): D

    fun getList(): List<T>

    fun getList(page: Int, size: Int): List<T>

    fun getDetailsList(): List<D>

    fun getDetailsList(query: Predicate<D>): List<D>

    fun getDetailsList(page: Int, size: Int): List<D>

    fun getDetailsList(page: Int, size: Int, query: Predicate<D>): List<D>
}