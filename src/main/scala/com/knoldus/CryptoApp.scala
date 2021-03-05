package com.knoldus

import com.knoldus.cgapi.CoinGeckoAPI
import com.knoldus.parser.ArgumentParser

import scala.collection.mutable.HashMap

object CryptoApp extends App {

  lazy val globalHelp =
    """Usage: crypto COMMAND [options]
      |
      |Commands:
      |list     List Available Coins and Currencies
      |price    Get crypto coin price in your currency.
      |ohlc     Get Open,High,Low,Close data for a crypto
      |
      |More Help:
      |crypto command --help
      |""".stripMargin

  lazy val listHelp =
    """Lists available crypto coins and currencies
      |Usage: crypto list --type T
      |
      |Options:
      |--type, -t                   Any of [coins,currency]
      |                             coins: lists cypto coins, use with grep to refine results
      |                             currency: lists vs currencies
      |""".stripMargin

  lazy val priceHelp =
    """Get current price of crypto in your currency
      |
      |Usage: crypto price --coinid COINID --tocurrency CURRENCY [options]
      |
      |Options:
      |--coinid, -c                 comma separated crypto-coin ids, eg bitcoin,monero
      |--tocurrency, -tc            comma separated currency, eg inr,usd,eur
      |--include_market_cap         true/false
      |--include_24hr_vol           true/false
      |--include_24hr_change        true/false
      |--include_last_updated_at    true/false
      |
      |Note:
      |Use `list -t coins` to list available coins
      |Use `list -t currency` to list currency
      |""".stripMargin

  lazy val ohclHelp =
    """Get Open, High, Low, Close data for a crypto currency for specefic number of days.
      |
      |Usage: crypto ohcl --coinid COINID --tocurrency CURRENCY --days DAYS
      |
      |Options:
      |--coinid, -c                 crypto coin id
      |--tocurrency, -tc            The target/vs currency of market data
      |--days                       Data up to number of days ago (1/7/14/30/90/180/365/max)
      |""".stripMargin


  def list(argsMap: HashMap[String, String]) = {
    (List("type", "t") collectFirst argsMap).fold {
      ArgumentParser.printHelpExit(listHelp)
    } {
      listType => println(CoinGeckoAPI.list(listType))
    }
  }

  def price(argsMap: HashMap[String, String]) = {
    val coinID: String = argsMap.getOrElse("coinid", argsMap.get("c").fold {
      ArgumentParser.printHelpExit(priceHelp)
      ""
    } {x=>x})
    val toCurrency: String = argsMap.getOrElse("tocurrency", argsMap.get("tc").fold {
      ArgumentParser.printHelpExit(priceHelp)
      ""
    } {x=>x})
    println(CoinGeckoAPI.getPrice(coinID, toCurrency, argsMap -- Set("coinid", "c", "tocurrency", "tc")))
  }

  def ohlc(argsMap: HashMap[String, String]) = {
    val coinID: String = argsMap.getOrElse("coinid", argsMap.get("c").fold {
      ArgumentParser.printHelpExit(ohclHelp)
      ""
    } {x=>x})
    val toCurrency: String = argsMap.getOrElse("tocurrency", argsMap.get("tc").fold {
      ArgumentParser.printHelpExit(ohclHelp)
      ""
    } {x=>x})
    val days: String = argsMap.get("days").fold {
      ArgumentParser.printHelpExit(ohclHelp)
      ""
    } {x=>x}
    println(CoinGeckoAPI.ohlc(coinID, toCurrency, days))
  }

  // parse sub-command
  (if (args.length > 0) ArgumentParser.command(args(0)) else None) match {
    case Some(cmd: String) => {
      val argsMap = ArgumentParser.parse(args.tail)
      cmd toLowerCase match {
        case "list" => list(argsMap)
        case "price" => price(argsMap)
        case "ohlc" => ohlc(argsMap)
        case _ => ArgumentParser.printHelpExit(globalHelp)
      }

    }
    case _ => ArgumentParser.printHelpExit(globalHelp)

  }
}
