package Utils.dataObjects;

import java.util.List;

public class Report {

    public String appInfo;
    public List<Feature> features;

    public String getAppInfo() {return appInfo;}
    public List<Feature> getFeatures() {return features;}

    public void setAppInfo(String appInfo) { this.appInfo = appInfo;}
    public void setFeatures(List<Feature> features) {this.features = features;}

    public class Feature {
        public String featureName;
        public List<Case> cases;

        public String getFeatureName() {
            return featureName;
        }

        public List<Case> getCases() {
            return cases;
        }

        public void setFeatureName(String featureName) {
            this.featureName = featureName;
        }

        public void setCases(List<Case> cases) {
            this.cases = cases;
        }

        public class Case {
            public String caseName;
            public Result result;
            public String caseResult;

            public void Case() {
            }

            public String getCaseName() {
                return caseName;
            }

            public Result getResult() {
                return result;
            }

            public void setCaseName(String caseName) {
                this.caseName = caseName;
            }

            public void setResult(Result result) {
                this.result = result;
            }


            public String getCaseResult() {
                return caseResult;
            }

            public void setCaseResult(String caseResult) {
                this.caseResult = caseResult;
            }
        }

        public class Result {
            public int time;
            public STATUS status;

            public int getTime() {
                return time;
            }

            public STATUS getStatus() {
                return status;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public void setStatus(STATUS status) {
                this.status = status;
            }
        }
    }
}



