package io.github.tombom4.webApp.tools;

import java.awt.geom.Point2D;
        import java.util.ArrayList;
        import java.util.HashMap;

/**
 * This class represents quadratic functions used for calculation on the website
 * @author Thomas Kirz, Sebastian Vogt
 */
public class QuadraticFunction {
    /**
     * Parameters of the function (abc for ax² + bx + c and ade for a(x + d)² + e
     */
    private double a, b, c, d, e;

    /**
     * The vertex of the function
     */
    Point2D.Double vertex;

    /**
     * The zeroes of the function
     */
    private double zero1;
    private double zero2;

    /**
     * Three points of a function that are used to generate the function term.
     */
    Point2D.Double p1, p2, p3;

    /**
     * * Initializes a function with a, b and c and generates d, e, the vertex and the zeroes
     *
     * @throws IllegalArgumentException if a is zero
     */
    public QuadraticFunction(double a, double b, double c) throws IllegalArgumentException {
        if (a == 0) {
            throw new IllegalArgumentException("a must not be 0!");
        }
        this.a = a;
        this.b = b;
        this.c = c;
        generateVertex();
        generateZero();
    }

    /**
     * Overloads the constructor of the class (shall be used in case you want to generate the function term using three points of the function).
     */
    public QuadraticFunction(Point2D.Double p1, Point2D.Double p2, Point2D.Double p3){
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        generateABC();
    }

    /**
     * @return the parameter a of the function
     */
    public double getA() {
        return a;
    }

    /**
     * @return the parameter b of the function
     */
    public double getB() {
        return b;
    }

    /**
     * @return the parameter c of the function
     */
    public double getC() {
        return c;
    }

    /**
     * @return the parameter d of the function
     */
    public double getD() {
        return d;
    }

    /**
     * @return the parameter e of the function
     */
    public double getE() {
        return e;
    }

    /**
     * @return the parameter p1 of the function
     */
    public Point2D.Double getP1(){
        return p1;
    }

    /**
     * @return the parameter p2 of the function
     */
    public Point2D.Double getP2(){
        return p2;
    }

    /**
     * @return the parameter p3 of the function
     */
    public Point2D.Double getP3(){
        return p3;
    }

    /**
     * Calculates y of the function for a given x
     *
     * @return y
     */
    public double getImageOf(double x) {
        return a * x * x + b * x + c;
    }

    /**
     * Gets the vertex of the function as a Point2D
     *
     * @return the vertex
     */
    public Point2D.Double getVertex() {
        return vertex;
    }

    /**
     * @return the zero 1
     */
    public double getZero1(){
        return zero1;
    }

    /**
     * @return the zero 2
     */
    public double getZero2(){
        return zero2;
    }

    /**
     * Generates the parameters d and e of the function f(x) = a(x + d)² + e
     * Used in constructor QuadraticFunction(double a, double b, double c)
     */
    private void generateVertex() {
        d =  b / (2 * a);
        e = (4 * a * c - b * b) / (4 * a);

        vertex = new Point2D.Double(-d, e);
    }

    /**
     * Generates the zeroes of the function
     */
    private void generateZero(){
        zero1 = Math.sqrt(-(e / a)) - d;
        zero2 = -Math.sqrt(-(e / a)) - d;
    }

    /**
     * Generates x-values
     */
    public double generateY(double x){
        double y = (a * (x * x)) + (b * x) + c;
        return y;
    }

    /**
     * This method returns all images of a function for an ArrayList
     * @param list the ArrayList containing Doubles to be get an image
     * @return the HashMap with x as key and y as value
     */
    public HashMap<Double, Double> getImagesOf(ArrayList<Double> list) {
        HashMap<Double, Double> images = new HashMap<>();
        list.forEach(x -> images.put(x, getImageOf(x)));
        return images;
    }

    public void generateABC(){
        double l = p1.y - p2.y;
        double m = p1.x -p2.x;
        double n = p2.y -p3.y;
        double o = (p2.x * p2.x) - (p3.x * p3.x);
        double r = (p2.x * p2.x) * n;
        double s = (p1.x * p1.x) * n;
        double z = -(p2.x * p2.x * p2.x) + (p3.x * p2.x * p2.x) + (p2.x * p1.x * p1.x) - (p3.x * p1.x * p1.x);
        b = (-(l * 0) - r + s) / (z - (m * o));
        a = (n - (p2.x * b) + (p3.x * b)) / o;
        c = p3.y - (p3.x * p3.x * a) - (p3.x * b);
    }

    /**
     * This method returns all images of a function with x starting with start, ending with end and
     *
     * @param start
     * @param end
     * @param step
     * @return
     * @throws IllegalArgumentException
     */
    public HashMap<Double, Double> getImagesOf(double start, double end, double step) throws IllegalArgumentException {
        ArrayList<Double> list = new ArrayList<>();
        for (double i = start; i <= end; i += step) {
            list.add(i);
        }
        return getImagesOf(list);
    }

    /**
     * Generates a string of the function like ax²+ bx + c
     * @return the string
     */
    public String toNormalString() {
        String part1, part2, part3;

        part1 = a + "x²";

        if (b > 0) {
            part2 = " + " + b + "x";
        } else if (b < 0) {
            part2 = " - " + -b + "x";
        } else {
            part2 = "";
        }

        if (c > 0) {
            part3 = " + " + c;
        } else if (c < 0) {
            part3 = " - " + -c;
        } else {
            part3 = "";
        }

        return part1 + part2 + part3;
    }

    /**
     * Generates a string of the function like a(x + d)²+ e
     * @return the string
     */
    public String toVertexString() {
        String part1, part2;
        if (d > 0) {
            part1 = a + "(x + " + d + ")²";
        } else if (d < 0) {
            part1 = a + "(x - " + -d + ")²";
        } else {
            part1 = a + "x²";
        }

        if (e > 0) {
            part2 = " + " + e;
        } else if (e < 0) {
            part2 = " - " + -e;
        } else {
            part2 = "";
        }

        return part1 + part2;
    }

    /**
     * Generates a string for the zeroes
     * @return the string
     */
    public String toZeroString(){
        if (a / e < 0) {
            return zero1 + ", " + zero2;
        } else {
            return "No zeroes";
        }
    }
}
