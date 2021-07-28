package jones.em.domain

import jones.em.domain.denominations.Denomination
import jones.em.domain.events.Event

typealias Formatter = (List<Denomination>, Boolean) -> String
typealias RegisterReducer = (RegisterStatus, Event) -> RegisterStatus