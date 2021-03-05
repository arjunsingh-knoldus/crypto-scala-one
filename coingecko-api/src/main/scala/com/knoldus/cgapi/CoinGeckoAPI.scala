package com.knoldus.cgapi

import scala.collection.mutable.HashMap
import java.text.SimpleDateFormat

/**
 * Interacts with Coingecko API for crypto price
 */
object CoinGeckoAPI {
  val apiURL = "https://api.coingecko.com/api/v3"

  def jsonResponse(url: String): ujson.Value.Value = {
    val r = requests.get(url)
    val json = ujson.read(r.text)
    json
  }

  def list(param: String): String = param match {
    case "coin" | "coins" => jsonResponse(s"$apiURL/coins/list?include_platform=false").arr.map({
      x => {
        val id = x.obj.value.getOrElse("id", "").toString.replaceAll("\"", "").trim
        val symbol = x.obj.value.getOrElse("symbol", "").toString.replaceAll("\"", "").trim
        val name = x.obj.value.getOrElse("name", "").toString.replaceAll("\"", "").trim
        s"\033[1;35mid:\033[0m $id\t\033[1;35msymbol:\033[0m $symbol\t\033[1;35mname:\033[0m $name"
      }
    }).mkString("\n")
    case "currency" => jsonResponse(s"$apiURL/simple/supported_vs_currencies").arr.map(x => s"${x.toString.replaceAll("\"", "")}").mkString("\t")
  }

  def getPrice(coinIDs: String, toCurrencies: String, options: HashMap[String, String]): String = {
    val json = jsonResponse(s"$apiURL/simple/price?ids=$coinIDs&vs_currencies=$toCurrencies${options.map { case (k, v) => s"$k=$v" }.mkString("&", "&", "")}")

    (for (coin <- coinIDs.split(","); currency <- toCurrencies.split(","))
      yield s"$coin/$currency : ${json(coin)(currency).num}"
      ).mkString("Prices\n", "\n", "")
  }
  private def timeToStr(epochMillis: Long) = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(epochMillis).toString

  def ohlc(coin: String, currency: String, days: String) = {
    jsonResponse(s"$apiURL/coins/$coin/ohlc?vs_currency=$currency&days=$days").arr.map(tohlc =>
      s"${timeToStr(tohlc(0).toString.toLong).padTo(20, ' ')}\t${tohlc(1).toString.padTo(12,' ')}\t${tohlc(2).toString.padTo(12,' ')}\t${tohlc(3).toString.padTo(12,' ')}\t${tohlc(4).toString.padTo(12,' ')}").mkString(s"${"time".padTo(20, ' ')}\t${"open".padTo(12, ' ')}\t${"high".padTo(12, ' ')}\t${"low".padTo(12, ' ')}\t${"close".padTo(12, ' ')}\n", "\n", "")
  }
}
