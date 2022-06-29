package ImageUndistortion;

//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Point;
//import org.opencv.core.Scalar;
//import org.opencv.highgui.HighGui;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import java.awt.Image;
//import java.awt.image.BufferedImage;
//import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
//import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Fisheye extends Application { // extends Application
    public void start(Stage stage) throws IOException {
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        float fishVal = 450.0f;
        float cX = 640;
        float cY = 480;
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
        Mat image = Imgcodecs.imread("D:\\KIBOPICS\\Target1Adjusted.png");
        Mat greyScaleMat = new Mat();


        Imgproc.cvtColor(image, greyScaleMat, Imgproc.COLOR_RGBA2GRAY);
        System.out.println(image.size());
        double [] ud = greyScaleMat.get(640,480);
        for(double x : ud){
            System.out.print(x+" ");
        }

        Mat dst = new Mat(greyScaleMat.rows(), greyScaleMat.cols(), greyScaleMat.type());
        Calib3d.fisheye_undistortImage(greyScaleMat, dst, K, D, Knew);



        //Converting matrix to JavaFX writable image
        Image img = HighGui.toBufferedImage(dst);
        WritableImage writableImage= SwingFXUtils.toFXImage((BufferedImage) img, null);
        //Setting the image view
        ImageView imageView = new ImageView(writableImage);
        imageView.setX(10);
        imageView.setY(10);
        imageView.setFitWidth(575);
        imageView.setPreserveRatio(true);
        //Setting the Scene object
        Group root = new Group(imageView);
        Scene scene = new Scene(root, 595, 400);
        stage.setTitle("Hough Circle Transform");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]) {
        launch(args);
    }
}