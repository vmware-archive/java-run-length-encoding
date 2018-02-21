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
					boolean initial = true;
					int old;

					@Override
					public boolean test(Integer item) {
						if (initial) {
							initial = false;
							old = item;
							return false;
						}
						if (item != old) {
							old = item;
							return true;
						}
						return false;
					}
				}, true). // cut before item that terminates the buffer
				flatMap(l -> Flux.just(l.size(), l.get(0)));
	}
}
