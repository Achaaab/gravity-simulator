package com.github.achaaab.gravity_simulator;

import javafx.scene.paint.Paint;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Body {

	private String name;
	private double radius;
	private double mass;
	private Vector2 position;
	private Vector2 velocity;
	private Paint paint;
	private double visualScale;

	/**
	 * @param name
	 * @param radius
	 * @param mass
	 * @param paint
	 * @since 0.0.0
	 */
	public Body(String name, double radius, double mass, Paint paint) {

		this.name = name;
		this.radius = radius;
		this.mass = mass;
		this.paint = paint;

		position = new Vector2();
		velocity = new Vector2();
		visualScale = 1.0;
	}

	/**
	 * @param deltaTime
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
	 * @param radius radius in meters
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return mass in kilograms
	 * @since 0.0.0
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass mass in kilograms
	 * @since 0.0.0
	 */
	public void setMass(double mass) {
		this.mass = mass;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * @param position
	 * @since 0.0.0
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 getVelocity() {
		return velocity;
	}

	/**
	 * @param velocity
	 * @since 0.0.0
	 */
	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Paint getPaint() {
		return paint;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public double getVisualScale() {
		return visualScale;
	}

	/**
	 * @param visualScale
	 * @since 0.0.0
	 */
	public void setVisualScale(double visualScale) {
		this.visualScale = visualScale;
	}

	@Override
	public String toString() {
		return name;
	}
}
