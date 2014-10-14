package piconot

/**
 * Modified by Sarah Gilkinson and Hayden Blauzvern
 */

case class PiconotException(str: String) extends Exception(str: String) {
  override def fillInStackTrace() = this
}
