package me.anitas;

import rx.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CombinedTransducerBuilder {

    private final List<Observable.Transformer<Object, Object>> transformerUtilsList;

    public CombinedTransducerBuilder() {
        this.transformerUtilsList = new ArrayList<>();
    }

    public class Connector<Input, Output> {

        @SuppressWarnings("unchecked")
        public <Next> Connector<Input, Next> chain(Observable.Transformer<Output, Next> transformer) {
            transformerUtilsList.add((Observable.Transformer<Object, Object>) transformer);
            return new Connector<>();
        }

        public Observable.Transformer<Input, Output> build() {

            return new CombinedTransformerUtils<Input, Output>(transformerUtilsList);
        }
    }

    @SuppressWarnings("unchecked")
    public <Input, Output> Connector<Input, Output> chain(Observable.Transformer<Input, Output> transformer) {
        transformerUtilsList.add((Observable.Transformer<Object, Object>) transformer);
        return new Connector<>();
    }

    private static class CombinedTransformerUtils<Input, Output> implements Observable.Transformer<Input, Output> {

        private final List<Observable.Transformer<Object, Object>> transformerUtilsList;

        private CombinedTransformerUtils(List<Observable.Transformer<Object, Object>> transformerUtilsList) {
            List<Observable.Transformer<Object, Object>> tempList = new ArrayList<>(transformerUtilsList.size());
            Collections.copy(tempList, transformerUtilsList);
            this.transformerUtilsList = Collections.unmodifiableList(tempList);
        }

        @Override @SuppressWarnings("unchecked")
        public Observable<Output> call(Observable<Input> input) {
            Observable<Object> temp = (Observable<Object>) input;
            for (Observable.Transformer<Object, Object> transformerUtils : transformerUtilsList) {
                temp = transformerUtils.call(temp);
            }
            return (Observable<Output>) temp;
        }
    }
}
