package ImageUndistortion;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class UDP {
    public static void main(String[] args) {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        float fishVal = 600.0f;
        float cX = 960;
        float cY = 540;
        Mat K = new Mat(3, 3, CvType.CV_32FC1);
        K.put(0, 0, new float[]{fishVal, 0, cX});
        K.put(1, 0, new float[]{0, fishVal, cY});
        K.put(2, 0, new float[]{0, 0, 1});

        Mat D = new Mat(1, 4, CvType.CV_32FC1);
        D.put(0, 0, new float[]{0, 0, 0, 0});

        Mat Knew = K.clone();
        Knew.put(0, 0, new float[]{fishVal * 0.4f, 0.0f, cX});
        Knew.put(1, 0, new float[]{0.0f, fishVal * 0.4f, cY});
        Knew.put(2, 0, new float[]{0.0f, 0.0f, 1.0f});
        Mat image = Imgcodecs.imread("D:\\KIBOPICS\\Target2Adjusted.png");
        Mat greyScaleMat = new Mat();
        Imgproc.cvtColor(image, greyScaleMat, Imgproc.COLOR_RGBA2GRAY);

        Mat dst = new Mat(greyScaleMat.rows(), greyScaleMat.cols(), greyScaleMat.type());
        Calib3d.fisheye_undistortImage(greyScaleMat, dst, K, D, Knew);

    }

}
