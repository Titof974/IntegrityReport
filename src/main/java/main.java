import org.apache.commons.cli.*;
import report.Report;

public class main {
    public static void main(String[] args) throws Exception {

        Options options = new Options();

        Option input = new Option("i", "input", true, "input path");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output", true, "output csv file");
        output.setRequired(false);
        options.addOption(output);

        Option exclude = new Option("ex", "exclude", true, "exclude files regex");
        exclude.setRequired(false);
        options.addOption(exclude);

        Option include = new Option("in", "include", true, "exclude files regex");
        include.setRequired(false);
        options.addOption(include);

        Option show = new Option("s", "show", false, "show report");
        show.setRequired(false);
        options.addOption(show);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("Integrity Report", options);

            System.exit(1);
        }

        String inputPath = cmd.getOptionValue("input");
        String excludeRegex = cmd.getOptionValue("exclude");
        String includeRegex = cmd.getOptionValue("include");
        Boolean showOpt = cmd.hasOption("show");

        Report report = new Report(inputPath, includeRegex, excludeRegex);
        report.generate();
        if (showOpt) {
            System.out.println(report.toString());
        }

        if (cmd.hasOption("output")) {
            report.saveAsCSV(cmd.getOptionValue("output"));
        }


    }
}
