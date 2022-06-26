package jp.jaxa.iss.kibo.rpc.defaultapk;
import jp.jaxa.iss.kibo.rpc.api.KiboRpcService;

import gov.nasa.arc.astrobee.Result;
import gov.nasa.arc.astrobee.types.Quaternion;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import android.util.Log;
import android.os.SystemClock;

import java.util.ArrayList;

public class YourService extends KiboRpcService {

    /*
     * @author Team: Now In Orbit
     * 3rd KIBO Robot Programming Challenge 2022
     */

    final gov.nasa.arc.astrobee.types.Point
            p1 = new gov.nasa.arc.astrobee.types.Point(10.71000f, -7.70000f, 4.48000f),
            p2 = new gov.nasa.arc.astrobee.types.Point(11.27460f, -9.92284f, 5.29881f),
            pG = new gov.nasa.arc.astrobee.types.Point(11.27460f, -7.89178f, 4.96538f),
            adjustT1 = new gov.nasa.arc.astrobee.types.Point(10.72000, -7.7682f, 4.48f),
            adjustT2 = new gov.nasa.arc.astrobee.types.Point(11.30336799f, -10.10894978f, 5.480937397f),
            approachP2 = new gov.nasa.arc.astrobee.types.Point (11.500f, -9.55510, 4.48000f),
            approachG = new gov.nasa.arc.astrobee.types.Point ( 10.4246f,  -8.82284f, 5.59881f);
    gov.nasa.arc.astrobee.types.Point adjustCenter;

    final Quaternion
            faceTarget1 = new Quaternion(0.00000f, 0.70700f, 0.00000f, 0.70700f),
            faceAirlock = new Quaternion(0.00000f, 0.00000f, -0.70700f, 0.70700f);

    byte
            loop = 5,
            counter = 0;

    Result result1, result2, result3, result4, result5, result6, resultG;

    Point center;

    @Override
    protected void runPlan1(){
        Log.i("Starting Mission", "Now In Orbit");
        api.startMission();

    /*
    ##########################################################################
    ########################### T A R G E T  01 ##############################
    ##########################################################################
    */

        moveToPoint1();
        api.reportPoint1Arrival();
        adjustTarget1();
        takeImage ("T1Adjusted.png");

        center = startCircleDetection();
        Log.i("Target1 Center Coordinates: ", "X: "+center.x+", Y: "+center.y);
        // points.add(center);
        //points.add(new Point(640,480));

        // getMeasurementStandard();
        movementT1();
        laserSnap1();

    /*
    ##########################################################################
    ############################ T A R G E T  02 ##############################
    ##########################################################################
    */

        approachPoint2();
        moveToPoint2();
        adjustTarget2();
        takeImage ("T2Adjusted.png");

        center = startCircleDetection();
        Log.i("Target2 Center Coordinates: ", "X: "+center.x+", Y: "+center.y);

        // getMeasurementStandard();
        movementT2();
        laserSnap2();

    /*
    ##########################################################################
    ################################ G O A L #################################
    ##########################################################################
    */
        approachGoal();
        moveToGoal();
        api.reportMissionCompletion();
    }

    /*
    ##########################################################################
    ######################### R U N  P L A N  02 #############################
    ##########################################################################
    */

    @Override
    protected void runPlan2(){
        // write here your plan 2
    }

    /*
    ##########################################################################
    ######################### R U N  P L A N  03 #############################
    ##########################################################################
    */

    @Override
    protected void runPlan3(){
        // write here your plan 3
    }

    /*
    ##########################################################################
    ################### M O V E  T O  F U N C T I O N S ######################
    ##########################################################################
    */

    public void moveToPoint1(){

        result1 = api.moveTo(p1, faceTarget1, true);
        counter = 0;

        while(!result1.hasSucceeded() && counter < loop){
            result1 = api.moveTo(p1, faceTarget1, true);
            ++counter;
        }
    }

    public void moveToPoint2(){

        result2 = api.moveTo(p2, faceAirlock, true);
        counter = 0;

        while(!result2.hasSucceeded() && counter < loop){
            result2 = api.moveTo(p2, faceAirlock, true);
            ++counter;
        }
    }

    public void moveToGoal(){

        resultG = api.moveTo(pG, faceAirlock, true);
        counter = 0;

        while(!resultG.hasSucceeded() && counter < loop){
            resultG = api.moveTo(pG, faceAirlock, true);
            ++counter;
        }
    }

	/*
    ##########################################################################
    ################## A P P R O A C H  F U N C T I O N S ####################
    ##########################################################################
	*/

