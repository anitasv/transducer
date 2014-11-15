package me.anitas;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import java.util.List;

public interface TransformerUtils {

    static <Input, Output> Observable.Transformer<Input, Output> map(
            Func1<? super Input, ? extends Output> func) {

        return inputObservable -> inputObservable.map(func);
    }

    static <Input, Output> Observable.Transformer<Input, Output> reduce(
            Output initialValue,
            Func2<Output, ? super Input, Output> accumulator) {

        return inputObservable -> inputObservable.reduce(initialValue, accumulator);
    }

    static <Input> Observable.Transformer<Input, Input> filter(
            Func1<? super Input, Boolean> predicate) {

        return inputObservable -> inputObservable.filter(predicate);
    }

    static <Input, Output> Observable.Transformer<Input, Output> flatMap(
            Func1<? super Input, ? extends Iterable<? extends Output>> flatter) {

        return inputObservable -> inputObservable.flatMapIterable(flatter);
    }

    static <Input> Observable.Transformer<Input, Input> skip(int num) {

        return inputObservable -> inputObservable.skip(num);
    }

    static <Input> Observable.Transformer<Input, List<Input>> buffer(int num) {

        return inputObservable -> inputObservable.buffer(num);
    }

    static <Input, Output> Observable.Transformer<Input, Output> cast(
            Class<Output> clazz) {

        return inputObservable -> inputObservable.cast(clazz);
    }

    static <Input, Output, Intermediate> Observable.Transformer<Input, Output> combine(
            Observable.Transformer<Input, Intermediate> transformer1,
            Observable.Transformer<Intermediate, Output> transformer2) {
        return new CombinedTransducerBuilder()
                .chain(transformer1)
                .chain(transformer2)
                .build();
    }

    static <Input, Output, Int1, Int2> Observable.Transformer<Input, Output> combine(
            Observable.Transformer<Input, Int1> transformer1,
            Observable.Transformer<Int1, Int2> transformer2,
            Observable.Transformer<Int2, Output> transformer3) {
        return new CombinedTransducerBuilder()
                .chain(transformer1)
                .chain(transformer2)
                .chain(transformer3)
                .build();
    }
    static <Input, Output, Int1, Int2, Int3> Observable.Transformer<Input, Output> combine(
            Observable.Transformer<Input, Int1> transformer1,
            Observable.Transformer<Int1, Int2> transformer2,
            Observable.Transformer<Int2, Int3> transformer3,
            Observable.Transformer<Int3, Output> transformer4) {
        return new CombinedTransducerBuilder()
                .chain(transformer1)
                .chain(transformer2)
                .chain(transformer3)
                .chain(transformer4)
                .build();
    }

}
