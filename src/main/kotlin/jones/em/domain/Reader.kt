package jones.em.domain

interface Reader<T> {
  fun parse(s: String): T
}