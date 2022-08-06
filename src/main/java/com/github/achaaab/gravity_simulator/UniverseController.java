package com.github.achaaab.gravity_simulator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.util.Duration;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static javafx.animation.Animation.INDEFINITE;
import static javafx.util.Duration.hours;
import static javafx.util.Duration.seconds;

/**
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class UniverseController implements EventHandler<KeyEvent> {

	private static final double ZOOM_FACTOR = 1.01;
	private static final double TIME_FACTOR = 1.5;
	private static final double DEFAULT_TIME_SCALE = 1_000_000;
	private static final double MINIMUM_TIME_SCALE = 1_000;
	private static final double MAXIMUM_TIME_SCALE = 1_000_000_000;
	private static final Duration FRAME_DURATION = seconds(1.0 / 60);
	private static final double MAXIMUM_DELTA_TIME = hours(24).toSeconds();

	private final UniverseModel model;
	private final UniverseView view;
	private final List<Body> bodies;
	private double timeScale;
	private Property<Body> anchor;
	private int anchorIndex;

	/**
	 * @param model
	 * @param view
	 * @since 0.0.0
	 */
	public UniverseController(UniverseModel model, UniverseView view) {

		this.model = model;
		this.view = view;

		bodies = model.getBodies();
		view.setBodies(bodies);

		anchor = new SimpleObjectProperty<>();
		view.anchor().bind(anchor);
		anchorIndex = -1;
		nextAnchor();

		timeScale = DEFAULT_TIME_SCALE;

		view.setOnScroll(this::zoom);

		var keyFrame = new KeyFrame(FRAME_DURATION, this::update);
		var animation = new Timeline(keyFrame);
		animation.setCycleCount(INDEFINITE);
		animation.play();
	}

	/**
	 * Updates the universe.
	 *
	 * @param keyFrameEvent
	 * @since 0.0.0
	 */
	public void update(ActionEvent keyFrameEvent) {

		var scaledTime = FRAME_DURATION.toSeconds() * timeScale;

		var deltaTime = 0;

		while (deltaTime + MAXIMUM_DELTA_TIME < scaledTime) {

			model.update(MAXIMUM_DELTA_TIME);
			deltaTime += MAXIMUM_DELTA_TIME;
		}

		model.update(scaledTime - deltaTime);

		view.draw();
	}

	/**
	 * @param scrollEvent
	 * @since 0.0.0
	 */
	public void zoom(ScrollEvent scrollEvent) {

		var scale = view.getScale();
		var units = scrollEvent.getDeltaY();
		scale *= pow(ZOOM_FACTOR, units);
		view.setScale(scale);
	}

	/**
	 * @param next
	 * @since 0.0.0
	 */
	public void changeAnchor(boolean next) {

		if (next) {
			nextAnchor();
		} else {
			previousAnchor();
		}
	}

	/**
	 * @since 0.0.0
	 */
	public void nextAnchor() {

		var bodyCount = bodies.size();

		anchorIndex++;

		var maximumIndex = bodyCount - 1;

		if (anchorIndex > maximumIndex) {
			anchorIndex = maximumIndex;
		}

		anchor.setValue(anchorIndex == -1 ? null : bodies.get(anchorIndex));
	}

	/**
	 * @since 0.0.0
	 */
	public void previousAnchor() {

		var bodyCount = bodies.size();

		anchorIndex--;

		var minimumIndex = bodyCount == 0 ? -1 : 0;
		var maximumIndex = bodyCount - 1;

		if (anchorIndex < minimumIndex) {
			anchorIndex = minimumIndex;
		}

		if (anchorIndex > maximumIndex) {
			anchorIndex = maximumIndex;
		}

		anchor.setValue(anchorIndex == -1 ? null : bodies.get(anchorIndex));
	}

	@Override
	public void handle(KeyEvent keyEvent) {

		var code = keyEvent.getCode();
		var shift = keyEvent.isShiftDown();

		switch (code) {

			case TAB -> changeAnchor(!shift);
			case ADD -> timeScale = min(MAXIMUM_TIME_SCALE, timeScale * TIME_FACTOR);
			case SUBTRACT -> timeScale = max(MINIMUM_TIME_SCALE, timeScale / TIME_FACTOR);
		}
	}
}
