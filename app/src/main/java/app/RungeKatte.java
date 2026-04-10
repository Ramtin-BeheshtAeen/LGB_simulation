package app;

import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class RungeKatte {
    final double c = 0.5;
    final double m = 1.0;
    final double k = 5.0;

    interface ODEfunction {
        double[] apply(double t, double[] state);
    }


   ODEfunction f = (t, state) -> {

        //state = {position, velocity}
        // m * x'' + c x' + kx = 0
        // l1  = x , l2 = x'
        // l1' = x' = l2
        // l2' = x''
        // l1' = x'  = dx/dt = velocity
        // l2' = x'' = dv/dt = acceleration
        // l2' = - (c / m ) * l2 - ( k / m ) l1

        double dx_dt  = state[1];
        double dv_dt  = - (c / m ) * state[1] - ( k / m ) * state[0];
        return new double[]{dx_dt, dv_dt};
        };

    public double[] rk4(ODEfunction func, final double dt, final double t0, final double[] state){
        //state = {position, velocity}
        double[] f1 = func.apply(t0, state);
        // in python with NumPy array it will look like this:
        //  f1    = func(t0, y0)
        //  f2    = func(t0 + dt/2, y0 + (dt/2) * f1 )
        //  f3    = func(t0 + dt/2, y0 + (dt/2) * f2 )
        //  f4    = func(t0 + dt  , y0 + dt     * f3 )
        //  y_out = y0 + (dt/6) * (f1 + 2*f2 + 2*f3 + f4)
        //  return y_out
        double[] state2 = { state[0] + dt/2 * f1[0], state[1] + dt/2 * f1[1] };
        double[] f2 = func.apply(t0 + dt/2 , state2);

        double[] state3 = { state[0] + dt/2 * f2[0], state[1] + dt/2 * f2[1] };
        double[] f3 = func.apply(t0 + dt/2, state3);

        // k4 = f(t + dt, y + dt * k3)
        double[] y4 = { state[0] + dt * f3[0], state[1] + dt * f3[1] };
        double[] k4 = func.apply(t0 + dt, y4);

        // Combine: y_next = y + (dt/6) * (k1 + 2k2 + 2k3 + k4)
        double[] nextState = new double[2];
        for (int z = 0; z < 2; z++) {
            nextState[z] = state[z] + (dt / 6.0) * (f1[z] + 2*f2[z] + 2*f3[z] + k4[z]);
        }
        return nextState;
    }

    public void solve() throws FileNotFoundException {
        double[] startState          = {1.0, 20.0};
        double dt                     = 0.01;
        double max_time               = 10.0;  //10 second
        double numTimePoints          = (int) (max_time  / dt);
        double[] timeVector           = new double[(int)numTimePoints];
        
        for (int u = 0; u < numTimePoints; u++) {
            timeVector[u] = u * dt;
        }
        
        double[][] state_vector     = new double[(int)numTimePoints][2];
        state_vector[0][0]          = startState[0];
        state_vector[0][1]          = startState[1];
        double[] initState         = startState;

        for(int t=0; t < numTimePoints-1; t++){
            double[] outPutState = rk4(f, dt, timeVector[t], initState );
            state_vector[t+1][0] = outPutState[0];
            state_vector[t+1][1] = outPutState[1];
            initState = outPutState;
        }



        PrintWriter writer = new PrintWriter("./data.csv");
        for (int i = 0; i < timeVector.length; i++) {
            writer.println(timeVector[i] + "," + state_vector[i][0] + "," + state_vector[i][1]);
        }
        writer.close();
    }

}
