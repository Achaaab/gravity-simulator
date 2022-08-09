package com.github.achaaab.gravity_simulator;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.BROWN;
import static javafx.scene.paint.Color.DARKGRAY;
import static javafx.scene.paint.Color.GRAY;
import static javafx.scene.paint.Color.GREEN;
import static javafx.scene.paint.Color.LIGHTBLUE;
import static javafx.scene.paint.Color.ORANGE;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.paint.Color.YELLOW;

/**
 * solar system simulation with following limitations and approximations:
 * <ul>
 *     <li>the sun has no initial velocity</li>
 *     <li>only gravitational forces are computed</li>
 *     <li>only Newton / Kepler laws are applied</li>
 *     <li>only 8 planets are rendered, plus the moon</li>
 *     <li>all bodies are modelled as discs, in a 2-dimensional space</li>
 *     <li>all planets and satellites orbit in same plan</li>
 *     <li>all planets and satellites start at their apoapsis</li>
 *     <li>time discretization can lead to significant trajectory errors,
 *     this is attenuated by limiting time step to 24 hours</li>
 * </ul>
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SolarSystemSimulation extends Application {

	private static final double SUN_RADIUS = 696_340_000;
	private static final double SUN_MASS = 1.9884E30;

	private static final double MERCURY_RADIUS = 2_439_700;
	private static final double MERCURY_MASS = 3.3011E23;
	private static final double MERCURY_APHELION = 6.98169E10;
	private static final double MERCURY_PERIHELION = 4.60012E10;

	private static final double VENUS_RADIUS = 6_051_800;
	private static final double VENUS_MASS = 4.8675E24;
	private static final double VENUS_APHELION = 1.08939E11;
	private static final double VENUS_PERIHELION = 1.07477E11;

	private static final double EARTH_RADIUS = 6_371_000;
	private static final double EARTH_MASS = 5.9722E24;
	private static final double EARTH_APHELION = 1.521E11;
	private static final double EARTH_PERIHELION = 1.47095E11;

	private static final double MOON_RADIUS = 1_737_400;
	private static final double MOON_MASS = 7.342E22;
	private static final double MOON_APOGEE = 405_400_000;
	private static final double MOON_PERIGEE = 362_600_000;

	private static final double MARS_RADIUS = 3_389_500;
	private static final double MARS_MASS = 6.4171E23;
	private static final double MARS_APHELION = 2.49261E11;
	private static final double MARS_PERIHELION = 2.0665E11;

	private static final double JUPITER_RADIUS = 69_911_000;
	private static final double JUPITER_MASS = 1.8982E27;
	private static final double JUPITER_APHELION = 8.16363E11;
	private static final double JUPITER_PERIHELION = 7.40595E11;

	private static final double SATURN_RADIUS = 58_232_000;
	private static final double SATURN_MASS = 5.6834E26;
	private static final double SATURN_APHELION = 1.51450E12;
	private static final double SATURN_PERIHELION = 1.35255E12;

	private static final double URANUS_RADIUS = 25_362_000;
	private static final double URANUS_MASS = 8.6810E25;
	private static final double URANUS_APHELION = 3.00639E12;
	private static final double URANUS_PERIHELION = 2.73556E12;

	private static final double NEPTUNE_RADIUS = 24_622_000;
	private static final double NEPTUNE_MASS = 1.02413E26;
	private static final double NEPTUNE_APHELION = 4.54E12;
	private static final double NEPTUNE_PERIHELION = 4.46E12;

	@Override
	public void start(Stage stage) {

		var universe = new UniverseModel();

		var sun = new Body("Sun", SUN_RADIUS, SUN_MASS, YELLOW);
		var mercury = new Body("Mercury", MERCURY_RADIUS, MERCURY_MASS, DARKGRAY);
		var venus = new Body("Venus", VENUS_RADIUS, VENUS_MASS, ORANGE);
		var earth = new Body("Earth", EARTH_RADIUS, EARTH_MASS, BLUE);
		var moon = new Body("Moon", MOON_RADIUS, MOON_MASS, GRAY);
		var mars = new Body("Mars", MARS_RADIUS, MARS_MASS, RED);
		var jupiter = new Body("Jupiter", JUPITER_RADIUS, JUPITER_MASS, BROWN);
		var saturn = new Body("Saturn", SATURN_RADIUS, SATURN_MASS, GREEN);
		var uranus = new Body("Uranus", URANUS_RADIUS, URANUS_MASS, WHITE);
		var neptune = new Body("Neptune", NEPTUNE_RADIUS, NEPTUNE_MASS, LIGHTBLUE);

		var mercuryOrbit = new EllipticOrbit(sun, mercury, MERCURY_APHELION, MERCURY_PERIHELION, true);
		var venusOrbit = new EllipticOrbit(sun, venus, VENUS_APHELION, VENUS_PERIHELION, true);
		var earthOrbit = new EllipticOrbit(sun, earth, EARTH_APHELION, EARTH_PERIHELION, true);
		var moonOrbit = new EllipticOrbit(earth, moon, MOON_APOGEE, MOON_PERIGEE, true);
		var marsOrbit = new EllipticOrbit(sun, mars, MARS_APHELION, MARS_PERIHELION, true);
		var jupiterOrbit = new EllipticOrbit(sun, jupiter, JUPITER_APHELION, JUPITER_PERIHELION, true);
		var saturnOrbit = new EllipticOrbit(sun, saturn, SATURN_APHELION, SATURN_PERIHELION, true);
		var uranusOrbit = new EllipticOrbit(sun, uranus, URANUS_APHELION, URANUS_PERIHELION, true);
		var neptuneOrbit = new EllipticOrbit(sun, neptune, NEPTUNE_APHELION, NEPTUNE_PERIHELION, true);

		universe.addBody(sun);
		universe.addOrbitingBody(mercuryOrbit);
		universe.addOrbitingBody(venusOrbit);
		universe.addOrbitingBody(earthOrbit);
		universe.addOrbitingBody(moonOrbit);
		universe.addOrbitingBody(marsOrbit);
		universe.addOrbitingBody(jupiterOrbit);
		universe.addOrbitingBody(saturnOrbit);
		universe.addOrbitingBody(uranusOrbit);
		universe.addOrbitingBody(neptuneOrbit);

		var view = new UniverseView();
		var controller = new UniverseController(universe, view);

		var root = new Group(view);
		var scene = new Scene(root);
		stage.setScene(scene);
		scene.setOnKeyPressed(controller);

		stage.setTitle("solar system simulation");
		stage.show();
	}
}
