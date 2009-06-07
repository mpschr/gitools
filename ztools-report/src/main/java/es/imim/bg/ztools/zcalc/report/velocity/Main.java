package org.gitools.zcalc.report.velocity;

import static org.kohsuke.args4j.ExampleMode.ALL;

import java.io.File;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class Main {

	private static final String appName = Main.class.getPackage().getImplementationTitle();
	private static final String versionString = Main.class.getPackage().getImplementationVersion();
	
	@Option(name="-h",aliases="-help",usage="Print this message.")
	public boolean help;
	
	@Option(name="-version",usage="Print the version information and exit.")
    public boolean version;
	
	@Option(name="-quiet",usage="Don't print any information.")
    public boolean quiet;
	
	@Option(name="-verbose",usage="Print extra information.")
    public boolean verbose;
	
	@Option(name="-debug",usage="Print debug level information.")
    public boolean debug;
	
	@Option(name="-t", aliases="-template", usage="Use the template <template>.", metaVar="<template>")
	public String templateName = "static";
	
	@Option(name="-T", aliases="-templates-basedir", usage="Use <basedir> as base directory where search for templates.", metaVar="<basedir>")
	public String templateBasedir = System.getProperty("user.dir");
	
	@Option(name="-o", aliases="-output", usage="Save results into <output> directory.", metaVar="<output>")
	public String output = System.getProperty("user.dir") + File.separator + "/output";
	
	@Argument(required=true, multiValued = false, usage="Report configuration", metaVar="REPORT")
	public String reportConfigPath;
	
	public static void main(String[] args) {
		new Main().run(args);
	}
	
	protected void run(String[] args) {

		parseArgs(args);

		process(reportConfigPath);
	}

	private void process(String reportPath) {
		
		Map<String, Object> model = new HashMap<String, Object>();
		
		try {
			model = new ReportModelLoader().load(model, reportPath);
		} catch (Exception e) {
			System.err.println("Error loading the report " + reportPath);
			if (debug)
				e.printStackTrace();
			System.exit(-1);
		}
		
		File templatePath = new File(templateBasedir + File.separator + templateName);
		if (!templatePath.exists() || (templatePath.exists() && !templatePath.isDirectory())) {
			System.err.println("Template not found at " + templatePath.getAbsolutePath());
			System.exit(-1);
		}
		File outputPath = new File(output);
		outputPath.mkdirs();
		
		try {
			TemplateProcessor proc = new TemplateProcessor(templatePath, "index.vm");
			proc.copyTemplateContents(outputPath);
			proc.process(model, outputPath, "index.html");
		}
		catch (Exception e) {
			System.err.println("Error processing the template from " + templatePath.getAbsolutePath());
			if (debug)
				e.printStackTrace();
			System.exit(-1);
		}
	}

	private void parseArgs(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);

		parser.setUsageWidth(80);

		try {
			parser.parseArgument(args);
		} catch (CmdLineException e) {
			System.err.println(e.getMessage());
			printUsage(System.err, parser);
			System.exit(-1);
		}

		if (version)
			printVersion();

		if (help) {
			printUsage(System.out, parser);
			System.exit(0);
		}
	}

	private void printVersion() {
		System.out.println(appName + " version " + versionString);
		System.out.println("Written by Christian Perez Llamas <christian.perez@upf.edu>");
		//System.out.println();
	}

	private void requiredArgument(String msg, CmdLineParser parser) {
		System.err.println(msg);
		printUsage(System.err, parser);
		System.exit(-1);
	}

	private void printUsage(PrintStream out, CmdLineParser parser) {
        System.err.println("Usage: " + appName + " [options] <input>");
        parser.printUsage(System.err);
        System.err.println();

        // print option sample. This is useful some time
        System.err.println("  Example: " + appName + " " + parser.printExample(ALL));
	}

}
