package edu.vadym.vorobel;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vadym on 10.01.2016.
 * Clas for generating images with strange contours
 */
public class ImageGenerator {

  private Mat image;
  private int numOfContours;
  private double imageWidth;
  private double imageHeight;
  private double maxContourWidth;
  private double maxContourHeight;
  private double contourRoundness = 2;

  /**
   * Constructor for ImageGenerator
   * @param imageSize - the size of output image
   * @param numOfContours - the number of contours in the output image
   */
  public ImageGenerator(Size imageSize, int numOfContours) {
    try {
      this.image = new Mat(imageSize, CvType.CV_8UC3);

      this.imageWidth = imageSize.width;
      this.imageHeight = imageSize.height;

      this.maxContourWidth = 0.05 * imageSize.width;
      this.maxContourHeight = 0.05 * imageSize.height;

      this.numOfContours = numOfContours;

      if (this.maxContourWidth == 0 || this.maxContourHeight == 0) {
        throw new Exception("Try to set a bigger size");
      } else if (this.numOfContours == 0) {
        throw new Exception("The number of contours must be grater then 0");
      }
    } catch (Exception error) {
      System.out.println("There is an error: " + error);
      System.exit(1);
    }
  }

  public Mat generate() {
    for (int i = 0; i < this.numOfContours; i++) {
      _generateMultipleContours();
    }
    return this.image;
  }


  private void _generateMultipleContours() {
    List<MatOfPoint> contours = new ArrayList<>();
    Point contourCenter = this._getContourCenter();

    for (int deltaAngle = 20; deltaAngle > 10; deltaAngle --) {
      contours.add(this._generateContour(contourCenter, deltaAngle));
    }

    for (int i=0; i<contours.size(); i++) {
      Imgproc.drawContours(this.image, contours, i, new Scalar(255, 255, 85));
      Imgproc.fillConvexPoly(this.image, contours.get(i), new Scalar(255, 255, 85));
    }
    Imgproc.fillPoly(this.image, contours, new Scalar(255, 255, 85));
  }


  private MatOfPoint _generateContour(Point contourCenter, int deltaAngle) {
    MatOfPoint contour = new MatOfPoint();
    List<Point> contourList = new ArrayList<>();

    for (int i = 0; i < 360; i += deltaAngle) {
      double angle = Math.random() * i;
      double rad = Math.toRadians(angle);
      contourList.add(this._getContourPoint(rad, this._getContourRadius(), contourCenter));
    }

    contour.fromList(contourList);
    return contour;
  }

  private Point _getContourCenter() {
    Point contourCenter = new Point();
    contourCenter.x = this.imageWidth * Math.random();
    contourCenter.y = this.imageHeight * Math.random();
    return contourCenter;
  }

  private int _getContourRadius() {
    Double maxRadius = this.maxContourHeight < this.maxContourWidth ? this.maxContourHeight : this.maxContourWidth;
    Double radius = maxRadius * Math.random();
    return radius.intValue();
  }

  private Point _getContourPoint(double angle, double radius, Point center) {
    Point contourPoint = new Point();
    contourPoint.x = radius * Math.cos(angle) + center.x;
    contourPoint.y = radius * Math.sin(angle) + center.y;
    return contourPoint;
  }

}
