package com.github.achaaab.gravity_simulator;

import org.junit.jupiter.api.Test;

import static javafx.scene.paint.Color.WHITE;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * unit tests of {@link Body}
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
class TestBody {

	@Test
	void testUpdate() {

		var body = new Body("body", 1.0, 1.0, WHITE);

		body.setPosition(new Vector2(0.0, 0.0));

		body.setVelocity(new Vector2(1.0, 2.0));
		body.update(3.0);
		assertEquals(new Vector2(3.0, 6.0), body.getPosition());

		body.setVelocity(new Vector2(-1.0, 2.0));
		body.update(-1.0);
		assertEquals(new Vector2(4.0, 4.0), body.getPosition());

		body.setVelocity(new Vector2(0.0, 0.0));
		body.update(10.0);
		assertEquals(new Vector2(4.0, 4.0), body.getPosition());

		body.setVelocity(new Vector2(10.0, -5.0));
		body.update(0.0);
		assertEquals(new Vector2(4.0, 4.0), body.getPosition());
	}
}
