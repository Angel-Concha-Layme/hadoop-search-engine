package com.angel.hadoopsearch.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.HashSet;

public class IntSumReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> uniqueFiles = new HashSet<>();
        for (Text val : values) {
            uniqueFiles.add(val.toString());
        }
        context.write(key, new Text(String.join(", ", uniqueFiles)));
    }
}