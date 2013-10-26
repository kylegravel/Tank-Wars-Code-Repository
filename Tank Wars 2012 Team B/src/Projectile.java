import ScreenManagement.*;

public class Projectile {
	private Sprite shot;
	private int weapon; //which weapon it is
	private int player;//person who fired the shot
	public boolean fired;//whether the shot had been fired
	
	
	public void setPlayer(int setter){
		player=setter;
	}
	public int getPlayer(){
		return player;
	}
	public void fireWeapon(float Xvel, float Yvel){
		fired=true;
		shot.setVelocityX(Xvel);
		shot.setVelocityY(Yvel);
		shot.setState(1);
	}
	public void weaponCollision(){
		fired=false;
		shot.setVelocityX(0);
		shot.setVelocityY(0);
		shot.setState(0);
	}
	public void setWeapon(int setter){
		weapon=setter;
	}
	public int getWeapon(){
		return weapon;
	}
	public void createShot(){
		
	}
	public Sprite getShotSprite() {
		return shot;
	}

	public void setShotSprite(Sprite shot) {
		this.shot = shot;
	}
	public int getDamage(){
		return weapon*weapon*10+10;
	}
}
