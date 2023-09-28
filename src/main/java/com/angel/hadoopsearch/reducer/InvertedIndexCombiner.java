package com.angel.hadoopsearch.reducer;

import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.HashSet;

public class InvertedIndexCombiner extends Reducer<Text, Text, Text, Text> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        HashSet<String> uniqueFiles = new HashSet<>();
        for (Text val : values) {
            uniqueFiles.add(val.toString());
        }
        context.write(key, new Text(String.join(", ", uniqueFiles)));
    }
}