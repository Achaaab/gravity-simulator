package com.github.achaaab.gravity_simulator;

import javafx.scene.paint.Paint;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Body {

	private final String name;
	private final double radius;
	private final double mass;
	private final Paint paint;

	private Vector2 position;
	private Vector2 velocity;

	/**
	 * Creates a new body.
	 *
	 * @param name name of the body
	 * @param radius radius of the body, in meters
	 * @param mass mass of the body, in kilograms
	 * @param paint how to paint the body
	 * @since 0.0.0
	 */
	public Body(String name, double radius, double mass, Paint paint) {

		this.name = name;
		this.radius = radius;
		this.mass = mass;
		this.paint = paint;

		position = new Vector2();
		velocity = new Vector2();
	}

	/**
	 * @param deltaTime time elapsed since the last update, in seconds
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {
		position = position.plus(velocity.times(deltaTime));
	}

	/**
	 * @return radius in meters
	 * @since 0.0.0
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @return mass in kilograms
	 * @since 0.0.0
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @return position of this body, in meters x meters
	 * @since 0.0.0
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position position of this body, in meters on x axis and meters on y axis
	 * @since 0.0.0
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @return velocity of this body, in meters per second on x axis and meters per second on y axis
	 * @since 0.0.0
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity velocity of this body, in meters per second on x axis and meters per second on y axis
	 * @since 0.0.0
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return how this body should be painted
	 * @since 0.0.0
	 */
	public Paint getPaint() {
		return paint;
	}

	@Override
	public String toString() {
		return name;
	}
}
