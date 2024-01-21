package com.fugisawa.ktutils

import com.fugisawa.ktutils.monad.Either
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.BeforeEach

class EitherTests {

    private val leftValue = "Left value"
    private val rightValue = 1
    private var eitherLeft: Either<String, Int> = Either.Left(leftValue)
    private var eitherRight: Either<String, Int> = Either.Right(rightValue)

    @BeforeEach
    fun setUp() {
        eitherLeft = Either.Left(leftValue)
        eitherRight = Either.Right(rightValue)
    }

    @Test
    fun `is Left branch correct`() {
        assertTrue { eitherLeft.hasLeft }
        assertFalse { eitherLeft.hasRight }
        assertEquals(leftValue, eitherLeft.value)
    }

    @Test
    fun `is Right branch correct`() {
        assertFalse { eitherRight.hasLeft }
        assertTrue { eitherRight.hasRight }
        assertEquals(rightValue, eitherRight.value)
    }

    @Test
    fun `does fold function work`() {
        val leftFold = eitherLeft.fold({ it.length }, { it })
        val rightFold = eitherRight.fold({ it }, { it })

        assertEquals(leftValue.length, leftFold)
        assertEquals(rightValue, rightFold)
    }

    @Test
    fun `does leftOrNull return correct value`() {
        assertNull(eitherRight.leftOrNull())
        assertEquals(leftValue, eitherLeft.leftOrNull())
    }

    @Test
    fun `does rightOrNull return correct value`() {
        assertNull(eitherLeft.rightOrNull())
        assertEquals(rightValue, eitherRight.rightOrNull())
    }

    @Test
    fun `does leftOrThrow throw on Right`() {
        assertThrows<Exception> { eitherRight.leftOrThrow(Exception("Expected left value")) }
    }

    @Test
    fun `does rightOrThrow throw on Left`() {
        assertThrows<Exception> { eitherLeft.rightOrThrow(Exception("Expected right value")) }
    }

    @Test
    fun `does leftOrDefault return the correct value`() {
        val defaultValue = "Default"
        assertEquals(leftValue, eitherLeft.leftOrDefault(defaultValue))
        assertEquals(defaultValue, eitherRight.leftOrDefault(defaultValue))
    }

    @Test
    fun `does rightOrDefault return the correct value`() {
        val defaultValue = 2
        assertEquals(defaultValue, eitherLeft.rightOrDefault(defaultValue))
        assertEquals(rightValue, eitherRight.rightOrDefault(defaultValue))
    }

    @Test
    fun `does leftOrElse return the right value`() {
        assertEquals(leftValue, eitherLeft.leftOrElse { "Fallback" })
        assertEquals("Fallback", eitherRight.leftOrElse { "Fallback" })
    }

    @Test
    fun `does rightOrElse return the right value`() {
        assertEquals(Int.MAX_VALUE, eitherLeft.rightOrElse { Int.MAX_VALUE })
        assertEquals(rightValue, eitherRight.rightOrElse { Int.MAX_VALUE })
    }
}