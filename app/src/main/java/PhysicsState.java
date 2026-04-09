

import java.util.ArrayList;
import java.util.List;

public class PhysicsState{

    public double x, y, z;     //position
    public double vx, vy, vz; //velocity 
    public double time;
    public boolean active;
    public boolean impacted;

    public List<double[]> trajectory = new ArrayList<>();

    public PhysicsState() {
	reset();
    }

    public void reset() { 
	x = -500;
	y =  300;
	z =  0;
	vx = 80;
	vy = -20;
	vz = 0;
	time = 0 ;
	impacted = false;
	active   = false;
    }

    public double speed() {
	return Math.sqrt(vx * vx + vy * vy + vz * vz);
    }

    public double gLoad(){
	return Math.abs(vy / 9.81);
    }



}
