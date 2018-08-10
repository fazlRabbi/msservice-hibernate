package no.hib.utils;

public class SearchStringGenerator {

    public static String getSearchString(String selector, String query, String queryString){
        if(!queryString.toLowerCase().equals("true") && !queryString.toLowerCase().equals("false")){
            queryString = "\"" + queryString + "\"";
        }
        String searchString = "{\"selector\": {\"" + selector + "\": {\"$" + query + "\": " + queryString + "}}}";
        return searchString;
    }

    public static String getSearchString(String selector1, String query1, String queryString1, String selector2,
                                         String query2, String queryString2) {
        if(!queryString1.toLowerCase().equals("true") && !queryString1.toLowerCase().equals("false")){
            queryString1 = "\"" + queryString1 + "\"";
        }
        if(!queryString2.toLowerCase().equals("true") && !queryString2.toLowerCase().equals("false")){
            queryString2 = "\"" + queryString2 + "\"";
        }
        String searchString = "{\"selector\": {\"" + selector1 + "\": {\"$" + query1 + "\": " + queryString1 + "}," +
                "\"" + selector2 + "\": {\"$" + query2 + "\": " + queryString2 + "}}}";
        return searchString;
    }
}