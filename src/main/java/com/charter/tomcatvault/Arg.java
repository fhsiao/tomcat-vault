package com.charter.tomcatvault;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Arg {

    private static final Logger logger = Logger.getLogger(Arg.class);
    private String[] args = null;
    private Options options = new Options();
    private static Arg arg;
    private Arg(){}

    public static Arg getInstance(){
        if (arg == null) {
            arg = new Arg();
        }
        return arg;
    }

    public void setArgs(String[] args) {
        this.args = args;
            final Option pward = Option.builder("P")
                    .longOpt("path")
                    .argName("resource=path")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Vault path: Use: resource_name=vault_path (Ex: db1=secret/hello)")
                    .build();
            final Option token = Option.builder("T")
                    .longOpt("token")
                    .argName("resource=token")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Vault token: Use: resource_name=vault_token (Ex: db1=25e4df62-4633-603c-1bdb-001d5f0154b9)")
                    .build();
            final Option uname = Option.builder("U")
                    .longOpt("username")
                    .argName("resource=username")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Vault user name: Use: resource_name=vault_user_name (db1=voes_db1)")
                    .build();
            final Option help = Option.builder("H")
                    .longOpt("help")
                    .desc("Help: Please contact VOES Team for more details.")
                    .build();
            options.addOption(pward)
                    .addOption(token)
                    .addOption(uname)
                    .addOption(help);
            this.parse();
    }

    private void parse() {
        CommandLineParser parser = new RelaxedParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("H")) {
                help();
            }
            if (cmd.hasOption("U")) {
                Properties prop = cmd.getOptionProperties("U");
                prop.forEach((k, v) -> {
                    PROP.addResource((String)k);
                    PROP.setResourceUser((String)k,(String)v);
                    logger.debug("PROP_U: " + k + "::" + v);
                });
            }
            if (cmd.hasOption("P")) {
                Properties prop = cmd.getOptionProperties("P");
                prop.forEach((k, v) -> {
                    PROP.addResource((String)k);
                    PROP.setResourcePath((String)k,(String)v);
                    logger.debug("PROP_P: " + k + "::" + v);
                });
            }
            if (cmd.hasOption("T")) {
                Properties prop = cmd.getOptionProperties("T");
                prop.forEach((k, v) -> {
                    PROP.addResource((String)k);
                    PROP.setResourceToken((String)k,(String)v);
                    logger.debug("PROP_T: " + k + "::" + v);
                });
            }
        } catch (ParseException e) {
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage(), e.fillInStackTrace());
            }
            help();
        }
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        final String syntax = "Main";
        final String usageHeader = "Usage of Tomcat Property Decoder";
        final String usageFooter = "\n  **** Please contact VOES for further details. ****\n";
        formatter.printHelp(130, syntax, usageHeader, options, usageFooter, true);
    }
}

class RelaxedParser extends DefaultParser {
    private static final Logger logger = Logger.getLogger(Arg.class);
    @Override
    public CommandLine parse(Options options, String[] arguments) throws ParseException {
        List<String> knownArguments = new ArrayList<>();
        for (String arg : arguments) {
            if (options.hasOption(arg)||arg.contains("=")) {
                knownArguments.add(arg);
            } else {
                logger.debug("Got unexpected Tomcatvault parameter::"+ arg + " -- Ignoring it");
            }
        }
        return super.parse(options, knownArguments.stream().toArray(String[]::new),false);
    }
}