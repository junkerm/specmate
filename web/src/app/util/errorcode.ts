import { ErrorCode } from '../model/ErrorCode';
export class ECode {
    public static isClientError(ecode: string): boolean {
        return ErrorCode[ecode] < ErrorCode.INTERNALPROBLEM;
    }
}
