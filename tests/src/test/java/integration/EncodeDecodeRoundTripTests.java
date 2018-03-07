package integration;

import functions.Decode;
import functions.Encode;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;

public class EncodeDecodeRoundTripTests {

	private static class TestError extends RuntimeException {
	}

	Encode encode;
	Decode decode;

	@Before
	public void setUp() {
		encode = new Encode();
		decode = new Decode();
	}

	@Test
	public void testEmpty() {
		StepVerifier.create(Flux.<Integer>empty().as(encode).as(decode))
			.verifyComplete();
	}

	@Test
	public void testSingle() {
		StepVerifier.create(Flux.just(0).as(encode).as(decode))
			.expectNext(0)
			.verifyComplete();
	}

	@Test
	public void testRuns() {
		StepVerifier.create(Flux.just(0, 0, 0, 1, 2, 2).as(encode).as(decode))
			.expectNext(0, 0, 0, 1, 2, 2)
			.verifyComplete();
	}

	@Test
	public void testLong() {
		StepVerifier.create(Flux.fromIterable(Collections.nCopies(1000, 0)).as(encode).as(decode))
			.expectNextSequence(Collections.nCopies(1000, 0))
			.verifyComplete();
	}

	@Test
	public void testErrorOnly() {
		StepVerifier.create(Flux.<Integer>error(new TestError()).as(encode).as(decode))
			.verifyError(TestError.class);
	}

	@Test
	public void testError() {
		StepVerifier.create(Flux.concatDelayError(Flux.just(1, 1), Flux.error(new TestError())).as(encode).as(decode))
			.expectNext(1, 1)
			.verifyError(TestError.class);
	}
}
