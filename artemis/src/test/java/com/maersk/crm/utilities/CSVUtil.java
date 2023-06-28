package com.maersk.crm.utilities;

import au.com.bytecode.opencsv.*;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSVUtil {

    public static void updateCSV(String fileToUpdate, String replace, int row, int col) throws IOException {

        File inputFile = new File(fileToUpdate);

// Read existing file
        CSVReader reader = new CSVReader(new FileReader(inputFile), ',');
        List<String[]> csvBody = reader.readAll();

// get CSV row column  and replace with by using row and column
        csvBody.get(row)[col] = replace;

        reader.close();

// Write to CSV file which is open
        CSVWriter writer = new CSVWriter(new FileWriter(inputFile), ',');
        writer.writeAll(csvBody);
        writer.flush();
        writer.close();
    }




}
