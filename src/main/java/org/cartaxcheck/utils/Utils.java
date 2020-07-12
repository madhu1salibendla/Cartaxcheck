package org.cartaxcheck.utils;

import org.cartaxcheck.model.VehicleDetails;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.cartaxcheck.utils.Constants.REGISTRATION_PATTERN;

public class Utils {

    private static final Pattern r = Pattern.compile(REGISTRATION_PATTERN);

    public static List<String> readInputFile() throws IOException {
        //read registration numbers
        List<String> registrationNumbers = new ArrayList<String>();

        URL resource = Utils.class.getClassLoader().getResource("car_input.txt");
        File file = new File(resource.getFile());

        try (FileReader reader = new FileReader(file);
            BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher matcher  = r.matcher(line);
                if (matcher.find()) {
                    registrationNumbers.add(matcher.group());
                }
            }
            return registrationNumbers;
        }
     }

        public static List<VehicleDetails> readOutputFile () throws IOException {
            List<VehicleDetails> vehicleDetailsList = new ArrayList<>();
            vehicleDetailsList.add(new VehicleDetails("SG18HTN", "Volkswagen", "Golf Se Navigation Tsi Evo", "White", "2018"));
            vehicleDetailsList.add(new VehicleDetails("DN09HRM", "BMW", "320D Se", "Black", "2009"));
            vehicleDetailsList.add(new VehicleDetails("BW57BOF", "Toyota", "Yaris T2", "Black", "2010"));
            vehicleDetailsList.add(new VehicleDetails("KT17DLX", "Skoda", "Superb Sportline Tdi S-A", "White", "2017"));

            return vehicleDetailsList;
        }

    }