package com.github.achaaab.gravity_simulator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.github.achaaab.gravity_simulator.UniverseModel.getCircularOrbitalVelocity;
import static javafx.animation.Animation.INDEFINITE;
import static javafx.scene.paint.Color.BLUE;
import static javafx.scene.paint.Color.BROWN;
import static javafx.scene.paint.Color.DARKGRAY;
import static javafx.scene.paint.Color.GRAY;
import static javafx.scene.paint.Color.ORANGE;
import static javafx.scene.paint.Color.RED;
import static javafx.scene.paint.Color.YELLOW;
import static javafx.util.Duration.seconds;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SolarSystemSimulation extends Application {

	private static final double SUN_RADIUS = 696_340_000;
	private static final double SUN_MASS = 1.9884E30;

	private static final double MERCURY_RADIUS = 2_439_700;
	private static final double MERCURY_MASS = 3.3011E23;
	private static final double SUN_MERCURY_DISTANCE = 5.790905E10;

	private static final double VENUS_RADIUS = 6_051_800;
	private static final double VENUS_MASS = 4.8675E24;
	private static final double SUN_VENUS_DISTANCE = 1.08208E11;

	private static final double EARTH_RADIUS = 6_371_000;
	private static final double EARTH_MASS = 5.9722E24;
	private static final double SUN_EARTH_DISTANCE = 1.496E11;

	private static final double MOON_RADIUS = 1_737_400;
	private static final double MOON_MASS = 7.342E22;
	private static final double EARTH_MOON_DISTANCE = 384_399_000;

	private static final double MARS_RADIUS = 3_389_500;
	private static final double MARS_MASS = 6.4171E23;
	private static final double SUN_MARS_DISTANCE = 2.27939366E11;

	private static final double JUPITER_RADIUS = 69_911_000;
	private static final double JUPITER_MASS = 1.8982E27;
	private static final double SUN_JUPITER_DISTANCE = 7.78479E11;

	private static final double TIME_SCALE = 1_000_000;

	@Override
	public void start(Stage stage) {

		var universe = new UniverseModel();
		var bodies = universe.getBodies();

		var sun = new Body("Sun", SUN_RADIUS, SUN_MASS, YELLOW);
		var mercury = new Body("Mercury", MERCURY_RADIUS, MERCURY_MASS, DARKGRAY);
		var venus = new Body("Venus", VENUS_RADIUS, VENUS_MASS, ORANGE);
		var earth = new Body("Earth", EARTH_RADIUS, EARTH_MASS, BLUE);
		var moon = new Body("Moon", MOON_RADIUS, MOON_MASS, GRAY);
		var mars = new Body("Mars", MARS_RADIUS, MARS_MASS, RED);
		var jupiter = new Body("Jupiter", JUPITER_RADIUS, JUPITER_MASS, BROWN);

		mercury.setPosition(new Vector2(SUN_MERCURY_DISTANCE, 0));
		venus.setPosition(new Vector2(SUN_VENUS_DISTANCE, 0));
		earth.setPosition(new Vector2(SUN_EARTH_DISTANCE, 0));
		moon.setPosition(new Vector2(SUN_EARTH_DISTANCE + EARTH_MOON_DISTANCE, 0));
		mars.setPosition(new Vector2(SUN_MARS_DISTANCE, 0));
		jupiter.setPosition(new Vector2(SUN_JUPITER_DISTANCE, 0));

		sun.setVisualScale(50);
		mercury.setVisualScale(1000);
		venus.setVisualScale(1000);
		earth.setVisualScale(1000);
		moon.setVisualScale(1000);
		mars.setVisualScale(1000);
		jupiter.setVisualScale(200);

		var mercuryVelocity = getCircularOrbitalVelocity(sun, mercury);
		mercury.setVelocity(mercuryVelocity);

		var venusVelocity = getCircularOrbitalVelocity(sun, venus);
		venus.setVelocity(venusVelocity);

		var earthVelocity = getCircularOrbitalVelocity(sun, earth);
		earth.setVelocity(earthVelocity);

		var moonVelocity = getCircularOrbitalVelocity(earth, moon);
		moon.setVelocity(earthVelocity.plus(moonVelocity));

		var marsVelocity = getCircularOrbitalVelocity(sun, mars);
		mars.setVelocity(marsVelocity);

		var jupiterVelocity = getCircularOrbitalVelocity(sun, jupiter);
		jupiter.setVelocity(jupiterVelocity);

		bodies.add(sun);
		bodies.add(mercury);
		bodies.add(venus);
		bodies.add(earth);
		bodies.add(moon);
		bodies.add(mars);
		bodies.add(jupiter);

		var view = new UniverseView(universe);

		var root = new Group(view);
		var scene = new Scene(root);
		stage.setScene(scene);

		stage.setTitle("solar system simulation");
		stage.show();

		var frameDuration = seconds(1.0 / 60.0);

		var keyFrame = new KeyFrame(frameDuration, onFinished -> {

			universe.update(TIME_SCALE * frameDuration.toSeconds());
			view.draw();
		});

		var animation = new Timeline(keyFrame);
		animation.setCycleCount(INDEFINITE);
		animation.play();
	}
}
