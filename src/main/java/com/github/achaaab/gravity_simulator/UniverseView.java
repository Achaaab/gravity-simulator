package com.github.achaaab.gravity_simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static java.lang.Math.log;
import static java.lang.Math.pow;
import static javafx.scene.paint.Color.BLACK;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class UniverseView extends Canvas {

	/**
	 * display scale, in meters per pixel
	 */
	private static final double INITIAL_SCALE = 1E10;

	/**
	 * display radius of the smallest body, in pixels
	 */
	private static final double MINIMUM_VISUAL_RADIUS = 2.0;

	private final UniverseModel model;
	private double scale;

	/**
	 * @param model
	 * @since 0.0.0
	 */
	public UniverseView(UniverseModel model) {

		this.model = model;
		scale = INITIAL_SCALE;

		setWidth(1600);
		setHeight(900);

		setOnScroll(scroll -> scale /= pow(2, scroll.getDeltaY() / 100));
	}

	/**
	 * @since 0.0.0
	 */
	public void draw() {

		var width = getWidth();
		var height = getHeight();

		var graphicsContext = getGraphicsContext2D();
		graphicsContext.setFill(BLACK);
		graphicsContext.fillRect(0, 0, width, height);

		graphicsContext.save();

		graphicsContext.translate(width / 2, height / 2);
		graphicsContext.scale(1 / scale, 1 / scale);

		var bodies = model.getBodies();

		bodies.stream().
				mapToDouble(Body::getRadius).
				min().ifPresent(
						minimalRadius -> bodies.forEach(body -> draw(body, graphicsContext, minimalRadius)));

		graphicsContext.restore();
	}

	/**
	 * @param body
	 * @param graphicsContext
	 * @param minimalRadius
	 * @since 0.0.0
	 */
	private void draw(Body body, GraphicsContext graphicsContext, double minimalRadius) {

		var paint = body.getPaint();

		var position = body.getPosition();
		var radius = body.getRadius();

		var visualRadius = scale * MINIMUM_VISUAL_RADIUS * log(radius / minimalRadius);

		graphicsContext.setFill(paint);

		graphicsContext.fillOval(
				position.getX() - visualRadius,
				position.getY() - visualRadius,
				2 * visualRadius,
				2 * visualRadius);
	}
}
