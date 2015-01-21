package com.hazebooth.MendelGuesser;

import com.hazebooth.MendelGuesser.genetics.Dominance;
import com.hazebooth.MendelGuesser.genetics.Gene;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    Written by Haze Booth.
 */
public class Main {

    /* user input */
    private static Scanner sc;
    /* the list containing all the dna types */
    public static List<Gene> geneList;
    public static boolean takeAnotherGene = false, firstTime = true;

    public static void main(String[] args) {
        /* create new instance of the scanner from console */
        sc = new Scanner(System.in);
        /* create new instance of the list */
        geneList = new ArrayList<>();
        /* take input into what the multiple gene names are */
            takeInGene();
        if(firstTime) takeAnotherGene = checkForAnotherGene();
           while(takeAnotherGene){
               takeInGene();
           }
        /* after we take in the genes, we must compare */

        StringBuilder easyHelper = new StringBuilder();
        int helper = 0;
        for(Gene g : geneList){
            System.out.println("Gene " + (helper+1) + ": {"+g.name + ", " + g.dominance.name() + ", " + g.getIdentifier() + "}");
            easyHelper.append(getNum(g)+" ");
            helper++;
        }
        System.out.println("Chance of all recessive: " + getPercentRecessiveness(easyHelper.toString()) + "%.");
        System.out.println("Chance of all dominant: " + getPercentDominance(easyHelper.toString()) + "%.");
    }


    static double getPercentRecessiveness(String helper){
        return 100 - getPercentDominance(helper);
    }
    static double getPercentDominance(String helper){
        double percent = 0;
        double numOfHomoDom = 0;
        double numOfHomoRec = 0;
        double numOfHetero = 0;
        for(String s : helper.split(" ")){
            switch (s){
                case "1":
                    numOfHetero++;
                    break;
                case "2":
                    numOfHomoDom++;
                    break;
                case "3":
                    numOfHomoRec++;
                    break;

            }
        }
        if(numOfHomoDom==geneList.size() || (numOfHomoRec == 0 && numOfHetero == 0)) return 100;
        percent = (numOfHomoRec + numOfHetero) / geneList.size();
        return percent*100;
    }

    static int getNum(Gene g){
        switch (g.dominance){
            case HETEROZYGOUS:
                return 1;
            case HOMODOMINANT:
                return 2;
            case HOMORECESSIVE:
                return 3;
        }
        return -1;
    }

    public static void takeInGene(){
        System.out.println("Please write the gene's name below.");
        /* get the gene name */
        String geneName = sc.nextLine();
        Dominance dominance = null;
        /* a nifty check to keep methods low */
        while (dominance==null) {
            System.out.println("Please write whether or not this gene is dominant. ie. [het] or [dom] or [rec]");
            /* interpret dominance of gene */
            try {
                /* 'try' to interpret and set dominance */
                dominance = Dominance.interpret(sc.nextLine());
            }catch (Dominance.InvalidDominanceException e) {
                /* have the user redo */
                System.out.println(e.getMessage() + " Please try again.");
            }
        }
        /* verify that the user was successful in creating a gene */
        System.out.println("Creating new gene: {" + geneName + ", " + dominance.name() + "}");
        geneList.add(new Gene(geneName,dominance));
        if(!firstTime) takeAnotherGene = checkForAnotherGene();
    }

    public static boolean checkForAnotherGene(){
        if(firstTime) firstTime = false;
        System.out.println("Would you like to add another gene to the list? [Y/N]");
        boolean helper = sc.nextLine().equalsIgnoreCase("y");
        takeAnotherGene = helper;
        return helper;
    }
}
