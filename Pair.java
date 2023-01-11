public class Pair implements Comparable<Pair> {

    //Instance variables
    private char value;
    private double prob;

    //Constructor

    public Pair(char value,double prob){
        this.value=value;
        this.prob=prob;
    }

    //Getters
    public char getValue(){return this.value;}
    public double getProb(){return this.prob;}

    //Setters
    public void setValue(char value){this.value=value;}
    public void setProb(double prob){this.prob=prob;}

    //compareTo method of the compareTo interface.
    public int compareTo(Pair p){
        return Double.compare(this.getProb(),p.getProb());
    }

    //toString method to represent the object in string format.
    public String toString(){
        String s="Character: "+this.value+" Probability: "+this.prob;
        return s;
    }



}

