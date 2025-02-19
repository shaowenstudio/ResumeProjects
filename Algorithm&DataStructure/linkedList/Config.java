package p2;
/**
 * Consists of all the variable used by the simulator class
 * to generate jobs automatically
 * These variables can be tweeked to obtain deterministic results
 * from the jobSimulator. Hence can be used for debugging purposes
 */

public class Config{
    /**
     * The probability on the type of jobs generated by the simulator
     * which can be either small jobs or large jobs
     * Setting it to a 50% chance. Can range between 0-100
     */
    public static final int PROBABILITY_OF_JOB_TYPE = 50;

    /**
     * The random number generated for deciding on the probability of
     * job creation and the job type will be between HIGH and LOW
     */
    public static final int HIGH = 100;
    public static final int LOW = 0;

    /**
     * This sets the probability value for creation of a large job
     * Can range between 0 - 100
     */
    public static final int PROBABILITY_OF_LARGE_JOB_CREATION = 45;

    /**
     * This sets the probability value for creation of a small job
     * Can range between 0 - 100
     */
    public static final int PROBABILITY_OF_SMALL_JOB_CREATION = 65;

    /**
     * Intervals in which small jobs are created based on the timeunits
     * of the current job in execution
     */
    public static final int SMALL_JOB_INTERVAL = 10;

    /**
     * Intervals in which large jobs are created based on the timeunits
     * of the current job in execution
     */
    public static final int LARGE_JOB_INTERVAL = 50;
}