package integration;

import functions.Decode;
import functions.Encode;
import org.junit.Before;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Collections;

public class DecodeEncodeRoundTripTests {

	private static class TestError extends RuntimeException {}

	Encode encode;
	Decode decode;

	@Before
	public void setUp() {
		encode = new Encode();
		decode = new Decode();
	}


	@Test
	public void testEmpty() {
		StepVerifier.create(Flux.<Integer>empty().as(decode).as(encode))
			.verifyComplete();
	}

	@Test
	public void testSingle() {
		StepVerifier.create(Flux.just(1, 0).as(decode).as(encode))
			.expectNext(1,0)
			.verifyComplete();
	}

	@Test
	public void testRuns() {
		StepVerifier.create(Flux.just(3, 0, 1, 1, 2, 2).as(decode).as(encode))
			.expectNext(3, 0, 1, 1, 2, 2)
			.verifyComplete();
	}

	@Test
	public void testDegenerateRun() {
		StepVerifier.create(Flux.just(0, 1).as(decode).as(encode))
			.verifyComplete();
	}

	@Test
	public void testDoubleRun() {
		StepVerifier.create(Flux.just(1, 1, 1, 1).as(decode).as(encode))
			.expectNext(2, 1)
			.verifyComplete();
	}

	@Test
	public void testLong() {
		StepVerifier.create(Flux.just(1000, 0).as(decode).as(encode))
			.expectNext(1000, 0)
			.verifyComplete();
	}

	@Test
	public void testCountWithNoValue() {
		StepVerifier.create(Flux.just(2, 1, 0 ).as(decode).as(encode))
			.verifyError(IllegalArgumentException.class);
	}

	@Test
	public void testNegativeCount() {
		StepVerifier.create(Flux.just(-1, 0).as(decode).as(encode))
			.verifyError(IllegalArgumentException.class);
	}

	@Test
	public void testErrorOnly() {
		StepVerifier.create(Flux.<Integer>error(new TestError()).as(decode).as(encode))
			.verifyError(TestError.class);
	}
	
	@Test
	public void testCountWithNoValueOnError() {
		StepVerifier.create(Flux.concatDelayError(Flux.just(1), Flux.error(new TestError())).as(decode).as(encode))
			.verifyError(TestError.class);
	}

	@Test
	public void testEncodeAbortedByError() {
		StepVerifier.create(Flux.concatDelayError(Flux.just(2, 1), Flux.error(new TestError())).as(decode).as(encode))
			.verifyError(TestError.class);
	}
}
