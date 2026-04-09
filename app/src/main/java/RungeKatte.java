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

    public void solve(ODEfunction func){
        double[] start_state           = {1.0, 20.0};
        double dt                     = 0.01;
        double time_interval          = 10.0;  //10 second
        double num_of_time_points     = (int) (time_interval / dt);
        double[][] state_vector       = new double[2][(int)num_of_time_points];
        state_vector[0][0]  = start_state[0];
        state_vector[1][0]  = start_state[1];
        double[] init_state = start_state;


    }

}
