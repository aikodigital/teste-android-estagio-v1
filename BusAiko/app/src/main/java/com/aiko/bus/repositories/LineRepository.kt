package com.aiko.bus.repositories

import com.aiko.bus.models.Line
import com.aiko.bus.sptrans.SPTransClient
import com.aiko.bus.sptrans.response.LineResponse

class LineRepository {

    suspend fun getLines(q: String): List<Line> {
        return SPTransClient.getLines(q).map {
            parse(it)
        }
    }

    suspend fun lineByBusLane(busLaneId: Int): List<Line> {
        return SPTransClient.getLineByBusLane(busLaneId).map {
            parse(it)
        }
    }

    private fun parse(response: LineResponse): Line {
        val identifier = "${response.identifierBegin}-${response.identifierEnd}"
        var origin = response.terminalPrimary
        var destination = response.terminalSecondary

        if (response.direction == 2) {
            origin = response.terminalSecondary
            destination = response.terminalPrimary
        }

        return Line(
            id = response.id,
            direction = response.direction,
            identifier = identifier,
            destination = destination,
            origin = origin
        )
    }
}