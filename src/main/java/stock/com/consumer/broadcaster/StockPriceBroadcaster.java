package stock.com.consumer.broadcaster;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.Map;

@Component
public class StockPriceBroadcaster {

    private final Sinks.Many<Map<String,Object>> sink = Sinks.many().multicast().onBackpressureBuffer();

    public void publish(Map<String,Object> price) {
        sink.tryEmitNext(price);
    }

    public Flux<Map<String,Object>> stream() {
        Flux<Map<String,Object>> flux = sink.asFlux();
        System.out.println("stream stock price: subscribing to flux");
        flux.doOnNext(price -> System.out.println("streamed stock price: " + price)).subscribe();
        return flux;
    }
}
