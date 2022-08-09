package com.github.achaaab.gravity_simulator;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import static java.lang.Math.log;
import static java.lang.Math.max;
import static javafx.scene.paint.Color.BLACK;

/**
 * JavaFX view of universe
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class UniverseView extends Canvas {

	/**
	 * display scale, in pixel per meter
	 */
	private static final double INITIAL_SCALE = 1.0E-10;

	/**
	 * display radius of the smallest body, in pixels
	 */
	private static final double MINIMUM_DISPLAY_RADIUS = 2.0;

	private List<Body> bodies;
	private double scale;
	private final Property<Body> anchor;

	/**
	 * @since 0.0.0
	 */
	public UniverseView() {

		scale = INITIAL_SCALE;

		setWidth(1600);
		setHeight(900);

		anchor = new SimpleObjectProperty<>();
	}

	/**
	 * @param bodies
	 * @since 0.0.0
	 */
	public void setBodies(List<Body> bodies) {
		this.bodies = bodies;
	}

	/**
	 * @return anchor property
	 * @since 0.0.0
	 */
	public Property<Body> anchor() {
		return anchor;
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
		graphicsContext.scale(scale, scale);

		if (anchor.getValue() != null) {

			var anchorPosition = anchor.getValue().getPosition();
			graphicsContext.translate(-anchorPosition.getX(), -anchorPosition.getY());
		}

		bodies.stream().
				mapToDouble(Body::getRadius).
				min().ifPresent(
						minimalRadius -> bodies.forEach(body -> draw(body, graphicsContext, minimalRadius)));

		graphicsContext.restore();
	}

	/**
	 * Draws a body using the given graphics context.
	 *
	 * @param body body to draw
	 * @param graphicsContext graphics context
	 * @param minimalRadius radius of the smallest body to draw, used to scale bodies
	 * so that no body is too small or too big
	 * @since 0.0.0
	 */
	private void draw(Body body, GraphicsContext graphicsContext, double minimalRadius) {

		var paint = body.getPaint();

		var position = body.getPosition();
		var radius = body.getRadius();

		var displayRadius = max(MINIMUM_DISPLAY_RADIUS * (1 + log(radius / minimalRadius)) / scale, radius);

		graphicsContext.setFill(paint);

		graphicsContext.fillOval(
				position.getX() - displayRadius,
				position.getY() - displayRadius,
				2 * displayRadius,
				2 * displayRadius);
	}

	/**
	 * @return display scale in meters per pixel
	 * @since 0.0.0
	 */
	public double getScale() {
		return scale;
	}

	/**
	 * @param scale display scale in meters per pixel
	 * @since 0.0.0
	 */
	public void setScale(double scale) {
		this.scale = scale;
	}
}
