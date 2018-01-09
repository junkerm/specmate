export class Strings {
    public static truncate(str: string, length: number, ellipsis?: string): string {
        if (!str) {
            return undefined;
        }
        if (!ellipsis) {
            ellipsis = '...';
        }
        if (str.length > length + ellipsis.length) {
            return str.slice(0, length) + ellipsis;
        }
        return str;
    }

    public static contains(haystack: string, needle: string): boolean {
        if (!haystack) {
            return false;
        }
        return haystack.indexOf(needle) >= 0;
    }
}
