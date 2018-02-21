package functions;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

public class Decode implements Function<Flux<Integer>, Flux<Integer>> {
	public Flux<Integer> apply(Flux<Integer> input) {
		return input.
				bufferUntil(new Predicate<Integer>() {
					boolean emit = true;

					@Override
					public boolean test(Integer item) {
						emit = !emit;
						return emit;
					}
				}).
				flatMap(l -> {
					if (l.size() != 2) {
						return Flux.error(new IllegalArgumentException("A run-length encoded stream must not have an odd number of elements"));
					}
					return Flux.fromIterable(Collections.nCopies(l.get(0), l.get(1)));
				});
	}
}
