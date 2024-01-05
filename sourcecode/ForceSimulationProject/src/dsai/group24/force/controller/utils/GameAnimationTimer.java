/*
 * Lớp GameAnimationTimer tính thời gian giữa hai khung hình
 * References : https://edencoding.com/animation-timer-pausing/ 
 */

package dsai.group24.force.controller.utils;

import javafx.animation.AnimationTimer;

public abstract class GameAnimationTimer extends AnimationTimer {

	private long lastFrameTimeNanos;

	
	private boolean isPaused;

	private boolean pauseScheduled;
	private boolean playScheduled;
	private boolean restartScheduled;

	public void pause() {
		if (!isPaused) {
			pauseScheduled = true;
		}
	}

	public void play() {
		if (isPaused) {
			playScheduled = true;
		}
	}

	@Override
	public void start() {
		super.start();
		restartScheduled = true;
	}

	@Override
	public void stop() {
		super.stop();
		isPaused = true;
		pauseScheduled = false;
		playScheduled = false;
	}

	// Phương thức handle được gọi mỗi khi có một khung hình mới
	@Override
	public void handle(long now) {
		if (pauseScheduled) {
			isPaused = true;
			pauseScheduled = false;
		}

		if (playScheduled) {
			isPaused = false;
			playScheduled = false;
			lastFrameTimeNanos = now;
		}

		if (restartScheduled) {
			isPaused = false;
			restartScheduled = false;
			lastFrameTimeNanos = now;
		}

		if (!isPaused) {
			double secondsSinceLastFrame = (double) ((now - lastFrameTimeNanos) / 1e9);
			lastFrameTimeNanos = now;
			tick(secondsSinceLastFrame);
		}
	}

	// Sử dụng để tính toán vận tốc và vị trí của đối tượng
	public abstract void tick(double secondsSinceLastFrame);

}