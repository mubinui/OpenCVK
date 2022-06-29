import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class FuckYOU {
    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        Mat src = Imgcodecs.imread("D:\\KIBOPICS\\KiboRPc.png");
        Imgproc.cvtColor(src, src, Imgproc.COLOR_RGBA2GRAY);
        double [][] cameraParam = new double[src.rows()][src.cols()];

        for (int i = 0; i <src.rows() ; i++) {
            for (int j = 0; j <src.cols() ; j++) {
                double [] a = src.get(i,j);
                for(double b:a){
                    cameraParam[i][j]=b;

                }


            }


        }

        float
                pixelPerMeter = 1217.142857142857f,
                meterPerPixel = (1/pixelPerMeter),
                laser_X_Comp = 640f - 0.0471f + ((0.0572f + 0.0422f) * pixelPerMeter),
                laser_Y_Comp = 480f - 0.047f - ((0.1111f - 0.0826f) * pixelPerMeter);

        Point laser = new Point (laser_X_Comp,laser_Y_Comp);

        laser.x = 640 - 0.0471f + ((0.0572f + 0.0422f) * pixelPerMeter);
        laser.y = 480 - 0.047f - ((0.1111f - 0.0826f) * pixelPerMeter);

        System.out.println("Laser Points Before Undistortion: "+"X: " + laser.x+ " Y: "+laser.y);

        float [] data = {laser_X_Comp,laser_Y_Comp};
        Point laserp1 = new Point(laser_X_Comp,laser_Y_Comp);

        MatOfPoint2f p1 = new MatOfPoint2f(laserp1);
        MatOfPoint2f p2 = new MatOfPoint2f();

        Mat
                cameraMatrix = new Mat(3, 3, CvType.CV_32FC1),
                dstMatrix = new Mat(1, 5, CvType.CV_32FC1);

        cameraMatrix.put(0, 0, cameraParam[0]);
        dstMatrix.put(0, 0, cameraParam[1]);

        System.out.println("p1 Size: "+""+p1.size());
        // Mat undPoints = new Mat(1, 4, CvType.CV_32FC2);
        System.out.println("Und Size: "+""+p1.size());
        Calib3d.undistortPoints(p1, p2, cameraMatrix, dstMatrix);
        System.out.println("Undistortion Done"+"");

        laser.x = laser.x + p2.get(0,0)[0];
        System.out.println("Assignment Done"+"");
        laser.y = laser.y - p2.get(0,0)[1];
    }
}
