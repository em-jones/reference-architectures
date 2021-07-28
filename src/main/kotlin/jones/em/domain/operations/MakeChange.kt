package jones.em.domain.operations

import jones.em.domain.Amount

class MakeChange(val amount: Amount) : Operation("change")