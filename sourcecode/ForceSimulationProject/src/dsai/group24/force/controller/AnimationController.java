package dsai.group24.force.controller;

import dsai.group24.force.controller.utils.GameAnimationTimer;
import dsai.group24.force.model.Simulation;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AnimationController {

	private final int BACKGROUND_WIDTH = 2000;

	// Tỷ lệ giữa clouds và bricks
	private final float RATIO_TWO_BACKGROUNDS = 200.0f;

	// Thời gian chuyển động
	private final int TRANSITION_DURATION = 15000;

	// model và timer
	private GameAnimationTimer timer;
	private Simulation simul;

	// các  transition cho animation
	private ParallelTransition parallelTransitionUp;
	private ParallelTransition parallelTransitionDown;

	@FXML
	private StackPane topStackPane;
	@FXML
	private StackPane downStackPane;
	@FXML
	private ImageView backGroundMiddleUp;
	@FXML
	private ImageView backGroundRightUp;
	@FXML
	private ImageView backGroundMiddleDown;
	@FXML
	private ImageView backGroundRightDown;

	public ParallelTransition getParallelTransitionUp() {
		return this.parallelTransitionUp;
	}

	public StackPane getTopStackPane() {
		return topStackPane;
	}

	public StackPane getDownStackPane() {
		return downStackPane;
	}

	public void init(Simulation simul) {
		setSim(simul);
		setupTransition();

		timer = new GameAnimationTimer() {
			@Override
			public void tick(double secondsSinceLastFrame) {
				simul.applyForceInTime(secondsSinceLastFrame);
			}
		};

		// Binding điều chỉnh tốc độ animation
		this.simul.sysVelProperty().addListener((observable, oldValue, newValue) -> {

			parallelTransitionUp.rateProperty().bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 0.2))
					.then(10e-5 * 5).otherwise(newValue.valueProperty().multiply(0.05)));
			parallelTransitionDown.rateProperty().bind(Bindings.when(newValue.valueProperty().isEqualTo(0, 0.2))
					.then(10e-5 * 5).otherwise(newValue.valueProperty().multiply(0.05)));
		});

		// Xử lý responsive cho giao diện
		backGroundRightUp.fitHeightProperty().bind(topStackPane.heightProperty());
		backGroundMiddleUp.fitHeightProperty().bind(topStackPane.heightProperty());
		backGroundRightDown.fitHeightProperty().bind(downStackPane.heightProperty());
		backGroundMiddleDown.fitHeightProperty().bind(downStackPane.heightProperty());

	}

	public void setSim(Simulation sim) {
		this.simul = sim;
	}

	private void setupTransition() {
		// Thiết lập transition cho background
		TranslateTransition translateTransitionRU = new TranslateTransition(
				Duration.millis(TRANSITION_DURATION * RATIO_TWO_BACKGROUNDS), backGroundRightUp);
		translateTransitionRU.setFromX(0);
		translateTransitionRU.setToX(-1 * BACKGROUND_WIDTH);
		translateTransitionRU.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransitionMU = new TranslateTransition(
				Duration.millis(TRANSITION_DURATION * RATIO_TWO_BACKGROUNDS), backGroundMiddleUp);
		translateTransitionMU.setFromX(0);
		translateTransitionMU.setToX(-1 * BACKGROUND_WIDTH);
		translateTransitionMU.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransitionMD = new TranslateTransition(Duration.millis(TRANSITION_DURATION),
				backGroundMiddleDown);
		translateTransitionMD.setFromX(0);
		translateTransitionMD.setToX(-1 * BACKGROUND_WIDTH);
		translateTransitionMD.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransitionRD = new TranslateTransition(Duration.millis(TRANSITION_DURATION),
				backGroundRightDown);
		translateTransitionRD.setFromX(0);
		translateTransitionRD.setToX(-1 * BACKGROUND_WIDTH);
		translateTransitionRD.setInterpolator(Interpolator.LINEAR);

		parallelTransitionUp = new ParallelTransition(translateTransitionRU, translateTransitionMU);
		parallelTransitionUp.setCycleCount(Animation.INDEFINITE);

		parallelTransitionDown = new ParallelTransition(translateTransitionMD, translateTransitionRD);
		parallelTransitionDown.setCycleCount(Animation.INDEFINITE);

		
		parallelTransitionUp.setRate(0.0);
		parallelTransitionDown.setRate(0.0);

	}

	public void startAmination() {
		parallelTransitionUp.play();
		parallelTransitionDown.play();
		timer.start();
	}

	public void continueAnimation() {
		parallelTransitionUp.play();
		parallelTransitionDown.play();
		timer.play();
	}

	public void pauseAnimation() {
		parallelTransitionUp.pause();
		parallelTransitionDown.pause();
		timer.pause();
	}

	public void resetAnimation() {
		parallelTransitionUp.jumpTo(Duration.ZERO);
		parallelTransitionDown.jumpTo(Duration.ZERO);
		parallelTransitionUp.stop();
		parallelTransitionDown.stop();
		timer.stop();
	}

}
