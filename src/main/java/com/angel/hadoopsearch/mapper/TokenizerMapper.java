package com.angel.hadoopsearch.mapper;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class TokenizerMapper extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();
    private Text fileName = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split("\\s+");
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String name = fileSplit.getPath().getName();
        fileName.set(name);

        for (String token : tokens) {
            word.set(token);
            context.write(word, fileName);
        }
    }
}
