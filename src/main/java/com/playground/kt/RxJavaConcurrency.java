package com.playground.kt;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class RxJavaConcurrency {
    public static void waitSleep() {
        try {
            Thread.sleep(999999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int intenseCalculation(int i) {
        try {
            System.out.println("Calculating " + i + " on " + Thread.currentThread().getName());

            Thread.sleep(10);
            return i;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        Observable<Integer> vals = Observable.range(1,100000);

        vals.flatMap(val -> Observable.just(val)
                .subscribeOn(Schedulers.computation())
                .map(i -> intenseCalculation(i))
        ).subscribe(val -> System.out.println("Subscriber received "
                + val + " on "
                + Thread.currentThread().getName())
        );

        waitSleep();
    }
}
