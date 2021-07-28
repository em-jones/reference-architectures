package jones.em.domain.operations

import jones.em.domain.denominations.Denomination

data class PutDenominations(val denominations: List<Denomination>) : Operation("put")