package functions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Decode implements Function<Flux<Integer>, Flux<Integer>> {
	public Flux<Integer> apply(Flux<Integer> input) {
		return input.
			buffer(2).
			flatMap(buf -> {
				if (buf.size() != 2) {
					return Flux.error(new IllegalArgumentException("A run-length encoded stream must not have an odd number of elements"));
				}
				return Flux.fromIterable(Collections.nCopies(buf.get(0), buf.get(1)));
			});
	}
}
