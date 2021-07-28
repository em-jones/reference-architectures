package jones.em.domain

import jones.em.domain.denominations.USD
import jones.em.domain.events.ChangeMade
import jones.em.domain.events.DenominationsPut
import jones.em.domain.events.DenominationsTaken
import jones.em.domain.operations.MakeChange
import jones.em.domain.operations.PutDenominations
import jones.em.domain.operations.RequestRegisterStatus
import jones.em.domain.operations.TakeDenominations
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

import java.math.BigInteger

internal class HandlersKtTest {

  fun registerWithFundsMap() = mapOf(
    20 to USD.twentyUSD(BigInteger.TEN),
    10 to USD.tenUSD(BigInteger.TEN),
    5 to USD.fiveUSD(BigInteger.TEN),
    2 to USD.twoUSD(BigInteger.TEN),
    1 to USD.oneUSD(BigInteger.TEN)
  )

  fun registerWithoutFundsMap() = mapOf(
    20 to USD.twentyUSD(BigInteger.ZERO),
    10 to USD.tenUSD(BigInteger.ZERO),
    5 to USD.fiveUSD(BigInteger.ZERO),
    2 to USD.twoUSD(BigInteger.ZERO),
    1 to USD.oneUSD(BigInteger.ZERO)
  )

  @Test
  fun handleOperation_success() {
    // Arrange
    val register = RegisterStatus(registerWithFundsMap())
    val putRequest = PutDenominations(listOf(USD.twentyUSD(BigInteger.TEN)))
    val takeRequest = TakeDenominations(listOf(USD.twentyUSD(BigInteger.TWO)))
    val makeChangeRequest = MakeChange(Amount.toAmount("20"))

    // Act
    val putEvent = handleOperation(register, putRequest)
    val takeEvent = handleOperation(register, takeRequest)
    val changeMadeEvent = handleOperation(register, makeChangeRequest)

    // Assert
    assertThat(putEvent, instanceOf(DenominationsPut::class.java))
    assertThat(takeEvent, instanceOf(DenominationsTaken::class.java))
    assertThat(changeMadeEvent, instanceOf(ChangeMade::class.java))
  }

  @Test
  fun handleOperation_fail() {
    // Arrange
    val register = RegisterStatus(registerWithFundsMap())
    val badRequest = RequestRegisterStatus()

    // Assert
    assertThrows<Exception> { handleOperation(register, badRequest) }

  }

  @Test
  fun tryTake_success() {
    // Arrange
    val expectedValue = 20
    val expectedAmountTaken = BigInteger.TEN
    val registerWithFunds = RegisterStatus(registerWithFundsMap())
    val successfulTakeRequest = TakeDenominations(listOf(USD.twentyUSD(expectedAmountTaken)))

    // Act
    tryTake(registerWithFunds, successfulTakeRequest).also { it ->
      // Assert
      assertThat(it, instanceOf(DenominationsTaken::class.java))
      assertThat((it as DenominationsTaken).denominations.size, `is`(1))
      assertThat(it.denominations[0].count, `is`(expectedAmountTaken))
      assertThat(it.denominations[0].value, `is`(expectedValue))
    }
  }

  @Test
  fun tryTake_fail() {
    // Arrange
    val expectedAmountTaken = BigInteger.TEN
    val register = RegisterStatus(registerWithoutFundsMap())
    val takeRequest = TakeDenominations(listOf(USD.twentyUSD(expectedAmountTaken)))

    // Assert
    assertThrows<Exception> { tryTake(register, takeRequest) }
  }

  @Test
  fun tryMakeChange_success() {
  }

  @Test
  fun tryMakeChange_fail() {
  }

  @Test
  fun putDenominations() {
  }

  @Test
  fun makeChange_success() {
  }

  @Test
  fun makeChange_fail() {
  }
}