package net.ngeor.bamboo;

/**
 * A collection of plan results.
 */
public class PlanResults {
    private ResultsWrapper results;

    public ResultsWrapper getResults() {
        return results;
    }

    /**
     * Wraps the results.
     */
    public static class ResultsWrapper {
        private BuildResult[] result;

        public BuildResult[] getResult() {
            return result;
        }
    }
}
