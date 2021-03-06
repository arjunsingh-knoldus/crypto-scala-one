# crypto-scala-one

A command-line utility to get cryptocurrency prices, written in scala.
It Utilises the Coingecko API.

```
Usage: crypto COMMAND [options]

Commands:
list     List Available Coins and Currencies
price    Get crypto coin price in your currency.
ohlc     Get Open,High,Low,Close data for a crypto

More Help:
crypto command --help
```
## Commands
### 1. price: Get the current crypto value in terms or given currency
```console
> crypto price --help

Get current price of crypto in your currency

Usage: crypto price --coinid COINID --tocurrency CURRENCY [options]

Options:
--coinid, -c                 comma separated crypto-coin ids, eg bitcoin,monero [Required]
--tocurrency, -tc            comma separated currency, eg inr,usd,eur [Required]
--include_market_cap         true/false
--include_24hr_vol           true/false
--include_24hr_change        true/false
--include_last_updated_at    true/false

Note:
Use `list -t coins` to list available coins
Use `list -t currency` to list currency

```
Examples
```shell
> crypto price -c monero -tc inr
monero/inr : 14820.96
```

```shell
# Multiple coins  
> crypto price -c monero,bitcoin,dogecoin -tc inr,usd
monero/inr : 14820.96
monero/usd : 202.52
bitcoin/inr : 3507418.0
bitcoin/usd : 47928.0
dogecoin/inr : 3.73
dogecoin/usd : 0.050942
```
### 2. list: list available coins and currencies

```shell
> crypto list --help
Lists available crypto coins and currencies
Usage: crypto list --type T

Options:
--type, -t                   Any of (coins/currency) [Required]
                             coins: lists crypto coins, use with grep to refine results
                             currency: lists vs currencies
```
> NOTE: Use grep to refine the search for coins (huge list)

Example
```shell
> crypto list -t coins | { head -1;grep monero;}
ID                        Symbol        Name
exmr-monero               exmr          EXMR FDN
monero                    xmr           Monero
monero-classic-xmc        xmc           Monero-Classic
monerov                   xmv           MoneroV

```
### 3. ohlc: Get Open, High, Low, Close data for a cryptocurrency for (1/7/14/30/90/180/365/max) days. 

```console
> crypto ohlc --help
Get Open, High, Low, Close data for a crypto currency for specefic number of days.

Usage: crypto ohcl --coinid COINID --tocurrency CURRENCY --days DAYS

Required Options:
--coinid, -c                 crypto coin id
--tocurrency, -tc            The target/vs currency of market data
--days                       Data up to number of days ago any of (1/7/14/30/90/180/365/max)
```

Examples
```shell
> crypto ohlc -c bitcoin -tc inr --days 1
date time           	open        	high        	low         	close       
2021-03-05 23:00:00 	3517278.03  	3517278.03  	3517278.03  	3517278.03  
2021-03-05 23:30:00 	3521192.29  	3534409.32  	3518842.96  	3534366.92  
2021-03-06 00:00:00 	3538905.82  	3566510.26  	3538905.82  	3566510.26 
                    .     .     .     .
2021-03-06 21:30:00 	3493224.76  	3496874.72  	3493224.76  	3494235.42  
2021-03-06 22:00:00 	3477272.81  	3511905.64  	3477272.81  	3511194.79  
2021-03-06 22:30:00 	3509820.62  	3509820.62  	3496293.81  	3508833.41  
2021-03-06 23:00:00 	3507418.48  	3507878.08  	3506282.18  	3507878.08                    
```

## Release
### [0.1](https://github.com/arjunsingh-knoldus/crypto-scala-one/releases/tag/0.1)