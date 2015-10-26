package main.java.RoboVx;

import robocode.AdvancedRobot;
import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.Rules;
import robocode.ScannedRobotEvent;

import java.awt.*;
import java.util.Random;

/**
 * 
 * @author kaamlk
 *
 */
public class TestBot3 extends AdvancedRobot {

	//private int numMissedBullets = 0;
	//private int numHitBullets = 0;
	private byte scanDirection = 1;
	//private EnemyBot enemy = new EnemyBot();
	Random random = new Random();

//	@Override
//	public void onBulletHit(BulletHitEvent event) {
//		numHitBullets++;
//	}
//
//	@Override
//	public void onBulletMissed(BulletMissedEvent event) {
//		numMissedBullets++;
//	}

	@Override
	public void onHitByBullet(HitByBulletEvent event) {
		setTurnRight(10);
		setAhead(20);
		execute();
	}

	@Override
	public void onHitRobot(HitRobotEvent event) {		
		setBack(50);
		execute();
	}

	@Override
	public void onHitWall(HitWallEvent event) {
		setBack(100);
		setTurnRight(45+random.nextInt(135));
		execute();
	}

	@Override
	public void onScannedRobot(ScannedRobotEvent event) {
//		enemy.update(event);
		double angle = normalize(getHeading()) + normalize(event.getBearing());
		setTurnRadarRight(normalize(angle - normalize(getRadarHeading())));
		setTurnGunRight(normalize(angle - normalize(getGunHeading())));
		execute();
		double exTurn = 0;
		if (getGunHeat() == 0 && event.getDistance() < 300) {
			double remainingTurn = Math.abs(getGunTurnRemaining());			
			if (remainingTurn < 0.5)
				setFire(Rules.MAX_BULLET_POWER);
			else if (remainingTurn < 1)
				setFire(2);
			else if (remainingTurn < 2)
				setFire(1.5);
			else if (remainingTurn < 4)
				setFire(1);
			else if (remainingTurn < 6)
				setFire(0.5);
			execute();
			scanDirection *= -1;
			exTurn = 50 * scanDirection;
		}else{
			exTurn = 50 * scanDirection;
		}		
		setTurnRight(normalize(event.getBearing()) + exTurn);
		//setAhead(random.nextDouble()*100.0+50.0);
		setAhead(100);
		execute();

	}

	@Override
	public void run() {
		setColors(Color.orange, null, null);
		setAdjustGunForRobotTurn(true);
		// setAdjustRadarForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		execute();
		while (true) {
			turnRadarRight(360000);
		}
	}

	private double normalize(double angle) {
		while (angle > 180)
			angle -= 360;
		while (angle < -180)
			angle += 360;
		return angle;
	}

//	private class EnemyBot {
//		private double bearing, energy, heading, velocity, x, y, absBearing;
//
//		public EnemyBot() {
//			reset();
//		}
//
//		public void update(ScannedRobotEvent e) {
//			this.bearing = e.getBearing();
//			this.energy = e.getEnergy();
//			this.heading = e.getHeading();
//			this.velocity = e.getVelocity();
//			this.absBearing = getHeading() + e.getBearing();
//			this.x = getX() + Math.sin(Math.toRadians(absBearing)) + e.getDistance();
//			this.y = getY() + Math.cos(Math.toRadians(absBearing)) + e.getDistance();
//		}
//
//		public void reset() {
//			this.bearing = 0;
//			this.energy = 0;
//			this.heading = 0;
//			this.velocity = 0;
//			this.x = -1;
//			this.y = -1;
//		}
//
//	}

}
