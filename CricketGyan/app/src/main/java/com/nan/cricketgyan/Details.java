package com.nan.cricketgyan;

import java.util.Comparator;

/**
 * Created by arvind on 13/3/17.
 */

public class Details {

    String name = "",country = "", runs = "", matches = "", description = "", image = "";



    public Details (String name, String country, String runs, String matches, String description, String image){
        this.name = name;
        this.country = country;
        this.description = description;
        this.matches = matches;
        this.runs = runs;
        this.image = image;
    }

    public static Comparator<Details> NameComparator = new Comparator<Details>() {

        public int compare(Details s1, Details s2) {
            String Name1 = s1.name.toUpperCase();
            String Name2 = s2.name.toUpperCase();

            return Name1.compareTo(Name2);

        }};

    public static Comparator<Details> Runs_asc = new Comparator<Details>() {

        public int compare(Details s1, Details s2) {

            int run1 = Integer.parseInt(s1.runs);
            int run2 = Integer.parseInt(s2.runs);

            return run1-run2;

        }};
    public static Comparator<Details> Runs_dsc = new Comparator<Details>() {

        public int compare(Details s1, Details s2) {

            int run1 = Integer.parseInt(s1.runs);
            int run2 = Integer.parseInt(s2.runs);

            return run2-run1;

        }};
    public static Comparator<Details> MatchesComparator = new Comparator<Details>() {

        public int compare(Details s1, Details s2) {

            int match1 = Integer.parseInt(s1.matches);
            int match2 = Integer.parseInt(s2.matches);

            return match1-match2;

        }};
}
