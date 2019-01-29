import { Component, Input } from '@angular/core';

@Component({
    moduleId: module.id.toString(),
    selector: '[truncated-text]',
    templateUrl: 'truncated-text.component.svg',
    styleUrls: ['truncated-text.component.css']
})
export class TruncatedText {

    @Input()
    public position: {x: number, y: number};

    @Input()
    public selected: boolean;

    @Input()
    public valid: boolean;

    @Input()
    public text: string;

    @Input()
    public ellipsis = '...';

    @Input()
    public width: number;

    @Input()
    public height = 30;

    @Input()
    public centered = true;

    public lineHeight = 15;

    public get lines(): string[] {
        const width = this.width / 10;
        const numLines = this.height / this.lineHeight;
        const lineBags: string[][] = [];
        const sepBags: string[][] = [];
        const wordSep = /[\s,;\-]/g;
        const words = this.text.split(wordSep).map(word => this.truncate(word, width, this.ellipsis));
        let sepMatches = this.text.match(wordSep) || [''];
        let wordIndex = 0;
        let lineIndex = 0;
        while (wordIndex < words.length && lineIndex < numLines) {
            if (lineBags[lineIndex] === undefined) {
                lineBags[lineIndex] = [];
                sepBags[lineIndex] = [];
            }

            const currentWord = words[wordIndex];
            const contentLength = lineBags[lineIndex].join(' ').length;
            const contentLengthWithWord = contentLength + (contentLength === 0 ? 0 : 1) + currentWord.length;

            if (contentLengthWithWord <= width) {
                lineBags[lineIndex].push(currentWord);
                sepBags[lineIndex].push(sepMatches[wordIndex]);
                wordIndex++;
            } else {
                lineIndex++;
            }
        }

        const lines = lineBags.map((lineWords, lineIndex) => lineWords.map((word, wordIndex) => {
            const sep = sepBags[lineIndex][wordIndex];
            return (sep == undefined) ? word : word + sep;
        }).join(''));


        if (wordIndex < words.length) {
            lines[lines.length - 1] = this.truncate(lines[lines.length - 1], width, this.ellipsis, wordIndex < words.length);
        }
        return lines;
    }

    private truncate(word: string, width: number, ellipsis: string, force = false): string {
        if (force) {
            if (word.length + ellipsis.length <= width) {
                return word + ellipsis;
            } else {
                return word.substring(0, width - ellipsis.length) + ellipsis;
            }
        }
        if (word.length > width) {
            return word.substring(0, width - ellipsis.length) + ellipsis;
        }
        return word;
    }
}
