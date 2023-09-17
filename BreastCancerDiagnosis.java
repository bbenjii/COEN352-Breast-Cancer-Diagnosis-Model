import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner; // Import the Scanner class to read text files


public class BreastCancerDiagnosis {
    public static void main(String[] args)
    {

        //create an array of patients
        Patient[] patients = new Patient[569];
        patientList(patients);

        //shuffle the list of patients and select N patients to train with, and T patients to test
        int n = 450, t = n/4;
        int k = 3;

        shuffle(patients);

        Patient[] training = new Patient[n];
        for(int i = 0; i < training.length; i++){
            training[i] = patients[i];
        }
        Patient[] testing = new Patient[t];
        for(int i = 0; i < testing.length; i++){
            testing[i] = patients[n+i];
        }

        //make tree
        kdTree tree = new kdTree(training);
        test(tree, testing, k);
    }

    public static void test(kdTree tree, Patient[] testing, int k){
        //function to test the machine learning model's accuracy and running time
        long start = System.currentTimeMillis();

        //for each patient in the testing list, diagnose it using the model, and increment accurate by 1 if the prediction is correct
        int accurate = 0;
        for (int i = 0; i < testing.length; i++){
            if(testing[i].diagnosis == diagnose(testing[i], tree, k)) accurate++;
        }
        //print accuracy and running time results

        System.out.println("Results:");

        double percentage = Double.valueOf(accurate)/Double.valueOf(testing.length)*100d ;
        System.out.println(accurate + "/"+testing.length +" correct predictions");
        System.out.println(percentage + "% accuracy" );

        long time = (System.currentTimeMillis() -start);
        System.out.println("Execution time: "+(time/1.0) + "ms");

    }
    public static char diagnose(Patient t, kdTree tree, int k){
        Patient[] neighbours =  tree.nearestNeighbours(t,k);

        int malignant = 0, benign = 0;
        for (int i = 0; i < neighbours.length ;i++){
            if(neighbours[i].diagnosis == 'M') malignant++;
            else benign++;
        }
        if(malignant > benign) return 'M';
        else return 'B';
    }

