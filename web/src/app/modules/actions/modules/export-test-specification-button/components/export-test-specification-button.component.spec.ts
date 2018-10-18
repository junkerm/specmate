import { ExportTestSpecificationButton } from './export-test-specification-button.component';

import { expect } from 'chai';
import 'mocha';
import { JSDOM } from 'jsdom';

describe('Export test specification button', () => {
    it('should return a csv', () => {
        const jsdom      = new JSDOM('<!DOCTYPE HTML><html><body></body></html>');
        let blob = new Blob(['helloWorld\n'], { type: 'text/csv' });
        let url = window.URL.createObjectURL(blob);
        if (navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, 'Book.csv');
        } else {
            let a = document.createElement('a');
            a.href = url;
            a.download = 'Book.csv';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
        }
        window.URL.revokeObjectURL(url);
    });
});
