package com.angel.hadoopsearch.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class IDReducer extends Reducer<Text, Text, Text, IntWritable> {

    private int fileCounter = 0;

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        fileCounter++;
        context.write(key, new IntWritable(fileCounter));
    }
}
