export class Url {
    public static SEP = '/';

    public static parent(url: string): string {
        let parts: string[] = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        return Url.build(parts);
    }

    public static build(parts: string[]): string {
        return Url.clean(parts.join(Url.SEP));
    }

    public static parts(url: string): string[] {
        if (url) {
            return url.split(Url.SEP);
        }
        return null;
    }

    public static clean(url: string) {
        while (url.indexOf(Url.SEP + Url.SEP) >= 0) {
            url = url.replace(Url.SEP + Url.SEP, Url.SEP);
        }
        return url;
    }
}