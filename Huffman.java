/**
 *This is the Huffman java class where we first read in from a file containing all the Alphabets in the english language and create a Huffman tree from that.
 *We call the findEncoding method to get all the encoded values for the alphabets from the Huffman tree that we have created and store the values in a String[] array.
 *We then prompt the user to input the line(Capital letters only) that needs to be encoded and decoded.We then call both the encoding and decoding methods
 *respectively to present the user with the encoded and decoded version of their input line.
 *
 *
 * @author Aarnav Lakhanpal 
 */



import java.io.*;
import java.util.*;



public class Huffman {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner op = new Scanner(System.in);

        //Creating two queues S and T after which we will get the Huffman Tree as the only element in queue T.
        Queue<BinaryTree<Pair>> S = new LinkedList<>();
        Queue<BinaryTree<Pair>> T = new LinkedList<>();

        //Using this to keep track of all the alphabets in the english language Alphabetically.
        ArrayList<Character> alphabetTracker = new ArrayList<>();

        //Reading from a file.
        System.out.print("Enter the name of the file with letters and probability: ");

        String filename = op.nextLine();
        File file = new File(filename);
        Scanner inputFile = new Scanner(file);
        StringTokenizer token;

        //Input from a file.
        while (inputFile.hasNext()) {
            String line = inputFile.nextLine();
            token = new StringTokenizer(line, "\t");


            String c = token.nextToken();
            double prob = Double.parseDouble(token.nextToken());
            char value = c.charAt(0);


            BinaryTree<Pair> tree = new BinaryTree<>();
            tree.makeRoot(new Pair(value, prob));
            S.add(tree);


        }
        inputFile.close();

    //We compare the first smallest weight tree from both the queues and perform actions upon them to create a 
    //Huffman Tree
        while (!(S.isEmpty())) {
            BinaryTree<Pair> A = new BinaryTree<>();
            BinaryTree<Pair> B = new BinaryTree<>();

            if (T.isEmpty()) {
                A = S.remove();
                B = S.remove();
            }

            if (!(T.isEmpty())) {

                if (S.peek().getData().getProb() <= T.peek().getData().getProb()) {
                    //element in S is smaller than T, we Assign A to removed element from S.
                    A = S.remove();
                } else if (S.peek().getData().getProb() > T.peek().getData().getProb()) {
                    //element in S is greater than T, we Assign A to removed element from T.
                    A = T.remove();
                }

                if (T.isEmpty() && !S.isEmpty()) {
                    B = S.remove();
                } else if (!T.isEmpty() && S.isEmpty()) {
                    B = T.remove();
                } else {
                    if (S.peek().getData().getProb() <= T.peek().getData().getProb()) {
                        //element in S is smaller than T, we Assign B to removed element from S.
                        B = S.remove();

                    } else if (S.peek().getData().getProb() > T.peek().getData().getProb()) {
                        //element in S is greater than T, we Assign B to removed element from T.
                        B = T.remove();

                    }
                }
            }

            BinaryTree<Pair> P = new BinaryTree<>();
            P.makeRoot(new Pair('0', (A.getData().getProb() + B.getData().getProb())));
            P.attachLeft(A);
            P.attachRight(B);

            T.add(P);

        }

        while (T.size() > 1) {
            BinaryTree<Pair> X = new BinaryTree<>();
            BinaryTree<Pair> Y = new BinaryTree<>();

            X = T.remove();
            Y = T.remove();

            BinaryTree<Pair> P = new BinaryTree<>();
            P.makeRoot(new Pair('0', X.getData().getProb() + Y.getData().getProb()));
            P.attachLeft(X);
            P.attachRight(Y);

            T.add(P);

        }


        //Taking input from the user.
        ArrayList<Character> inputList = new ArrayList<>();


        System.out.print("Enter a line of text (uppercase letters only): ");
        String input = op.nextLine();
        for (int i = 0; i < input.length(); i++) {
            inputList.add(input.charAt(i));
            System.out.print(inputList.get(i) + "\t");
        }

