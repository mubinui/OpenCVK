
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
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class CircleDetection extends Application { // extends Application
    public void start(Stage stage) throws IOException {
        //Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
//        String file = "D:\\Backgrounds\\Screenshot 2022-06-06 035115.png";
        String file = "D:\\KIBOPICS\\Target2Adjusted.png";
        Mat src = Imgcodecs.imread(file);
        Imgproc.resize(src,src, new Size(0, 0), 1.5, 1.5,
                Imgproc.INTER_AREA);
        //Converting the image to Gray
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGBA2GRAY);
        //Blurring the image
        Mat blur = new Mat();
        Imgproc.medianBlur(src, blur, 5);
        //Detecting the Hough Circles
        Mat circles = new Mat();
        Point center = new Point();
        Imgproc.HoughCircles(gray, circles, Imgproc.HOUGH_GRADIENT, Math.PI/180, 150);
        for (int i = 0; i < circles.cols(); i++ ) {
            double[] data = circles.get(0, i);
            center = new Point(Math.round(data[0]), Math.round(data[1]));
            // circle center
            Imgproc.circle(src, center, 1, new Scalar(0, 0, 255), 3, 8, 0 );
            // circle outline
            int radius = (int) Math.round(data[2]);
            Imgproc.circle(src, center, radius, new Scalar(0,0,255), 3, 8, 0 );
        }
        System.out.println(center.toString());
        //Converting matrix to JavaFX writable image
        Image img = HighGui.toBufferedImage(src);
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