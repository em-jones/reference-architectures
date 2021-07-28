import jones.em.application.CLIReaderV1
import jones.em.application.formatter
import jones.em.domain.RegisterService
import jones.em.domain.events.Reducers
import jones.em.domain.strategyV1
import java.util.*

fun isStillRunning(output: String) = output != "Bye"
fun main(args: Array<String>) {

  val reducerV1 = Reducers()
  val handlerStrategy = strategyV1(::formatter)
  val registerService = RegisterService(reducerV1::registerV1, CLIReaderV1(), handlerStrategy)
  var running = true
  while (running){
    println("Please enter your request:")
    val input = Optional.ofNullable(readLine())
    running = input.map(registerService::run).filter(::isStillRunning).isPresent
  }
}