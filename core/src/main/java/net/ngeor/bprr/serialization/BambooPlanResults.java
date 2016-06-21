package net.ngeor.bprr.serialization;

public class BambooPlanResults {
    private ResultsWrapper results;

    public ResultsWrapper getResults() {
        return results;
    }

    public static class ResultsWrapper {
        private BambooBuildResult[] result;

        public BambooBuildResult[] getResult() {
            return result;
        }
    }
}
