package jones.em.domain

import jones.em.domain.events.ChangeMade

import jones.em.domain.operations.*

fun strategyV1(formatter: Formatter) = fun(register: Register) = fun(op: Operation): String = try {
  when (op) {
    is TakeDenominations, is PutDenominations ->
      register.handle(op).let { register.status.denominationCounts.values.toList() }.let{l -> formatter(l, true)}
    is MakeChange -> register.handle(op).let { (it as ChangeMade).denominations }.let{ l -> formatter(l, false)}
    is RequestRegisterStatus -> register.status.denominationCounts.values.toList().let{ l -> formatter(l, true)}
    else -> "Bye"
  }
} catch(e: InsufficientFundsException) { "sorry" }