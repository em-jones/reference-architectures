package jones.em.domain

import jones.em.domain.operations.Operation
import jones.em.domain.denominations.*
import jones.em.domain.events.Event
import java.math.BigInteger

class Register(private val state: MutableCollection<Event>,
               private val reducer: RegisterReducer) {
  companion object {
    private fun initDenominations() = listOf(20, 10, 5, 2, 1).map { USD(BigInteger.ZERO, it) }
    fun initialStatus() = RegisterStatus(initDenominations().associateBy { it.value })
  }
  var status: RegisterStatus

  init{
    status = state.fold(initialStatus(), reducer)
  }

  fun handle(op: Operation): Event = handleOperation(status, op).also {
    state.add(it)
    status = state.fold(initialStatus(), reducer)
  }

}