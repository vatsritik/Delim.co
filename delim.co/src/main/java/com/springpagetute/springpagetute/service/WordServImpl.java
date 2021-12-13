package com.springpagetute.springpagetute.service;

import com.springpagetute.springpagetute.deliminput.DelimInput;
import org.springframework.expression.spel.ast.Assign;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class WordServImpl implements WordServ {
    List<DelimInput> list;

    public WordServImpl() {
        list = new ArrayList<>();
    }

    @Override
    public String test(DelimInput delimInput) {
        list.add(delimInput);

        String resultMain = handleDelimiter(delimInput.getDelimiter(), delimInput.getInput(), delimInput.getExplode());
        String resultClone = handleAttackClones(delimInput.isAttackClones(), resultMain, delimInput.getDelimiter());
        String resultQuotes = handleQuotes(delimInput.getQuotes(), resultClone, delimInput.getDelimiter());
        String resultTags = handleTags(delimInput.getOpenTag(), delimInput.getCloseTag(), delimInput.getDelimiter(), resultQuotes);
        String resultInterval=handleInterval(resultTags,delimInput.getDelimiter(),delimInput.getInterval());
        String resultIntervalWrap=handleIntervalWrap(delimInput.getIntervalOpenWrap(),delimInput.getIntervalCloseWrap(),resultInterval);

        return handleTidyUp(delimInput.isTidyUp(), resultIntervalWrap, delimInput.getDelimiter());

    }

    public static final Map<String, String> staticMap =
            Map.of("New Lines", "[\\t\\n\\r]+",
                    "Spaces", " ",
                    "Commas", ",",
                    "Semicolons", ";",
                    "New Line", "\n"
            );

    //main handle method
    private String handleDelimiter(String delimiter, String result, String explode) {

        if (delimiter.equals("New Line")) {
            return result.replaceAll(staticMap.get(explode), "\n");
        } else if (delimiter.equals("Spaces")) {
            return result.replaceAll(staticMap.get(explode), " ");
        } else {
            return result.replaceAll(staticMap.get(explode), delimiter);
        }
    }

    //Add a new line after x amount
    private String handleInterval(String result,String delimiter,int interval){
        if(interval!=0) {
            if(delimiter.equals("New Line") || delimiter.equals("Spaces")){
                String r=staticMap.get(delimiter);
                char s=r.charAt(0);
                int occur = 0;
                for (int i = 0; i < result.length(); i++) {
                    if (result.charAt(i) == s) {
                        occur += 1;
                    }
                    if (occur == interval) {
                        result = result.substring(0, i) + "\n" + result.substring(i + 1);
                        occur = 0;
                    }
                }
                return result;
            }else {
                char s = delimiter.charAt(0);
                int occur = 0;
                for (int i = 0; i < result.length(); i++) {
                    if (result.charAt(i) == s) {
                        occur += 1;
                    }
                    if (occur == interval) {
                        result = result.substring(0, i) + "\n" + result.substring(i + 1);
                        occur = 0;
                    }
                }
                return result;
            }

        }
        else
            return result;
    }

    //Use Tags to wrap your records. EX : <strong>
    private String handleTags(String openTag, String closeTag, String delimiter, String result) {
        String r = result.replaceAll("^", openTag).replaceAll("$", closeTag);

        if (delimiter.equals("New Line") || delimiter.equals("Spaces")) {
            return r.replaceAll(staticMap.get(delimiter), closeTag + staticMap.get(delimiter) + openTag);

        } else {

            return r.replaceAll(delimiter, closeTag + delimiter + openTag);
        }
    }

    //Wrap your intervals with tags
    private String handleIntervalWrap(String openTag,String closeTag,String result){
        String r = result.replaceAll("^", openTag).replaceAll("$", closeTag);
        return r.replaceAll("\n", closeTag +"\n"+ openTag);

    }

    //Add quotes to each record
    private String handleQuotes(String quotes, String result, String delimiter) {
        if (quotes.equalsIgnoreCase("no")) {
            return result;
        } else if (quotes.equalsIgnoreCase("double")) {
            if (delimiter.equals("New Line") || delimiter.equals("Spaces")) {
                String resultDouble = result.replaceAll("^|$", "\"");
                return resultDouble.replaceAll(staticMap.get(delimiter), "\"" + staticMap.get(delimiter) + "\"");

            } else {
                String resultDouble = result.replaceAll("^|$", "\"");
                return resultDouble.replaceAll(delimiter, "\"" + delimiter + "\"");
            }
        } else if (quotes.equalsIgnoreCase("single")) {

            if (delimiter.equals("New Line") || delimiter.equals("Spaces")) {
                String resultSingle = result.replaceAll("^|$", "'");
                return resultSingle.replaceAll(staticMap.get(delimiter), "'" + staticMap.get(delimiter) + "'");
            } else {
                String resultSingle = result.replaceAll("^|$", "'");
                return resultSingle.replaceAll(delimiter, "'" + delimiter + "'");
            }
        }
        return result;
    }

    //Remove the new lines from output?
    private String handleTidyUp(boolean tidyUp, String result, String delimiter) {
        if (tidyUp) {
            return result;
        } else {
            if (delimiter.equals("New Line") || delimiter.equals("Spaces")) {
                return result.replaceAll(staticMap.get(delimiter), staticMap.get(delimiter) + "\n");

            } else
                return result.replaceAll(delimiter, delimiter + "\n").
                        replaceAll("$",delimiter);
        }
    }

    //Remove the duplicates from the result set
    private String handleAttackClones(boolean attackClone, String result, String delimiter) {
        if (attackClone) {
            if (delimiter.equals("New Line") || delimiter.equals("Spaces")) {
                return Stream.of(result.split(staticMap.get(delimiter))) //split in delimiter
                        .distinct()                       //remove duplicates
                        .collect(Collectors.joining(staticMap.get(delimiter))); //rejoin string
            } else {
                return Stream.of(result.split(delimiter)) //split in delimiter
                        .distinct()                       //remove duplicates
                        .collect(Collectors.joining(delimiter)); //rejoin string
            }
        } else
            return result;
    }


}
//Explode your records using this
//    private String handleExplode(String ExplodeType, String result) {
//        if (ExplodeType.equalsIgnoreCase("New Lines")) {
//          //  return result.replaceAll("[\\t\\n\\r]+", ",");
//            return handleConvertString(result,"\\t\\n\\r]+",",");
//        } else if (ExplodeType.equalsIgnoreCase("Spaces")) {
//           // return result.replaceAll("\\s", ",");
//            return handleConvertString(result,"\\s",",");
//        } else if (ExplodeType.equalsIgnoreCase("Commas")) {
//            //return result.replaceAll(",", ",");
//            return handleConvertString(result,",",",");
//        } else if (ExplodeType.equalsIgnoreCase("semicolons")) {
//          //  return result.replaceAll(";", ",");
//            return handleConvertString(result,";",",");
//        }
//        return result.replaceAll("[\\t\\n\\r]+", ",");
//    }


//Character used between records
//    private String handleDelimiter(String delimiter,String result,String regex,String replace){
//        if(delimiter.equalsIgnoreCase(",")){
//            return handleConvertString(result,staticMap.get(regex),",");
//            //return result.replaceAll(staticMap.get(explode), ",");
//
//        }else if(delimiter.equalsIgnoreCase(";")){
//            return handleConvertString(result,staticMap.get(regex),";");
//        }else if(delimiter.equalsIgnoreCase("|")){
//            return handleConvertString(result,staticMap.get(regex),"|");
//        }else if(delimiter.equalsIgnoreCase("spaces")){
//            return handleConvertString(result,staticMap.get(regex),"\\s");
//        }else if(delimiter.equalsIgnoreCase("new line")){
//            return handleConvertString(result,staticMap.get(regex),"\\t\\n\\r]+");
//        }
//        return handleConvertString(result,staticMap.get(regex),",");
//
//    }

//convert to comma separated string
//    private String handleConvertString(String s,String regex,String replace) {
//        //String result = s.replaceAll("[\\t\\n\\r]+", ",");
//        String result = s.replaceAll(regex, replace);
//        return result;
//    }
