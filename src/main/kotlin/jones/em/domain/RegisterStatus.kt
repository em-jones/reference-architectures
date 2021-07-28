package jones.em.domain

import jones.em.domain.denominations.Denomination

data class RegisterStatus(val denominationCounts: Map<Int, Denomination>)
