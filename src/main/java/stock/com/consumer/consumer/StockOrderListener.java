package stock.com.consumer.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Component
public class StockOrderListener {

    private static final Logger logger = LoggerFactory.getLogger(StockOrderListener.class);

    private final ObjectMapper objectMapper;

    public StockOrderListener(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "stock-orders", groupId = "stock-consumer-group")
    public void listen(String message) {
        try {
            Map<String, Object> order = objectMapper.readValue(message, Map.class);
            logger.info("Parsed order: {}", order);
            // Add your processing logic here
        } catch (Exception e) {
            logger.error("Failed to parse message", e);
        }
    }
}
