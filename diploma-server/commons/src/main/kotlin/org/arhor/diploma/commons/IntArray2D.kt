package org.arhor.diploma.commons

import java.util.*

@Suppress("unused")
class IntArray2D private constructor(@JvmField val rows: Int, @JvmField val cols: Int) {

    init {
        require(rows >= 0) { "rows number must not be negative" }
        require(cols >= 0) { "cols number must not be negative" }
    }

    private val buffer: IntArray = IntArray(rows * cols)

    operator fun get(row: Int, col: Int): Int {
        val index = index(row, col)
        return buffer[index]
    }

    operator fun set(row: Int, col: Int, value: Int) {
        val index = index(row, col)
        buffer[index] = value
    }

    inline fun forEach(consumer: (value: Int, row: Int, col: Int) -> Unit) {
        forEach { value, row, col, _ -> consumer(value, row, col) }
    }

    inline fun forEach(consumer: (value: Int, row: Int, col: Int, array: IntArray2D) -> Unit) {
        for (i in 0 until this.rows) {
            for (j in 0 until this.cols) {
                consumer(this[i, j], i, j, this)
            }
        }
    }

    private fun index(row: Int, col: Int): Int {
        ensureSafeAccess(row, col)
        return (cols * row) + col
    }

    private fun ensureSafeAccess(row: Int, col: Int) {
        val rowIsOut = (row < 0) || (row >= rows)
        val colIsOut = (col < 0) || (col >= cols)
        if (rowIsOut || colIsOut) {
            throw IndexOutOfBoundsException(
                "index [${row},${col}] is out of bound (${rows},${cols})"
            )
        }
    }

    override fun toString(): String {
        val result = StringJoiner("\n")
        for (i in 0 until rows) {
            val row = StringJoiner(" ")
            for (j in 0 until cols) {
                row.add(this[i, j].toString())
            }
            result.add(row.toString())
        }
        return result.toString()
    }

    companion object {
        @JvmStatic
        val EMPTY = IntArray2D(0, 0)

        @JvmStatic
        fun create(rows: Int, cols: Int): IntArray2D {
            return if (rows != 0 && cols != 0) {
                IntArray2D(rows, cols)
            } else {
                EMPTY
            }
        }

        @JvmStatic
        inline fun create(rows: Int, cols: Int, init: (Int, Int) -> Int): IntArray2D {
            return create(rows, cols).also {
                for (i in 0 until rows) {
                    for (j in 0 until cols) {
                        it[i, j] = init(i, j)
                    }
                }
            }
        }
    }
}