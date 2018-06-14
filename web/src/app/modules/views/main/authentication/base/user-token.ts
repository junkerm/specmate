import { UserSession } from '../../../../../model/UserSession';

export class UserToken {
    constructor(public session: UserSession, public project: string) { }

    public static INVALID = new UserToken(undefined, 'INVALID');

    public static isInvalid(token: UserToken): boolean {
        return token === undefined ||
            token === UserToken.INVALID ||
            (token.session === UserToken.INVALID.session && token.project === UserToken.INVALID.project);
    }
}
