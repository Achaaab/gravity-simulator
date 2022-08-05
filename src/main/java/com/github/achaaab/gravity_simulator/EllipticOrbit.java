package com.github.achaaab.gravity_simulator;

import static com.github.achaaab.gravity_simulator.UniverseModel.G;
import static java.lang.Math.sqrt;

/**
 * simplified elliptic orbit
 *
 * @param primaryBody primary body
 * @param secondaryBody secondary body
 * @param apoapsis apsis of the secondary body where it is the farthest from the primary body, in meters
 * @param periapsis apsis of the secondary body where it is the nearest of the primary body, in meters
 * @param prograde {@code true} if the orbit is prograde, {@code false} if the orbit is retrograde
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public record EllipticOrbit(

		Body primaryBody,
		Body secondaryBody,
		double apoapsis,
		double periapsis,
		boolean prograde) {

	/**
	 * @return velocity of the secondary body at its apoapsis
	 * @since 0.0.0
	 */
	public Vector2 getVelocityAtApoapsis() {

		var standardGravitationalParameter = G * primaryBody.getMass();
		var semiMajorAxis = (apoapsis + periapsis) / 2;

		var magnitude = sqrt(standardGravitationalParameter * (2 / apoapsis - 1 / semiMajorAxis));
		return new Vector2(0, prograde ? -magnitude : magnitude);
	}
}
