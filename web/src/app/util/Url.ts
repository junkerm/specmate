export class Url {
    public static parent(url: string): string {
        console.log(url);
        var parts: string[] = url.split("/");
        var parent = "";
        for(var i = 0; i < parts.length - 1; i++) {
            parent += parts[i] + "/";
        }
        console.log(parent);
        return parent.substr(0, parent.length - 1);
    }
}