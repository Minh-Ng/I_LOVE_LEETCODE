import java.util.concurrent.atomic.AtomicReference;

public class PIDController extends Controller {

    private final AtomicReference<Double> dampener;
    private final double kp;
    private final double ki;
    private final double kd;
    private double setPoint;
    private double previousError = 0.0;
    private double integral = 0.0;
    private double max = Double.MAX_VALUE;
    private double min = Double.MIN_VALUE;

    private PIDController(double setPoint, double kp, double ki, double kd, AtomicReference<Double> dampener) {
        this.setPoint = setPoint;
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
        this.dampener = dampener;
    }

    /**
     * @param setPoint target set point
     * @param kp proportional gain constant
     * @param ki integral gain constant
     * @param kd derivative gain constant
     * @param dampener gain dampening factor
     */
    public PIDController of(double setPoint, double kp, double ki, double kd, AtomicReference<Double> dampener) {
        return new PIDController(setPoint, kp, ki, kd, dampener);
    }

    /**
     * @param setPoint target set point
     * @param kp proportional gain constant
     * @param ki integral gain constant
     * @param kd derivative gain constant
     */
    public PIDController of(double setPoint, double kp, double ki, double kd) {
        return new PIDController(setPoint, kp, ki, kd, new AtomicReference<>(1.0D));
    }

    @Override
    public double step(double deltaT, double currentValue) {
        double error = setPoint - currentValue;
        double derivative = !(Double.isNaN(deltaT) || deltaT == 0.0) ? (error - previousError) / deltaT : 0.0;
        integral += error * deltaT;

        previousError = error;

        double output = dampener.get() * ((kp * error) + (kd * derivative) + (dampening * ki * integral));
        return Math.min(Math.max(output, min), max);
    }

    public double getMax() {
        return max;
    }

    public PIDController setMax(double max) {
        if (max < min) {
            throw new IllegalArgumentException("Max must be greater than than min: " + min);
        }

        this.max = max;
        return this;
    }

    public double getMin() {
        return min;
    }

    public PIDController setMin(double min) {
        if (min > max) {
            throw new IllegalArgumentException("Min must be less than max: " + max);
        }

        this.min = min;
        return this;
    }
}
