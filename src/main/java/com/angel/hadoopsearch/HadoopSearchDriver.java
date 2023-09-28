package com.angel.hadoopsearch;

import com.angel.hadoopsearch.mapper.TokenizerMapper;
import com.angel.hadoopsearch.reducer.IntSumReducer;
import com.angel.hadoopsearch.reducer.InvertedIndexCombiner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HadoopSearchDriver {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        conf.setBoolean("mapreduce.input.fileinputformat.input.dir.recursive", true);
        conf.set("mapreduce.map.output.compress", "true");
        conf.set("mapreduce.map.output.compress.codec", "org.apache.hadoop.io.compress.SnappyCodec");

        Job job = Job.getInstance(conf, "hadoop search optimized");
        job.setJarByClass(HadoopSearchDriver.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(InvertedIndexCombiner.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
