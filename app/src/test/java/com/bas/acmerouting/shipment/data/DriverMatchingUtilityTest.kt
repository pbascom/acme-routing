package com.bas.acmerouting.shipment.data

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class DriverMatchingUtilityTest {

    private lateinit var sut: DriverMatchingUtility

    @Before
    fun setUp() {
        sut = DriverMatchingUtility()
    }

    @Test
    fun `Test AnalyzedDriver Creation`() {
        val analyzedDriver = DriverMatchingUtility.AnalyzedDriver.create(testDriver.name)
        assertEquals(10, analyzedDriver.nameLength)
        assertEquals(3, analyzedDriver.vowelCount)
        assertEquals(7, analyzedDriver.consonantCount)
        assertEquals(testDriver.name, analyzedDriver.name)
    }

    @Test
    fun `Test AnalyzedDestination Creation`() {
        val analyzedDestination = DriverMatchingUtility.AnalyzedDestination.create(testDestination.address)
        assertEquals(8, analyzedDestination.streetNameLength)
        assertEquals(testDestination.address, analyzedDestination.destination)
    }

    @Test
    fun `Test Suitability Score for Odd with No Common Factors`() {
        val oddNoCommonFactors = sut.computeSuitabilityScore(analyzedDriver1, analyzedDestination1)
        assertEquals(7f, oddNoCommonFactors) // Number of consonants in the driver's name
    }

    @Test
    fun `Test Suitability Score for Even with No Common Factors`() {
        val evenNoCommonFactors = sut.computeSuitabilityScore(analyzedDriver2, analyzedDestination2)
        assertEquals(9f, evenNoCommonFactors) // 1.5 * Number of vowels in the driver's name
    }

    @Test
    fun `Test Suitability Score for Even with One Common Factor`() {
        val evenWithCommonFactor = sut.computeSuitabilityScore(analyzedDriver1, analyzedDestination2)
        assertEquals(6.75f, evenWithCommonFactor)
    }

    @Test
    fun `Test Suitability Score for Odd with One Common Factor`() {
        val oddWithCommonFactor = sut.computeSuitabilityScore(analyzedDriver1, analyzedDestination3)
        assertEquals(10.5f, oddWithCommonFactor)
    }

    @Test
    fun `Test Suitability Score for Even with Two Common Factors`() {
        val evenWithTwoCommonFactors = sut.computeSuitabilityScore(analyzedDriver1, analyzedDestination4)
        assertEquals(6.75f, evenWithTwoCommonFactors)
    }

    @Test
    fun `Test Adapt method`() {
        val shipments = sut.adapt(testResponse)

        val drivers = shipments.keys
        val bob = drivers.first { it.name == "Bob Belcher" }
        val louise = drivers.first{ it.name == "Louise Belcher" }

        assertEquals("1 N 4th Pl", shipments[bob]?.address)
        assertEquals("123 Wharf Way", shipments[louise]?.address)
    }



    companion object {
        private val testDriver = Driver("Bob Belcher")
        private val testDestination = Destination("123 Wharf Way")

        private val testResponse = ShipmentResponse(
            listOf("123 Wharf Way", "1 N 4th Pl"),
            listOf("Bob Belcher", "Louise Belcher")
        )

        private val analyzedDriver1 = DriverMatchingUtility.AnalyzedDriver(
            nameLength = 10,
            vowelCount = 3,
            consonantCount = 7,
            name = "Bob Belcher"
        )
        private val analyzedDriver2 = DriverMatchingUtility.AnalyzedDriver(
            nameLength = 13,
            vowelCount = 6,
            consonantCount = 7,
            name = "Louise Belcher" // Don't tell her she's too young to drive.
        )

        private val analyzedDestination1 = DriverMatchingUtility.AnalyzedDestination(
            streetNameLength = 7,
            destination = "123 Wharf Way"
        )
        private val analyzedDestination2 = DriverMatchingUtility.AnalyzedDestination(
            streetNameLength = 14,
            destination = "12 Grimmauld Place"
        )
        private val analyzedDestination3 = DriverMatchingUtility.AnalyzedDestination(
            streetNameLength = 5,
            destination = "1 N 4th Pl"
        )
        private val analyzedDestination4 = DriverMatchingUtility.AnalyzedDestination(
            streetNameLength = 20,
            destination = "24 Really Far Away Way Road"
        )
    }
}