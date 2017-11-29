import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Task1 {
    public static void main ( String[] args ) throws ExecutionException, InterruptedException {
        Scanner sc = new Scanner ( System.in );
        System.out.println ( "Enter the first number:" );
        final double num1 = Double.parseDouble ( sc.nextLine () );
        System.out.println ( "Enter the second number:" );
        final double num2 = Double.parseDouble ( sc.nextLine () );
        String symbols = "+ - * / % == > < ";
        System.out.println ( "Enter one of these symbols to get the result of operations: " + symbols );
        System.out.println ();
        final String symbol = sc.nextLine ();
        if ( ! symbols.contains ( symbol ) ) return;

        FutureTask <String> futureTask = new FutureTask <String> ( new Callable <String> () {
            public String call ( ) throws Exception {
                TaskToDo toDo = new TaskToDo ();
                return toDo.toDo ( num1 , num2 , symbol );
            }
        } );
        new Thread ( futureTask ).start ();
        System.out.println ( futureTask.get () );
    }
}

class TaskToDo {

    public String toDo ( double num1 , double num2 , String symbol ) {
        if ( symbol.equals ( "+" ) ) return Double.toString ( num1 + num2 );
        if ( symbol.equals ( "-" ) ) return Double.toString ( num1 - num2 );
        if ( symbol.equals ( "*" ) ) return Double.toString ( num1 * num2 );
        if ( symbol.equals ( "/" ) ) return Double.toString ( num1 / num2 );
        if ( symbol.equals ( "%" ) ) return Double.toString ( num1 % num2 );
        if ( symbol.equals ( "==" ) ) return Boolean.toString ( num1 == num2 );
        if ( symbol.equals ( "<" ) ) return Boolean.toString ( num1 < num2 );
        if ( symbol.equals ( ">" ) ) return Boolean.toString ( num1 > num2 );
        return null;
    }
}
