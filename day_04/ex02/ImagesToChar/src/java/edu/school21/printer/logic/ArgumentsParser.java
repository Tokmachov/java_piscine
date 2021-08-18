package edu.school21.printer.logic;
import com.beust.jcommander.*;

import java.util.Arrays;

@Parameters(separators = "=")
public class ArgumentsParser implements IParameterValidator {
    @Parameter(names = "--white", description = "white pixel color")
    private String whitePixelColor;
    @Parameter(names = "--black", description = "black pixel color")
    private String blackPixelColor;
    public String getBlackPixelColor() {
        return blackPixelColor;
    }
    public String getWhitePixelColor() {
        return whitePixelColor;
    }
    public void parsePixelColors(String[] cmdLineArgs) {
        JCommander jc = new JCommander(this);
        try {
            jc.parse(cmdLineArgs);
            validate("--white", whitePixelColor);
            validate("--black", blackPixelColor);
        } catch (ParameterException ex) {
            System.err.println(ex);
        }
    }
    public boolean isParsingSuccessful() {
        return (blackPixelColor != null && whitePixelColor != null);
    }
    @Override
    public void validate(String s, String s1) throws ParameterException {
        String[] colors = {"RED", "WHITE", "BLUE"};
        if (Arrays.asList(colors).contains(s1) == false) {
            throw new ParameterException("Error: wrong color.\n Allowed colors are: " + Arrays.toString(colors));
        }
    }
}