export class Id {
    public static fromName(name: string): string {
        name = Id.replaceAll(name, ' ', '');
        name = Id.replaceAll(name, '/', '-');
        return name;
    }

    private static replaceAll(str: string, search: string, replacement: string): string {
        while(str.indexOf(search) >= 0) {
            str = str.replace(search, replacement);
        }
        return str;
    }
}