package com.github.achaaab.gravity_simulator;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.util.Objects.hash;

/**
 * vector in 2-dimensional space
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class Vector2 {

	private double x;
	private double y;

	/**
	 * @since 0.0.0
	 */
	public Vector2() {
		this(0, 0);
	}

	/**
	 * @param x
	 * @param y
	 * @since 0.0.0
	 */
	public Vector2(double x, double y) {

		this.x = x;
		this.y = y;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public double magnitude() {
		return sqrt(x * x + y * y);
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public double squaredMagnitude() {
		return x * x + y * y;
	}

	/**
	 * @param that
	 * @return {@code this - that}
	 * @since 0.0.0
	 */
	public Vector2 minus(Vector2 that) {
		return new Vector2(this.x - that.x, this.y - that.y);
	}

	/**
	 * @param that
	 * @return {@code this + that}
	 * @since 0.0.0
	 */
	public Vector2 plus(Vector2 that) {
		return new Vector2(this.x + that.x, this.y + that.y);
	}

	/**
	 * Adds that to this.
	 *
	 * @param that
	 * @since 0.0.0
	 */
	public void setPlus(Vector2 that) {

		x += that.x;
		y += that.y;
	}

	/**
	 * @param scalar
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 times(double scalar) {
		return new Vector2(x * scalar, y * scalar);
	}

	/**
	 * @param scalar
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 divide(double scalar) {
		return new Vector2(x / scalar, y / scalar);
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 normalize() {
		return divide(magnitude());
	}

	/**
	 * @param theta
	 * @return
	 * @since 0.0.0
	 */
	public Vector2 rotate(double theta) {

		return new Vector2(
				x * cos(theta) - y * sin(theta),
				x * sin(theta) + y * cos(theta));
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public double getX() {
		return x;
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public double getY() {
		return y;
	}

	@Override
	public boolean equals(Object object) {

		if (this == object) {
			return true;
		}

		if (object == null || getClass() != object.getClass()) {
			return false;
		}

		var vector2 = (Vector2) object;

		return Double.compare(vector2.x, x) == 0 &&
				Double.compare(vector2.y, y) == 0;
	}

	@Override
	public int hashCode() {

		return hash(
				x,
				y);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}
