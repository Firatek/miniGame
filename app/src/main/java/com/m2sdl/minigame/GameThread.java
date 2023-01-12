package com.m2sdl.minigame;

import android.graphics.Canvas;
import android.os.Handler;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    public boolean running;
    public Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;
    private Handler cubeHandler;
    private Handler cubeHandler1;
    private Runnable createCubeOnTime = new Runnable() {
        @Override
        public void run() {
                canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    synchronized (surfaceHolder) {
                        gameView.draw(canvas);
                    }
                    cubeHandler.postDelayed(this, 1000/60);
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
        }
    };

    private Runnable moveCube = new Runnable() {
        @Override
        public void run() {
            gameView.update();
            cubeHandler.postDelayed(this, 1000/20);
        }
    };

    public GameThread(SurfaceHolder surfaceHolder, GameView gameView) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
        this.cubeHandler = new Handler();
    }

    @Override
    public void run() {

        this.cubeHandler.post(createCubeOnTime);
        this.cubeHandler.post(moveCube);
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }


}