    public void approachPoint2() {

        result3 = api.moveTo(approachP2, faceAirlock, true);
        counter = 0;

        while(!result3.hasSucceeded() && counter < loop){
            result3 = api.moveTo(approachP2, faceAirlock, true);
            ++counter;
        }
    }

    public void approachGoal() {

        result4 = api.moveTo(approachG, faceAirlock, true);
        counter = 0;

        while(!result4.hasSucceeded() && counter < loop){
            result4 = api.moveTo(approachG, faceAirlock, true);
            ++counter;
        }
    }

    /*
    ##########################################################################
    ################## A D J U S T M E N T  F U N C T I O N S ################
    ##########################################################################
	*/

    public void adjustTarget1() {

        result5 = api.moveTo(adjustT1, faceTarget1, true);
        counter = 0;

        while(!result5.hasSucceeded() && counter < loop){
            result5 = api.moveTo(adjustT1, faceTarget1, true);
            ++counter;
        }
    }

    public void adjustTarget2() {

        result6 = api.moveTo(adjustT2, faceAirlock, true);
        counter = 0;

        while(!result6.hasSucceeded() && counter < loop){
            result6 = api.moveTo(adjustT2, faceAirlock, true);
            ++counter;
        }
    }

    /*
    ##########################################################################
    ################## M O V E M E N T  F U N C T I O N S ####################
    ##########################################################################
	*/

    public void movementT1() {

        float [] target1_pos = getMeasurementStandard ();
        Log.i("Distance From Center (In Meters) ", "X: "+target1_pos[1]+"Y: "+target1_pos[0]);

        if (target1_pos[0]>0 && target1_pos[1]>0) {
            // MoveRight  // MoveDown
            Log.i("Movement at T1: ", "MoveRight, MoveDown");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(target1_pos[1], target1_pos[0], 0f);
            api.relativeMoveTo(adjustCenter, faceTarget1, true);
        }
        else if (target1_pos[0]>0 && target1_pos[1]<0){
            // MoveRight // MoveUp
            Log.i("Movement at T1: ", "MoveRight, MoveUp");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(-1f* target1_pos[1], target1_pos[0], 0f );
            api.relativeMoveTo(adjustCenter,faceTarget1, true);
            // swapped 1
        }
        else if (target1_pos[0]<0 && target1_pos[1]>0){
            // MoveLeft // MoveDown
            Log.i("Movement at T1: ", "MoveLeft, MoveDown");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(target1_pos[1], -1f* target1_pos[0], 0f);
            api.relativeMoveTo(adjustCenter,faceTarget1, true);
            // swapped 1
        }
        else if (target1_pos[0]<0 && target1_pos[1]<0){
            // MoveLeft // MoveUp
            Log.i("Movement at T1: ", "MoveLeft, MoveUp");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(-1f* target1_pos[1], -1f* target1_pos[0], 0f);
            api.relativeMoveTo(adjustCenter,faceTarget1, true);
        }
    }

    public void movementT2() {

        float [] target2_pos = getMeasurementStandard ();
        Log.i("Distance From Center (In Meters) ", "X: "+target2_pos[0]+"Z: "+target2_pos[1]);

        if (target2_pos[0]>0 && target2_pos[1]>0) {
            // MoveLeft // MoveUp
            Log.i("Movement at T2: ", "MoveLeft, MoveUp");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point( -1f* target2_pos[0], 0f, -1f* target2_pos[1]);
            api.relativeMoveTo(adjustCenter,faceAirlock, true);

            // Swapped 1
        }
        else if (target2_pos[0]>0 && target2_pos[1]<0){
            // MoveLeft // MoveDown
            Log.i("Movement at T2: ", "MoveLeft, MoveDown");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point( -1f* target2_pos[0], 0f, target2_pos[1]);
            api.relativeMoveTo(adjustCenter,faceAirlock, true);
            //swapped 2
        }
        else if (target2_pos[0]<0 && target2_pos[1]>0){
            // MoveRight // MoveUp
            Log.i("Movement at T2: ", "MoveRight, MoveUp");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(target2_pos[0], 0f, -1f* target2_pos[1]);
            api.relativeMoveTo(adjustCenter,faceAirlock, true);
            // swapped 2

        }
        else if (target2_pos[0]<0 && target2_pos[1]<0){
            // MoveRight  // MoveDown
            Log.i("Movement at T2: ", "MoveRight, MoveDown");
            adjustCenter = new gov.nasa.arc.astrobee.types.Point(target2_pos[0], 0f, target2_pos[1]);
            api.relativeMoveTo(adjustCenter,faceAirlock, true);
            // Swapped 1
        } else if (target2_pos[0]==0 && target2_pos[1]==0) {
            
        }
    }

