package jones.em.domain.events

import jones.em.domain.RegisterStatus
import jones.em.domain.denominations.Denomination
import jones.em.domain.denominations.USD

class Reducers {
  fun registerV1(status: RegisterStatus, e: Event) = when (e) {
    is ChangeMade -> status.let{
      e.denominations.map { v -> USD(status.denominationCounts[v.value]!!.count.subtract(v.count), v.value) }
              .associateBy { it.value }
    }
    is DenominationsPut -> status.let{
      e.denominations.map { v -> USD(status.denominationCounts[v.value]!!.count.add(v.count), v.value) }
              .associateBy { it.value }
    }
    is DenominationsTaken -> status.let {
      e.denominations.map { v -> USD(status.denominationCounts[v.value]!!.count.subtract(v.count), v.value) }
              .associateBy { it.value }
    }
    else -> mapOf<Int, Denomination>()
  }.let(::RegisterStatus)
}