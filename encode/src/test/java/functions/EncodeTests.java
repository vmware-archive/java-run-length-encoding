package functions;

import static org.junit.Assert.assertEquals;

import java.util.*;

import org.junit.Before;
import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class EncodeTests {

	Encode encode;

	@Before
	public void setUp() {
		encode = new Encode();
	}

	@Test
	public void testEmpty() {
		StepVerifier.create(Flux.<Integer>empty().as(encode))
			.verifyComplete();
	}

	@Test
	public void testSingle() {
		StepVerifier.create(Flux.just(0).as(encode))
			.expectNext(1, 0)
			.verifyComplete();
	}

	@Test
	public void testRuns() {
		StepVerifier.create(Flux.just(0, 0, 0, 1, 2, 2).as(encode))
			.expectNext(3, 0, 1, 1, 2, 2)
			.verifyComplete();
	}

	@Test
	public void testLong() {
		StepVerifier.create(Flux.fromIterable(Collections.nCopies(1000, 0)).as(encode))
			.expectNext(1000, 0)
			.verifyComplete();
	}


}
