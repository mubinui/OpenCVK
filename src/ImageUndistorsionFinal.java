import org.opencv.calib3d.Calib3d;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ImageUndistorsionFinal {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String pic = "D:\\KIBOPICS\\Target2Adjusted.png";
        Mat src = Imgcodecs.imread(pic);
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGBA2GRAY);
        List<Double> list = new ArrayList<Double>();
        for (int i = 0; i <gray.rows() ; i++) {
            for (int j = 0; j <gray.rows() ; j++) {
                double [] a = gray.get(i,j);
                for(double b : a){
                    list.add(b);
                }

            }

        }
        System.out.println(list.toString());

    }

}
