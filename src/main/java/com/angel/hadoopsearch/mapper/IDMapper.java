package com.angel.hadoopsearch.mapper;

import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class IDMapper extends Mapper<Object, Text, Text, IntWritable> {

    private Text filenameKey = new Text();
    private static final IntWritable one = new IntWritable(1);

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fullPath = fileSplit.getPath().toString();
        String name = new Path(fullPath).getName();
        filenameKey.set(name);

        context.write(filenameKey, one); // Emitir un IntWritable en lugar de Text
    }
}
