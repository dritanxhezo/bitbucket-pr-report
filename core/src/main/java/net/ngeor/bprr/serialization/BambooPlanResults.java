package net.ngeor.bprr.serialization;

public class BambooPlanResults {
    private ResultsWrapper results;

    public ResultsWrapper getResults() {
        return results;
    }

    static class ResultsWrapper {
        private Result[] result;

        public Result[] getResult() {
            return result;
        }
    }

    static class Result {
        private String key;
        private String lifeCycleState;
        private String state;

        public String getKey() {
            return key;
        }

        public String getLifeCycleState() {
            return lifeCycleState;
        }

        public String getState() {
            return state;
        }
    }
}
