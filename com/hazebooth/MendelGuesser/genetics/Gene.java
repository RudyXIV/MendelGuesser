package com.hazebooth.MendelGuesser.genetics;

import com.hazebooth.MendelGuesser.Main;

import java.util.Objects;

/**
 * Created by Haze on 1/20/2015.
 */
public class Gene {

    public String name;
    public Dominance dominance;

    public Gene(String name, Dominance dominance){
        this.name = name;
        this.dominance = dominance;
    }

    public String getIdentifier(){
        char ident = name.toLowerCase().charAt(0);
        String helper = doesListContainAnotherTypeOfMe()[1]+"";
        if(Integer.parseInt(helper)==0){
            helper = "";
        }
        switch (dominance){
            case HOMODOMINANT:
                return Character.toUpperCase(ident) + "" + Character.toUpperCase(ident) + "" + helper;
            case HETEROZYGOUS:
                return Character.toUpperCase(ident) + "" + ident + "" + helper;
            case HOMORECESSIVE:
                return ident + "" + ident + "" + helper;
        }
        return "nil";
    }

    boolean useCapitalsBasedOnDominance(){
        return this.dominance == Dominance.HETEROZYGOUS || this.dominance == Dominance.HOMODOMINANT;
    }

    private Object[] doesListContainAnotherTypeOfMe(){
        Object[] objLst = new Object[]{false,-1};
        int helper = 0;
        for(Gene g : Main.geneList){
            if(g.name.equalsIgnoreCase(this.name)){
                objLst[0] = true;
                objLst[1] = helper;
                helper++;
            }
        }
        return objLst;
    }

}
