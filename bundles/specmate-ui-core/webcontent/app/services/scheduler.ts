import { EOperation } from "./operations";
import { IContainer } from '../model/IContainer';
import { SpecmateDataService } from "./specmate-data.service";
import { Objects } from "../util/Objects";
import { Command } from "./command";
import { Arrays } from "../util/Arrays";

export class Scheduler {

    private commands: Command[] = [];

    constructor(private dataService: SpecmateDataService) { }

    public commit(): Promise<void> {
        return this.chainCommits().then(() => {
            this.clearCommits();
        });
    }

    public undo(): void {
        let lastCommand: Command = this.commands.pop();
        
        if(!lastCommand) {
            console.log("OUT OF HISTORY");
            return;
        }
        
        if(!lastCommand.originalValue) {
            console.log("ERROR");
            console.log(lastCommand);
            return;
        }
        switch (lastCommand.operation) {
            case EOperation.CREATE:
                this.dataService.undoCreate(lastCommand.newValue.url);
                break;
            case EOperation.UPDATE:
                this.dataService.undoUpdate(lastCommand.originalValue);
                break;
            case EOperation.DELETE:
                this.dataService.undoDelete(lastCommand.originalValue);
                break;
            default:
                break;
        }
    }

    public clearCommits(): void {
        this.commands = [];
    }

    public get hasCommits(): boolean {
        return this.countOpenCommits > 0;
    }

    public get countOpenCommits(): number {
        return this.commands.filter((command: Command) => command.operation !== EOperation.INIT && command.operation !== EOperation.RESOLVED).length;
    }

    private chainCommits(): Promise<void> {
        let chain: Promise<void> = Promise.resolve();
        for(let i = 0; i < this.commands.length; i++) {
            chain = chain.then(() => {
                return this.dataService.getPromiseForCommand(this.commands[i]);
            });
        }
        return chain;
    }

    private getCommands(url: string): Command[] {
        return this.commands.filter((command: Command) => command.url === url);
    }

    private getLastStoredValue(url: string): IContainer {
        let commands: Command[] = this.getCommands(url);
        if(commands && commands.length > 0) {
            return commands[commands.length - 1].newValue;
        }
        return undefined;
    }

    private getFirstCommand(url: string): Command {
        return this.getCommands(url).filter((command: Command) => command.operation !== EOperation.INIT && command.operation !== EOperation.RESOLVED)[0];
    }

    public initElement(element: IContainer): void {
        let command: Command = new Command(element.url, element, element, EOperation.INIT);
    }

    public schedule(url: string, operation: EOperation, newValue: IContainer, originalValue?: IContainer): void {
        if(!originalValue) {
            originalValue = this.getLastStoredValue(url);
        }

        let command: Command = new Command(url, originalValue, newValue, operation);
        this.commands.push(command);
    }

    public isVirtualElement(url: string): boolean {
        return this.getCommands(url).some((command: Command) => command.operation === EOperation.CREATE);
    }

    public resolve(url: string): void {
        let firstCommand: Command = this.getFirstCommand(url);
        if(firstCommand) {
            firstCommand.resolve();
            return;
        }
        throw new Error('Tried to resolve ' + url + ', but no command was found.');
    }

}