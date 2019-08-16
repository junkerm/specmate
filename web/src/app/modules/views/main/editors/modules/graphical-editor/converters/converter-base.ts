export abstract class ConverterBase<N, T> {
    public abstract convertTo(item: N): T;
    public abstract convertFrom(value: T): N;
}
