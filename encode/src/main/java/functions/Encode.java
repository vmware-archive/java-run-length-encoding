package functions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Encode implements Function<Flux<Integer>, Flux<Integer>> {
	public Flux<Integer> apply(Flux<Integer> input) {
		return input.
			bufferUntil(new Predicate<Integer>() {
				Integer old;

				@Override
				public boolean test(Integer item) {
					try {
						return !item.equals(old);
					} finally {
						old = item;
					}
				}
			}, true). // cut before item that terminates the buffer
			flatMap(buf -> Flux.just(buf.size(), buf.get(0)));
	}
}
