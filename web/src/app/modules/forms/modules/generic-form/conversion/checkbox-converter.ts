import { IConverter } from './i-converter';

export class CheckboxConverter implements IConverter<string, boolean> {
    convertFromModelToControl(val: string): boolean {
        if (val && val === 'true') {
            return true;
        }
        return null;
    }
    convertFromControlToModel(val: boolean): string {
        if (val) {
            return 'true';
        }
        return 'false';
    }
}
