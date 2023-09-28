package com.angel.hadoopsearch.mapper;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

public class TokenizerMapper extends Mapper<Object, Text, Text, Text> {

    private Text word = new Text();
    private Text filePath = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String[] tokens = value.toString().split("\\\\s+");
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fullPath = fileSplit.getPath().toString();
        String parentDir = new Path(fullPath).getParent().getName();
        String name = new Path(fullPath).getName();
        String combinedPath = parentDir + "/" + name;
        filePath.set(combinedPath);

        for (String token : tokens) {
            word.set(token);
            context.write(word, filePath);
        }
    }
}