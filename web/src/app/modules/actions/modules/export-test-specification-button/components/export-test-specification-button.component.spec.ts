import { ExportTestSpecificationButton } from './export-test-specification-button.component'

import { expect } from 'chai';
import 'mocha';

describe('Export test specification button', () => {
    it('should return a csv', () => {
        var file = new Blob(["line1", "line2"], {type: 'text/csv'});
        ExportTestSpecificationButton.exportAsCSV(file);
        expect(file).to.contain("line1", "line2");
    });
});