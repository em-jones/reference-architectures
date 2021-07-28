package jones.em.domain

import jones.em.domain.denominations.Denomination
import jones.em.domain.events.Event
import jones.em.domain.operations.Operation

typealias Formatter = (List<Denomination>, Boolean) -> String
typealias RegisterReducer = (RegisterStatus, Event) -> RegisterStatus
typealias OperationHandlerStrategy<T> = (Register) -> (Operation) -> T