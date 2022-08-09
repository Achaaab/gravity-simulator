package com.github.achaaab.gravity_simulator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link Vector2}
 * 
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestVector2 {

	@Test
	void testTimes() {

		var vector = new Vector2(1.0, 2.0);
		assertEquals(new Vector2(3.0, 6.0), vector.times(3.0));
		assertEquals(new Vector2(0.5, 1.0), vector.times(0.5));
		assertEquals(new Vector2(0.0, 0.0), vector.times(0.0));
		assertEquals(new Vector2(-2.0, -4.0), vector.times(-2.0));
	}
}
