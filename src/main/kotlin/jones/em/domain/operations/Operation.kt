package jones.em.domain.operations

import jones.em.domain.Amount
import jones.em.domain.denominations.fiveArgDenominationList
import jones.em.domain.whenArgCountIsFiveStrategy
import jones.em.domain.whenArgCountIsOneStrategy
import java.util.*

open class Operation(val name: String) {
  companion object {
    private fun buildOperation(operationAndArgs: Pair<String, String>) = when (operationAndArgs.first)
    {
      "change" -> whenArgCountIsOneStrategy(operationAndArgs.second).let(Amount.Companion::toAmount).let(::MakeChange)
      "put" -> whenArgCountIsFiveStrategy(operationAndArgs.second).let { v -> fiveArgDenominationList(v) }.let(::PutDenominations)
      "quit" -> QuitProgram()
      "show" -> RequestRegisterStatus()
      "take" -> whenArgCountIsFiveStrategy(operationAndArgs.second).let { v -> fiveArgDenominationList(v) }.let(::TakeDenominations)
      else -> throw Exception("Operation does not exist")
    }

    fun asOperation(s: String, readToPairStrategy: (String) -> Pair<String, String>) =
            readToPairStrategy(s).let(Companion::buildOperation)
  }
}