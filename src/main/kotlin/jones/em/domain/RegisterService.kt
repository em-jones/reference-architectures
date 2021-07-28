package jones.em.domain

import jones.em.domain.events.Event
import jones.em.domain.operations.Operation

class RegisterService(private val reducer: RegisterReducer,
                      private val reader: Reader<Operation>,
                      private val handlerStrategy: OperationHandlerStrategy<String>) {
  private val registerConnections = HashMap<String, Register>()

  fun run(intention: String, connection : String = "default"): String =
          registerConnections.computeIfAbsent(connection) { conn -> hydrateRegister(conn, reducer) }
                  .let { register ->
                      reader.parse(intention)
                        .let(handlerStrategy(register))
                        .also(::println)
                  }
  companion object {
    fun hydrateRegister(_conn: String, reducer: (RegisterStatus, Event) -> RegisterStatus) = Register(arrayListOf(), reducer)
  }
}