package jones.em.application

import jones.em.domain.operations.Operation
import jones.em.domain.Reader

class CLIReaderV1 : Reader<Operation> {
  companion object{
    private const val requestTemplate = "[operation] [space_delimited_parameter_list]"
    private const val requestExample = "e.g. - 'change 20'"
    private fun errMessage (s: String) =
            """
        There was an issue performing the requested operation "$s".
        Please submit request in the form: $requestTemplate
        $requestExample
      """.trimIndent()
    private fun listToPair(l: List<String>) = Pair(l.getOrElse(0){""}, l.getOrElse(1){""})
    fun splittingStrategy(s: String): Pair<String, String> =
            try {
              s.split(" ", limit = 2).let(::listToPair)
            } catch (e: IndexOutOfBoundsException) {
              throw Exception(errMessage(s))
            }
  }

  override fun parse(s: String): Operation = Operation.asOperation(s, ::splittingStrategy)
}