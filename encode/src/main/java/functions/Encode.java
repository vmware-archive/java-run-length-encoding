package functions;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

public class Encode implements Function<Flux<Integer>, Flux<Integer>> {
	public Flux<Integer> apply(Flux<Integer> input) {
		return input.
			bufferUntil(new Predicate<Integer>() {
				AtomicReference<Integer> old = new AtomicReference<>();

				@Override
				public boolean test(Integer item) {
					return !item.equals(old.getAndSet(item));
				}
			}, true). // cut before item that terminates the buffer
			flatMap(buf -> Flux.just(buf.size(), buf.get(0)));
	}
}
