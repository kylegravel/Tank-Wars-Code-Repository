// Jamar testing 


import ScreenManagement.*;

public class Tank {

	private Sprite tankSprite;

	private int health = 100;

	private Sprite tankImage;

	private int weapon2 = 0; // says if weapon 2 is on 1=on

	private double angle = 90;

	private double tankSlant = 0;

	private int movesLeft = 100;

	// player power adjustment -1 to 0 -1=100
	private float ShotPower = -.3f;

	// X coordinate at end of the turret
	private int turretAngleX;

	// Y coordinate at end of the turret
	private int turretAngleY = 299;

	// sin holder
	private double sin = 90;

	private int secondWeaponAmmo = 2;
	
	private int thirdWeaponAmmo = 1;
	
	private int fourthWeaponAmmo = 3;
	
	private int fifthWeaponAmmo = 1;

	// getters/setters

	public int getSecondWeaponAmmo() {
		return secondWeaponAmmo;
	}

	public void setSecondWeaponAmmo(int secondWeaponAmmo) {
		if (secondWeaponAmmo < 0) {
			this.secondWeaponAmmo = 0;
		} else {
			this.secondWeaponAmmo = secondWeaponAmmo;
		}
	}
	
	public int getThirdWeaponAmmo() {
		return thirdWeaponAmmo;
	}
	
	public int getFourthWeaponAmmo() {
		return fourthWeaponAmmo;
	}
	
	public int getFifthWeaponAmmo() {
		return fifthWeaponAmmo;
	}

	public void setThirdWeaponAmmo(int thirdWeaponAmmo) {
		if (secondWeaponAmmo < 0) {
			this.thirdWeaponAmmo = 0;
		} else {
			this.thirdWeaponAmmo = thirdWeaponAmmo;
		}
	}
	
	public void setFourthWeaponAmmo(int fourthWeaponAmmo) {
		if (secondWeaponAmmo < 0) {
			this.fourthWeaponAmmo = 0;
		} else {
			this.fourthWeaponAmmo = fourthWeaponAmmo;
		}
	}
	
	public void setFifthWeaponAmmo(int fifthWeaponAmmo) {
		if (secondWeaponAmmo < 0) {
			this.fifthWeaponAmmo = 0;
		} else {
			this.fifthWeaponAmmo = fifthWeaponAmmo;
		}
	}
	

	public int getWeapon2() {
		return weapon2;
	}

	public void setWeapon2(int setter) {
		weapon2 = setter;
	}

	public int getMovesLeft() {
		return movesLeft;
	}

	public void setMovesLeft(int setter) {
		if (setter > 0)
			movesLeft = setter;
		else
			movesLeft = 0;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 0) {
			this.health = 0;
		} else {
			this.health = health;
		}
	}

	public Sprite getTankImage() {
		return tankImage;
	}

	public void setTankImage(Sprite tankImage) {
		this.tankImage = tankImage;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getTankSlant() {
		return tankSlant;
	}

	public void setTankSlant(double setter) {
		tankSlant = setter;
	}

	public float getShotPower() {
		return ShotPower;
	}

	public void setShotPower(float shotPower) {
		ShotPower = (float) shotPower;
	}

	public int getTurretAngleX() {
		return turretAngleX;
	}

	public void setTurretAngleX(int turretAngleX) {
		this.turretAngleX = turretAngleX;
	}

	public int getTurretAngleY() {
		return turretAngleY;
	}

	public void setTurretAngleY(int turretAngleY) {
		this.turretAngleY = turretAngleY;
	}

	public double getSin() {
		return sin;
	}

	public void setSin(double sin) {
		this.sin = sin;
	}

	public Sprite getTankSprite() {
		return tankSprite;
	}

	public void setTankSprite(Sprite tankSprite) {
		this.tankSprite = tankSprite;
	}

	public void increaseAngle() {
		if (angle <= 170)// checks to see if turret angle has hit max (max = 172)
		{
			angle = angle + 2;// increases the angle by 2
			fixTurret();
		}
	}

	public void decreaseAngle() {
		if (angle >= 10) // checks to see if turret angle has hit min (min = 8)
		{  
			
			angle = angle - 2;// decreases the angle by 2
			fixTurret();
			
		}
	}
	public void fixTurret()
	{
		int holder;
		int holder2;
		turretAngleX = Math.round(getTankSprite().getX()+ (int) (getTankSprite().getWidth() * .5));
		// finds middle of x for turret
		
		holder = (int) ((Math.sin(angle / 57.3)) * (int) ((getTankSprite().getHeight() * .75) + 1));
		// finds the end of the turret
		
		holder2 = (int) ((Math.cos(angle / 57.3)) * (int) ((getTankSprite().getHeight() * .75) + 1));
		turretAngleX = (turretAngleX + holder2);
		// sets the end of the turret off the middle of the tank
		turretAngleY = (Math.round(getTankSprite().getY()) - holder);
	}

}
