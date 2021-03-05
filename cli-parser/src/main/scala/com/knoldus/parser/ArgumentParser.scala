package com.knoldus.parser

import scala.annotation.tailrec
import scala.collection.mutable

/** Utility Methods To Parse Arguments */
object ArgumentParser {
  /**
   * Parse Argument Array
   *
   * @param args CLI Argument Array
   * @return a map with flags as keys and corresponding values
   */
  def parse(args: Array[String]): mutable.HashMap[String, String] = {
    val argsMap = mutable.HashMap[String, String]()

    @tailrec
    def recParse(argsList: List[String]): Unit = {
      argsList match {
        case flag :: flagVal :: restList if isFlag(flag) => {
          argsMap += flag.dropWhile(_ == '-') -> flagVal
          recParse(restList)
        }
        case Nil => {}
        case _ => {
          throw new IllegalArgumentException("Wrong format")
        }
      }
    }

    recParse(args.toList)
    argsMap
  }

  def isFlag(argument: String): Boolean = argument(0) == '-'

  /** Check Argument to see if its a command */
  def command(firstArg: String): Option[String] = if (!isFlag(firstArg)) Some(firstArg) else None

  /** Prints Text and Exit */
  def printHelpExit(message: String): Unit = {
    print(message)
    System.exit(0)
  }
}
