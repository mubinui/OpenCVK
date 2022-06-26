import com.sun.javafx.geom.Vec3d;
import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
public class Rectangle extends Application {
    public void start(Stage stage) throws IOException {
        boolean flag = false;
        //Loading the OpenCV core library
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
//        String file = "D:\\Backgrounds\\Screenshot 2022-06-06 035115.png";
        String file = "D:\\KIBOPICS\\Target2Adjusted.png";
        Mat src = Imgcodecs.imread(file);
        Imgproc.resize(src,src, new Size(0, 0), 1.5, 1.5,
                Imgproc.INTER_AREA);
        //Converting the image to Gray
        System.out.println(src.size());

       //""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""""
        Mat gray = new Mat();
        Imgproc.cvtColor(src, gray, Imgproc.COLOR_RGBA2GRAY);
        //Converting the image to Gray
        Mat detectedCircles = new Mat();
        Imgproc.blur(gray, gray, new Size(7, 7), new Point(2, 2));
        Imgproc.HoughCircles(gray, detectedCircles, Imgproc.CV_HOUGH_GRADIENT, Math.PI/180, 150, 100, 90, 0, 1000);
        Point center = new Point();
        if (detectedCircles.cols() > 0) {
            for (byte x=0; x < Math.min(detectedCircles.cols(), 5); x++ ) {
                double [] circleVec = detectedCircles.get(0, x);
                if (circleVec == null) break;

                center = new Point((int) circleVec[0], (int) circleVec[1]);
                Imgproc.circle(gray, center, 1,new Scalar(0,0,255), 3, 8, 0);
            }
        }
        System.out.println(center.toString());



        Image img1 = HighGui.toBufferedImage(gray);
        WritableImage writableImage1= SwingFXUtils.toFXImage((BufferedImage) img1, null);
        //Setting the image view
        ImageView imageView1 = new ImageView(writableImage1);
        imageView1.setX(10);
        imageView1.setY(10);
        imageView1.setFitWidth(575);
        imageView1.setPreserveRatio(true);
        //Setting the Scene object
        Group root1 = new Group(imageView1);
        Scene scene1 = new Scene(root1, 595, 400);
        stage.setTitle("Hough Rectangle Transform");
        stage.setScene(scene1);
        stage.show();
    }
    public static void main(String [] args) {
        launch(args);
   }
//
}
