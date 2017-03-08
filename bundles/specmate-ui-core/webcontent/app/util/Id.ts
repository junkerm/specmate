import { Url } from './Url';
import { Config } from '../config/config';

export class Id {

    

    public static fromName(name: string): string {
        for (var i = 0; i < Config.ID_FORBIDDEN_CHARS.length; i++) {
            name = Id.replaceAll(name, Config.ID_FORBIDDEN_CHARS[i], Config.ID_FORBIDDEN_REPLACEMENT);
        }
        return name;
    }

    private static replaceAll(str: string, search: string, replacement: string): string {
        while (str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    }
}