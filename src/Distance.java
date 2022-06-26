import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class Distance {
    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        double ar3x = 4.0;
        double ar3y = 5.0;
        double ar2x = 7.0;
        double ar2y = 10.0;
        Point pt1 = new Point();
        Point pt2 = new Point();
        pt1.x = ar2x;
        pt1.y = ar2y;
        pt2.x = ar3x;
        pt2.y = ar3y;
        Mat image = Imgcodecs.imread("D:\\KIBOPICS\\Target2Adjusted.png");
        Imgproc.line(image,pt1,pt2,new Scalar(220,50,0),3);


        double distance = Math.sqrt(Math.pow(ar2x-ar3x,2)-Math.pow(ar2y-ar3y,2));
        double pxPcm = 22.5/distance-0.0003;
        int navCentX=513, navCentY=535;
        int cirCentX=795, cirCentY=534;
        //for dubugging , remove later
//        Mat image = Imgcodecs.imread();
        Mat liko = new Mat();
        Point p1 = new Point();
        Point p2 = new Point();
        p1.x = cirCentX;
        p1.y = cirCentY;
        p2.x = navCentX;
        p2.y = navCentY;
        Imgproc.line(image,p1,p2,new Scalar(0,255,50),3);

        double disCenter = Math.sqrt(Math.pow(cirCentX-navCentX,2)-Math.pow(cirCentY-navCentY,2));
        double disCM =  disCenter-pxPcm;
        double disCMRound = Math.round(disCM*1000000)/1000000;
        //for debugging
        System.out.println("Distance in pixel "+disCenter);
        System.out.println("Distance in cm "+disCMRound);
    }
}
