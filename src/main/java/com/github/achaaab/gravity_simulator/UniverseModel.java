package com.github.achaaab.gravity_simulator;

import java.util.HashSet;
import java.util.Set;

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

	private final Set<Body> bodies;

	/**
	 * @since 0.0.0
	 */
	public UniverseModel() {
		bodies = new HashSet<>();
	}

	/**
	 * Adds a body to this universe.
	 *
	 * @param body body to add
	 * @since 0.0.0
	 */
	public void addBody(Body body) {
		bodies.add(body);
	}

	/**
	 * @param orbit
	 * @since 0.0.0
	 */
	public void addOrbitalBody(EllipticOrbit orbit) {

		var primaryBody = orbit.primaryBody();
		var secondaryBody = orbit.secondaryBody();
		var apoapsis = orbit.apoapsis();

		var primaryPosition = primaryBody.getPosition();
		var secondaryPosition = primaryPosition.plus(new Vector2(apoapsis, 0));
		secondaryBody.setPosition(secondaryPosition);

		var primaryVelocity = primaryBody.getVelocity();
		var secondaryVelocity = primaryVelocity.plus(orbit.getVelocityAtApoapsis());
		secondaryBody.setVelocity(secondaryVelocity);

		addBody(secondaryBody);
	}

	/**
	 * @param deltaTime time elapsed since last update, in seconds
	 * @since 0.0.0
	 */
	public void update(double deltaTime) {

		// first step: collect exerted forces
		var forces = bodies.stream().collect(toMap(identity(), this::getResultingForce));

		// second step: apply collected forces
		forces.forEach((body, bodyForces) -> apply(bodyForces, body, deltaTime));

		// third step: update bodies
		bodies.forEach(body -> body.update(deltaTime));
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
	private Vector2 getResultingForce(Body body) {

		var resultingForce = new Vector2();

		addGravitationalForces(body, resultingForce);

		return resultingForce;
	}

	/**
	 * @param body
	 * @param resultingForce
	 * @since 0.0.0
	 */
	private void addGravitationalForces(Body body, Vector2 resultingForce) {

		bodies.stream().
				filter(not(body::equals)).
				forEach(otherBody -> addGravitationalForce(body, otherBody, resultingForce));
	}

	/**
	 * Computes and adds the gravitational force exerted by body1 on body0.
	 *
	 * @param body0
	 * @param body1
	 * @param resultingForce
	 * @since 0.0.0
	 */
	private void addGravitationalForce(Body body0, Body body1, Vector2 resultingForce) {

		var mass0 = body0.getMass();
		var mass1 = body1.getMass();
		var position0 = body0.getPosition();
		var position1 = body1.getPosition();

		var deltaPosition = position1.minus(position0);
		var squaredDistance = deltaPosition.squaredMagnitude();

		var direction = deltaPosition.normalize();
		var magnitude = G * mass0 * mass1 / squaredDistance;

		var gravitationalForce = direction.times(magnitude);
		resultingForce.setPlus(gravitationalForce);
	}

	/**
	 * @return
	 * @since 0.0.0
	 */
	public Set<Body> getBodies() {
		return bodies;
	}
}