    public static void patientList(Patient[] patients) {
        //function that reads the data file and store each patient_information in an array
        File data_file = new File("src/WDBC.txt"); //the file must be in the same src file as code
        try {
            Scanner data = new Scanner(data_file); //open the text file with the Scanner class
            int count  = 0;

            while(data.hasNextLine()){ //each line of the file contains information of a patient

                //store the info the patient in an array
                String instance = data.nextLine();
                String[] info = instance.split(",") ;

                //separate and store the ID, Diagnosis and the first 10 attributes of the patient
                int ID = Integer.parseInt(info[0]);
                char diagnosis = info[1].charAt(0);
                Float[] attributes = new Float[10];
                for (int i = 2; i <=11; i++){
                    attributes[i-2] = Float.parseFloat(info[i]);
                }

                //instantiate an object representing the patient using its information
                patients[count++] = new Patient(ID, diagnosis, attributes);
            }
        }
        catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void shuffle(Patient[] p){
        //function to shuffle a list of patients
        int N = p.length;
        Random randomInt = new Random();
        for (int i = 0; i < N; i++){
            int j = randomInt.nextInt(i+1);
            Patient temp = p[i];
            p[i] = p[j];
            p[j] = temp;
        }
    }


//////////////////////////////////////////////////////////////////////////////////////////////////////


    public static class Patient {
        public int ID; //ID of the patient
        public char diagnosis; //diagnosis of the patient, either M (malignant)or B(Benign)

        public Float[] attributes; //an array containing each attribute (1 to 10)
        public  Patient(int id, char diag, Float[] att){ //constructor create instance
            ID = id;
            diagnosis = diag;

            if(att.length == 10) {
                attributes = new Float[att.length];
                for (int i = 0; i < 10; i++){
                    attributes[i] = att[i];
                }
            }
            else System.out.println("Array has " + att.length + " attributes");
        }
        public void info(){ //display the Patient's information
            String info = "Id: " + ID
                    + "\nDiagnosis: " + diagnosis
                    + "\nAttributes: ";
            for (int i = 0; i <10; i++){
                info =info + attributes[i] + ",";
            }
            System.out.println(info);
        }

        //a comparator for each 10 attributes of a patient
        public static class ByA1 implements Comparator<Patient> {
            public int compare(Patient a, Patient b){
                if((a.attributes[0] < b.attributes[0]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA2 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[1] < b.attributes[1]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA3 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[2] < b.attributes[2]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA4 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[3] < b.attributes[3]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA5 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[4] < b.attributes[4]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA6 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[5] < b.attributes[5]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA7 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[6] < b.attributes[6]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA8 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[7] < b.attributes[7]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA9 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[8] < b.attributes[8]))
                    return 1;
                else return 0;
            }
        }
        public static class ByA10 implements Comparator<Patient>{
            public int compare(Patient a, Patient b){
                if((a.attributes[9] < b.attributes[9]))
                    return 1;
                else return 0;
            }
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class kdTree {
        static Node root = null;
        double max_distance;
        Node[] bestneighbours;

        private static class Node{
            public int axis;
            public Patient patient;
            public int depth;
            public Node left, right;


            public Node(Patient m,int a, int d){
                axis = a;
                patient = m;
                depth = d;
            }
        }

        public kdTree(Patient[] p){
            root = kdTree(p, 0, p.length-1, 1);
        }

        private static Node kdTree(Patient[] p, int start, int end, int depth){

            if (end <= start) {
                return null;}
            int axis = depth % 10;
            int middle = start + (end-start)/2;


            switch (axis) { //sort the sublist according to corresponding axis
                case 1:
                    MergeSort.sort(p, start, end, new Patient.ByA1());
                    break;
                case 2:
                    MergeSort.sort(p, start, end, new Patient.ByA2());
                    break;
                case 3:
                    MergeSort.sort(p, start, end, new Patient.ByA3());
                    break;
                case 4:
                    MergeSort.sort(p, start, end, new Patient.ByA4());
                    break;
                case 5:
                    MergeSort.sort(p, start, end, new Patient.ByA5());
                    break;
                case 6:
                    MergeSort.sort(p, start, end, new Patient.ByA6());
                    break;
                case 7:
                    MergeSort.sort(p, start, end, new Patient.ByA7());
                    break;
                case 8:
                    MergeSort.sort(p, start, end, new Patient.ByA8());
                    break;
                case 9:
                    MergeSort.sort(p, start, end, new Patient.ByA9());
                    break;
                case 0:
                    MergeSort.sort(p, start, end, new Patient.ByA10());
                    break;

            }
            //make the node equal to the patient with the median attribute value
            Node n = new Node(p[middle], axis, depth);

            //repeat on the left and right side of the node
            n.left = kdTree(p, start, middle, depth+1);
            n.right = kdTree(p, middle+1, end, depth+1);

            return n;
        }

        public Patient[] nearestNeighbours(Patient target, int k){ //function to find the k-nearest neighbours
            if (root == null) return null;

            //reset list of neighbours and best distances
            max_distance = 0;
            bestneighbours = new Node[k];
            findNearest(root, target);


            //make a list of nearest patients and return it
            Patient[] neighbours = new Patient[bestneighbours.length];
            for(int i = 0; i < neighbours.length; i++){
                neighbours[i] = bestneighbours[i].patient;
            }

            return neighbours;
        }

        private void findNearest(Node root, Patient target){
            if (root == null) return;
            int axis = root.axis;

            //check if this is part of the k-nearest neighbour
            updateNeighbour(root, target);

            //traverse left or right of node
            if(target.attributes[axis-1] < root.patient.attributes[axis-1]){
                //traverse left of node
                findNearest(root.left, target);

                //rewinds and check if there could be a better neighbor on the other side
                if( distanceAxis(root.patient, target, axis) < max_distance){
                    findNearest(root.right, target);
                }
            }
            else{
                //traverse right of node
                findNearest(root.right, target);
                if( distanceAxis(root.patient, target, axis) < max_distance){
                    findNearest(root.left, target);
                }
            }
        }

        private void updateNeighbour(Node n, Patient target){
            //function to check if a given node is one of the k-nearest neighers

            if(bestneighbours[0] == null) { //if there's no current neighbour add node to the list
                bestneighbours[0] = n;
                return;
            }
            int max = 0; //index of nearest neighbour with the longest distance

            for(int i = 1; i < bestneighbours.length; i++){ //find index of neighbour with the longest distance

                if(bestneighbours[i] == null){ //if there's no neighbour at an index, add node to this index
                    bestneighbours[i] = n;
                    return;
                }

                //if distance of node at index i is bigger than the current furthest node, then index i becomes furthest neighbour
                if(distance(bestneighbours[i].patient, target) > distance((bestneighbours[max].patient), target)){
                    max = i;
                }
            }

            // if current node is closer than furthest neighbor, replace it
            if(distance(n.patient, target) < distance(bestneighbours[max].patient,target)){
                bestneighbours[max] = n;
            }
            //update max distance
            max_distance = distance((bestneighbours[max].patient), target);

        }

        private double distance(Patient a, Patient b){
            //function to check distance between two nodes

            double distance = 0;
            for (int i = 0; i<10; i++){
                distance += Math.pow((b.attributes[i] - a.attributes[i]), 2);
            }
            distance = Math.sqrt(distance);

            return distance;
        }
        private double distanceAxis(Patient p1, Patient p2, int axis){
            //function to check the distance between the axis of two nodes

            double distance = 0;
            float a = p1.attributes[axis-1];
            float b = p2.attributes[axis-1];
            distance = Math.sqrt((b-a)*(b-a));

            return distance;
        }
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public static class MergeSort {
        //a class to sort class to sort a list of patients by a given attribute comparator
        //taken from my mergesort code in Assignment 2

        public static void sort(Patient[] a, int start, int end,Comparator comparator) { //sort only a section of an array
            Patient[] aux = new Patient[a.length];
            sort(a, aux, start, end, comparator);
        }

        private static void sort(Patient[] a, Patient[] aux, int lo, int hi, Comparator comparator) {
            if (hi <= lo) return;
            int mid = lo + (hi - lo) / 2;
            sort(a, aux, lo, mid, comparator);
            sort(a, aux, mid + 1, hi, comparator);
            merge(a, aux, lo, mid, hi, comparator);
        }

        private static void merge(Patient[] a, Patient[] aux, int lo, int mid, int hi, Comparator comparator) {
            //first copy the array
            for (int k = lo; k <= hi; k++) {
                aux[k] = a[k];
            }
            //pointers to the two sorted subarrays
            int i = lo, j = mid + 1;

            //merge
            for (int k = lo; k <= hi; k++) {
                if (i > mid) a[k] = aux[j++];
                else if (j > hi) a[k] = aux[i++];
                else if (less(aux[j], aux[i], comparator)) a[k] = aux[j++];
                else a[k] = aux[i++];
            }
        }
        private static boolean less(Patient a, Patient b, Comparator comparator) {
            if (comparator.compare(a, b) == 1) return true;
            else return false;
        }
    }
}

