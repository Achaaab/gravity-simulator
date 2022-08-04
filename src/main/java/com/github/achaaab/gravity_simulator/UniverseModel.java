package com.github.achaaab.gravity_simulator;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.util.function.Function.identity;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toMap;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class UniverseModel {

	/**
	 * gravitational constant in m<sup>3</sup> kg<sup>-1</sup> s<sup>-2</sup>
	 */
	public static final double G = 6.6743E-11;

	/**
	 * @param primaryBody
	 * @param secondaryBody
	 * @since 0.0.0
	 */
	public static Vector2 getCircularOrbitalVelocity(Body primaryBody, Body secondaryBody) {

		var primaryToSecondary = secondaryBody.getPosition().minus(primaryBody.getPosition());
		var radius = primaryToSecondary.getMagnitude();
		var direction = primaryToSecondary.rotate(PI / 2).normalize();
		var magnitude = sqrt(G * primaryBody.getMass() / radius);

		return direction.times(magnitude);
	}

	private final Set<Body> bodies;

	/**
	 * @since 0.0.0
	 */
	public UniverseModel() {
		bodies = new HashSet<>();
	}

	/**
	 * @param deltaTime time elapsed since last update, in seconds
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {

		// first step: collect exerted forces
		var forces = bodies.stream().collect(toMap(identity(), this::getForces));

		// second step: apply collected forces
		forces.forEach((body, bodyForces) -> apply(bodyForces, body, deltaTime));

		// third step: update bodies
		bodies.forEach(body -> body.update(deltaTime));
	}

	/**
	 * @param forces
	 * @param body
	 * @param deltaTime
	 * @since 0.0.0
	 */
	public void apply(Set<Vector2> forces, Body body, double deltaTime) {

		var resultingForce = forces.stream().
				reduce(Vector2::plus).
				orElse(new Vector2());

		apply(resultingForce, body, deltaTime);
	}

	/**
	 * @param force
	 * @param body
	 * @param deltaTime
	 * @since 0.0.0
	 */
	public void apply(Vector2 force, Body body, double deltaTime) {

		var mass = body.getMass();
		var velocity = body.getVelocity();
		var acceleration = force.divide(mass);

		body.setVelocity(velocity.plus(acceleration.times(deltaTime)));
	}

	/**
	 * @param body
	 * @return
	 * @since 0.0.0
	 */
	private Set<Vector2> getForces(Body body) {

		var forces = new HashSet<Vector2>();

		addGravitationalForces(body, forces);

		return forces;
	}

	/**
	 * @param body
	 * @param forces
	 * @since 0.0.0
	 */
	private void addGravitationalForces(Body body, Set<Vector2> forces) {

		bodies.stream().
				filter(not(body::equals)).
				forEach(otherBody -> addGravitationalForce(body, otherBody, forces));
	}

	/**
	 * Computes and adds the gravitational force exerted by body1 on body0.
	 *
	 * @param body0
	 * @param body1
	 * @since 0.0.0
	 */
	private void addGravitationalForce(Body body0, Body body1, Set<Vector2> forces) {

		var mass0 = body0.getMass();
		var mass1 = body1.getMass();
		var position0 = body0.getPosition();
		var position1 = body1.getPosition();

		var deltaPosition = position1.minus(position0);
		var squaredDistance = deltaPosition.getSquaredMagnitude();

		var direction = deltaPosition.normalize();
		var magnitude = G * mass0 * mass1 / squaredDistance;

		var gravitationalForce = direction.times(magnitude);
		forces.add(gravitationalForce);
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Set<Body> getBodies() {
		return bodies;
	}
}
