export class Url {
    public static SEP = '/';
    
    // UNTESTED!!!
    public static parent(url: string): string {
        var parts: string[] = url.split(Url.SEP);
        parts = parts.splice(parts.length - 1);
        var parent: string = Url.build(parts);
        return parent.substr(0, parent.length - 1);
    }

    public static build(parts: string[]): string {
        return parts.join(Url.SEP);
    }

    public static parts(url: string): string[] {
        if(url) {
            return url.split(Url.SEP);
        }
        return null;
    }
}