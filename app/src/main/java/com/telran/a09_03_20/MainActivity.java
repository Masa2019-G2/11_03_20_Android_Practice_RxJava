package com.telran.a09_03_20;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Maybe;
import io.reactivex.MaybeEmitter;
import io.reactivex.MaybeOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MessageManager manager = new MessageManagerImpl();
    Subscriber s = new MySubscriber();
    Disposable d;

    Button startBtn;
    ProgressBar myProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager.subscribe(s);
        manager.subscribe(new SuperPuperSubscriber(this));
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnOne).setOnClickListener(this);
        findViewById(R.id.btnTwo).setOnClickListener(this);
        startBtn = findViewById(R.id.startrBtn);
        myProgress = findViewById(R.id.myProgress);
        startBtn.setOnClickListener(this);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                manager.unsubscribe(s);
            }
        }).start();

//        basicCreation();
//        sourcesTemplates();

//        mapper(createObservable())
//                .subscribe(s -> Log.d("MY_TAG", "onCreate: " + s));
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnOne){
            manager.push("Clicked one");
        }else if(v.getId() == R.id.btnTwo){
            manager.push("Clicked btn two");
        }else if(v.getId() == R.id.startrBtn){
            myProgress.setVisibility(View.VISIBLE);
            startBtn.setEnabled(false);
            Gson gson = new Gson();
            Worker worker = new Worker();
//            String json = worker.doWork();
//            Person p = gson.fromJson(json,Person.class);
//            Log.d("MY_TAG", "onClick: " + p);
//            myProgress.setVisibility(View.INVISIBLE);
//            startBtn.setEnabled(true);


            worker.doWork()
                    .map(json -> gson.fromJson(json,Person.class))
                    .doOnNext(p -> Log.d("MY_TAG", "onClick: " + p))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(person -> {
                        myProgress.setVisibility(View.INVISIBLE);
                        startBtn.setEnabled(true);
                    });
        }
    }

    public Observable<String> mapper(Observable<Integer> obs){
        return obs.filter(n -> n%2 == 0)
                .map(n -> n*2)
                .map(n -> "Item: " + n);
    }

    public Observable<Integer> createObservable(){
        return Observable.just(1,2,3,4,5,6,7,8,9);
    }
    public void sourcesTemplates(){
        Observable<Integer> observable = Observable.just(10,15,25);
        Integer[] arr = {100,500,600,700};
        observable = Observable.fromArray(arr);

        observable = Observable.fromIterable(Arrays.asList(arr));

        observable = Observable.fromCallable(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                throw new RuntimeException("My Error");
            }
        });

        d = observable.subscribe(
                item -> Log.d("MY_TAG", "onNext: " + item),
                error -> Log.d("MY_TAG", "onError: " + error),
                ()-> Log.d("MY_TAG", "onComplete: ")
        );
    }

    public void types(){
        Single<Integer> single = Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(SingleEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(123);
            }
        });

        single.subscribe(value -> Log.d("MY_TAG", "single: " + value));

        Completable completable = Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(CompletableEmitter emitter) throws Exception {
                emitter.onComplete();
            }
        });

        completable.subscribe(()-> Log.d("MY_TAG", "Completable: complete"));

        Maybe<Integer> maybe = Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(MaybeEmitter<Integer> emitter) throws Exception {
                emitter.onSuccess(234);
            }
        });

        maybe.subscribe(
                num -> Log.d("MY_TAG", "maybe onSuccess: " + num),
                error -> Log.d("MY_TAG", "maybe error: " + error),
                () -> Log.d("MY_TAG", "maybe onComplete: ")
                );

        Observable<Integer> observable = Observable.just(456);
        Single<Integer> integerSingle = Single.fromObservable(observable);
        
    }

    public void basicCreation(){
        Observable<Integer> observable = new Observable<Integer>() {
            @Override
            protected void subscribeActual(@NonNull Observer<? super Integer> observer) {
                observer.onNext(100);
                observer.onNext(200);
                observer.onNext(300);
//                observer.onError(new RuntimeException());
                observer.onComplete();
                observer.onNext(500);
            }
        };

        d = observable.subscribeWith(new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer integer) {
                Log.d("MY_TAG", "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("MY_TAG", "onError: " + e);
                d.dispose();
            }

            @Override
            public void onComplete() {
                Log.d("MY_TAG", "onComplete: ");
                d.dispose();
            }
        });

//        observable.subscribe(new Observer<Integer>() {
//            @Override
//            public void onSubscribe(@NonNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NonNull Integer integer) {
//                Log.d("MY_TAG", "onNext: " + integer);
//            }
//
//            @Override
//            public void onError(@NonNull Throwable e) {
//                Log.d("MY_TAG", "onError: " + e);
//            }
//
//            @Override
//            public void onComplete() {
//                Log.d("MY_TAG", "onComplete: ");
//            }
//        });
    }
}
