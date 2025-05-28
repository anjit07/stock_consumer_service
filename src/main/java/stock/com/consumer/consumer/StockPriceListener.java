package stock.com.consumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import stock.com.consumer.broadcaster.StockPriceBroadcaster;

import java.util.Map;

public class StockPriceListener {

    private static final Logger logger = LoggerFactory.getLogger(StockPriceListener.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final StockPriceBroadcaster broadcaster;

    public StockPriceListener(StockPriceBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @KafkaListener(topics = "stock-prices", groupId = "stock-ticker")
    public void consume(String json) throws JsonProcessingException {
        Map<String, Object> price = mapper.readValue(json, Map.class);
        logger.info("Stock prices: {}", price);
        broadcaster.publish(price);
    }

}
