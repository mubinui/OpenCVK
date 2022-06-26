package Quaternion;

public class QuaternionTester {
    public static void main(String[] args) {
       double yaw_degree = 30;
       double pitch_degree = 30;
       double roll_degree = 30;
       Quaternion q = eulerToQuaternion(yaw_degree,pitch_degree,roll_degree);
       System.out.println(q.toString());





    }

    public static Quaternion eulerToQuaternion(double yaw_degree, double pitch_degree, double roll_degree) {
        double yaw = Math.toRadians(yaw_degree); //radian = degree*PI/180
        double pitch = Math.toRadians(pitch_degree);
        double roll = Math.toRadians(roll_degree);

        double cy = Math.cos(yaw * 0.5);
        double sy = Math.sin(yaw * 0.5);
        double cp = Math.cos(pitch * 0.5);
        double sp = Math.sin(pitch * 0.5);
        double cr = Math.cos(roll * 0.5);
        double sr = Math.sin(roll * 0.5);

        double qx = sr * cp * cy - cr * sp * sy;
        double qy = cr * sp * cy + sr * cp * sy;
        double qz = cr * cp * sy - sr * sp * cy;
        double qw = cr * cp * cy + sr * sp * sy;

        return new Quaternion((float) qx, (float) qy, (float) qz, (float) qw);
    }
}
