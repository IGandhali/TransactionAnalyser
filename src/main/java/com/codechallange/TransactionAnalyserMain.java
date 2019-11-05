package com.codechallange;

import com.codechallange.model.OutputStats;
import com.codechallange.service.RecordsReader;
import com.codechallange.service.StatGenerator;
import com.codechallange.service.TransactionAnalyser;
import com.codechallange.util.Constants;
import org.joda.time.DateTime;

import java.util.Scanner;

public class TransactionAnalyserMain {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter merchant name: ");
        String merchantName = sc.nextLine();

        System.out.println("Enter date from-  ");
        DateTime dateFrom = Constants.joda.parseDateTime((sc.nextLine()));

        System.out.println("Enter date to- ");
        DateTime dateTo = Constants.joda.parseDateTime((sc.nextLine()));

        if (dateTo.compareTo(dateFrom) < 0) {
            System.out.println("Date range is incorrect!");
        } else {
            System.out.println("Enter csv file location (absolute Path) ");
            String filePath = sc.nextLine();

            TransactionAnalyser analyser = new TransactionAnalyser(new RecordsReader(filePath, merchantName, dateTo, dateFrom), new StatGenerator());
            OutputStats outputStats = analyser.analyse();
            System.out.println(outputStats);
        }
    }
}
