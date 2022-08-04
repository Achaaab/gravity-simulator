package com.github.achaaab.gravity_simulator;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import static javafx.scene.paint.Color.BLACK;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class UniverseView extends Canvas {

	/**
	 * reality to screen scale in meters per pixel
	 */
	private static final double SCALE = 1.0 / 2_000_000_000;

	private final UniverseModel model;

	/**
	 * @param model
	 * @since 0.0.0
	 */
	public UniverseView(UniverseModel model) {

		this.model = model;

		setWidth(1600);
		setHeight(900);
	}

	/**
	 * @since 0.0.0
	 */
	public void draw() {

		var graphicsContext = getGraphicsContext2D();
		graphicsContext.setFill(BLACK);
		graphicsContext.fillRect(0, 0, getWidth(), getHeight());

		graphicsContext.save();
		graphicsContext.translate(getWidth() / 2, getHeight() / 2);
		graphicsContext.scale(SCALE, SCALE);
		model.getBodies().forEach(body -> draw(body, graphicsContext));
		graphicsContext.restore();
	}

	/**
	 * @param body
	 * @param graphicsContext
	 * @since 0.0.0
	 */
	private void draw(Body body, GraphicsContext graphicsContext) {

		var paint = body.getPaint();

		var position = body.getPosition();
		var radius = body.getRadius();
		var visualScale = body.getVisualScale();

		var visualRadius = radius * visualScale;

		graphicsContext.setFill(paint);

		graphicsContext.fillOval(
				position.getX() - visualRadius,
				position.getY() - visualRadius,
				2 * visualRadius,
				2 * visualRadius);
	}
}
