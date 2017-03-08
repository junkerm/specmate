import { Url } from './Url';

export class Id {

    private static FORBIDDEN_CHARS = ['/', ' ', '\'', '"', '?', '%', '(', ')', '@', ',', '.', '[', ']', '{', '}', '--'];
    private static FORBIDDEN_REPLACEMENT = '-';

    public static fromName(name: string): string {
        for (var i = 0; i < Id.FORBIDDEN_CHARS.length; i++) {
            name = Id.replaceAll(name, Id.FORBIDDEN_CHARS[i], Id.FORBIDDEN_REPLACEMENT);
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