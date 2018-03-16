export interface IConverter<ModelType, ControlType> {
    convertFromModelToControl(val: ModelType): ControlType;
    convertFromControlToModel(val: ControlType): ModelType;
}
