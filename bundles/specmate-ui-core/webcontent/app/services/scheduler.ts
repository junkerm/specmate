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

    public clearCommits(): void {
        this.commands = [];
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

        console.log("SCHEDULING " + EOperation[operation] + " FOR " + url);
        console.log(command);
    }

    public isVirtualElement(url: string): boolean {
        return this.getCommands(url).some((command: Command) => command.operation === EOperation.CREATE);
    }

    public resolve(url: string): void {
        console.log(this.commands);
        let firstCommand: Command = this.getFirstCommand(url);
        if(firstCommand) {
            firstCommand.resolve();
            return;
        }
        console.log(this.commands);
        throw new Error('Tried to resolve ' + url + ', but no command was found.');
    }

}