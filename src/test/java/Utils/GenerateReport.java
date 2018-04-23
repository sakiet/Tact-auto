package Utils;

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

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
//        Report.Feature feature = report.new Feature();

        //folder dir
        File folder = new File("target/report");

        File[] listOfFiles = folder.listFiles();
        System.out.println("number of files : " + listOfFiles.length);
        for (File f : listOfFiles){
            if (f.isFile()){
                Report.Feature feature = report.new Feature();
                System.out.println("*** File : " + f.getName());
                feature = readFeatureData(f.getPath(), pw, feature);
                features.add(feature);
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
            pw.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        writeNewReport();
    }

}
