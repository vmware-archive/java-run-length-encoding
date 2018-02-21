package functions;

import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;

public class DecodeTests {

	Decode decode;

	@Before
	public void setUp() {
		decode = new Decode();
	}

	@Test
	public void testEmpty() {
		StepVerifier.create(Flux.<Integer>empty().as(decode))
				.verifyComplete();
	}

	@Test
	public void testSingle() {
		StepVerifier.create(Flux.just(1, 0).as(decode))
				.expectNext(0)
				.verifyComplete();
	}

	@Test
	public void testRuns() {
		StepVerifier.create(Flux.just(3, 0, 1, 1, 2, 2).as(decode))
				.expectNext(0, 0, 0, 1, 2, 2)
				.verifyComplete();
	}

	@Test
	public void testLong() {
		StepVerifier.create(Flux.just(1000, 0).as(decode))
				.expectNextSequence(Collections.nCopies(1000, 0))
				.verifyComplete();
	}

	@Test
	public void testInvalid() {
		StepVerifier.create(Flux.just(0).as(decode)) // Count but no value
				.verifyError(IllegalArgumentException.class);
	}
}
