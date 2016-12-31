package driver;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BooleanWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import classifier.BuildModelReducer;
import classifier.ObservationMapper;


/**
 * Driver class to run hadoop chained jobs using ToolRunner.
 * Source: http://unmeshasreeveni.blogspot.com/2014/04/chaining-jobs-in-hadoop-mapreduce.html
 * @author Meera
 *
 */
public class RunDriver extends Configured implements Tool {

	public static void main(String[] args) throws Exception {
		
		ToolRunner.run(new Configuration(), new RunDriver(), args);
	}

	@Override
	public int run(String[] args) throws Exception {

		Configuration conf = new Configuration();
		conf.set("region", args[2]);
		// Configuring job for Pre Processing
		Job parserJob = Job.getInstance(conf, "Observation Parser");
		parserJob.setJarByClass(RunDriver.class);
		parserJob.setMapOutputKeyClass(Text.class);
		parserJob.setMapOutputValueClass(NullWritable.class);
		parserJob.setOutputKeyClass(Text.class);
		parserJob.setOutputValueClass(NullWritable.class);
		parserJob.setMapperClass(ObservationMapper.class);
		MultipleOutputs.addNamedOutput(parserJob, "train", TextOutputFormat.class, Text.class,
				NullWritable.class);
		MultipleOutputs.addNamedOutput(parserJob, "test", TextOutputFormat.class, Text.class,
				NullWritable.class);
		parserJob.setNumReduceTasks(0);
		FileInputFormat.addInputPath(parserJob, new Path(args[0]));
		FileOutputFormat.setOutputPath(parserJob, new Path(args[1]));
		int status = parserJob.waitForCompletion(true) ? 0: 1;
		return status;
	}


}

