import { expect } from 'chai';
import 'mocha';

describe('Objects Specmate', () => {
    it('should return undefined if both objects are undefined', () => {
        const o1: IContainer = { ___nsuri: 'string', url: '', className: 'string',
            id : '1', name : 'weeds', description : 'this is weeds' };
        const o2: IContainer = undefined;
        // expect(Objects.changedFields(o1, o2)).to.be.undefined;
    });
});