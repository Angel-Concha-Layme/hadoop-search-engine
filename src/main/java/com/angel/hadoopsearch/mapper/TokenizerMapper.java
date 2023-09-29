package com.angel.hadoopsearch.mapper;

import org.apache.hadoop.mapreduce.lib.input.FileSplit;
import java.io.BufferedReader;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.fs.FileSystem;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import java.util.regex.Matcher;

public class TokenizerMapper extends Mapper<LongWritable, Text, Text, Text> {

    private Text word = new Text();
    private Text fileID = new Text();
    private final Pattern WORD_PATTERN = Pattern
            .compile("(https?://[^\\s]+)|([\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6})|(\\b[\\w']+\\b)");
    private Map<String, String> fileToID = new HashMap<>();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        Path[] inputPaths = FileInputFormat.getInputPaths(context);
        for (Path inputPath : inputPaths) {
            if (inputPath.toString().contains("/user/angel/IDs")) { // Un indicador que se refiere al archivo de mapeo
                                                                    // de
                // IDs
                FileSystem fs = FileSystem.get(context.getConfiguration());
                BufferedReader br = new BufferedReader(new InputStreamReader(fs.open(inputPath)));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("\t");
                    if (parts.length == 2) {
                        fileToID.put(parts[0], parts[1]);
                    }
                }
                br.close();
            }
        }
    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        String fileName = fileSplit.getPath().getName();

        if (esArchivoDeTexto(fileName)) {
            String id = fileToID.get(fileName);
            if (id == null) {
                System.err.println("File ID not found for: " + fileName);
                throw new IOException("File ID not found for: " + fileName);
            }

            fileID.set(id);

            Matcher matcher = WORD_PATTERN.matcher(value.toString());
            while (matcher.find()) {
                word.set(matcher.group());
                context.write(word, fileID);
            }
        }
    }

    private boolean esArchivoDeTexto(String fileName) {
        return fileName.endsWith(".txt");
    }
}
