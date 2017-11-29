package task2;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;


public class Utils {
    private int[] array;

    public Utils(int[] array) {
        this.array = array;
    }

    public double calculateTreadPool(int numProcessors, ExecutorService threadPool) throws ExecutionException, InterruptedException {
        ArrayList<FutureTask<Double>> futureTasks = new ArrayList<>(numProcessors);
        double sum=0.0;
        for (int i = 0; i < numProcessors; i++) {
            FutureTask<Double> futureTask = getFutureTask(numProcessors, i);
            threadPool.submit(futureTask);
            futureTasks.add(futureTask);
        }
        for (FutureTask<Double> value: futureTasks) {
            sum += value.get();
        }
        return sum;
    }

    private FutureTask<Double> getFutureTask(double numProcessors, int i) {
        int left = (int) (Math.ceil(array.length / numProcessors)) * i;
        int right = (int) (Math.ceil(array.length / numProcessors)) * (i + 1);
        if (right > array.length) right = array.length;

        int finalRight = right;
        return new FutureTask<Double>(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return sumElem (left, finalRight);
            }
        });
    }

    public double calculateTreads(int numProcessors) throws ExecutionException, InterruptedException {
        ArrayList<FutureTask<Double>> futureTasks = new ArrayList<>(numProcessors);
        double sum = 0.0;
        for (int i = 0; i < numProcessors; i++) {
            FutureTask<Double> futureTask = getFutureTask(numProcessors, i);
            Thread thread = new Thread(futureTask);
            thread.start();
            futureTasks.add(futureTask);
        }
        for (FutureTask<Double> value: futureTasks) {
            sum += value.get();
        }
        return sum;
    }

    public double sumElem(int startId, int finishId){
        double sum =0.0;
        for (int i = startId; i < finishId; i++) {
            sum += sumSinAndCos(array[i]);
        }
        return sum;
    }
    private double sumSinAndCos(int x) {
        return (Math.sin(x)+ Math.cos(x));
    }
}
