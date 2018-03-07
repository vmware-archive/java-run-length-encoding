package functions;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class Encode implements Function<Flux<Integer>, Flux<Integer>> {

	private static final int ANY_INT = 99; // any integer will do

	public Flux<Integer> apply(Flux<Integer> input) {
		return input.compose(source -> {
			AtomicReference<Integer> last = new AtomicReference<>(null);
			return source.windowUntil(i -> !i.equals(last.getAndSet(i)), true)
					.filter(it -> last.get() != null)
					.flatMap(run -> run
							.onErrorResume(t -> Mono.empty())
							.count()
							.map(Long::intValue)
							.concatWith(Mono.just(last.get()))
					);
		});
	}

	private static Flux<Flux<Integer>> partitionBySameValue(Flux<Integer> source) {
		Flux<Integer> windowSource = source.publish()
			.refCount(2);

		return windowSource.window(windowSource.distinctUntilChanged());
	}

	private static Flux<Tuple2<Integer, Integer>> encode(Flux<Flux<Integer>> partitions) {
		return partitions
				.flatMap(window -> window
						//we use scan and takeLast(1) to keep the state in case of error
						.scan(
								Tuples.of(ANY_INT, 0),
								(state, next) -> Tuples.of(next, state.getT2() + 1)
						)
						//window will also signal the flatMap coordinator, so we must ignore errors here
						.onErrorResume(t -> Mono.empty())
						//as a result, the last step of the run count reduction is still visible and we can emit it
						.takeLast(1)
				)
				.filter(t -> t.getT2() > 0); // window produces an empty first window
	}

	private static Flux<Integer> deTuple(Flux<Tuple2<Integer, Integer>> encoding) {
		return encoding.
			flatMap(t -> Flux.just(t.getT2(), t.getT1()));
	}
}
