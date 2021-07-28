package jones.em.domain.events

import jones.em.domain.denominations.Denomination
import jones.em.domain.events.Event

data class DenominationsTaken(val denominations: List<Denomination>) : Event