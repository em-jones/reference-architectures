package jones.em.domain.operations

import jones.em.domain.denominations.Denomination
import java.math.BigInteger
import java.util.*

data class TakeChangeOperation(val inRegister: Map<Int, Denomination>,
                               val moneyLeft: BigInteger,
                               val changeOwed: BigInteger,
                               val removed: Stack<Denomination>,
                               val nextDenomination: Int
                               )