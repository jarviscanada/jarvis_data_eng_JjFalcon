package ca.jrvs.apps.trading.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  private Logger logger = LoggerFactory.getLogger(AppConfig.class);

  @Bean
  public MarketDataConfig marketDataConfig() {
    MarketDataConfig marketDataConfig = new MarketDataConfig();
    marketDataConfig.setHost("https://cloud.iexapis.com/v1/");
    marketDataConfig.setToken(System.getenv("IEX_TOKEN"));
    return marketDataConfig;
  }
}
