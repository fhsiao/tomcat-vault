package com.charter.tomcatvault;

import org.apache.commons.cli.*;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class Arg {

    private static final Logger logger = Logger.getLogger(Arg.class);
    private String[] args = null;
    private Options options = new Options();
    private final static HashMap<String,String> resourceMap = new HashMap<>();
    private static Arg arg;
    private Arg(){}
    
    public static Arg getInstance(){
        if (resourceMap.isEmpty() && arg == null) {
            arg = new Arg();
        }
        return arg;
    }
    public static String getValue(String key){
        return resourceMap.get(key);
    }

    public void setArgs(String[] args) {
        this.args = args;
            final Option pward = Option.builder("P")
                    .longOpt("path")
                    .argName("property=value")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Vault path: Use: Resource=Password (DB1=secret/hello)")
                    .build();
            final Option token = Option.builder("T")
                    .longOpt("token")
                    .argName("property=value")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Resource's token: Use: Resource=token (DB1=25e4df62-4633-603c-1bdb-001d5f0154b9)")
                    .build();
            final Option uname = Option.builder("U")
                    .longOpt("username")
                    .argName("property=value")
                    .hasArgs()
                    .numberOfArgs(2)
                    .valueSeparator()
                    .desc("Vault user name: Use: Resource=UserName (DB1=DB1_USER_NAME)")
                    .build();
            final Option help = Option.builder("H")
                    .longOpt("help")
                    .desc("Help: Assign Objects by \"-Pobject=objectName\"\n")
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
            } else if (cmd.hasOption("U")) {
                Properties prop = cmd.getOptionProperties("U");
                prop.forEach((k, v) -> {
                    logger.debug("PROP_U: " + k + "::" + v + "::HASHING::" + k + "|U");
                    if(resourceMap.containsKey(k+"|U")){
                        logger.warn("Overwriting the username:"+ v +" for resource::" + k + "|U");
                    }
                    resourceMap.put(k+"|U",(String)v);
                });
            } else if (cmd.hasOption("P")) {
                Properties prop = cmd.getOptionProperties("P");
                prop.forEach((k, v) -> {
                    logger.debug("PROP_P: " + k + "::" + v + "::HASHING::" + k + "|P");
                    if(resourceMap.containsKey(k+"|P")){
                        logger.warn("Overwriting the path:"+ v +" for resource::" + k + "|P");
                    }
                    resourceMap.put(k+"|P",(String)v);
                });
            } else if (cmd.hasOption("T")) {
                Properties prop = cmd.getOptionProperties("T");
                prop.forEach((k, v) -> {
                    logger.debug("PROP_T: " + k + "::" + v + "::HASHING::" + k + "|T");
                    if (resourceMap.containsKey(k+"|T")) {
                        logger.warn("Overwriting the token:"+ v +" for resource::" + k + "|T");
                    }
                    resourceMap.put(k+"|T", (String) v);
                });
            } else {
                cmd.getArgList().forEach(k-> logger.debug("Got unexpected Tomcatvault parameter!!::"+k));
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
        formatter.printHelp(90, syntax, usageHeader, options, usageFooter, true);
    }
}

class RelaxedParser extends DefaultParser {
    @Override
    public CommandLine parse(Options options, String[] arguments) throws ParseException {
        List<String> knownArguments = new ArrayList<>();
        for (String arg : arguments) {
            if (options.hasOption(arg)||arg.contains("=")) {
                knownArguments.add(arg);
            }
        }
        return super.parse(options, knownArguments.stream().toArray(String[]::new),false);
    }
}