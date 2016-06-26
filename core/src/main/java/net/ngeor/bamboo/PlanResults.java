package net.ngeor.bamboo;

public class PlanResults {
    private ResultsWrapper results;

    public ResultsWrapper getResults() {
        return results;
    }

    public static class ResultsWrapper {
        private BuildResult[] result;

        public BuildResult[] getResult() {
            return result;
        }
    }
}
