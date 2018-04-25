package Utils;

import Utils.dataObjects.AndroidDate;
import Utils.dataObjects.IOSTime;
import Utils.dataObjects.Report;
import Utils.dataObjects.STATUS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sun.reflect.CallerSensitive;

import java.io.*;
import java.util.*;

import static Utils.dataObjects.STATUS.*;

public class GenerateReport {

    public static void getReport() {
        writeNewReport();
    }


    public static void writeNewReport() {

        FileWriter fileWriter = null;
        FileWriter fileWriterTxt = null;
        try {
            File file = new File("target/iosReport.json");
            fileWriter = new FileWriter(file);

            File txtFile = new File("target/iosReport.txt");
            fileWriterTxt = new FileWriter(txtFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Json
        List<Report> reports = new ArrayList<Report>();
        Report report = new Report();
        report.setAppInfo(getAppVersion());
        //txt
        PrintWriter pw = new PrintWriter(fileWriterTxt);
        pw.println(getAppVersion());

        //Json
        List<Report.Feature> features = new ArrayList<Report.Feature>();

        //folder dir
        File folder = new File("target/report");

        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files : " + listOfFiles.length);

        for (File f : listOfFiles){
            if (f.isFile()){
                Report.Feature feature = report.new Feature();
                System.out.println(" *** File : " + f.getName());
                feature = readFeatureData(f.getPath(), pw, feature);
                features = combineDupFeatureCases(features, feature);
            }
        }

        report.setFeatures(features);
        reports.add(report);

        List<Report.Feature> list = report.getFeatures();
        for (Report.Feature f : list){
            System.out.println("name : " + f.getFeatureName());
        }

        //prettyPrint
        Gson g = new Gson();
        String s = g.toJson(reports);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(s);
        String prettyJsonString = gson.toJson(jsonElement);

        PrintWriter pwJSON = new PrintWriter(fileWriter);
        pwJSON.println(prettyJsonString);

//        //uglyJsonString
//        Gson g = new Gson();
//        String s = g.toJson(reports);
//
//        PrintWriter pwJSON = new PrintWriter(fileWriter);
//        pwJSON.println(s);

        try {
            fileWriter.flush();
            fileWriterTxt.flush();
            pw.close();
            pwJSON.close();
            fileWriter.close();
            fileWriterTxt.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void generteHtml(){

        String iosFileDir     = "target/iosReport.json";
        String androidFileDir = "target/androidReport.json";
        String tempFileDir = "src/test/java/Utils/dataObjects/tempReport.json";

        //check whether the file exists or not, if not replace with temp.json file
        if (!new File(iosFileDir).exists()) {
            iosFileDir = tempFileDir;
        }
        if (!new File(androidFileDir).exists()){
            androidFileDir = tempFileDir;
        }

        JSONParser parser = new JSONParser();
        FileWriter fileWriterHTML = null;

        try {
            JSONObject iOSObj = (JSONObject)((JSONArray) parser.parse(new FileReader(iosFileDir))).get(0);
            JSONObject androidObj = (JSONObject)((JSONArray) parser.parse(new FileReader(androidFileDir))).get(0);

            Gson g = new Gson();
            Report iOSReport = g.fromJson(iOSObj.toString(), Report.class);
            Report androidReport = g.fromJson(androidObj.toString(), Report.class);

            System.out.println("ios : " + iOSObj.toString().getClass());
            System.out.println("and : " + iOSObj.toJSONString().getClass());

            String report = "";

            //html head
            report += "<!DOCTYPE html> \n";
            report += "<html> \n";
            report += "<head> \n<meta charset=\"utf-8\"> \n<title>Report for two platforms</title>\n</head>\n";

            //html body
            report += "<body>\n";
            //table
            report += reportsToHtmlTable(iOSReport, androidReport);
            //close html body
            report += "</body>\n";
            //close html
            report += "</html>\n";

            System.out.println("\nreport : \n" + report);

            //html
            File htmlFile = new File("target/report.html");
            fileWriterHTML = new FileWriter(htmlFile);

            PrintWriter pw = new PrintWriter(fileWriterHTML);
            pw.println(report);


            //close file
            fileWriterHTML.flush();
            pw.close();
            fileWriterHTML.close();

        } catch (FileNotFoundException e){

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    public static String reportsToHtmlTable(Report iosReport, Report android) {
        List<Report.Feature> features = iosReport.getFeatures();

        // maybe sort the features list here.

        String html = "";
        html += "<table style=\"width:100%\"> \n";
        //table caption with currentDate
        html += "<caption><h2>Report</h2>" + IOSTime.getCurrentDate() + "</caption> \n";
        //table header
        html += "<tr>";
        html += "<th>Feature</th><th>Case</th>";
        html += "<th>" + iosReport.getAppInfo() + "</th>";
        html += "<th>" + android.getAppInfo() + "</th>";
        html += "</tr> \n";

        //table body
        for (Report.Feature iosFeature : features) {
            boolean bfirst = true;

            List<Report.Feature> androidFeatures = android.getFeatures();
            Report.Feature androidFeature = null;
            for (Report.Feature tmpFeature : androidFeatures) {
                if (tmpFeature.getFeatureName().equals(iosFeature.getFeatureName())) {
                    androidFeature = tmpFeature;
                }
            }
            html += featureToHtml(iosFeature, androidFeature);
        }

        //note
        html += "<tr><th></th></tr> \n";
        html += "<tr><th>note: \"/\" - Do not support</th></tr> \n";

        //close the table
        html += "</table> \n";

        return html;
    }

    public static String featureToHtml(Report.Feature iosFeature, Report.Feature androidFeature) {

        List<Report.Feature.Case> iosCases = iosFeature.getCases();
        String html = "";

        Map<String, Report.Feature.Result> androidReults = new HashMap<>();
        if (androidFeature != null && androidFeature.getCases() != null) {
            for (Report.Feature.Case androidCase : androidFeature.getCases()) {
                androidReults.put(androidCase.getCaseName(), androidCase.getResult());
            }
        }

        for (Report.Feature.Case iosCase : iosCases) {
            String line = "<tr>";
            // line += "<td>" + iosCase.getPriority() + "</td>";
            line += "<td>" + iosFeature.getFeatureName() + "</td>";
            line += "<td>" + iosCase.getCaseName() + "</td>";

            if (iosCase.getResult() != null) {
                if (iosCase.getResult().getStatus().equals(STATUS.passed)){
                    line += "<td><font color=\"green\">" + STATUS.passed + "</font></td>";
                } else if (iosCase.getResult().getStatus().equals(STATUS.failed)){
                    line += "<td><font color=\"red\">" + STATUS.failed + "</font></td>";
                } else {
                    line += "<td><font color=\"blud\">" + STATUS.skiped + "</font></td>";
                }
//                line += "<td>" + iosCase.getResult().getStatus() + "</td>";
            } else {
                line += "<td>/</td>";
            }

            if (androidReults.containsKey(iosCase.getCaseName())) {
                STATUS androidResult = androidReults.get(iosCase.getCaseName()).getStatus();
                if (androidResult.equals(STATUS.passed)){
                    line += "<td><font color=\"green\">" + STATUS.passed + "</font></td>";
                } else if (androidResult.equals(STATUS.failed)){
                    line += "<td><font color=\"red\">" + STATUS.failed + "</font></td>";
                } else {
                    line += "<td><font color=\"blue\">" + STATUS.skiped + "</font></td>";
                }
//                line += "<td>" + androidReults.get(iosCase.getCaseName()).getStatus() + "</td>";
                androidReults.remove(iosCase.getCaseName());
            } else {
                line += "<td>/</td>";
            }
            line += "</tr> \n";
            html += line ;
        }


        for (Map.Entry<String, Report.Feature.Result> entry : androidReults.entrySet()) {
            String line = "<tr>";
            // line += "<td>" + iosCase.getPriority() + "</td>";
            line += "<td>" + iosFeature.getFeatureName() + "</td>";
            line += "<td>" + entry.getKey() + "</td>";
            line += "<td>/</td>";
            line += "<td>" + entry.getValue().getStatus() + "</td>";
            line += "</tr> \n";
            html += line ;
        }

        return html;
    }


    public static Report.Feature readFeatureData(String filePath, PrintWriter pw, Report.Feature feature){
        List<Report.Feature.Case> cases = new ArrayList<Report.Feature.Case>();

        JSONParser parser = new JSONParser();
        String caseResult = "passed";

        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader( filePath ));
            JSONObject jsonObject = (JSONObject)array.get(0);
            //featureName
            String featureName =  (String) jsonObject.get("name");
            feature.setFeatureName(featureName);

            JSONArray elements = (JSONArray)jsonObject.get("elements");

            for (Object testCaseObj : elements) {
                Report.Feature.Case testCase = feature.new Case();
                Report.Feature.Result result = feature.new Result();

                JSONObject testCaseJSONObj = (JSONObject) testCaseObj;

                //testCaseName
                String testCaseName = (String)testCaseJSONObj.get("name");
                testCase.setCaseName(testCaseName);
                pw.println(">>>>>> test case : ---" + testCaseName + "---");

                JSONArray steps = (JSONArray) testCaseJSONObj.get("steps");
                int duringTime = 0;
                for (Object testStepObj : steps) {
                    JSONObject testStep = (JSONObject) testStepObj;

                    String stepName = (String)testStep.get("name");
                    String stepResult = (String)((JSONObject)testStep.get("result")).get("status");
                    String stepTime;
                    if (!stepResult.equalsIgnoreCase("passed") && !caseResult.equalsIgnoreCase("failed")){
                        caseResult = stepResult;
                    } else if (stepResult.equalsIgnoreCase("failed")){
                        caseResult = stepResult;
                    }
                    try {
                        stepTime = Long.toString(((long) ((JSONObject) testStep.get("result")).get("duration")) / 1000000000);
                    } catch(NullPointerException e){
                        stepTime = "0";
                    }
                    duringTime = duringTime + Integer.parseInt(stepTime);

                    pw.println( stepResult + "\t:\t" + stepName );

                }
                pw.println( "RESULT: " + caseResult + " - time(s)\t:\t" + duringTime);
                pw.println("\n\n");

                result.setTime(duringTime);
                //passed, failed, skiped
                switch (caseResult) {
                    case "passed":
                        result.setStatus(STATUS.passed);
                        break;
                    case "Note":
                        result.setStatus(STATUS.failed);
                        break;
                    case "Task":
                        result.setStatus(STATUS.skiped);
                        break;
                    default:
                        result.setStatus(STATUS.skiped);
                }

                caseResult = "passed";

                //JSON
                testCase.setResult(result);
                cases.add(testCase);
            }
            feature.setCases(cases);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feature;
    }

    public static List<Report.Feature> combineDupFeatureCases(List<Report.Feature> features, Report.Feature newFeature) {
        if (features.size() == 0){
            features.add(newFeature);
        } else {
            int holder = -1;
            for (int i=0; i<features.size(); i++){
                Report.Feature tempF = features.get(i);

                //combineCases
                if (tempF.getFeatureName().equals(newFeature.getFeatureName())){
                    holder = i;
                    List<Report.Feature.Case> tempFCases = tempF.getCases();
                    List<Report.Feature.Case> newFCases = newFeature.getCases();
                    List<Report.Feature.Case> combineCases = new ArrayList<>();
                    combineCases.addAll(tempFCases);
                    combineCases.addAll(newFCases);

                    features.get(i).setCases(combineCases);
                    break;
                }
            }
            if (holder == -1 ){
                features.add(newFeature);
            } else {
                holder = -1;
            }
        }
        return features;
    }


    public static void writeNewJsonReport(){

        List<Report> reports = new ArrayList<Report>();
        Report report = new Report();
        report.setAppInfo("IOS : Tact - 3.6");

        List<Report.Feature> features = new ArrayList<Report.Feature>();
        for (int j = 100; j<300; ) {

            Report.Feature feature = report.new Feature();
            feature.setFeatureName(Integer.toString(j) + " feature");

            List<Report.Feature.Case> cases = new ArrayList<Report.Feature.Case>();
            for (int i = 0; i < 2; i++) {
                Report.Feature.Case testCase = feature.new Case();
                Report.Feature.Result result = feature.new Result();

                testCase.setCaseName(Integer.toString(i+j) + " test");

                result.setTime(i+j);
                result.setStatus(passed);

                testCase.setResult(result);
                cases.add(testCase);
            }

            feature.setCases(cases);

            features.add(feature);

            j = j + 100;
        }

        report.setFeatures(features);
        reports.add(report);

        Gson gson = new Gson();
        String s = gson.toJson(reports);
        System.out.println(s);

        //folder dir
        File folder = new File("target/report");

        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files : " + listOfFiles.length);

    }

    public static void printVersion(){
        File file = new File("target/osVersion.txt");
        try {
            Scanner sc = new Scanner(file);
            String s = sc.nextLine();
            String OS = s.split(" : ")[0];
            String appInfo = s.split(" : ")[1];
            System.out.println("|\t\t" + OS + "\t\t|\t\t" + appInfo + "\t\t|");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getAppVersion(){
        String appVersion = null;
        File file = new File("target/osVersion.txt");
        try {
            Scanner sc = new Scanner(file);
            appVersion = sc.nextLine();
            String OS = appVersion.split(" : ")[0];
            String appInfo = appVersion.split(" : ")[1];
            System.out.println("|\t\t" + OS + "\t\t|\t\t" + appInfo + "\t\t|");

        } catch (IOException e){
            e.printStackTrace();
        }
        return appVersion;
    }

    public static void main(String[] args){
//        writeNewReport();
        generteHtml();

        File tmpDir = new File("dataObjects/tempReport.json");
        boolean exists =  tmpDir.exists();
        if (exists){
            System.out.println("exists");
        } else {
            System.out.println("cannot find");
        }
    }

}
