export class UserToken {
    constructor(public token: string, public project: string) { }

    public static INVALID = new UserToken('INVALID', 'INVALID');

    public static isInvalid(token: UserToken): boolean {
        return token === undefined ||
            token === UserToken.INVALID ||
            (token.token === UserToken.INVALID.token && token.project === UserToken.INVALID.project);
    }
}