        //This String array now has all the encoded values of the all the alphabets.
        String[] HuffmanEncoded = findEncoding(T.peek());


        System.out.println();
        System.out.println();


        System.out.println("Here's the encoded line: ");
        System.out.println();
        char a = 'A';
        for (int i = 0; i < 26; i++) {
            alphabetTracker.add(a);
            a++;
        }

        ArrayList<String> encodedList = encode(HuffmanEncoded, inputList, alphabetTracker);
        encodePrint(HuffmanEncoded, inputList, alphabetTracker);


        System.out.println();


        System.out.println();
        System.out.println("The decoded line is: ");
        System.out.println();
        decodePrint(alphabetTracker, encodedList, HuffmanEncoded);

        /*ArrayList<Character>decodedList=decode(alphabetTracker, encodedList, HuffmanEncoded);
        for(int i=0;i<decodedList.size();i++){
            System.out.print(decodedList.get(i));
         }*/
        System.out.println();



    }

   /**
    * This method encodes the users input using the codes generated through Huffman tree.
    *
    *
    * @param HuffmanEncoded
    * @param inputList
    * @param track
    * @return an ArrayList of type String containing the encoded input.
    */
   
    public static ArrayList<String> encode(String[] HuffmanEncoded, ArrayList<Character> inputList, ArrayList<Character> track) {
        int idx = 0;

        ArrayList<String> encodedList = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            idx = track.indexOf(inputList.get(i));

            if(idx==-1){
                encodedList.add(" ");
            }
             else {
                encodedList.add(HuffmanEncoded[idx]);
            }
        }
         return encodedList;
    }



    public static void encodePrint(String[] HuffmanEncoded, ArrayList<Character> inputList, ArrayList<Character> track) {
        int idx = 0;

        for (int i = 0; i < inputList.size(); i++) {
            idx = track.indexOf(inputList.get(i));

            if (inputList.get(i).equals(' ')) {
                System.out.print(" ");
            }
            else {
                System.out.print(HuffmanEncoded[idx]);
            }

        }

    }


  /**
   * This method decodes the users input using the codes generated through Huffman tree.
   * 
   * @param track
   * @param encodedValue
   * @param HuffmanEncoded
   * @return an ArrayList of type character containing the decoded input.
   */
    public static ArrayList<Character> decode(ArrayList<Character> track, ArrayList<String> encodedValue, String[] HuffmanEncoded) {
        int idx = 0;
        ArrayList<String> huffmanEncoded = new ArrayList<>( Arrays.asList( HuffmanEncoded ) );
        ArrayList<Character> decodedList=new ArrayList<>();

        for (int i = 0; i < encodedValue.size(); i++) {
            idx =huffmanEncoded.indexOf(encodedValue.get(i));

            if (idx==-1) {
                decodedList.add(' ');
            } else {
                decodedList.add(track.get(idx));
            }
        }

        return decodedList;
    }


    public static void decodePrint(ArrayList<Character> track, ArrayList<String> encodedValue, String[] HuffmanEncoded) {
        int idx = 0;
        ArrayList<String> huffmanEncoded = new ArrayList<>( Arrays.asList( HuffmanEncoded ) );

        for (int i = 0; i < encodedValue.size(); i++) {
            idx =huffmanEncoded.indexOf(encodedValue.get(i));

            if (idx==-1) {
                System.out.print(" ");
            } 
            else {
                System.out.print(track.get(idx));
            }
        }


    }

        private static String[] findEncoding (BinaryTree < Pair > bt) {
            String[] result = new String[26];
            findEncoding(bt, result, "");
            return result;
        }
        private static void findEncoding (BinaryTree < Pair > bt, String[]a, String prefix){
            // test is node/tree is a leaf
            if (bt.getLeft() == null && bt.getRight() == null) {
                a[(bt.getData().getValue() - 65)] = prefix;
            }
            // recursive calls
            else {
                findEncoding(bt.getLeft(), a, prefix + "0");
                findEncoding(bt.getRight(), a, prefix + "1");
            }
        }


    }



