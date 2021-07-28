package jones.em.domain

import jones.em.domain.events.ChangeMade
import jones.em.domain.events.Event
import jones.em.domain.operations.*
import kotlin.system.exitProcess

class RegisterService(private val reducer: RegisterReducer,
                      private val reader: Reader<Operation>,
                      val formatter: Formatter) {
  private val registerConnections = HashMap<String, Register>()
  private fun serviceHandler(register: Register, formatter: Formatter) =
    fun(op: Operation) = try {
      when (op) {
        is TakeDenominations, is PutDenominations ->
          register.handle(op).let { register.status.denominationCounts.values.toList() }.let{l -> formatter(l, true)}
        is MakeChange -> register.handle(op).let { (it as ChangeMade).denominations }.let{ l -> formatter(l, false)}
        is RequestRegisterStatus -> register.status.denominationCounts.values.toList().let{l -> formatter(l, true)}
        else -> exitProcess(0)
      }
    } catch(e: InsufficientFundsException) {
      println("sorry")
    }
  fun run(intention: String, connection : String = "default") =
          registerConnections.computeIfAbsent(connection) { conn -> hydrateRegister(conn, reducer) }
                  .let { register ->
                      reader
                        .parse(intention)
                        .let(serviceHandler(register, formatter))
                        .let(::println)
                  }
  companion object {
    fun hydrateRegister(_conn: String, reducer: (RegisterStatus, Event) -> RegisterStatus) = Register(arrayListOf(), reducer)
  }
}