export class Search {
    public static processSearchQuery(query: string): string {
        return  query.replace(/([^\(\):\s-+]+(-[^\(\):\s-+]+)*)\b(?!\:)/g, '$&*');
    }
}
