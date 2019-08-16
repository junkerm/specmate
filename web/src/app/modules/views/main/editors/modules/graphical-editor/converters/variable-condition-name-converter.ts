import { ConverterBase } from './converter-base';

export class VariableConditionToNameConverter extends ConverterBase<{ variable: string, condition: string }, string> {

    static SEPARATOR = ':';

    public convertTo(item: { variable: string, condition: string }): string {
        return item.variable + VariableConditionToNameConverter.SEPARATOR + ' ' + item.condition;
    } public convertFrom(value: string): { variable: string, condition: string } {
        const parts = value.split(VariableConditionToNameConverter.SEPARATOR);
        const variable = parts[0].trim();
        const condition = value.substring(parts[0].length + VariableConditionToNameConverter.SEPARATOR.length).trim();
        return {
            variable: variable,
            condition: condition
        };
    }
}
