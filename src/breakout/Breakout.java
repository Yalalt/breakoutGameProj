package breakout;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.*;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
 * Breakout game main class
 * @author yalalt
 * @version 1.0.0
 *
 */
@SuppressWarnings("serial")
public class Breakout extends GraphicsProgram {

	private GOval ball = new GOval(WIDTH / 2, HEIGHT / 2, BALL_RADIUS, BALL_RADIUS);
	private double vx = 0, vy;
	private boolean isPlaying = true;
	private GRect paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
	private GRect[] toosgo = new GRect[NROW * NBLOCKS_PER_ROW];
	private int removedBlocks = 0, ndie = 0, i;

	/**
	 * Main run function
	 */
	public void run() {
		addMouseListeners();
		setSize(WIDTH, HEIGHT);
		initGame();
		playGame();
		for(i = 0; i < NROW * NBLOCKS_PER_ROW; i++) {
			if(toosgo[i] == null) remove(toosgo[i]);
		}
		remove(paddle);
		remove(ball);
		GLabel title = new GLabel("Game over");
		title.setColor(Color.RED);
		title.setLocation(WIDTH/2, HEIGHT/2);
		if(removedBlocks >= NROW * NBLOCKS_PER_ROW) {
			title.setLabel("You win");
			title.setColor(Color.GREEN);
		} else if(ndie >= NTURNS) title.setLabel("You lose");
		
		add(title);
	}
	
	/**
	 * Mouse move event
	 * Move by mouse position
	 */
	public void mouseMoved(MouseEvent e) {
		int x = e.getX();
		if(x >= WIDTH - PADDLE_WIDTH) x = WIDTH - PADDLE_WIDTH;
		
		paddle.setLocation(x, paddle.getY());
	}

	/**
	 * Game start init values
	 */
	public void initGame() {
		initBlocks();
		initPaddle();
	}

	/**
	 * initialize ball
	 */
	public void initBall() {
		RandomGenerator rgen = RandomGenerator.getInstance();
		ball.setLocation(WIDTH / 2, HEIGHT / 2);
		ball.setFilled(true);
		vx = vx + rgen.nextDouble(1.0, 2.0);
		vy = 3;

		if (rgen.nextBoolean(0.5))
			vx = -vx;
		// APPLET NOT WORKING ON JAVA 17

		add(ball);
	}

	/**
	 * Paddle prepare function
	 */
	public void initPaddle() {
		paddle.setFilled(true);
		add(paddle, WIDTH / 2 - PADDLE_WIDTH / 2, PADDLE_Y_OFFSET);
	}
	
	
	/**
	 * Blocks prepare function and initialize
	 */
	public void initBlocks() {
		int dx;
		double dy;

		Color[] color = { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN };

		for (i = 0; i < NROW * NBLOCKS_PER_ROW; i++) {
			toosgo[i] = new GRect(BLOCK_WIDTH, BLOCK_HEIGHT);
			dx = i % NBLOCKS_PER_ROW;
			dy = i / NROW;
			toosgo[i].setLocation(dx * BLOCK_WIDTH + dx * BLOCKS_GAP,
					dy * BLOCK_HEIGHT + dy * BLOCKS_GAP + BLOCK_Y_OFFSET);

			toosgo[i].setFilled(true);
			toosgo[i].setColor(color[i / NBLOCKS_PER_ROW]);
			add(toosgo[i]);
		}
	}

	/**
	 * Control blocks, ball, ball position method when game playing
	 */
	public void playGame() {
		initBall();
		while (isPlaying) {
			ballMove();

			GObject collider = getCollidingObject();

			for (i = 0; i < NROW * NBLOCKS_PER_ROW; i++) {
				if (collider == toosgo[i]) {
					remove(toosgo[i]);
					removedBlocks++;
					vy = -vy;
					vx++;
				}
			}

			if (collider == paddle)
				vy = -vy;

			if (ball.getLocation().getY() > HEIGHT) {
				ndie++;
				if (ndie >= NTURNS) {
					isPlaying = false;
				} else {
					initBall();
					continue;
				}
			}

			if (ball.getLocation().getY() <= 0)
				vy = -vy;

			if (ball.getLocation().getX() <= 0 || ball.getLocation().getX() + BALL_RADIUS*2 >= WIDTH)
				vx = -vx;
			
			pause(20);
			
			if(removedBlocks >= NROW * NBLOCKS_PER_ROW) isPlaying = false;

		}
	}

	/**
	 * Ball move method
	 */
	public void ballMove() {
		ball.move(vx, vy);
	}

	/**
	 * Find object when ball collide with bricks
	 * @return GObject
	 */
	public GObject getCollidingObject() {
		GObject result = null;

		if (getElementAt(ball.getX(), ball.getY()) != null) {
			result = getElementAt(ball.getX(), ball.getY());
		}
		return result;
	}

	/** Game board of width, height */
	private static final int WIDTH = 400;
	private static final int HEIGHT = 600;

	/** Paddle width, height */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Paddle on bottom Y */
	private static final int PADDLE_Y_OFFSET = 500;

	/** Number block per row */
	private static final int NBLOCKS_PER_ROW = 5;

	/** Number rows per game start */
	private static final int NROW = 5;

	/** Blocks between gap size */
	private static final int BLOCKS_GAP = 2;

	/** Block width, height */
	private static final int BLOCK_WIDTH = (WIDTH - (NBLOCKS_PER_ROW - 1) * BLOCKS_GAP) / NBLOCKS_PER_ROW;
	private static final int BLOCK_HEIGHT = 20;

	/** Ball radius */
	private static final int BALL_RADIUS = 8;

	/** Top row position offset coordinate */
	private static final int BLOCK_Y_OFFSET = 50;

	/** Total live number */
	private static final int NTURNS = 3;
}
