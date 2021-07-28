package jones.em.application

import jones.em.domain.denominations.Denomination
import jones.em.domain.denominations.USD
import jones.em.domain.denominations.denominationsWhenGivenFive
import jones.em.domain.denominations.getTotalInRegister
import java.math.BigInteger

fun defaultValues() = denominationsWhenGivenFive.map{ USD(BigInteger.ZERO, it) }.associateBy { it.value }
fun formatter(denominations: List<Denomination>, includeTotal: Boolean) =
        if(includeTotal)
          totalAndDenominations(denominations).joinToString(" ", "$")
        else
          getDenominationsWithDefaults(denominations).map{ d -> d.count }.joinToString(" ")
fun getDenominationsWithDefaults(denominations: List<Denomination>): List<Denomination> =
        denominations.associateBy { d -> d.value }
                .let{
                  defaultValues().map{entry -> it.getOrDefault(entry.key, entry.value)}
                }
fun totalAndDenominations(denominations: List<Denomination>): List<BigInteger> =
        getDenominationsWithDefaults(denominations).let {
                  arrayListOf(getTotalInRegister(it)).also{
                    l -> l.addAll(it.map { d -> d.count })
                  }
                }

