public class Physics{

    public double mass        = 250.0;  // kg
    public double dragCoeff   = 0.42;   // Cd
    public double area        = 0.25;   // reference area m^2
    public double launchVel   = 210.0;  // m/s
    public double launchAngel = -10.0;  //degrees (negative means diving)
    public double windx       = 0.0;    // m/s east

    private static final double G     = 9.81;  // m / s^2
    private static final double RHO_SEA = 1.225; // kg/m^3 at see level
    private static final double DT      = 0.05; // time step s

    /** Calculation Air density via barometric model **/
    private double airDensity(double altitude) { 
	 // Based on international Atmosphere approximation
	return RHO_SEA * Math.pow(1 - 2.2558e-5 * altitude, 5.2561);
    }

    /** Drag Force magnitude **/
    private double dragForce(double speed, double altitude){
	double rho = airDensity(Math.max(0, altitude));
	return 0.5 * rho * dragCoeff * area * speed * speed;
    }

    /** Advanced Simulation by one timestep using RK4 **/
    public void step(PhysicsState state){
        if(!state.active || state.impacted) return;

        //State vector : [x, y, z, vx, vy, vz]
        double[] states = {state.x, state.y, state.z, state.vx, state.vy, state.vz };

    }



}
