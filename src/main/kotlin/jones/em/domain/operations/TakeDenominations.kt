package jones.em.domain.operations

import jones.em.domain.denominations.Denomination

data class TakeDenominations(val denominations: List<Denomination>) : Operation("take")