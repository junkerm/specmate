import { Config } from '../config/config';
import { IContainer } from "../model/IContainer";

export class Id {

    public static fromName(name: string): string {
        var arr: string[] = name.toLowerCase().split('');
        for (var i = 0; i < arr.length; i++) {
            var char: string = arr[i];
            if (!Id.isAllowed(char)) {
                arr[i] = Config.ID_FORBIDDEN_REPLACEMENT;
            }
        }
        return arr.join('');
    }

    private static isAllowed(char: string): boolean {
        return Config.ID_ALLOWED_CHARS.indexOf(char) >= 0;
    }

    private static replaceAll(str: string, search: string, replacement: string): string {
        while (str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    }

    public static highestId(elements: IContainer[]): number {
        var max: number = Config.ID_MIN;
        for(var i = 0; i < elements.length; i++) {
            var element: IContainer = elements[i];
            var regex = new RegExp('.+' + Config.ID_SEP + '([0-9]+)', 'g');
            var match = regex.exec(element.id);
            if(match) {
                var num: number = Number(match[1]);
                if(num > max) {
                    max = num;
                }
            }
        }
        return max;
    }

    public static generate(existingElements: IContainer[], prefix: string): string {
        return prefix + Config.ID_SEP + (Id.highestId(existingElements) + 1);
    }
}