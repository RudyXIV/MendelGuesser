package com.hazebooth.MendelGuesser.genetics;

import org.omg.CORBA.DynAnyPackage.Invalid;

/**
 * Created by Haze on 1/20/2015.
 */
public enum Dominance {
    HOMORECESSIVE, HOMODOMINANT, HETEROZYGOUS;

    public static Dominance interpret(String dominance) throws InvalidDominanceException{
        if(dominance.split("-")[0].equalsIgnoreCase("het") && (dominance.contains("r") || dominance.contains("d"))){
            throw new InvalidDominanceException(dominance);
        }
        if(dominance.split("-")[0].equalsIgnoreCase("het")){
            return HETEROZYGOUS;
        } else {
            switch (dominance.toLowerCase()) {
                case "recessive":
                case "rec":
                case "r":
                    return HOMORECESSIVE;
                case "dominant":
                case "dom":
                case "d":
                    return HOMODOMINANT;
            }
        }
        return null;
    }


    public static class InvalidDominanceException extends Exception {
        public InvalidDominanceException(String type){
            super("[" + type + "] is an invalid domiannce type.");
        }
    }
}
