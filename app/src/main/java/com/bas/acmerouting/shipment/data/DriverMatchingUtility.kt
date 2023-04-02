package com.bas.acmerouting.shipment.data

import java.util.*

/**
 * Adapts the json structure specified by the coding assignment into a Map of (Driver, Destination)
 * pairs. See [ShipmentRepository] kdoc for an explanation of the motivation behind this class.
 */
class DriverMatchingUtility {
    fun adapt(response: ShipmentResponse): Map<Driver, Destination> {
        val shipments = hashMapOf<Driver, Destination>()
        // There are two parts to this assignment. First, we need to determine each driver's
        // suitability to take each shipment. Next, we must assign drivers to destinations which
        // maximize the total suitability over the entire dataset.

        // Computing an SS for each driver / destination pair is O(n^2) by itself, and I don't
        // immediately see a matching algorithm that doesn't involve a huge number of comparisons
        // as well. But, may as well get started.

        // We'll start by pulling the relevant metrics out of each data set.
        val analyzedDrivers = response.drivers.map { driver ->AnalyzedDriver.create(driver) }
        val analyzedDestinations = response.destinations.map { destination -> AnalyzedDestination.create(destination) }

        // So, if I understand the problem correctly, we would need to check every possible
        // combination of driver / destination pairs to guarantee we get the maximum value. That may
        // even be possible with a data set this small, but there's no way it scales to anything even
        // approaching real numbers, so I won't do it here. Instead, I'm going to implement a simple
        // greedy algorithm which will add the highest SS pair, and then the highest remaining pair,
        // and so on. This algorithm is vulnerable to getting stuck in local minima, and I realize
        // there may be a clever math trick I'm missing, but damnit Jim, I'm an Android engineer not
        // a Graph Theorist.

        // We'll start by calculating every Suitability Score and putting them in a priority queue.
        // By default, the Priority Queue puts the smallest priority on top, so we invert the
        // comparator.
        val suitabilityComparator: Comparator<Suitability> = compareBy { it.score * -1 }
        val suitabilityQueue: PriorityQueue<Suitability> = PriorityQueue(suitabilityComparator)

        for (driver in analyzedDrivers) {
            for (destination in analyzedDestinations) {
                suitabilityQueue.add(Suitability(
                    driver,
                    destination,
                    computeSuitabilityScore(driver, destination))
                )
            }
        }

        // Now, we make hashSets of pre-assigned drivers and destinations for that sweet, sweet
        // O(1) member access time.
        val assignedDrivers = hashSetOf<AnalyzedDriver>()
        val assignedDestinations = hashSetOf<AnalyzedDestination>()

        // And finally, we iterate through the Priority Queue until it's empty, taking the highest
        // unassigned pair and adding it to the shipments Map.
        while(!suitabilityQueue.isEmpty()) {
            val mostSuitableMatch = suitabilityQueue.poll() ?: continue
            if(assignedDrivers.contains(mostSuitableMatch.driver)) continue
            if(assignedDestinations.contains(mostSuitableMatch.destination)) continue

            assignedDrivers.add(mostSuitableMatch.driver)
            assignedDestinations.add(mostSuitableMatch.destination)
            shipments[Driver(mostSuitableMatch.driver.name)] = Destination(mostSuitableMatch.destination.destination)
        }

        return shipments
    }

    private fun computeSuitabilityScore(driver: AnalyzedDriver, destination: AnalyzedDestination): Float {
        // If the length of the shipment's destination street name is even, the base suitability
        // score is the number of vowels in the driver's name multiplied by 1.5. If the length of
        // the shipment's destination street name is odd, the base suitability score is the number
        // of consonants in the driver's name multiplied by 1
        val baseSuitabilityScore = when(destination.streetNameLength %2 == 0) {
            true -> 1.5f * driver.vowelCount
            false -> driver.consonantCount.toFloat()
        }

        // If the length of the shipment's destination street name shares any common factors (other
        // than 1) with the length of the driver's name, the SS is increased by 50% above the base.
        var hasCommonFactor = false
        var factorToCheck = 2
        while( !hasCommonFactor
            && factorToCheck <= driver.nameLength/2
            && factorToCheck <= destination.streetNameLength/2
        ) {
            if(driver.nameLength % factorToCheck == 0
                && destination.streetNameLength % factorToCheck == 0
            ) {
                hasCommonFactor = true
            } else {
                factorToCheck++
            }
        }

        return when(hasCommonFactor) {
            true -> 1.5f * baseSuitabilityScore
            false -> baseSuitabilityScore
        }
    }

    /**
     * Convenience class to pull out relevant metrics from the [Destination] class. The Factory
     * method is syntax sugar for easier initialization.
     */
    data class AnalyzedDestination(
        val streetNameLength: Int,
        val destination: String
    ) {
        companion object {
            fun create(destinationAddress: String): AnalyzedDestination {
                return AnalyzedDestination(
                    streetNameLength = destinationAddress.count { !it.isWhitespace() && !it.isDigit() },
                    destination = destinationAddress
                )
            }
        }
    }

    /**
     * Same, but for [Driver]s.
     */
    data class AnalyzedDriver(
        val nameLength: Int,
        val vowelCount: Int,
        val consonantCount: Int,
        val name: String
    ) {
        companion object {
            fun create(driverName: String): AnalyzedDriver {
                // We would need a more complicate regex matcher for real data
                val strippedName = driverName.filterNot { it.isWhitespace() }
                return AnalyzedDriver(
                    nameLength = strippedName.length,
                    vowelCount = strippedName.count { VOWELS.contains(it.lowercaseChar()) },
                    consonantCount = strippedName.count { !VOWELS.contains(it.lowercaseChar()) },
                    name = driverName
                )
            }
        }
    }

    data class Suitability(
        val driver: AnalyzedDriver,
        val destination: AnalyzedDestination,
        val score: Float
    )

    companion object {
        private val VOWELS = hashSetOf('a', 'e', 'i', 'o', 'u')
    }
}