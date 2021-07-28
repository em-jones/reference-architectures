package jones.em.domain

import jones.em.application.CLIReaderV1
import jones.em.application.formatter
import jones.em.domain.events.Reducers
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class RegisterTest {

  @Test
  fun handle() {
    val reducerV1 = Reducers()
    val handlerStrategy = strategyV1(::formatter)
    val registerService = RegisterService(reducerV1::registerV1, CLIReaderV1(), handlerStrategy)
    registerService.run("put 1 2 3 4 5")
    registerService.run("show")
    registerService.run("put 1 2 3 0 5")
    registerService.run("take 1 4 3 0 10")
    registerService.run("change 11")
    registerService.run("change 14")
    registerService.run("quit")
  }

  @Test
  fun getState() {
  }
}