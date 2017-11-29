package task2;

import java.util.Scanner;
import java.util.concurrent.*;


public class Task2 {

    public static void main ( String[] args ) throws ExecutionException, InterruptedException {
        int numProcessors = Runtime.getRuntime ().availableProcessors ();
        ExecutorService threadPool = Executors.newFixedThreadPool ( numProcessors );


        int size = 80000000;
        int[] intArray = createArray ( size );

        System.out.println ( "Type number of calculations" );
        Scanner scanner = new Scanner ( System.in );
        int number = Integer.parseInt ( scanner.nextLine () );

        Utils utils = new Utils ( intArray );

        System.out.println ( "Calculation by ONE thread" );

        double sum = 0.0;
        long startTime = System.currentTimeMillis ();
        for ( int i = 0 ; i < number ; i++ ) {
            sum += utils.sumElem ( 0 , intArray.length );
        }
        System.out.println ( "Time=" + ( System.currentTimeMillis () - startTime ) );
        System.out.println ( sum );


        System.out.println ( "Calculation by threads" );

        startTime = System.currentTimeMillis ();
        sum = 0.0;
        for ( int j = 0 ; j < number ; j++ ) {
            sum += utils.calculateTreads ( numProcessors );
        }
        System.out.println ( "Time=" + ( System.currentTimeMillis () - startTime ) );
        System.out.println ( sum );


        System.out.println ( "Calculation by threadPool" );
        startTime = System.currentTimeMillis ();
        sum = 0.0;
        for ( int j = 0 ; j < number ; j++ ) {
            sum += utils.calculateTreadPool ( numProcessors , threadPool );
        }
        threadPool.shutdown ();
        System.out.println ( "Time=" + ( System.currentTimeMillis () - startTime ) );
        System.out.println ( sum );
    }

    private static int[] createArray ( int size ) {
        int[] intArray = new int[ size ];
        for ( int i = 0 ; i < size ; i++ ) {
            intArray[ i ] = i + 1;
        }
        return intArray;
    }

}