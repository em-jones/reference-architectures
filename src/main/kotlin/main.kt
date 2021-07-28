import jones.em.application.CLIReaderV1
import jones.em.application.formatter
import jones.em.domain.RegisterService
import jones.em.domain.events.Reducers
import java.util.*

fun main(args: Array<String>) {

  val reducerV1 = Reducers()
  val registerService = RegisterService(reducerV1::registerV1, CLIReaderV1(), ::formatter)
  while (true){
    println("Please enter your request:")
    val input = Optional.ofNullable(readLine())
    input.ifPresent(registerService::run)
  }
}