	/*
    ##########################################################################
    ###################### L A S E R  F U N C T I O N S ######################
    ##########################################################################
	*/

    public void laserSnap1(){
        api.laserControl(true);
        // SystemClock.sleep(5000);
        api.takeTarget1Snapshot();
        takeImage ("T1AfterMovement.png");
        // SystemClock.sleep(5000);
        api.laserControl(false);
    }

    public void laserSnap2(){
        api.laserControl(true);
        // SystemClock.sleep(5000);
        api.takeTarget2Snapshot();
        takeImage ("T2AfterMovement.png");
        // SystemClock.sleep(5000);
        api.laserControl(false);
    }

    public Mat navCamImage(){
        //api.flashlightControlFront(0.35f);
        SystemClock.sleep(3500);
        return api.getMatNavCam();
        //api.flashlightControlFront(0.35f);
        // return img;
    }

    public void takeImage(String imgName){
        // SystemClock.sleep(2000);
        Mat img = navCamImage();
        api.saveMatImage(img, imgName);
    }

    /*
    ##########################################################################
    ###################### C I R C L E  F U N C T I O N ######################
    ##########################################################################
	*/

    public Point startCircleDetection() {

        Mat
                circleImage = navCamImage(),
                detectedCircles = new Mat();

        Imgproc.blur(circleImage, circleImage, new Size(7, 7), new Point(2, 2));
        Imgproc.HoughCircles(circleImage, detectedCircles, Imgproc.CV_HOUGH_GRADIENT, Math.PI/180, 150, 100, 90, 0, 1000);

        if (detectedCircles.cols() > 0) {
            for (byte x=0; x < Math.min(detectedCircles.cols(), 5); x++ ) {
                double [] circleVec = detectedCircles.get(0, x);
                if (circleVec == null) break;

                center = new Point((int) circleVec[0], (int) circleVec[1]);
                Imgproc.circle(circleImage, center, 1,new Scalar(0,0,255), 3, 8, 0);
            }
        } return center;
    }

    /*
    ##########################################################################
    ############### U N D I S T O R T I O N  F U N C T I O N #################
    ##########################################################################
	*/


    public Mat getUndistortedImage (){

        double [][] cameraParam = api.getNavCamIntrinsics();

        Mat
                undistortedImage = new Mat(),
                cameraMatrix = new Mat(3, 3, CvType.CV_32FC1),
                dstMatrix = new Mat(1, 5, CvType.CV_32FC1),
                inputImage = navCamImage();

        cameraMatrix.put(0, 0, cameraParam[0]);
        dstMatrix.put(0, 0, cameraParam[1]);

        Imgproc.undistort(inputImage,undistortedImage,cameraMatrix,dstMatrix);
        api.saveMatImage(undistortedImage, "Target2Adjusted.png");
        return undistortedImage;
    }

    public ArrayList<Mat> getUndistortedPoints () {
        double [][] cameraParam = api.getNavCamIntrinsics();

        ArrayList<Mat> points = new ArrayList<>();

        Mat sfx = new Mat();

        Mat
                cameraMatrix = new Mat(3, 3, CvType.CV_32FC1),
                dstMatrix = new Mat(1, 5, CvType.CV_32FC1),
                inputImage = navCamImage();

        cameraMatrix.put(0, 0, cameraParam[0]);
        dstMatrix.put(0, 0, cameraParam[1]);

        for (int i = 0; i < points.size(); i++) {
            Mat undistortedPoints = new Mat(1, 4, CvType.CV_32FC2);
            Imgproc.undistortPoints(points.get(i), undistortedPoints, cameraMatrix, dstMatrix);
            points.set(i, undistortedPoints);
        }
        return points;
    }

    public float[] getMeasurementStandard() {

        float
                pixelPerMeter = 1217.142857142857f,
                meterPerPixel = (1/pixelPerMeter),
                laserX = 640 - 0.0371f + ((0.0572f + 0.0422f) * pixelPerMeter), // px val
                laserZ = 480 -0.027f - ((0.1111f - 0.0826f) * pixelPerMeter),
                offsetX = (float) ((center.x - laserX) * meterPerPixel), //met val
                offsetZ = (float) ((center.y - laserZ) * meterPerPixel);

        Log.i("Pixel Per Meter: ", "" + pixelPerMeter);
        Log.i("Meter Per Pixel: ", "" + meterPerPixel);
        Log.i("Offset X & Z (In Meter): ", "X: " + offsetX + "Z: " + offsetZ);

        return new float[] {offsetX, offsetZ};
    }
}
