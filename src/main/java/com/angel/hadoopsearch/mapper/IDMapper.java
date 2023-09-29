package com.angel.hadoopsearch.mapper;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class IDMapper extends Mapper<Object, Text, Text, Text> {

    private Text fileNameKey = new Text();
    private Text dummyValue = new Text("1");

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();

        if (esArchivoDeTexto(fileName)) {
            fileNameKey.set(fileName);
            context.write(fileNameKey, dummyValue);
        }
    }

    private boolean esArchivoDeTexto(String fileName) {
        // Misma heurística o patrón usado anteriormente
        return fileName.endsWith(".txt");
    }
}
