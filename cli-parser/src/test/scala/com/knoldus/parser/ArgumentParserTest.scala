package com.knoldus.parser

import org.scalatest.flatspec.AsyncFlatSpec
import scala.collection.mutable.HashMap
class ArgumentParserTest extends AsyncFlatSpec {

  """Parsing args["--flag1", "value1", "-f2", "value2", "--flag3", "value3"]""" should "return a corresponding k-v map" in {
    val argsMap = ArgumentParser.parse(Array("--flag1", "value1", "-f2", "value2", "--flag3", "value3"))
    assert(argsMap == HashMap("flag1" -> "value1", "f2" -> "value2", "flag3" -> "value3"))
  }

  """Parsing args ["--flag1", "value1", "-f2" ]""" should "produce IllegalArgumentException" in {
    assertThrows[IllegalArgumentException] {
      val argsMap = ArgumentParser.parse(Array("--flag1", "value1", "-f2"))
    }
  }

  "-flagName" should "is a flag" in {
    assert(ArgumentParser.isFlag("-flagName"))
  }

  "noDashName" should "is not a flag" in {
    assert(!ArgumentParser.isFlag("noDashName"))
  }

}
