import { EOperation } from "./operations";
import { IContainer } from "../model/IContainer";
import { SpecmateDataService } from "./specmate-data.service";

export class Scheduler {

    private operations: { [key: string]: EOperation } = {};

    constructor(private dataService: SpecmateDataService) {

    }

    public commit(): Promise<void> {
        return this.chainCommits().then(() => {
            this.cleanOperations();
            this.clearCommits();
        });
    }

    public clearCommits(): void {
        this.operations = {};
    }

    private cleanOperations(): void {
        let clean: { [key: string]: EOperation } = {};
        for (let url in this.operations) {
            if (this.operations[url] && this.operations[url] !== EOperation.RESOLVED) {
                clean[url] = this.operations[url];
            }
        }
        this.operations = clean;
    }

    private chainCommits(): Promise<void> {
        let chain: Promise<void> = Promise.resolve();
        let commits: string[] = this.orderCommits();
        for (let i = 0; i < commits.length; i++) {
            let url: string = commits[i];
            chain = chain.then(() => {
                return this.dataService.getPromiseForUrl(url);
            });
        }
        return chain;
    }

    private orderCommits(): string[] {
        let commits: string[] = [];
        this.getCreateCommits().forEach((url: string) => commits.push(url));
        this.getUpdateCommits().forEach((url: string) => commits.push(url));
        this.getDeleteCommits().forEach((url: string) => commits.push(url));
        return commits;
    }

    private getCreateCommits(): string[] {
        return this.getCommitsOfOperation(EOperation.CREATE);
    }

    private getUpdateCommits(): string[] {
        return this.getCommitsOfOperation(EOperation.UPDATE);
    }

    private getDeleteCommits(): string[] {
        return this.getCommitsOfOperation(EOperation.DELETE);
    }

    private getCommitsOfOperation(operation: EOperation): string[] {
        let commits: string[] = [];
        for (let url in this.operations) {
            if (this.operations[url] === operation) {
                commits.push(url);
            }
        }
        return commits;
    }

    public schedule(url: string, operation: EOperation): void {
        let previousOperation: EOperation = this.operations[url];
        if (previousOperation === EOperation.CREATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            } else if (operation === EOperation.UPDATE) {
                operation = EOperation.CREATE;
            } else if (operation === EOperation.DELETE) {
                operation = EOperation.RESOLVED;
            }
        } else if (previousOperation === EOperation.UPDATE) {
            if (operation === EOperation.CREATE) {
                console.error('Trying to overwrite element ' + url);
            }
        } else if (previousOperation === EOperation.DELETE) {
            if (operation === EOperation.CREATE) {
                operation = EOperation.UPDATE;
            } else if (operation === EOperation.UPDATE) {
                console.error('Trying to update deleted element ' + url);
            } else if (operation === EOperation.DELETE) {
                console.error('Trying to delete deleted element ' + url);
            }
        }
        console.log("SCHEDULING " + EOperation[operation] + " FOR " + url);
        this.operations[url] = operation;
    }

    public resolve(url: string): void {
        this.operations[url] = EOperation.RESOLVED;
    }

    public isOperation(url: string, operation: EOperation): boolean {
        return this.operations[url] === operation;
    }
}