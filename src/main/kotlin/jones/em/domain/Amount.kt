package jones.em.domain

import java.math.BigInteger

data class Amount(val value: BigInteger) {
  companion object{
    fun toAmount(s: String): Amount = try {
      Amount(s.toBigInteger())
    } catch (e: NumberFormatException){
      throw Exception("Error parsing $this into decimal for 'Amount' instance")
    }
  }
}



