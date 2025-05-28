package stock.com.consumer.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import stock.com.consumer.broadcaster.StockPriceBroadcaster;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/stocks")
public class StockStreamController {


    private final StockPriceBroadcaster broadcaster;

    public StockStreamController(StockPriceBroadcaster broadcaster) {
        this.broadcaster = broadcaster;
    }

    @GetMapping(value = "/live", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<Map<String, Object>> streamLivePrices() {
        return broadcaster.stream();
    }
}
