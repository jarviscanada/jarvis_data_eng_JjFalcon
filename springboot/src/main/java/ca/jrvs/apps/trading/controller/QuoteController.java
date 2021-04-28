package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.IexQuote;
import ca.jrvs.apps.trading.model.domain.Quote;
import ca.jrvs.apps.trading.service.QuoteService;
import ca.jrvs.apps.trading.util.ResponseExceptionUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/quote")
public class QuoteController {

  private QuoteService quoteService;

  public QuoteController(QuoteService quoteService) {
    this.quoteService = quoteService;
  }

  @ApiOperation(value = "Show iexQuote", notes = "Show iexQuote for a given ticker/symbol")
  @ApiResponses(value = {@ApiResponse(code = 404, message = "ticker is not found")})
  // GET /iex/ticker/{ticker}
  @GetMapping(path = "/iex/ticker/{ticker}")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public IexQuote getQuote(@PathVariable String ticker) {
    try {
      return quoteService.findIexQuoteByTicker(ticker);
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Update quote table using iex data", notes = "Update all quotes in the quote table.  Use IEX trading API as market data source.")
  // PUT /iexMarketData
  @PutMapping(path = "/iexMarketData")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> updateMarketData(){
    try {
      return quoteService.updateMarketData();
    } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Update a give quote in the quote table", notes = "Manually update a quote in the quote table using IEX market data")
  @PutMapping(path = "/")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public Quote putQuote(@RequestBody Quote quote){
    try {
      return quoteService.saveQuote(quote);
          } catch (Exception e) {
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value= "Add a new ticker to the dailyList (quote table)", notes = "Add a new ticker/symbol to the quote table, so tracker can trade this security.")
  @PostMapping(path = "/tickerId/{ticker}")
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(value = {@ApiResponse(code =404, message = "ticker is not found in IEX system")})
  @ResponseBody
  public Quote createQuote(@PathVariable String tickerId){
    try {
      return quoteService.saveQuote(tickerId);
    } catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }

  @ApiOperation(value = "Show the dailyList", notes = "Show dailyList for this trading system.")
  @GetMapping(path = "/dailyList")
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public List<Quote> getDailyLIst() {
    try {
      return quoteService.findAllQuotes();
    } catch (Exception e){
      throw ResponseExceptionUtil.getResponseStatusException(e);
    }
  }
}