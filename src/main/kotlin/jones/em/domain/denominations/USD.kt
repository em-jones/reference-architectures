package jones.em.domain.denominations

import java.math.BigInteger

class USD(count: BigInteger, value: Int) : Denomination(count, value){
  companion object{
    fun oneUSD(count: BigInteger) = USD(count, 1)
    fun twoUSD(count: BigInteger) = USD(count, 2)
    fun fiveUSD(count: BigInteger) = USD(count, 5)
    fun tenUSD(count: BigInteger) = USD(count, 10)
    fun twentyUSD(count: BigInteger) = USD(count, 20)
  }
}