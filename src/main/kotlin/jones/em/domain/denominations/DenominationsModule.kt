package jones.em.domain.denominations

import jones.em.domain.operations.TakeChangeOperation
import java.math.BigInteger

val denominationsWhenGivenFive = listOf(20, 10, 5, 2, 1)
fun argsToDenominationList(denominations: List<Int>) =
        fun(args: String): List<Denomination> =
                args.split(" ").map(String::toBigInteger).zip(denominations)
                .map { (amount, denomination) -> USD(amount, denomination) }

val fiveArgDenominationList = argsToDenominationList(denominationsWhenGivenFive)

val twenty = BigInteger.TEN.multiply(BigInteger.TWO)
val five = BigInteger.valueOf(5)

fun needs(v: Int, remaining: BigInteger) = remaining.subtract(v.toBigInteger()) >= BigInteger.ZERO

fun hasRemaining(name: Int, denominations: Map<Int, Denomination>) =
        denominations[name]!!.count > BigInteger.ZERO

fun removeDenomination(value: Int, op: TakeChangeOperation)
        = op.inRegister.map {
            (if (it.key == value) it.value.count.subtract(BigInteger.ONE) else it.value.count)
                    .let { it2 -> USD(it2, it.key) } }
        .associateBy { it.value }
        .let {
          val deducting = value.toBigInteger()
          op.removed.push(USD(BigInteger.ONE, deducting.toInt()));
          TakeChangeOperation(it, op.moneyLeft.subtract(deducting), op.changeOwed.subtract(deducting), op.removed, op.nextDenomination)
        }

fun replaceDenomination(op: TakeChangeOperation)
        = op.removed.pop().let{
          lastRemoved -> op.inRegister.map {
            (if (it.key == lastRemoved.value) it.value.count.add(BigInteger.ONE) else it.value.count)
            .let { it2 -> USD(it2, it.key) } }
          .associateBy { it.value }
          .let {
            val deducting = lastRemoved.value.toBigInteger()
            val nextDenomination = denominationsWhenGivenFive.firstOrNull{ it < lastRemoved.value} ?: 1
            TakeChangeOperation(it, op.moneyLeft.add(deducting), op.changeOwed.add(deducting), op.removed, nextDenomination)
          }

}
fun getTotalInRegister(denominations: Collection<Denomination>) = denominations.sumOf { it.count * it.value.toBigInteger() }
