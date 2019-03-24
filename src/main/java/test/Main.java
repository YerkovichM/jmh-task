package test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class Main {

    int[] mas;
    int N = 100000;

    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(Main.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Setup
    public void setup(){
        mas = new int[N];
        for (int i = 0; i < N; i++) {
            mas[i] = (int)(Math.random()*100);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void withStackTrace(){
        try {
            throw new Exception();
        }catch (Exception e){
            e.getStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void withoutStackTrace(){
        try {
            throw new Exception();
        }catch (Exception e){
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void forLoop(){
        int sumLess50 = 0;
        for (int x: mas) {
            if (x < 50) {
                sumLess50 += x;
            }
        }
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void stream(){
        int sumLess50 = Arrays.stream(mas)
                .filter(x -> x < 50)
                .sum();
    }
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void parallelStream(){
        int sumLess50 = Arrays.stream(mas)
                .parallel()
                .filter(x -> x < 50)
                .sum();
    }
}