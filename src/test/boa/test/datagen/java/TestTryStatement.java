package boa.test.datagen.java;

import java.io.IOException;

import org.junit.Test;

public class TestTryStatement extends Java8BaseTest {

	@Test
	public void tryStatement() throws IOException {
		testWrapped(
			load("test/datagen/java/TryStatement.java").trim(),
			load("test/datagen/boa/TryStatement.boa").trim()
		);
	}
	
}
