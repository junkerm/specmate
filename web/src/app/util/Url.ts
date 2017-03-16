
import { Config } from "../config/config";

export class Url {
    public static SEP = '/';

    public static parent(url: string): string {
        let parts: string[] = url.split(Url.SEP);
        parts.splice(parts.length - 1, 1);
        return Url.build(parts);
    }

    public static build(parts: string[]): string {
        let joined: string = parts.join(Url.SEP);
        return Url.clean(joined);
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
        if(url.startsWith(Url.SEP)) {
            url = url.slice(1, url.length);
        }
        return url;
    }

    public static urlCreate(url: string): string {
        return Url.build([Config.URL_BASE, Url.parent(url), Config.URL_CONTENTS]);
    }

    public static urlDelete(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_DELETE]);
    }

    public static urlUpdate(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_ELEMENT]);
    }

    public static urlElement(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_ELEMENT]);
    }

    public static urlContents(url: string): string {
        return Url.build([Config.URL_BASE, url, Config.URL_CONTENTS]);
    }
}